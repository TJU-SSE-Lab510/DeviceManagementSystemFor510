'use strict';
mrmedia.directive("ticToc", function() {
 	return {
    restrict: 'E',
    bindToController: {
      startTic: '=',
      onTimeout: '&',
      timeout: '='
    },
    controller: ['$timeout', '$interval', '$scope', function($timeout, $interval, $scope) {

        var self = this;



        self.tic = function() {
          var lastTime = new Date().getTime();
          return $interval(function(){
            var now   = new Date().getTime(),
                delta = now - lastTime;
            lastTime  = now;
            self.timeout = self.timeout - delta;
            if (self.timeout < 0) {
              $interval.cancel(self.clock);
              self.startTic = false;
              self.onTimeout();
            }
          }, 1000);
        };

        self.timer = true;
        self.clock = null;

        $scope.$on('$destroy', function() {
          if(self.clock) {
            $interval.cancel(self.clock);
          }
        });
        $scope.$watch('startTic', function(o, n) {
          // console.log(o, n);
          if (self.startTic) {
            self.clock = self.tic();
          } else {
            $interval.cancel(self.clock);
          }
        });



      }],
    controllerAs: '$ctrl',
    template: '<span ng-if="$ctrl.startTic" style="color:white;">{{$ctrl.timeout | date: "mm:ss"}}</span>'
    // template: '<span ng-if="$ctrl.startTic" style="color:red;">{{$ctrl.time.m}}:{{$ctrl.time.s}}</span>'

  //    	scope: {
  //        	otherModelValue: "=compareTo"
  //    	},
  //    	link: function(scope, element, attributes, ngModel) {
	// ngModel.$validators.compareTo = function(modelValue) {
  //            	return modelValue === scope.otherModelValue;
  //        	};
	// scope.$watch("otherModelValue", function() {
  //            	ngModel.$validate();
  //        	});
  //    	}
 	};
});

// miao.component('ticToc', {
//   bindings: {
//     startTic: '=',
//     onTimeout: '&',
//     timeout: '='
//   },
//   controller: ['$timeout', '$interval', '$scope', function($timeout, $interval, $scope) {
//
//       var self = this;
//       console.log(self);
//       function addZero(num, len){
//         return Array(Math.abs(("" + num).length - ((len || 2) + 1))).join(0) + num;
//       }
//
//       // function tic(self){
//       //
//       //   return $interval(function(){
//       //     self.timeout = self.timeout - 1000;
//       //     self.time.s = addZero((self.timeout / 1000) % 60, 2);
//       //     self.time.m = addZero(Math.floor((self.timeout / 1000) / 60), 2);
//       //     if (self.timeout < 0) {
//       //       $interval.cancel(self.clock);
//       //       self.startTic = false;
//       //       self.onTimeout();
//       //     }
//       //   }, 1000);
//       //
//       //   // $timeout(function() {
//       //   //
//       //   // }, self.timeout);
//       // }
//
//       // function cancel() {
//       //
//       // }
//
//       // this.tic = tic;
//       this.timer = true;
//       this.time = {
//         s: addZero((this.timeout / 1000) % 60, 2),
//         m: addZero(Math.floor((this.timeout / 1000) / 60), 2),
//       };
//       this.clock = null;
//
//       $scope.$on('$destroy', function() {
//         if(self.clock) {
//           $interval.cancel(self.clock);
//         }
//       });
//
//       $interval(function(){
//         if (self.startTic) {
//           self.timeout = self.timeout - 1000;
//           self.time.s = addZero((self.timeout / 1000) % 60, 2);
//           self.time.m = addZero(Math.floor((self.timeout / 1000) / 60), 2);
//           if (self.timeout < 0) {
//             $interval.cancel(self.clock);
//             self.startTic = false;
//             self.onTimeout();
//           }
//         }
//       }, 1000);
//
//       // $scope.startTic = self.startTic;
//       // self.$onInit = function() {
//       //   console.log(self);
//       //   self.$watch = function('startTic', function(o, n) {
//       //     console.log(o, n);
//       //     if (self.startTic) {
//       //
//       //       self.clock = tic(self);
//       //     }
//       //   }, true)
//       // };
//         // $scope.$watch = function('startTic', function(o, n) {
//         //   console.log(o, n);
//         //   if (self.startTic) {
//         //
//         //     self.clock = tic(self);
//         //   }
//         // }, true)
//
//
//
//     }],
//     template: '<span ng-if="$ctrl.startTic" style="color:red;">{{$ctrl.time.m}}:{{$ctrl.time.s}}</span>'
// });
