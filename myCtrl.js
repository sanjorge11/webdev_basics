// AngularJS modules define AngularJS applications.
// AngularJS controllers control AngularJS applications.
app.controller('myCtrl', function($scope) {
    $scope.altfname= "John";
    $scope.altlname= "Doe";

    $scope.printVal = function() {
        console.log($scope.val2controller);
    };

});
