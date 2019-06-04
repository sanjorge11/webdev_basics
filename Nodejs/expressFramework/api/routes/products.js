
var express = require('express');
var router = express.Router();
var mongoose = require('mongoose'); 

var Product = require('../models/product'); 

router.get('/', function(req, res, next) {
  Product.find()
  .select('name price _id') //select which fields to select, ignore the internal _v id
  .exec() //execute, returning a promise
  .then(function(docs) { //the promise
    var response = {
      count: docs.length,
      products: docs.map(function(doc) {  //convert object into a different format object
          return { 
            name: doc.name, 
            price: doc.price, 
            _id: doc._id, 
            request: { 
              type: 'GET', 
              url: 'http://localhost:8080/products/' + doc._id
            }
          }
      })
    };

    res.status(200).json(response);
  })   
  .catch(function(err) { 
    console.log(err);
    res.status(500).json({
      error: err
    }); 
  });
});

router.post('/', function(req, res, next) {
  //body property given by body-parser
  //these properties are expected to be defined this 
  //way, according to our own design, this is what 
  //should be documented if released as a public API
  /*var product = { 
    name: req.body.name, 
    price: req.body.price
  }; */

  //create instance of model we defined 
  var product = new Product({
    _id: new mongoose.Types.ObjectId(), //create new unique mongoose id
    name: req.body.name, 
    price: req.body.price
  }); 

  //save is a mongoose function to save into DB
  //we chain a callback function to it to handle 
  //success vs failure operations 
  product.save().then(function(result) {
    console.log(result);
    //status 201 -- successfully created resource
    res.status(201).json({
      message: 'Created new product successfully',
      createdProduct: {
        name: result.name, 
        price: result.price, 
        _id: result._id, 
        request: { 
          type: 'GET', 
          url: 'http://localhost:8080/products/' + result._id
        }
      }
    });
  }).catch(function(err) {  //we chain catch to catch any errors that may arise 
    console.log(err);
    res.status(500).json({
      error: err
    });
  });
});

//:variableName -- url-encoded variable to be extracted
router.get('/:productId', function(req, res, next) {
  //req.params holds all url parameters from request
  //in this case the object would be like: { productId: '123' }
  var id = req.params.productId;
  
  //findById is a query method provided by mongoose
  //note that we send status back until we are within 
  //the execution block, placing after may cause it to execute 
  //before it was appropriately needed due to asynchronousness
  Product.findById(id)
    .select('name price _id')
    .exec()
    .then(function(doc) {
      console.log("From database", doc);
      if (doc) {  //if object is not null 
        res.status(200).json({
          product: doc, 
          request: { 
            type: 'GET',
            description: 'Get all products',
            url: 'http://localhost:8080/products'
          }
        }); 
      } else { 
        res.status(404).json({
          message: "No valid entry found for provided ID"
        });
      }
    })
    .catch(function(err) {
      console.log(err);
      res.status(500).json({error: err});
    });
});

router.patch('/:productId', function(req, res, next) {
  var id = req.params.productId;
  /*var updateOps = {}; //empty javascript object 
  for(var ops of req.body) {  //we expect req.body to be array of operations we want to perform
    updateOps[ops.propName] = ops.value;  //we will set up requests so that we can extract this later
  }
  //use $set: updateOps below
  */

  //cleaner solution
  var props = req.body;

  //we dynamically create an object that has all properties we want updated
  //$set is a propery understood by mongoose to know that the following properties
  //are to be updated with the patch request
  Product.update({ _id: id }, { $set: props })
  .exec()
  .then(function(result) { 
    console.log(result); 
    res.status(200).json({
      message: 'Product updated',
      request: { 
        type: 'GET', 
        url: 'http://localhost:8080/products/' + id
      }
    }); 
  })
  .catch(function(err) { 
    console.log(err);
    res.status(500).json({
      error: err
    });
  }); 
 
});

router.delete('/:productId', function(req, res, next) {
  //req.params holds all url parameters from request
  //in this case the object would be like: { productId: '123' }
  var id = req.params.productId;
  Product.remove({_id: id})  //delete all with this criteria, id is unique so delete one
  .exec()
  .then(function(result){
    res.status(200).json({
      message: 'Product deleted',
      request: { 
        type: 'POST',   //info to user to instruct how to create new record if needed
        url: 'http://localhost:8080/products',
        body: { name: 'String', price: 'Number' }
      }
    });
  })
  .catch(function(err) { 
    console.log(err);
    res.status(500).json({
      error: err  //this error object is gotten from mongoose
    });
  });
});

//this is to allow for this file to be imported elsewhere
module.exports = router;
