'use strict';

labsystem.service('ProfileSrv', ['$resource','$http', 'baseURL' ,function ($resource, $http, baseURL) {


  //查看所有管理员。
  this.getUser = function(){
    return $resource(baseURL + '/admin/item');
  };

  this.editUser = function(uid){
    return $resource(baseURL + '/admin/update',null,{'add':{method: 'POST'}});
  };


  this.upload = function(uid){
    return $resource(baseURL + '/admin/upload',null,{'add':{method: 'POST',headers:{'Content-Type':undefined}}});
  };

}]);
