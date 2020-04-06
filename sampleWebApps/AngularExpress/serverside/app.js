var express = require('express');
var app = express();
var bodyParser = require('body-parser');  //module for parsing url-encoded or json data from request body
var morgan = require('morgan'); //module used for logging your project

var facultyRoutes = require('./api/routes/faculty');

//use morgan module with 'dev' format 
app.use(morgan('dev'));
app.use(bodyParser.urlencoded({extended: false}));  //setting for reading urlencoded body
app.use(bodyParser.json()); 

//make folder to frontend content static
app.use(express.static('../frontend/dist/frontend')); 

/*
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
});  */

//routes
app.use('/', facultyRoutes);


//the catch-all for invalid route requests -- assuming none from above were caught
app.use(function(req, res, next) {
  var error = new Error('Not found'); 
  error.status = 404; 
  next(error);  //this forwards the Error object to the catch-all handler
});

//the catch-all handler
app.use(function(error, req, res, next) {
//if error.status is undefined, then choose 500
  res.status(error.status || 500);  
  res.json({
    error: {
      message: error.message
    }
  });
});

module.exports = app;