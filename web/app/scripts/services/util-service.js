'use strict';
/**
 * Created by Horac on 2016/11/17.
 */
mrmedia.service('UtilSrv', ['$resource', 'baseURL', function($resource, baseURL) {

  var baseUrl = "http://139.224.41.121:8090/";

  /**
   *@description: 将null或undefined转换成空字符串''
   *@param: value 需要转换的值
   *@return: 转换后的值
   */
  this.nvl = function(value){
    if (value === undefined || value === null) { return ''; } else { return value;}
  };

  /**
   *@description: 修改urllist
   *@param: urllist 转换值
   *@return: 转换后的值
   */
  this.formatUrlList = function (urlList) {
    urlList.forEach(function (elem) {
      elem.url = baseUrl + elem.url;
    });
    return urlList;
  };

  /**
   *@description: 修改url
   *@param: url 转换值
   *@return: 转换后的值
   */
  this.formatUrl = function (url) {
    url = baseUrl + url;
    return url;
  };

}]);
