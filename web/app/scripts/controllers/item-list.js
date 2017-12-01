'use strict';

labsystem.controller('ItemListCtrl',
  ['$scope', 'ItemSrv','NoticeSrv', '$uibModal','$state','UtilSrv','$http','TokenSrv',
    function($scope,ItemSrv,NoticeSrv, $uibModal, $state, UtilSrv,$http,TokenSrv) {

      $scope.item = {
        itemName: '',
        itemQTY:'',
        url:'../images/defautPicture.png'
      } ;

      var editid;

      /**
       *@description:　新建或修改设备
       */
      $scope.showNewUserModal = function(){
        $('#editUser').modal('show');
        $scope.isDisabled = false;
        $scope.item = {
          itemName: '',
          itemQTY:'',
          url:'../images/defautPicture.png'
        } ;
        $scope.modalName = "新建设备";
      };


      $scope.editUser = function(item){
        $scope.isDisabled = true;
        $scope.item = {
          itemName: item.itemName,
          itemQTY:item.itemQTY,
          url:item.url
        } ;
        editid = item.id;
        $scope.modalName = "修改设备";
      };

      $scope.user_submit = function () {
        if($scope.modalName == "新建设备"){
          var item = Object.assign({},$scope.item);
          item.date =  Date.parse(new Date())/1000;
          console.log(Date.parse(new Date()));
          ItemSrv.addUser().add(item)
            .$promise.then(function(response){
              console.log(response);
              if(response.errCode === 0){
                NoticeSrv.success("新建成功");
                getUser();
                $('#editUser').modal('hide');
              }
            },function (response) {
              NoticeSrv.error("新建用户错误,http状态码:"+response.status);
            });
        }else {
          var item = Object.assign({},$scope.item);
          item.id = editid;
          ItemSrv.editUser().add(item)
            .$promise.then(function(response){
              console.log(response);
              if(response.errCode === 0){
                NoticeSrv.success("修改成功");
                getUser();
                $('#editUser').modal('hide');
              }
            },function (response) {
              NoticeSrv.error("修改设备错误,http状态码:"+response.status);
            });


        }

      };


      /**
       *@description:　获取设备
       *@param:
       *@return:
       */
      var getUser = function () {
        ItemSrv.getUser().get()
          .$promise.then(function(response){
          if(response.errCode === 0){
            $scope.userCollection = response.data;
          }
        },function (response) {
          NoticeSrv.error("获取设备列表错误,http状态码:"+response.status);
        });

      };

      getUser();


      /**
       *@description:　删除设备
       *@param:
       *@return:
       */

      var deleteData ={id:''};

      $scope.deleteUser = function (id) {
        deleteData ={id:''};
        deleteData.id = id;
      };

      $scope.comfirmDelete = function () {
        ItemSrv.deleteUser().add(deleteData)
          .$promise.then(function(response){
          if(response.errCode === 0){
            getUser();
            NoticeSrv.success("删除成功");
            $('#modifyDelete').modal('hide');
          }
        },function (response) {
          NoticeSrv.error("删除设备错误,http状态码:"+response.status);
        });
      };



      $('.avatar-input').change(function(event) {
        // 根据这个 <input> 获取文件的 HTML5 js 对象
        var files = event.target.files, file;
        if (files && files.length > 0) {
          // 获取目前上传的文件
          file = files[0];
          // 来在控制台看看到底这个对象是什么
          console.log(file);
          // 那么我们可以做一下诸如文件大小校验的动作
          if(file.size > 1024 * 1024 * 2) {
            alert('图片大小不能超过 2MB!');
            return false;
          }
          console.log(file);
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
          crop: function(data) {
          }
        };

        $('#avatarImg').cropper(option);




      };

      $scope.save = function(){

        var dataurl = $('#avatarImg').cropper('getCroppedCanvas').toDataURL('image/png');
        console.log(dataurl);
        $scope.item.url = dataurl;
        $('#avatar-modal').modal('hide');
      };


      $scope.rotate_left =function () {
        $('#avatarImg').cropper("rotate",-90);
      };

      $scope.rotate_right =function () {
        $('#avatarImg').cropper("rotate",90);
      };




    }]);
