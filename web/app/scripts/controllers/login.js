'use strict';
labsystem.controller('LoginCtrl', ['$scope', 'NoticeSrv', 'TokenSrv', '$state','BackBorrowSrv',
function($scope, NoticeSrv, TokenSrv, $state,BackBorrowSrv) {
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
      BackBorrowSrv.login().add($scope.user)
        .$promise.then(function(response) {
        if (response.errCode === 0) {
          TokenSrv.setToken(response.data.token);
          TokenSrv.setAuth(response.data.superuser);
          sessionStorage.setItem("token", response.data.token);
          sessionStorage.setItem("superuser", response.data.superuser);
          console.log(response.data);
          if(remember)
          {
            localStorage.setItem("token", response.data.token);
            localStorage.setItem("superuser", response.data.superuser);
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
