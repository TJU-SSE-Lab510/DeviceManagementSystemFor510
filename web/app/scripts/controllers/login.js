'use strict';
labsystem.controller('LoginCtrl', ['$scope', 'NoticeSrv', 'TokenSrv', '$state','BackSrv',
function($scope, NoticeSrv, TokenSrv, $state,BackSrv) {

  $("body").css("height","auto");

  /**
   * @description:　初始化用户名密码
   * @param:
   * @return:
   */
  $scope.user = {
    "username": '',
    "password": ''

  };

  /**
   * @description:　初始化remember me
   * @param:
   * @return:
   */
  var remember = false;

  /**
   * @description:　点击remember
   * @param:
   * @return:
   */
  $scope.remember =function () {
      remember = !remember;
  };

  /**
   * @description:　初始化验证码
   * @param:
   * @return:
   */
  var verifyCode = new GVerify("v_container");


  /**
   * @description:　点击登陆
   * @param:
   * @return:
   */
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
          $state.go('app.itemlist');
        }
      }, function(response) {
        $state.go('app.itemlist');
        NoticeSrv.error("登录错误,http状态码:"+response.status);
      });
    }else{
      NoticeSrv.error("验证码错误！");
    }
  };

}]);
