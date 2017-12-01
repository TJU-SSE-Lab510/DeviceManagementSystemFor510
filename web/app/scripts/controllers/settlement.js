'use strict';

labsystem.controller('SettlementCtrl',
  ['$scope', 'AManagerSrv','NoticeSrv', '$uibModal','$state','UtilSrv',
    function($scope,AManagerSrv,NoticeSrv, $uibModal, $state, UtilSrv) {
      $scope.anchorCollection = AManagerSrv.managerList;
      var modifyOption ={
        editMode : '编辑',
        save : '保存'
      };

      $scope.tableModify = modifyOption.editMode;


      $scope.modify = function () {
        if($scope.tableModify === modifyOption.editMode){
          $scope.tableModify = modifyOption.save;
        }else {
          $scope.tableModify = modifyOption.editMode;
        }
      };

      $scope.beforeTaxShow = function (num) {
          if($scope.tableModify === modifyOption.editMode){
            $scope.beforeTaxText = num;
            return false;
          }else {
            $scope.beforeTaxText = '';
            $scope.beforeTaxInput = num;
            return true
          }
      };

      $scope.afterTaxShow = function (num) {
        if($scope.tableModify === modifyOption.editMode){
          $scope.afterTaxText = num;
          return false;
        }else {
          $scope.afterTaxText = '';
          $scope.afterTaxInput = num;
          return true
        }
      };

      var date=new Date;
      var year=date.getFullYear();
      var month=date.getMonth()+1;
      month =(month<10 ? "0"+month:month);
      $scope.monthInput = year.toString()+'-'+month.toString();
      console.log(year);
      console.log(month);


    }]);
