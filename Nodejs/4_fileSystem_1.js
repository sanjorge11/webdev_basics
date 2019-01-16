
//you can use nodejs as a File Server

//first use the File System module
var fs = require('fs');

//A File System module can be used for:
  //Read files
  //Create files
  //Update files
  //Delete files
  //Rename files

//Read files example:
var http = require('http');

http.createServer(function (req, res) {
  //use the fs.readFile() method to read files on your computer and return the content
  //note that we assume that demofile1.html is in same file as this nodejs app
  fs.readFile('demofile1.html', function(err, data) {
    res.writeHead(200, {'Content-Type': 'text/html'});  //add header to return HTML
    res.write(data);
    res.end();
  });
}).listen(8080);
