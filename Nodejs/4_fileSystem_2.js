
//Create files example:

var fs = require('fs');

//appendFile adds content to an existing file
//or it also creates a new file with the specified content
//parameters: filePath, data, callback function
fs.appendFile('mynewfile1.txt', 'Hello content!', function (err) {
  if (err) throw err;
  console.log('Saved!');
});

//sidenote: callbacks are functions that are to be executed after
//another function has finished executing, hence the name callback
//JavaScript does not wait for a code's response to execute the next

//callbacks are a way to make sure certain code doesn’t execute
//until other code has already finished execution


//sidenote about callbacks vs promises: callbacks are a way to
//execute code once the asynchronous call is finished, but a promise
// is an object that wraps an asynchronous operation and notifies
//when it's done -- promise objects provide .then() to execute
//when a successful result is returned, and .catch() when something
//went wrong

//promise example:
/*
  someAsyncOperation(someParams)
  .then(function(result){
      // Do something with the result
  })
  .catch(function(error){
      // Handle error
  });
*/


//the second parameter of the open() method is a flag
//'w' means that eh file is opened for writing,
//if it does not exist, then an empty file is created
fs.open('mynewfile2.txt', 'w', function (err, file) {
  if (err) throw err;
  console.log('Saved!');
});


//writeFile() method replaces the file and the contnent rather than
//appending to it, if the file deos not exist, then it creates a new file
fs.writeFile('mynewfile3.txt', 'Hello content!', function (err) {
  if (err) throw err;
  console.log('Saved!');
});
