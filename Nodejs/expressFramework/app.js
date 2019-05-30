

var express = require('express');
var app = express();
var productRoutes = require('./api/routes/products');
var orderRoutes = require('./api/routes/orders');

app.use('/products', productRoutes);
app.use('/orders', orderRoutes);

module.exports = app;


/*
var express = require('express');
var app = express();

app.use(function(req, res, next) {
  //set status code to 200 and send json response
  //this json object will be stringified before sent
  res.status(200).json({
    message: 'It works!'
  });
});

module.exports = app;
*/
