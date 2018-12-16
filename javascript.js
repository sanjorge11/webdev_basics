// AngularJS modules define AngularJS applications.
// AngularJS controllers control AngularJS applications.

var app = angular.module('myApp', []);

app.controller('myCtrl', function($scope) {
    $scope.fname= "John";
    $scope.lname= "Doe";

});
