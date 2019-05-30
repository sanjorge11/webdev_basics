
var express = require('express');
var router = express.Router();

router.get('/', function(req, res, next) {
  res.status(200).json({
    message: 'Orders were fetched'
  });
});

router.post('/', function(req, res, next) {
  res.status(201).json({
    message: 'Order was created'
  });
});

router.get('/:orderId', function(req, res, next) {
  res.status(200).json({
    message: 'Order details',
    orderId: req.params.orderId
  });
});

router.delete('/:orderId', function(req, res, next) {
  res.status(200).json({
    message: 'Order deleted',
    orderId: req.params.orderId
  });
});


module.exports = router;
