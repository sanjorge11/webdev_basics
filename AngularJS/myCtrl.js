// AngularJS modules define AngularJS applications.
// AngularJS controllers control AngularJS applications.
app.controller('myCtrl', function($scope) {
    var s = $scope; //scope can be assigned to a variable
    $scope.altfname= "John";
    s.altlname= "Doe";

    $scope.printVal = function() {
        console.log($scope.val2controller);
    };

    s.cars = [
      { model : "Ford Mustang", color : "red" },
      { model : "Fiat 500", color : "white" },
      { model : "Volvo XC90", color : "black" }
    ];

    //these key value pairs are objects, e.g. car01 -- x and Ford -- y
    s.carObjects = {
    car01 : "Ford",
    car02 : "Fiat",
    car03 : "Volvo"
    };

    s.carObjects2 = {
        car01 : {brand : "Ford", model : "Mustang", color : "red"},
        car02 : {brand : "Fiat", model : "500", color : "white"},
        car03 : {brand : "Volvo", model : "XC90", color : "black"}
    }

    s.ngShowVar = 5;

    s.trackMouse = function(eventVar) {
      ////to see the event object, uncomment below
      //console.log(eventVar);
      s.xCor = eventVar.clientX;
      s.yCor = eventVar.clientY;
    }

    s.testHTML = "this is html from an ng-included file";

    s.hideBoolean = false;

    s.toggleHide = function() {
      s.hideBoolean = !s.hideBoolean;
    }
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
  capture a response data with $http service

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
    //add a second function used for error handling if needed
    //the response could be layered data, you could specify a property
    //array and have an ng-repeat loop through the response to display

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



//create directive for form validation -- this example was gotten from tutorial
//may have to research more about the structure of custom validation directives if needed
app.directive('formValidationDirective', function() {
  return {
    require: 'ngModel',
    link: function(scope, element, attr, mCtrl) {
      //the order of parameters above matters, first is app scope, second
      //is the element object, object's attributes, and lastly the controlller

      function myValidation(value) {
        //the parameter is the input value of the input element, it is
        //passed as the user edits the element
        if (value.indexOf("e") > -1) {
          //indexOf returns the position of the first occurrence of a specified
          //value in a string, if not found then -1 is returned
          mCtrl.$setValidity('charE', true);  //the controller has $setValidity
        } else {
          mCtrl.$setValidity('charE', false); //we can set situtations of valid/invalid input
        }
        return value;
      }
      mCtrl.$parsers.push(myValidation);
      //the line above adds the myValidation function to an array of other
      //functions, which will be executed every time the input value changes
    }
  };
});



//configure routes -- note that you could provide a templateUrl instead of raw HTML
app.config(function($routeProvider){
    $routeProvider
        .when('/', {
            template: '<h2>{{page}}</h2>',
            controller: ['$scope', function($scope){
                $scope.page = 'Home';
            }]
        })
        .when('/about', {
            template: '<h2>{{page}}</h2>',
            controller: ['$scope', function($scope){
                $scope.page = 'Hello World';
            }]
        })
        .otherwise({redirectTo: '/'});
});

  // you can add a name to the configuration
// app.config(['$routeProvider', function($routeProvider){
//     $routeProvider
//         .when('/', {
//             template: '<h2>{{page}}</h2>',
//             controller: ['$scope', function($scope){
//                 $scope.page = 'home';
//             }]
//         })
//         .when('/about', {
//             template: '<h2>{{page}}</h2>',
//             controller: ['$scope', function($scope){
//                 $scope.page = 'Hello World';
//             }]
//         })
//         .otherwise({redirectTo: '/'});
// }]);

//the otherwise route above is the default route when none of the others match
