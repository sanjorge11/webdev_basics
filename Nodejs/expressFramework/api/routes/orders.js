
var express = require('express');
var router = express.Router();
var mongoose = require('mongoose');

var Order = require('../models/order'); 
var Product = require('../models/product'); 

router.get('/', function(req, res, next) {
  Order.find()
  .select('product quantity _id')
  .populate('product', 'name') //used for gathering more external info, we use product property 
  .exec()                   //which is the only one that points to another model, you can select specific properties of that model, like 'name'
  .then(function(docs) { 
    res.status(200).json({
      count:docs.length,
      orders: docs.map(function(doc) {
        return {
          _id: doc._id, 
          product: doc.product, 
          quantity: doc.quantity, 
          request: {
            type: 'GET',
            url: 'http://localhost:8080/orders/' + doc._id
          }
        }
      })
    });
  })
  .catch(function(err) { 
    res.status(500).json({
      error: err
    });
  });
});

router.post('/', function(req, res, next) {
  Product.findById(req.body.productId)
  .then(function(product) { 
    if(!product) { 
      return res.status(404).json({
        message: 'Product not found'
      });
    }


    var order = new Order({
      _id: mongoose.Types.ObjectId(),
      quantity: req.body.quantity, 
      product: req.body.productId
    }); 
  
    return order.save();
    //.exec() save mongoose method gives you a real promise, no need for exec like in other queries
  })
  .then(function(result) { 
    console.log(result); 
    res.status(201).json({
      message: 'Order created successfully', 
      createdOrder: {
        _id: result._id,
        product: result.product, 
        quantity: result.quantity
      },
      request: { 
        type: 'GET', 
        url: 'http://localhost:8080/orders/' + result._id
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

router.get('/:orderId', function(req, res, next) {
  Order.findById(req.params.orderId)
  .populate('product')
  .exec()
  .then(function(order) { 
    if(!order) { 
      return res.status(404).json({
        message: 'Order not found'
      });
    }

    res.status(200).json({
      order: order,
      request: {
        type: 'GET',
        url: 'http://localhost:8080/orders'
      }
    });
  })
  .catch(function(err) { 
    res.status(500).json({
      error: err
    });
  }); 
});

router.delete('/:orderId', function(req, res, next) {
  Order.remove({_id: req.params.orderId})
  .exec()
  .then(function(result) { 
    res.status(200).json({
      message: 'Order deleted',
      request: {
        type: 'POST', 
        url: 'http://localhost:8080/orders',
        body: { productId: 'ID', quantity: 'Number' } 
      }
    });
  })
  .catch(function(err) { 
    res.status(500).json({
      error: err
    });
  });
});


module.exports = router;
