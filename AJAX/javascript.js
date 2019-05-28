
function loadDoc() {
    var xhttpObject = new XMLHttpRequest();

    /* Some properties of XMLHttpRequest object:
      - readyState -- holds the status of the XMLHttpRequest
            0: request not initialized
            1: server connection established
            2: request received
            3: processing request
            4: request finished and response is ready
      - onreadystatechange -- defines a function to be executed when the 'readyState' changes
      - status and statusText -- holds the status of the XMLHttpRequest object
            200: "OK"
            403: "Forbidden"
            404: "Page not found"

        note: the onreadystatechange function is called every time the readyState changes
        note: when readyState is 4 and status is 200, the response is ready
    */

    //execute when request is finished and status is OK
    xhttpObject.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {   //'this' is xhttpObject
        document.getElementById("content").innerHTML = this.responseText;
          //the responseText will be the HTML content of the txt file you requested
      }
    }

    //syntax: .open creates a request -- .send essentially sends the request to the server
    //        XMLHttpRequest.open(HTTP method, url) or
    //        XMLHttpRequest.open(HTTP method, url, async) or
    //        XMLHttpRequest.open(HTTP method, url, async, user) or
    //        XMLHttpRequest.open(HTTP method, url, async, user, password)
    xhttpObject.open("GET", "ajax_info.txt", true);
    xhttpObject.send();
      //the url can be a HTML resource or a scripting file on the server
      //setting the async boolean to false would mean that the control flow
      //would not be asynchonously executed so it would not make sense to
      //make a function that depends on .onreadystatechange property, so
      //the following would executed synchonously (wait for server response,
      //and then continue the control flow)
    /*
    xhttp.open("GET", "ajax_info.txt", false);
    xhttp.send();
    document.getElementById("demo").innerHTML = xhttp.responseText; */
}


function get_request1_1() {
  var xhttp = new XMLHttpRequest();

  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("get-example-1-1").innerHTML = this.responseText;
    }
  };

  xhttp.open("GET", "demo_get1", true);
  xhttp.send();

}


function get_request1_2() {
  var xhttp = new XMLHttpRequest();

  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("get-example-1-2").innerHTML = this.responseText;
    }
  };

  //server does not handle the URL parameter, but the unique URL route used
  //makes it so that the local machine does not use cached data and makes
  //a new GET request every time
  xhttp.open("GET", "demo_get1?t=" + Math.random(), true);
  xhttp.send();

}


function get_request2() {

  var xhttp = new XMLHttpRequest();

  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("get-example-2").innerHTML = this.responseText;
    }
  };

  xhttp.open("GET", "demo_get2?fname=Henry&lname=Ford", true);
  xhttp.send();

}


function post_request1() {
  var xhttp = new XMLHttpRequest();

  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("post-example-1").innerHTML = this.responseText;
    }
  };

  xhttp.open("POST", "demo_post1", true);
  xhttp.send();

}


function post_request2() {
  //note: formidable module could also be used to parse IncomingForm object 
  var xhttp = new XMLHttpRequest();

  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("post-example-2").innerHTML = this.responseText;
    }
  };

  //note: if you are to send JSON data to the server, you must preprocess your
  //JSON data into a strings before sending it with the HTTP request
  //for example: var json = JSON.stringify({name: "Jorge", age: 22});
  //             xhttp.setRequestHeader("Content-type", "application/json");
  //On the server side, you convert JavaScript objects to JSON strings to
  //recieve in the client-side, and use JSON parse to object JavaScript object

  xhttp.open("POST", "demo_post2", true);
  xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
  xhttp.send("fname=Henry&lname=Ford");

}
