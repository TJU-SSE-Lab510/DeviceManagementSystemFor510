'use strict';

labsystem.controller('BackuserCtrl',
  ['$scope', 'BackSrv','NoticeSrv', '$uibModal','$state','UtilSrv','TokenSrv',
    function($scope,BackSrv,NoticeSrv, $uibModal, $state, UtilSrv,TokenSrv) {

      $scope.user = {
        username: '',
        name: '',
        phoneNumber: '',
        password: '',
        cpassword:'',
        superuser:0
      } ;




      /**
       *@description:　新建或修改用户
       */
      $scope.showNewUserModal = function(){
        $('#editUser').modal('show');
        $scope.isDisabled = false;
        $scope.user = {
          username: '',
          name: '',
          phoneNumber: '',
          password: '',
          cpassword:'',
          superuser:0
        } ;
        $scope.modalName = "新建用户";
      };



      $scope.editUser = function(item){
        $scope.isDisabled = true;
        if(item.superuser == '1'){
          $scope.isSuper = true;
        }
        $scope.user = {
          id: item.id,
          username: item.username,
          name : item.name,
          phoneNumber: item.phoneNumber,
          password: '',
          cpassword:'',
          superuser:item.superuser
        } ;
        $scope.modalName = "修改用户";
      };

      $scope.user_submit = function () {
        if($scope.modalName == "新建用户"){
          var user = Object.assign({},$scope.user);
          user.superuser = user.superuser * 1;
          if(user.password == user.cpassword)
          {
            BackSrv.addUser().add(user)
              .$promise.then(function(response){
              if(response.errCode === 0){
                NoticeSrv.success("新建成功");
                getUser();
                $('#editUser').modal('hide');
              }
            },function (response) {
              NoticeSrv.error("新建用户错误,http状态码:"+response.status);
            });
          }
          else {
            NoticeSrv.error("2次密码输入不同");
          }
        }else {
          var user = Object.assign({},$scope.user);
          user.superuser = user.superuser * 1;
          if(user.password == user.cpassword)
          {
            BackSrv.editUser().add(user)
              .$promise.then(function(response){
              if(response.errCode === 0){
                NoticeSrv.success("修改成功");
                getUser();
                $('#editUser').modal('hide');
              }
            },function (response) {
              NoticeSrv.error("修改用户错误,http状态码:"+response.status);
            });
          }
          else {
            NoticeSrv.error("2次密码输入不同");
          }

        }

      };

      if(TokenSrv.getAuth() == '1'){
        $scope.isSuperUser = true;
      }


      /**
       *@description:　获取用户
       *@param:
       *@return:
       */
      var getUser = function () {

        BackSrv.getUser().get()
          .$promise.then(function(response){
          if(response.errCode === 0){
            $scope.userCollection = response.data;

          }
        },function (response) {
          NoticeSrv.error("获取用户列表错误,http状态码:"+response.status);
        });
      };

      getUser();


      /**
       *@description:　获取用户
       *@param:
       *@return:
       */
      $scope.getScore = function(id){
        BackSrv.getUserScore(id).get()
          .$promise.then(function(response){
          if(response.errCode === 0){
            $scope.scoreCollection = response.data;
          }
        });
      };

      /**
       *@description:　删除用户
       *@param:
       *@return:
       */

      var deleteData ={id:''};

      $scope.deleteAdmin = function (id) {
        deleteData ={id:''};
        deleteData.id = id;
      };

      $scope.comfirmDelete = function () {
          BackSrv.deleteUser().add(deleteData)
            .$promise.then(function(response){
            if(response.errCode === 0){
              getUser();
              NoticeSrv.success("删除成功");
              $('#modifyDelete').modal('hide');
            }
          },function (response) {
            NoticeSrv.error("获取用户列表错误,http状态码:"+response.status);
          });
        };

    }]);
