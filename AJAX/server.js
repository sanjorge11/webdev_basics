var http = require('http');
var fs = require('fs');
var url = require('url');

var fileStream;
/* Creating server */
/* note that the function parameter constantly listens for requests
   and handles it in the logic we wrote */
http.createServer(function (request, response) {
  var q = url.parse(request.url, true);

  if (q.pathname == '/' || q.pathname == '/index.html') {
      fileStream = fs.createReadStream('./index.html');
      fileStream.pipe(response);
  } else if (q.pathname == '/ajax_info.txt') {
      fileStream = fs.createReadStream('./ajax_info.txt');
      fileStream.pipe(response);
  }  else if (q.pathname == '/javascript.js') {
      fileStream = fs.createReadStream('./javascript.js');
      fileStream.pipe(response);
  }  else if (q.pathname == '/records.json') {
      fileStream = fs.createReadStream('./records.json');
      fileStream.pipe(response);
  } else if (q.pathname == '/query') {    //if you want to query a name in a JSON file
      if (q.search != null && q.search !== '?') {
        var recordsArr = require('./records.json').records;
        var queryObj = q.query;

        for(var i=0; i<recordsArr.length; i++) {
          if (recordsArr[i].fname == queryObj.fname && recordsArr[i].lname == queryObj.lname) {
              response.write('Hello ' + queryObj.fname + ' ' + queryObj.lname);
          }
        }
      }
      return response.end();
  } else if (q.pathname == '/demo_get2') {
      var firstName = '';
      var lastName = '';
      var queryObj = q.query;

      if (q.search != null && q.search !== '?') {
        firstName = queryObj.fname;
        lastName = queryObj.lname;
      }

      response.write('Hello ' + firstName + ' ' + lastName);
      return response.end();
  } else {
      return response.end();
  }
}).listen(8080);
console.log('server running');
