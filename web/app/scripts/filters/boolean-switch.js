'use strict';
miao.filter('checkmark', function() {
  return function(input) {
    var out = "";
    if (input === 0) {
      out = '正常';
    }else if (input === 1) {
      out = '黑名单';
    }else {
      out = "未知" + input;
    }
    return out;
  };
});
