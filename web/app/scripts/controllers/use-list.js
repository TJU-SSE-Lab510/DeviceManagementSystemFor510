'use strict';

labsystem.controller('UseListCtrl',
  ['$scope', 'UseSrv','NoticeSrv', '$uibModal','$state','$http','TokenSrv',
    function($scope,UseSrv,NoticeSrv, $uibModal, $state,$http,TokenSrv) {

      /**
       * @description:　是否可以进行超级管理操作
       * @param:
       * @return:
       */
      if(TokenSrv.getAuth() == '1'){
        $scope.isSuperUser = true;
      }

      /**
       * @description:　修改新建记录弹窗初始化
       * @param:
       * @return:
       */
      $scope.use = {
        itemName: '',
        name: '',
        studentNumber:'',
        number:''
      } ;

      var editid;


      /**
       * @description:　修改记录弹窗
       * @param: item 所修改记录的详情
       * @return:
       */
      $scope.editRecord = function(item){
        $scope.isDisabled = true;
        $scope.use = {
          itemName: item.item_name,
          name: '',
          studentNumber:'',
          number:''
        } ;
        editid = item.id;
        $scope.modalName = "修改记录";
      };


      /**
       * @description:　提交新建/修改的记录
       * @param:
       * @return:
       */
      $scope.record_submit = function () {

          var use = Object.assign({},$scope.use);
        use.id = editid;
          UseSrv.editRecord().add(use)
            .$promise.then(function(response){
              if(response.errCode === 0){
                NoticeSrv.success("修改成功");
                getRecord();
                $('#editRecord').modal('hide');
              $scope.form.$setUntouched()
              }
            },function (response) {
              NoticeSrv.error("修改记录错误,http状态码:"+response.status);
            $scope.form.$setUntouched()
            });


      };

      /**
       * @description:　归还设备
       * @param: id 归还的设备的id
       * @return:
       */
      $scope.show_return = function (id) {
        $scope.return = {
          id:id,
          number: ''
        };
      };

      $scope.return_submit = function () {
        var data = Object.assign({},$scope.return);
        data.returnTime =  Date.parse(new Date())+"";
        UseSrv.returnItem().add(data)
          .$promise.then(function(response){
          if(response.errCode === 0){
            NoticeSrv.success("归还成功");
            getRecord();
            $('#newReturn').modal('hide');
          }
        },function (response) {
          NoticeSrv.error("归还失败,http状态码:"+response.status);
        });
      };


      /**
       * @description:　获取记录
       * @param:
       * @return:
       */
      var getRecord = function () {
        var data = {
          name: null
        };
        UseSrv.getRecord().add(data)
          .$promise.then(function(response){
          if(response.errCode === 0){
            $scope.recordCollection = response.data;
          }
        },function (response) {
          NoticeSrv.error("获取记录列表错误,http状态码:"+response.status);
        });

      };

      getRecord();


      /**
       * @description:　删除记录
       * @param:
       * @return:
       */

      var deleteData ={id:''};

      $scope.deleteRecord = function (id) {
        deleteData ={id:''};
        deleteData.id = id;
      };

      $scope.comfirmDelete = function () {
        UseSrv.deleteRecord().add(deleteData)
          .$promise.then(function(response){
          if(response.errCode === 0){
            getRecord();
            NoticeSrv.success("删除成功");
            $('#modifyDelete').modal('hide');
          }
        },function (response) {
          NoticeSrv.error("删除记录错误,http状态码:"+response.status);
        });
      };


      /**
       * @description:　搜索学生
       * @param:
       * @return:
       */
      $scope.searchStudent = function () {
        var data = {
          studentNumber: $scope.use.studentNumber + ''
        };
        UseSrv.search().add(data).$promise.then(function(response){
          if(response.errCode === 0){
            $scope.selectOptions = response.data;
          }
        },function (response) {
          NoticeSrv.error("搜索错误,http状态码:"+response.status);
        });
      }

    }]);
