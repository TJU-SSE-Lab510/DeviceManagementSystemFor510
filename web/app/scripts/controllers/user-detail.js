'use strict';

labsystem.controller('UserDetailCtrl',
      ['$scope', 'BackSrv','NoticeSrv', '$uibModal','$state','TokenSrv','$stateParams','BorrowSrv','UseSrv','ProfileSrv','TokenSrv',
    function($scope,BackSrv,NoticeSrv, $uibModal, $state,TokenSr,$stateParams,BorrowSrv,UseSrv,ProfileSrv,TokenSrv)
    {
      //
      // $scope.user ={
      //   name: "杜盛禹",
      //   url: "http://100.64.208.218:82/user/default.png",
      //   phone: "13601643669",
      //   email: "Qweqwe@qq.com",
      //   studentNumber: "179xxxx"
      // };

      /*
       获取用户id
       */
      var userId = {
        adminid : $stateParams.id
      };

      if(userId.adminid == ''|| userId.adminid == null){
        $scope.isEditDisabled =  false;
      }else {
        $scope.isEditDisabled = true;
      }
      /**
       * @description:　获取记录
       * @param:
       * @return:
       */
      var getUser = function () {
        console.log(userId);
        if(userId.adminid == ''){
          userId.adminid = null;
        }
        BackSrv.getuUserById().add(userId)
          .$promise.then(function(response){
          if(response.errCode === 0){
            $scope.user = response.data;
            getBorrowRecord();
            getUseRecord();
          }
        },function (response) {
          NoticeSrv.error("获取记录列表错误,http状态码:"+response.status);
        });

      };

      getUser();

      var getBorrowRecord = function () {
        var data = {
          name: $scope.user.name
        };
        BorrowSrv.getRecord().add(data)
          .$promise.then(function(response){
          if(response.errCode === 0){
            $scope.borrowCollection = response.data;
            for(var i=0;i<$scope.borrowCollection.length;i++){
              if($scope.borrowCollection[i].return_time != "未归还"){
                number++
              }
            }
            $scope.lendNumber = number;
          }
        },function (response) {
          NoticeSrv.error("获取记录列表错误,http状态码:"+response.status);
        });

      };



      var getUseRecord = function () {
        var data = {
          name: $scope.user.name
        };
        UseSrv.getRecord().add(data)
          .$promise.then(function(response){
          if(response.errCode === 0){
            $scope.useCollection = response.data;
            var number = 0
            for(var i=0;i<$scope.useCollection.length;i++){
              if($scope.useCollection[i].return_time != "未归还"){
                number++
              }
            }
            $scope.useNumber = number;
          }
        },function (response) {
          NoticeSrv.error("获取记录列表错误,http状态码:"+response.status);
        });

      };


      $scope.show_edit = function () {
        $scope.edit = Object.assign({},$scope.user);
        $scope.edit.cpassword = $scope.user.password;
        $scope.isDisabled = true;
        $scope.form.$setUntouched();
      }



      /**
       * @description:　提交修改
       * @param:
       * @return:
       */

      $scope.user_submit = function () {

        var user = Object.assign({},$scope.edit);
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


      /**
       * 上传头像
       */

      /**
       * @description:　当所选图片发生改变
       * @param:
       * @return:
       */
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



      /**
       * @description:　上传头像弹窗
       * @param:
       * @return:
       */
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
       * @description:　保存头像
       * @param:
       * @return:
       */

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


      /**
       * @description:　旋转头像
       * @param:
       * @return:
       */
      $scope.rotate_left =function () {
        $('#avatarImg').cropper("rotate",-90);
      };

      $scope.rotate_right =function () {
        $('#avatarImg').cropper("rotate",90);
      };


    }


    ]);
