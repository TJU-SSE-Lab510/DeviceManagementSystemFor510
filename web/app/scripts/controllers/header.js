'use strict';

labsystem.controller('HeaderCtrl', ['$scope', 'BackuserSrv', function($scope, BackuserSrv) {
  $scope.admin = BackuserSrv.getUser();

}]);
