
//NPM is node package manager, it is
//used for downloading the many free
//packages thast are available for nodejs


//a package in Nodejs contains all the files
//you need for a module

//a module, as we know, are JavaScript libraries
//that you can include in your project

//this is example of to install a package
//and including it in your project

//Let's assume npm is installed
// 1. (in terminal) npm install upper-case
//    we installed the upper-case package
//    which converts all text into upper case

// 2. npm creates a directory called node_modules
//    all of the installed packages are placed there
//    the node_modules directory must be in the same
//    directory as the project, if the current
//    directory deos not have one, running npm install will
//    make one 


// 3. we include the package once installed
var uc = require('upper-case');

//here we include another package: http
var http = require('http');

http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/html'});
    //here we use the upper-case package
  res.write(uc("Hello World!"));
  res.end();
}).listen(8080);

// 4. we run the application with: node filename
// the project is seen @ http://localhost:8080
