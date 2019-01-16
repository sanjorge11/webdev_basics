
//As previously mentioned the built-in HTTP module,
//allows Node.js to transfer data over HTTP

//we first include the module
var http = require('http');

//we use the 'createServer' method to create an HTTP server:
http.createServer(function (req, res) {
  //we specify the content-type in the HTTP header, to have an html response
  //first argument is 200, which is the status code OK, and the
  //second parameter is the object containing the response headers
  res.writeHead(200, {'Content-Type': 'text/html'});
  res.write(req.url); //write a response to the client
                      //the url property of the request object holds the
                      //part of the url that comes after the domain name

  res.end(); //end the response, note you can write to response body here too 
}).listen(8080); //the server object listens on port 8080
