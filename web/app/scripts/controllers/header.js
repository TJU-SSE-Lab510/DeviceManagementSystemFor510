'use strict';

labsystem.controller('HeaderCtrl', ['$scope', 'BackBorrowSrv', function($scope, BackBorrowSrv) {
  $scope.admin = BackBorrowSrv.getUser();

}]);
