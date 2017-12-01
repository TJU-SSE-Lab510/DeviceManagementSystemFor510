'use strict';

labsystem.controller('UserListCtrl',
  ['$scope', 'UserSrv','NoticeSrv', '$uibModal','$state','UtilSrv','$http','TokenSrv',
    function($scope,UserSrv,NoticeSrv, $uibModal, $state, UtilSrv,$http,TokenSrv) {

      $scope.record = {
        name: '',
        itemName: '',
        phone:''
      } ;

      var editid;

      /**
       *@description:　新建或修改记录
       */
      $scope.showNewUserModal = function(){
        $('#editUser').modal('show');
        $scope.isDisabled = false;
        $scope.record = {
          name: '',
          itemName: '',
          phone:''
        } ;
        $scope.modalName = "新建记录";
      };


      $scope.editUser = function(item){
        $scope.isDisabled = true;
        $scope.record = {
          name: item.name,
          itemName: item.itemName,
          phone:item.phone
        } ;
        editid = item.id;
        $scope.modalName = "修改记录";
      };

      $scope.user_submit = function () {
        if($scope.modalName == "新建记录"){
          var record = Object.assign({},$scope.record);
          record.borrowedTime =  Date.parse(new Date())/1000;
          record.borrowOperator = TokenSrv.getToken();
          console.log(Date.parse(new Date()));
          UserSrv.addUser().add(record)
            .$promise.then(function(response){
              console.log(response);
              if(response.errCode === 0){
                NoticeSrv.success("新建成功");
                getUser();
                $('#editUser').modal('hide');
              }
            },function (response) {
              NoticeSrv.error("新建用户错误,http状态码:"+response.status);
            });
        }else {
          var record = Object.assign({},$scope.record);
          record.id = editid;
          UserSrv.editUser().add(record)
            .$promise.then(function(response){
              console.log(response);
              if(response.errCode === 0){
                NoticeSrv.success("修改成功");
                getUser();
                $('#editUser').modal('hide');
              }
            },function (response) {
              NoticeSrv.error("修改记录错误,http状态码:"+response.status);
            });


        }

      };

      $scope.return = function (id) {
        var data = {};
        data.id = id;
        data.returnTime =  Date.parse(new Date())/1000;
        data.returnOperator = TokenSrv.getToken();
        UserSrv.returnItem().add(data)
          .$promise.then(function(response){
          console.log(response);
          if(response.errCode === 0){
            NoticeSrv.success("归还成功");
            getUser();
            $('#editUser').modal('hide');
          }
        },function (response) {
          NoticeSrv.error("归还失败,http状态码:"+response.status);
        });
      };

      /**
       *@description:　获取记录
       *@param:
       *@return:
       */
      var getUser = function () {
        UserSrv.getUser().get()
          .$promise.then(function(response){
          if(response.errCode === 0){
            $scope.userCollection = response.data;
          }
        },function (response) {
          NoticeSrv.error("获取记录列表错误,http状态码:"+response.status);
        });

      };

      getUser();


      /**
       *@description:　删除记录
       *@param:
       *@return:
       */

      var deleteData ={id:''};

      $scope.deleteUser = function (id) {
        deleteData ={id:''};
        deleteData.id = id;
      };

      $scope.comfirmDelete = function () {
        UserSrv.deleteUser().add(deleteData)
          .$promise.then(function(response){
          if(response.errCode === 0){
            getUser();
            NoticeSrv.success("删除成功");
            $('#modifyDelete').modal('hide');
          }
        },function (response) {
          NoticeSrv.error("删除记录错误,http状态码:"+response.status);
        });
      };



    }]);
