
//url module is useful for splitting up a
//web address into readable parts
var url = require('url');

//the url.parse() method will return a URL object
// with each part of the address ass properties


var adr = 'http://localhost:8080/default.htm?year=2017&month=february';
var q = url.parse(adr, true);
//second parameter is: parseQueryString<boolean>
//which determines if you want a URL object to be returned -- read
//comments in 3_httpModule_2 for more info

console.log(q.host); //returns 'localhost:8080'
console.log(q.pathname); //returns '/default.htm'
console.log(q.search); //returns '?year=2017&month=february'

var qdata = q.query; //returns an object: { year: 2017, month: 'february' }
console.log(qdata.month); //returns 'february'
