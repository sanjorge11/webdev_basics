
//Update files example

//the same methods used for creating a new file
//are used for updating a file, here we assume the
//file has already been created, so excuting it does
//not create a new file but updates it instead


var fs = require('fs');

//append content to file
fs.appendFile('mynewfile1.txt', ' This is my text.', function (err) {
  if (err) throw err;
  console.log('Updated!');
});


//completely replace file and its contents
fs.writeFile('mynewfile3.txt', 'This is my text', function (err) {
  if (err) throw err;
  console.log('Replaced!');
});
