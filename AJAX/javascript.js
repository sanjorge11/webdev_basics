
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

    xhttpObject.onreadystatechange = function() {
      if (this.readyState == 4 && this.status == 200) {   //'this' is xhttpObject
        document.getElementById("content").innerHTML = this.responseText;
          //the responseText will be the HTML content of the txt file you requested
      }
    }

    //syntax: .open creates a request -- .send essentially sends the request to the server
    //        XMLHttpRequest.open(method, url) or
    //        XMLHttpRequest.open(method, url, async) or
    //        XMLHttpRequest.open(method, url, async, user) or
    //        XMLHttpRequest.open(method, url, async, user, password)
    xhttpObject.open("GET", "ajax_info.txt", true);
    xhttpObject.send();

}


function requestGET1() {

  var xhttp = new XMLHttpRequest();

  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("get-example-1").innerHTML = this.responseText;
    }
  };

  //need to create a back end to handle request and send custom response
  xhttp.open("GET", "demo_get.html?fname=Henry&lname=Ford", true);
  xhttp.send();

}
//
// var url_string = window.location.href;
//   console.log(url_string);
// var url = new URL(url_string);
//   console.log(url);
//var c = url.searchParams.get("c");
