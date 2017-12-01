'use strict';
labsystem.filter('decode', function() {
  return function(input,decodeTable) {
    var out = input;
    if(decodeTable.hasOwnProperty(input)) {
      out = decodeTable[input];
    }
    return out;
  };
});
