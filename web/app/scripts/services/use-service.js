/**
 * Created by MSI on 2017/3/29.
 */
'use strict';

labsystem.service('UseSrv', ['$resource','$http', 'baseURL' ,function ($resource, $http, baseURL) {

  //查看所有用户。
  this.getRecord = function(){
    return $resource(baseURL + '/use/getAll');
  };


  //新建记录
  this.addRecord = function(data){
    return $resource(baseURL + '/use/add',null,{'add':{method: 'POST'}});
  };

  //修改
  this.editRecord = function(data){
    return $resource(baseURL + '/use/edit',null,{'add':{method: 'POST'}});
  };

  //归还物品
  this.returnItem = function(data){
    return $resource(baseURL + '/record/returnItem',null,{'add':{method: 'POST'}});
  };

  //删除
  this.deleteRecord = function(data){
    return $resource(baseURL + '/record/delete',null,{'add':{method: 'POST'}});
  };
}]);
