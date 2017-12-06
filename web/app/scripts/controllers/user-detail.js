'use strict';

labsystem.controller('UserDetailCtrl',
  ['$scope', 'BackSrv','NoticeSrv', '$uibModal','$state','TokenSrv','$stateParams',
    function($scope,BackSrv,NoticeSrv, $uibModal, $state,TokenSr,$stateParams)
    {

      /*
       获取用户id
       */
      var userId = {
        id : $stateParams.id
      };

      /**
       *@description:　获取用户
       *@param:
       *@return:
       */
      var getUser = function () {

        BackSrv.getUserById().get(userId)
          .$promise.then(function(response){
          if(response.errCode === 0){
            $scope.user = response.data;
          }
        },function (response) {
          NoticeSrv.error("获取用户错误,http状态码:"+response.status);
        });
      };

      getUser();


    }
    ]);
