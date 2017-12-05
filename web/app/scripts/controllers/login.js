'use strict';
labsystem.controller('LoginCtrl', ['$scope', 'NoticeSrv', 'TokenSrv', '$state','BackSrv',
function($scope, NoticeSrv, TokenSrv, $state,BackSrv) {
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
    var res = verifyCode.validate($scope.validateCode);
    if(res){
      BackSrv.login().add($scope.user)
        .$promise.then(function(response) {
        if (response.errCode === 0) {
          TokenSrv.setToken(response.data.token);
          TokenSrv.setAuth(response.data.superuser);
          TokenSrv.setUrl(response.data.url);
          sessionStorage.setItem("token", response.data.token);
          sessionStorage.setItem("superuser", response.data.superuser);
          sessionStorage.setItem("url", response.data.url);
          if(remember)
          {
            localStorage.setItem("token", response.data.token);
            localStorage.setItem("superuser", response.data.superuser);
            localStorage.setItem("url", response.data.url);
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
