
var http = require('http');
var url = require('url');

http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/html'});

  //the querystring module's the .parse method's
  //parameters are: url, boolean which, if true will
  //set the .query property to an object that is returned by
  //the querystring module's .parse() method
  var q = url.parse(req.url, true)
  console.log(q);

  q = q.query;
  var txt = q.year + " " + q.month;
  res.end(txt);
}).listen(8080);

//sample querystring --> ?year=2019&month=January
//second parameter is boolean to parse querystring, it's true
//so the object returned will have .query property set to it

//this is what the object returned by .parse() looks like
/*Url {
  protocol: null,
  slashes: null,
  auth: null,
  host: null,
  port: null,
  hostname: null,
  hash: null,
  search: '?year=2019&month=January',
  query: [Object: null prototype] { year: '2019', month: 'January' },
  pathname: '/',
  path: '/?year=2019&month=January',
  href: '/?year=2019&month=January' }
*/

//in url after 8080, write: /?year=2017&month=July
//this code will parse the url parameters and send a
//response --> 2017 July
