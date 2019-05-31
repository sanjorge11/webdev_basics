
var express = require('express');
var router = express.Router();
var mongoose = require('mongoose'); 

var Product = require('../models/product'); 

router.get('/', function(req, res, next) {
  Product.find()
  .exec() //execute, returning a promise
  .then(function(docs) { //the promise
    console.log(docs);
    //if(docs.length >= 0) { 
      res.status(200).json(docs);
    // } else { 
    //   res.status(404).json({
    //     message: 'No entries found'
    //   });
    // }
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
      message: 'Handling POST requests to /products',
      createdProduct: result 
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
    .exec()
    .then(function(doc) {
      console.log("From database", doc);
      if (doc) {  //if object is not null 
        res.status(200).json(doc); 
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
  //req.params holds all url parameters from request
  //in this case the object would be like: { productId: '123' }
  var id = req.params.productId;
  res.status(200).json({
    message: 'Updated product'
  });
});

router.delete('/:productId', function(req, res, next) {
  //req.params holds all url parameters from request
  //in this case the object would be like: { productId: '123' }
  var id = req.params.productId;
  Product.remove({_id: id})  //delete all with this criteria, id is unique so delete one
  .exec()
  .then(function(result){
    res.status(200).json(result);
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
