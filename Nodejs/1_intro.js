//note that this tutorial is meant to be tested on your local server

//To begin, we copied this code to run our first nodejs server
//we will break down the concepts as we go along
var http = require('http');

http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/html'});
  res.end('Hello World!');
}).listen(8080);

console.log('server is running on port 8080');
//on browser, check localhost:8080 for server
