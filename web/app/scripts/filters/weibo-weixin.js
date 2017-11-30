'use strict';
miao.filter('checktask', function() {
  return function(input) {
    input = parseInt(input);
    var out = "";
    if (input === 1) {
      out = '微博';
    } else if (input === 2) {
      out = '微信';
    } else if (input === 3) {
      out = '分众任务';
    } else if (input === 4) {
      out = '微信流量';
    } else{
      out = '未知:'+ input;
    }
    return out;
  };
});
