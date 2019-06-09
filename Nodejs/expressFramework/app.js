

var express = require('express');
var app = express();
var morgan = require('morgan');
var bodyParser = require('body-parser');  //module for parsing url-encoded or json data from request body
var mongoose = require('mongoose'); //module for easily handling actions with MongoDB

var productRoutes = require('./api/routes/products');
var orderRoutes = require('./api/routes/orders');

//dynamically get password
mongoose.connect('mongodb+srv://testUser:'+process.env.MONGO_ATLAS_PW+'@rest-api-testdb-vhmkz.mongodb.net/test?retryWrites=true&w=majority',
{ useNewUrlParser: true } //instructed to be used by mongoose logs in terminal
/*,
{ //set this to use MongoDB client 
  useMongoClient: true
}*/);

//use morgan module with 'dev' format -- morgan is a module used for logging your project
app.use(morgan('dev'));

//either a route needed to be defined to recieve a request and return an image,
//or we make the uploads folder public by making it static, we add 
//'/uploads' to sepcify the foler path and apply static property to it
//omitting '/uploads' would mean that /uploads path is not needed and direct path 
//will work to get image, but we allow to add the extra path to have organization
app.use('/uploads', express.static('uploads')); 

app.use(bodyParser.urlencoded({extended: false}));  //setting for reading urlencoded body
app.use(bodyParser.json()); 

//adjust server response headers to let browser
//know particular HTTP methods, HTTP headers, Cross-Origin Access
app.use(function(req, res, next) { 
  res.header("Access-Control-Allow-Origin", "*"); //allow access from any origin
  res.header( //restrict which headers you can append to requests
    "Access-Control-Allow-Headers",
    "Origin, X-Requested-With, Content-Type, Accept, Authorization"
  ); 

  //browser sends an OPTIONS request first to affirm
  //if HTTP method used is supported, here we intercept 
  //that call to define which HTTP methods out API supports
  if(req.method === 'OPTIONS') { 
    res.header(
      "Access-Control-Allow-Methods",
      "PUT, POST, PATCH, DELETE, GET"
      );
      //empty response, this call was just to find which 
      //HTTP methods are supported 
      return res.status(200).json({}); 
  }
  //if not calling OPTIONS HTTP method, then continue
  //to the next routing calls we defined underneath
  next();  
}); 

app.use('/products', productRoutes);
app.use('/orders', orderRoutes);

//the catch-all for invalid route requests -- assuming none from above were caught
app.use(function(req, res, next) {
  var error = new Error('Not found');  //error object is available by default without any imports
  error.status = 404; //set status code to 404
  next(error);  //this forwards the Error object we created rather than the default error HTML page
});

//the error catch for invalid routes above is routed to here
//and the other errors from elsewhere will be send here as well, e.g. database errors
app.use(function(error, req, res, next) {
//if error.status is undefined, then choose 500
  res.status(error.status || 50);  //either the error object code, or 500 we assign (generic error code)
  res.json({
    error: {
      message: error.message
    }
  });
});

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
