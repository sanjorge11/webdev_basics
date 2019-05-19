
//Nodejs is event-driven, every action
//on a computer is an event, like when a
//connection is made or a file is opened

//objects in Nodejs can fire events,
//like the readStream object first events
//when opening and closing a file
var fs = require('fs');
//this is another fs method, like readFile()
var rs = fs.createReadStream('./demofile.txt');

//here we use the method .on() which is part of the
//EventEmitter class
//this is an example where we assign an event handler,
//the given anonymous function, to the event 'open'
//the event handler will console.log a message when
//the event 'open' is invoked
rs.on('open', function () {
  console.log('The file is open');
});


//the events module allows you to create, fire, and listen for
//your own events
var events = require('events');

//you can assign event handlers to your own
//events with the EventEmitter object
var eventEmitter = new events.EventEmitter();

//Create an event handler:
var myEventHandler = function () {
  console.log('I hear a scream!');
}

//Assign the event handler to an event:
eventEmitter.on('scream', myEventHandler);

//to fire an event, use the emit() method...
//Fire the 'scream' event:
eventEmitter.emit('scream');
