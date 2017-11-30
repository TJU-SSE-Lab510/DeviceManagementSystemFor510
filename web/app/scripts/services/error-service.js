'use strict';
mrmedia.factory('ErrorSrv', [function() {
  var error, getError;

  error = {
    '-1': '未知错误',
    '100': '用户名或密码错误',
    '101': '用户名重复',
    '102': '找不到用户',
  };

  getError = function(errCode) {
    return error[errCode];
  };

  return {
    getError: getError
  };
}]);
