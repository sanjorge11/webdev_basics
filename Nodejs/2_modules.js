//modules can be considered to be the same as JavaScript libraries
//they are a set of functions you want to include in your app

//nodejs has a set of built-in modules that you can use,
//or you could download others
//list of built-in modules can be found here:
        //https://www.w3schools.com/nodejs/ref_modules.asp

//to include a module, use the require function with the name of the module
var http = require('http');
    //http is the module used, it makes Node.js act as an HTTP server

//once the module is included and assigned to a variable, it can be used

//you can also create your own module and include it, I created
//a module in myfirstmodule.js

//we use ./ to locate the module, this path indicates that the
//module is located in the same folder as this nodejs file
var dt = require('./myfirstmodule');

http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/html'});
  res.write("The date and time are currently: " + dt.myDateTime());
  res.end();
}).listen(8080);

//SHOULD FIND RESOURCES THAT EXPLAINS res.write, res.end, etc...
