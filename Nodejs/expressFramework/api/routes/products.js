
var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {
  res.status(200).json({
    message: 'Handling GET requests to /products'
  });
});

router.post('/', function(req, res, next) {
  //body property given by body-parser
  //these properties are expected to be defined this 
  //way, according to our own design, this is what 
  //should be documented if released as a public API
  var product = { 
    name: req.body.name, 
    price: req.body.price
  };

  //status 201 -- successfully created resource
  res.status(201).json({
    message: 'Handling POST requests to /products',
    createdProduct: product 
  });
});

//:variableName -- url-encoded variable to be extracted
router.get('/:productId', function(req, res, next) {
  //req.params holds all url parameters from request
  //in this case the object would be like: { productId: '123' }
  var id = req.params.productId;
  res.status(200).json({
    message: 'You passed ID: ' + id
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
  res.status(200).json({
    message: 'Deleted product'
  });
});

//this is to allow for this file to be imported elsewhere
module.exports = router;
