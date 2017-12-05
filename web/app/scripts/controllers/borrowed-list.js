'use strict';

labsystem.controller('BorrowListCtrl',
  ['$scope', 'BorrowSrv','NoticeSrv', '$uibModal','$state','UtilSrv','$http','TokenSrv',
    function($scope,BorrowSrv,NoticeSrv, $uibModal, $state, UtilSrv,$http,TokenSrv) {
      if(TokenSrv.getAuth() == '1'){
        $scope.isSuperUser = true;
      }
      $scope.record = {
        name: '',
        itemName: '',
        phone:'',
        email:''
      } ;

      var editid;

      /**
       *@description:　新建或修改记录
       */
      $scope.showNewRecordModal = function(){
        $('#editRecord').modal('show');
        $scope.isDisabled = false;
        $scope.record = {
          name: '',
          itemName: '',
          phone:'',
          email:''
        } ;
        $scope.modalName = "新建记录";
      };


      $scope.editRecord = function(item){
        $scope.isDisabled = true;
        $scope.record = {
          name: item.name,
          itemName: item.itemName,
          phone:item.phone,
          email:item.email
        } ;
        editid = item.id;
        $scope.modalName = "修改记录";
      };


      $scope.record_submit = function () {
        if($scope.modalName === "新建记录"){
          var record = Object.assign({},$scope.record);
          record.borrowedTime =  Date.parse(new Date())+"";
          record.borrowOperator = TokenSrv.getToken();
          BorrowSrv.addRecord().add(record)
            .$promise.then(function(response){
              if(response.errCode === 0){
                NoticeSrv.success("新建成功");
                getRecord();
                $('#editRecord').modal('hide');
                $scope.form.$setUntouched()
              }
            },function (response) {
              NoticeSrv.error("新建用户错误,http状态码:"+response.status);
            $scope.form.$setUntouched()
            });
        }else {
          var record = Object.assign({},$scope.record);
          record.id = editid;
          BorrowSrv.editRecord().add(record)
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


        }

      };

      $scope.return = function (id) {
        var data = {};
        data.id = id;
        data.returnTime =  Date.parse(new Date())+"";
        data.returnOperator = TokenSrv.getToken();
        BorrowSrv.returnItem().add(data)
          .$promise.then(function(response){
          if(response.errCode === 0){
            NoticeSrv.success("归还成功");
            getRecord();
            $('#editRecord').modal('hide');
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
      var getRecord = function () {
        BorrowSrv.getRecord().get()
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
       *@description:　删除记录
       *@param:
       *@return:
       */

      var deleteData ={id:''};

      $scope.deleteRecord = function (id) {
        deleteData ={id:''};
        deleteData.id = id;
      };

      $scope.comfirmDelete = function () {
        BorrowSrv.deleteRecord().add(deleteData)
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



    }]);
