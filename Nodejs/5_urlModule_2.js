
//here we will create a demo fileserver
//that opens a user-requested file and returns content

var http = require('http');
var url = require('url');
var fs = require('fs');

http.createServer(function (req, res) {
  //req object is contains all of the metadata concerning the http request
  var q = url.parse(req.url, true);
  //the url property returns the data concerning only the url provided

//console.log(q);
//when consoling, this is the object returned for the following path:
//    http://localhost:8080/data
/*
  Url {
    protocol: null,
    slashes: null,
    auth: null,
    host: null,
    port: null,
    hostname: null,
    hash: null,
    search: null,
    query: [Object: null prototype] {},
    pathname: '/data',
    path: '/data',
    href: '/data' }
*/

  var filename = "." + q.pathname;
  //we are going to prepend a . to search for a file
  //with a file path that we expect to be in the current directory

  //we use the fs module to read the file requested
  //    http://localhost:8080/winter.html
  //should return the winter.html file that we have in our local machine
  fs.readFile(filename, function(err, data) {
    if (err) {
      res.writeHead(404, {'Content-Type': 'text/html'});

      //if the requested file does not exist, a 404 error is returned
      //so then return am error message
      return res.end("404 Not Found");
    }
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.write(data);
    return res.end();
  });
}).listen(8080);
