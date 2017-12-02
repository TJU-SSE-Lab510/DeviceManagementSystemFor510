/**
 * Created by MSI on 2017/3/29.
 */
'use strict';

labsystem.service('ItemSrv', ['$resource','$http', 'baseURL' ,function ($resource, $http, baseURL) {

  //查看所有用户。
  this.getUser = function(){
    return $resource(baseURL + '/facility/getAll');
  };

  //新建用户
  this.addFacility = function(uid){
    return $resource(baseURL + '/facility/add',null,{'add':{method: 'POST'}});
  };

  //修改用户
  this.editUser = function(uid){
    return $resource(baseURL + '/facility/edit',null,{'add':{method: 'POST'}});
  };

  //删除用户
  this.deleteUser = function(uid){
    return $resource(baseURL + '/facility/delete',null,{'add':{method: 'POST'}});
  };

  //删除用户
  this.upload = function(uid){
    return $resource(baseURL + '/facility/upload',null,{'add':{method: 'POST'}});
  };
}]);
