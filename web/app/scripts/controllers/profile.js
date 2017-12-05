'use strict';

labsystem.controller('ProfileCtrl',
  ['$scope', 'ProfileSrv','NoticeSrv', '$uibModal','$state','UtilSrv','TokenSrv',
    function($scope,ProfileSrv,NoticeSrv, $uibModal, $state, UtilSrv,TokenSrv)
    {

      $scope.userAvatar = TokenSrv.getUrl();

      /**
       *@description:　获取用户
       *@param:
       *@return:
       */
      var getUser = function () {

        ProfileSrv.getUser().get()
          .$promise.then(function(response){
          if(response.errCode === 0){
            $scope.user = response.data;
            $scope.user.cpassword = response.data.password;
          }
        },function (response) {
          NoticeSrv.error("获取用户列表错误,http状态码:"+response.status);
        });
      };

      getUser();


      $scope.user_submit = function () {

          var user = Object.assign({},$scope.user);
          if(user.password === user.cpassword)
          {
            ProfileSrv.editUser().add(user)
              .$promise.then(function(response){
              if(response.errCode === 0){
                NoticeSrv.success("修改成功");
                getUser();
                $('#editUser').modal('hide');
              }
            },function (response) {
              NoticeSrv.error("修改用户错误,http状态码:"+response.status);
            });
          }
          else {
            NoticeSrv.error("2次密码输入不同");
          }



      };


      $('.avatar-input').change(function(event) {
        // 根据这个 <input> 获取文件的 HTML5 js 对象
        var files = event.target.files, file;
        if (files && files.length > 0) {
          // 获取目前上传的文件
          file = files[0];
          // 来在控制台看看到底这个对象是什么
          // 那么我们可以做一下诸如文件大小校验的动作
          if(file.size > 1024 * 1024 * 2) {
            alert('图片大小不能超过 2MB!');
            return false;
          }
          // !!!!!!
          // 下面是关键的关键，通过这个 file 对象生成一个可用的图像 URL
          // 获取 window 的 URL 工具
          var URL = window.URL || window.webkitURL;
          // 通过 file 生成目标 url
          var imgURL = URL.createObjectURL(file);
          // 使用下面这句可以在内存中释放对此 url 的伺服，跑了之后那个 URL 就无效了
          // URL.revokeObjectURL(imgURL);
          $('#avatarImg').cropper('replace',imgURL);
        }
      });



      $scope.showModal = function(){
          $('#avatar-modal').modal('show');

          var option = {

            preview: '.avatar-preview',
            aspectRatio: 1,
            strict: false,
            crop: function (data) {
            }
          };

          $('#avatarImg').cropper(option);





      };

      /**
       * 将以base64的图片url数据转换为Blob
       * @param urlData
       *            用url方式表示的base64图片数据
       */
      function convertBase64UrlToBlob(urlData){

        var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte

        //处理异常,将ascii码小于0的转换为大于0
        var ab = new ArrayBuffer(bytes.length);
        var ia = new Uint8Array(ab);
        for (var i = 0; i < bytes.length; i++) {
          ia[i] = bytes.charCodeAt(i);
        }

        return new Blob( [ab] , {type : 'image/png'});
      }

      $scope.save = function(){
        var dataurl = $('#avatarImg').cropper('getCroppedCanvas').toBlob(function (blob) {
          var formData = new FormData();
          formData.append('file', blob);
          ProfileSrv.upload().add(formData)
            .$promise.then(function(response){
            if(response.errCode === 0){
              $scope.userIcon = response.data;
              $scope.userAvatar =response.data;
              TokenSrv.setUrl(response.data);
              if(localStorage.getItem('token')!=null){
                localStorage.setItem('url',response.data);
              }
              if(sessionStorage.getItem("token")!=null)
              {
                sessionStorage.setItem('url',response.data);
              }
              location.reload();
            }
          },function (response) {
            NoticeSrv.error("上传图片错误,http状态码:"+response.status);
          });
        });

      };


      $scope.rotate_left =function () {
        $('#avatarImg').cropper("rotate",-90);
      };

      $scope.rotate_right =function () {
        $('#avatarImg').cropper("rotate",90);
      };


    }
    ]);
