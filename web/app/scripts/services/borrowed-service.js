/**
 * Created by MSI on 2017/3/29.
 */
'use strict';

labsystem.service('BorrowSrv', ['$resource','$http', 'baseURL' ,function ($resource, $http, baseURL) {

  //查看所有用户。
  this.getUser = function(){
    return $resource(baseURL + '/record/getAll');
  };

  //查看用户成绩
  this.getUserScore = function(uid){
    return $resource(baseURL + '/score/getAll?uid='+uid);
  };

  //新建记录
  this.addUser = function(uid){
    return $resource(baseURL + '/record/register',null,{'add':{method: 'POST',}});
  };

  //修改
  this.editUser = function(uid){
    return $resource(baseURL + '/record/edit',null,{'add':{method: 'POST'}});
  };

  //归还物品
  this.returnItem = function(uid){
    return $resource(baseURL + '/record/returnItem',null,{'add':{method: 'POST'}});
  };

  //删除
  this.deleteUser = function(uid){
    return $resource(baseURL + '/record/delete',null,{'add':{method: 'POST'}});
  };
}]);
