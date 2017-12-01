'use strict';

labsystem.service('BackBorrowSrv', ['$resource','$http', 'baseURL' ,function ($resource, $http, baseURL) {


  //查看所有管理员。
  this.getUser = function(){
    return $resource(baseURL + '/admin/getAll');
  };

  //新建管理员
  this.addUser = function(uid){
    return $resource(baseURL + '/admin/register',null,{'add':{method: 'POST'}});
  };

  //修改管理员
  this.editUser = function(uid){
    return $resource(baseURL + '/admin/edit',null,{'add':{method: 'POST'}});
  };

  //修改管理员
  this.deleteUser = function(){
    return $resource(baseURL + '/admin/delete',null,{'add':{method: 'POST'}});
  };
  //修改管理员
  this.login = function(){
    return $resource(baseURL + '/admin/login',null,{'add':{method: 'POST'}});
  };


}]);
