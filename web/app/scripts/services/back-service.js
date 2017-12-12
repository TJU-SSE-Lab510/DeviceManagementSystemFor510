'use strict';

labsystem.service('BackSrv', ['$resource','$http', 'baseURL' ,function ($resource, $http, baseURL) {


  //查看所有管理员。
  this.getUser = function(){
    return $resource(baseURL + '/admin/getAll');
  };

  //新建管理员
  this.addUser = function(data){
    return $resource(baseURL + '/admin/register',null,{'add':{method: 'POST'}});
  };

  //修改管理员
  this.editUser = function(data){
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


  //获取管理员详情
  this.getuUserById = function(){
    return $resource(baseURL + '/admin/item',null,{'add':{method: 'POST'}});
  };


}]);
