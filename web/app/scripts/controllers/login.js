'use strict';
labsystem.controller('LoginCtrl', ['$scope', 'NoticeSrv', 'TokenSrv', '$state','BackUserSrv',
function($scope, NoticeSrv, TokenSrv, $state,BackUserSrv) {
  $scope.user = {
    "username": '',
    "password": ''

  };

  var remember = false;

  $scope.remember =function () {
      remember = !remember;
  };

  var verifyCode = new GVerify("v_container");


  $("body").css("height","auto");

  $scope.login = function () {
    console.log(remember);
    var res = verifyCode.validate($scope.validateCode);
    if(res){
      BackUserSrv.login().add($scope.user)
        .$promise.then(function(response) {
        if (response.errCode === 0) {
          TokenSrv.setToken(response.data.token);
          sessionStorage.setItem("token", response.data.token);
          console.log(response.data);
          if(remember)
          {
            localStorage.setItem("token", response.data.token);
          }
          NoticeSrv.success("登录成功！");
          $state.go('app.userlist');
        }
      }, function(response) {
        $state.go('app.userlist');
        NoticeSrv.error("登录错误,http状态码:"+response.status);
      });
    }else{
      NoticeSrv.error("验证码错误！");
    }
  };

}]);
