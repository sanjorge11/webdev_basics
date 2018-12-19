// AngularJS modules define AngularJS applications.
// AngularJS controllers control AngularJS applications.
app.controller('myCtrl', function($scope) {
    var s = $scope; //scope can be assigned to a variable
    $scope.altfname= "John";
    s.altlname= "Doe";

    $scope.printVal = function() {
        console.log($scope.val2controller);
    };

});

// AngularJS will invoke the controller with a $scope object.
// In AngularJS, $scope is the application object (the owner of application
// variables and functions).

//notice that the $scope object is available to both the view and controller

//When adding properties to the $scope object in the controller,
//the view (HTML) gets access to these properties. In the view, you
//do not use the prefix $scope, you just refer to a propertyname, like {{var}}


app.controller('colorControl', function($scope) {
    $scope.color = "blue";

});

app.run(function($rootScope) {
    $rootScope.color = "red";
});


// create a new custom filter
app.filter('myFormat', function() {
    return function(x) {
        var i;

        for (i = 0; i < x.length; i++) {
            if (x[i] === 1) { x[i] = 'one'; }
            else if (x[i] === 5) { x[i] = 'five'; }
            else {  }
        }
        return x;
    };
});

/* these will not work, you are making a GET on a third-party url
  but observe how you can pass parameters to the controller and
  capture a response data

app.controller('services', function($scope, $http) {
    var sc = $scope;

    //option 1
    $http({
    method : "GET",
    url : "welcome.htm"
  }).then(function mySuccess(response) {
    $scope.myWelcome = response.data;
  }, function myError(response) {
    $scope.myWelcome = response.statusText;
  });

    //option 2
    $http.get("welcome.htm").then(function (response) {
      sc.myWelcome = response.data;
    });

});
*/


//create custom service
app.service('toBinaryService', function() {  //note this only works for positive #'s
    this.convert = function (x) {   //'this' is the toBinaryService object
        return x.toString(2); //to binary
    }
});

app.controller('services', function($scope, toBinaryService) {
    var sc = $scope;
    sc.numberInput = 0;
    sc.binary = 0;

    sc.update = function() {
      sc.binary = toBinaryService.convert(parseInt(sc.numberInput));
    }

});


//use custom service inside filter
//note: Once you have created a service, and connected it to your application,
//you can use the service in any controller, directive, filter, or even inside
//other services. To use the service inside a filter, add it as a dependency
//when defining the filter:

app.filter('binaryFormat',['toBinaryService', function(toBinaryService) {
    return function(x) {
        return toBinaryService.convert(x);
    };
}]);
