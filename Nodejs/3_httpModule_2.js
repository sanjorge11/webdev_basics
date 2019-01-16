
var http = require('http');
var url = require('url');

http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/html'});

  //the querystring module's the .parse method's
  //parameters are: url, boolean which, if true will
  //set the .query property to an object that is returned by
  //the querystring module's .parse() method
  var q = url.parse(req.url, true).query;
  var txt = q.year + " " + q.month;
  res.end(txt);
}).listen(8080);


//in url after 8080, write: /?year=2017&month=July
//this code will parse the url parameters and send a
//response --> 2017 July
