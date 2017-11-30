'use strict';
miao.filter('checkwithdraw', function() {
  return function(input) {
    var out = "";
    if (input === 0) {
      out = '等待审核';
    } else if (input === 3) {
      out = '正在审核中';
    } else if (input === -1) {
      out = '审核不通过';
    } else if (input === 1) {
      out = '审核通过等待转账';
    } else if (input === 4) {
      out = '正在转账中';
    } else if (input === 2) {
      out = '转账成功';
    } else if (input === -2) {
      out = '转账失败';
    } else {
      out = "未知 " + input;
    }
    return out;
  };
});
