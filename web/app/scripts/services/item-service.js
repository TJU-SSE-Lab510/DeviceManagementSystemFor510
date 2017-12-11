/**
 * Created by MSI on 2017/3/29.
 */
'use strict';

labsystem.service('ItemSrv', ['$resource','$http', 'baseURL' ,function ($resource, $http, baseURL) {

  //查看所有。
  this.getItem = function(){
    return $resource(baseURL + '/facility/getAll');
  };

  //新建
  this.addFacility = function(data){
    return $resource(baseURL + '/facility/add',null,{'add':{method: 'POST'}});
  };

  //修改
  this.editItem = function(data){
    return $resource(baseURL + '/facility/edit',null,{'add':{method: 'POST'}});
  };

  //删除
  this.deleteItem = function(data){
    return $resource(baseURL + '/facility/delete',null,{'add':{method: 'POST'}});
  };

  //搜索
  this.search = function(data){
    return $resource(baseURL + '/admin/search',null,{'add':{method: 'POST'}});
  };

  //上传图片

  this.upload = function(data){
    return $resource(baseURL + '/facility/upload',null,{'add':{method: 'POST',headers:{'Content-Type':undefined}}});
  };
}]);
