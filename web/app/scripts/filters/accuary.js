'use strict';
miao.filter('accuary', function() {
  return function(input, param) {
    return input.toFixed(param);
  };
});
