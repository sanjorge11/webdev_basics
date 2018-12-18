// AngularJS modules define AngularJS applications.
// AngularJS controllers control AngularJS applications.
app.controller('myCtrl', function($scope) {
    $scope.altfname= "John";
    $scope.altlname= "Doe";

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
