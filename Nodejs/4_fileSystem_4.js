
//Delete files example

//we use fs.unlink() to delete a specified file

var fs = require('fs');

fs.unlink('mynewfile2.txt', function (err) {
  if (err) throw err;
  console.log('File deleted!');
});


//we can also rename files
fs.rename('mynewfile1.txt', 'myrenamedfile.txt', function (err) {
  if (err) throw err;
  console.log('File Renamed!');
});


//you can also upload files in Nodejs, you can read
//online docs for more info on that
