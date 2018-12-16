// AngularJS modules define AngularJS applications.
// AngularJS controllers control AngularJS applications.

// The "myApp" parameter refers to an HTML element in which the
//application will run. Now you can add controllers, directives,
//filters, and more, to your Angular app
var app = angular.module('myApp', []);
//this is compiled AFTER the library has been loaded in script tag within head


//note: When naming a directive, you must use a camel case name,
//injectElement, but when invoking it, you must use - separated name,
//inject-element
app.directive("injectElement", function() {
    return {
        template : "<h1> injected from element </h1>"
    };
});

app.directive("injectComment", function() {
  return {
      restrict : "M",
      replace : true,
      template: "<h1> injected from comment </h1>"
  };
});
