'use strict';

mrmedia.controller('HeaderCtrl', ['$scope', 'BackuserSrv', function($scope, BackuserSrv) {
  $scope.admin = BackuserSrv.getUser();

}]);
