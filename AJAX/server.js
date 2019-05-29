var http = require('http');
var fs = require('fs');
var url = require('url');
var querystring = require('querystring');   //for parsing URL-encoded query strings


var fileStream;
/* Creating server */
/* note that the function parameter constantly listens for requests
   and handles it in the logic we wrote */
http.createServer(function (request, response) {
  var q = url.parse(request.url, true);

  //routes to all these files must be defined -- if index.html uses
  //javascript.js, then the route to that files must be defined here so that
  //client is able to retrieve and download the javascript code onto your browser
  //from the server that hosts it
  if (q.pathname == '/' || q.pathname == '/index.html') {
      fileStream = fs.createReadStream('./index.html');
      fileStream.pipe(response);
  } else if (q.pathname == '/ajax_info.txt') {
      fileStream = fs.createReadStream('./ajax_info.txt');
      fileStream.pipe(response);
  } else if (q.pathname == '/javascript.js') {
      fileStream = fs.createReadStream('./javascript.js');
      fileStream.pipe(response);
  } else if (q.pathname == '/records.json') {
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
  } else if (q.pathname == '/demo_get1') {
    var time = new Date();
    response.write('This content was requested using the GET method.\n\n');
    response.write('Requested at: ');

    var dateStr = time.toLocaleDateString();
    var timeStr = time.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true });

    response.write(dateStr + ' ' + timeStr);

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
  } else if (q.pathname == '/demo_post1') {
      var time = new Date();
      response.write('This content was requested using the POST method.\n\n');
      response.write('Requested at: ');

      var dateStr = time.toLocaleDateString();
      var timeStr = time.toLocaleString('en-US', { hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: true });

      response.write(dateStr + ' ' + timeStr);

      return response.end();
  } else if (q.pathname == '/demo_post2') {
    var firstName = '';
    var lastName = '';
    let body = '';

    // very important to handle errors
    request.on('error', (err) => {
        if(err) {
            response.writeHead(500, {'Content-Type': 'text/html'});
            response.write('An error occurred');
            return response.end();    //note that 'return' is used to explicitly stop control flow
            //could prevent execution of any other code after this point, for example
            console.log('error');   //this console will run without the 'return'
        }
    });

    // read chunks of POST data as they arrive independently
    //    note: Transfer-Encoding: chunked in Request Header
    request.on('data', chunk => {
        body += chunk.toString();
    });

    // when complete POST data is received
    request.on('end', () => {
        // use parse() method
        body = querystring.parse(body);

        //expected JSON format --> { fname: 'Henry', lname: 'Ford' }
        firstName = body.fname;
        lastName = body.lname;

        //reponse to send back to client
        response.write('Hello ' + firstName + ' ' + lastName);

        //ready to end and send a response to client
        return response.end();
    });

    //this console message may execute well before the response.on('end')
    //callback function -- the POST request body information comes in chunks
    //asynchonously over time
    //    console.log('here');

  } else if (q.pathname == '/cd_catalog.json'){
      fileStream = fs.createReadStream('./cd_catalog.json');
      fileStream.pipe(response);
  } else {
      return response.end();
  }
}).listen(8080);
console.log('server running');
