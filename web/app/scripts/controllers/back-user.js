'use strict';

labsystem.controller('BackuserCtrl',
  ['$scope', 'BackSrv','NoticeSrv', '$uibModal','$state','TokenSrv',
    function($scope,BackSrv,NoticeSrv, $uibModal, $state,TokenSrv) {

      /**
       * @description:　修改新建用户弹窗初始化
       * @param:
       * @return:
       */

      $scope.user = {
        username: '',
        name: '',
        phoneNumber: '',
        password: '',
        cpassword:'',
        superuser:'0'
      } ;

      /**
       * @description:　前往用户详情页
       * @param: id 用户id
       * @return:
       */

      $scope.goToUser = function (id) {
        $state.go('app.userDetail', {id: id});
      };

      /**
       * @description:　是否可以进行超级管理员的操作
       * @param:
       * @return:
       */

      if(TokenSrv.getAuth() == '1'){
        $scope.isSuperUser = true;
      }

      /**
       *@description:　新建用户弹窗
       * @param:
       * @return:
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
          superuser:0+''
        } ;
        $scope.modalName = "新建用户";
      };


      /**
       *  @description:　修改用户弹窗
       * @param  item 修改用户的详情
       * @return
       */

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
          superuser:item.superuser+''
        } ;
        $scope.modalName = "修改用户";
      };

      /**
       *@description:　提交修改或新建用户。
       *@param:
       *@return:
       */

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
