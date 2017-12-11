'use strict';

labsystem.controller('ItemListCtrl',
  ['$scope', 'ItemSrv','NoticeSrv', '$uibModal','$state','$http','TokenSrv','BorrowSrv','UseSrv',
    function($scope,ItemSrv,NoticeSrv, $uibModal, $state,$http,TokenSrv,BorrowSrv,UseSrv) {

      /**
       * @description:　是否拥有超级管理的操作
       * @param:
       * @return:
       */

      $scope.isDisabled = false;

      if(TokenSrv.getAuth() == '1'){
        $scope.isSuperUser = true;
        $scope.modalBtn = '保存';
        $scope.isInputDisabled = false;
      }else {
        $scope.isInputDisabled = true;
        $scope.modalBtn = '关闭';
      }

      $scope.item = {
        itemName: '',
        itemQTY:'',
        url:'../images/defautPicture.png'
      } ;

      var editid;

      /**
       * @description:　新建设备弹框
       * @param:
       * @return:
       */

      $scope.showNewItemModal = function(){
        $('#editItem').modal('show');
        $scope.isDisabled = false;
        $scope.item = {
          itemName: '',
          itemQTY:'',
          url:'../images/defautPicture.png'
        } ;
        $scope.modalName = "新建设备";
        $scope.form.$setUntouched()
      };

      /**
       * @description:　修改设备弹框
       * @param:
       * @return:
       */

      $scope.editItem = function(item){
        $scope.isDisabled = true;
        $scope.item = {
          itemName: item.itemName,
          itemQTY:item.itemQTY,
          url:item.url
        } ;
        editid = item.id;
        $scope.modalName = "修改设备";
        $scope.form.$setUntouched()
      };

      /**
       * @description:　提交哦修改/新建设备
       * @param:
       * @return:
       */

      $scope.item_submit = function () {
        if(TokenSrv.getAuth() == '1') {
        if($scope.modalName === "新建设备"){
          var item = Object.assign({},$scope.item);
          item.date =  Date.parse(new Date())+"";
          var temp = item.url.split('/');
          item.url = temp[temp.length-1];
          ItemSrv.addFacility().add(item)
            .$promise.then(function(response){
              if(response.errCode === 0){
                NoticeSrv.success("新建成功");
                getItem();
                $('#editItem').modal('hide');
                $scope.form.$setUntouched()
              }
            },function (response) {
              NoticeSrv.error("新建设备错误,http状态码:"+response.status);
            });
        }else {
          var item = Object.assign({},$scope.item);
          item.date =  Date.parse(new Date())+"";
          var temp = item.url.split('/');
          item.url = temp[temp.length-1];
          item.id = editid;
          ItemSrv.editItem().add(item)
            .$promise.then(function(response){
              if(response.errCode === 0){
                NoticeSrv.success("修改成功");
                getItem();
                $('#editItem').modal('hide');
                $scope.form.$setUntouched()
              }
            },function (response) {
              NoticeSrv.error("修改设备错误,http状态码:"+response.status);
            });


        }
        }else {
          $('#editItem').modal('hide');
        }


      };


      /**
       * @description:　获取设备
       * @param:
       * @return:
       */

      var getItem = function () {
        ItemSrv.getItem().get()
          .$promise.then(function(response){
          if(response.errCode === 0){
            $scope.itemCollection = response.data;
          }
        },function (response) {
          NoticeSrv.error("获取设备列表错误,http状态码:"+response.status);
        });

      };

      getItem();


      /**
       * @description:　删除设备
       * @param:
       * @return:
       */

      var deleteData ={id:''};

      $scope.deleteItem = function (id) {
        deleteData ={id:''};
        deleteData.id = id;
      };

      $scope.comfirmDelete = function () {
        ItemSrv.deleteItem().add(deleteData)
          .$promise.then(function(response){
          if(response.errCode === 0){
            getItem();
            NoticeSrv.success("删除成功");
            $('#modifyDelete').modal('hide');
          }
        },function (response) {
          NoticeSrv.error("删除设备错误,http状态码:"+response.status);
        });
      };


      /**
       * 上传图片操作
       */

      /**
       * @description:　当上传的图片发生改变
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
       * @description:　上传图片弹窗
       * @param:
       * @return:
       */

      $scope.showModal = function(){
        if(TokenSrv.getAuth() == '1') {
          $('#avatar-modal').modal('show');

          var option = {

            preview: '.avatar-preview',
            aspectRatio: 1,
            strict: false,
            crop: function (data) {
            }
          };

          $('#avatarImg').cropper(option);
        }
      };

      /**
       * @description:　上传图片
       * @param:
       * @return:
       */

      $scope.save = function(){
        var dataurl = $('#avatarImg').cropper('getCroppedCanvas').toBlob(function (blob) {
          var formData = new FormData();
          formData.append('file', blob);
            ItemSrv.upload().add(formData)
              .$promise.then(function(response){
              if(response.errCode === 0){
                $scope.item.url = response.data;
              }
            },function (response) {
              NoticeSrv.error("上传图片错误,http状态码:"+response.status);
            });
          });

        $('#avatar-modal').modal('hide');
      };


      /**
       * @description:　图片旋转
       * @param:
       * @return:
       */

      $scope.rotate_left =function () {
        $('#avatarImg').cropper("rotate",-90);
      };

      $scope.rotate_right =function () {
        $('#avatarImg').cropper("rotate",90);
      };


      /**
       * @description:　借出设备
       * @param: item 借出设备的详情
       * @return:
       */

      $scope.newBorrow = function(item){
        $scope.isDisabled = true;
        $scope.record = {
          itemName: item.itemName,
          name: '',
          phone:'',
          email:''
        } ;
        $scope.form2.$setUntouched()
      };

      $scope.borrow_submit = function () {
          var record = Object.assign({},$scope.record);
          record.borrowedTime =  Date.parse(new Date()) +"";
          record.borrowOperator = TokenSrv.getToken();

          BorrowSrv.addRecord().add(record)
            .$promise.then(function(response){
            if(response.errCode === 0){
              NoticeSrv.success("新建成功");
              getItem();
              $('#newBorrow').modal('hide');
              $scope.form2.$setUntouched()
            }
          },function (response) {
            NoticeSrv.error("新建借出错误,http状态码:"+response.status);
          });
      };

      $scope.searchStudent = function () {
        var data = {
          studentNumber: $scope.use.studentNumber + ''
        };
        ItemSrv.search().add(data).$promise.then(function(response){
          if(response.errCode === 0){
            $scope.selectOptions = response.data;
          }
        },function (response) {
          NoticeSrv.error("搜索错误,http状态码:"+response.status);
        });
      }

      /**
       * @description:　分配设备
       * @param: item 分配设备的详情
       * @return:
       */

      $scope.newUse = function(item){
        $scope.isDisabled = true;
        $scope.use = {
          itemName: item.itemName,
          name: '',
          studentNumber:'',
          number:''
        } ;
        $scope.form3.$setUntouched()
      };


      $scope.use_submit = function () {
        var data = Object.assign({},$scope.use);
        data.borrowedTime =  Date.parse(new Date()) +"";

        UseSrv.addRecord().add(data)
          .$promise.then(function(response){
          if(response.errCode === 0){
            NoticeSrv.success("新建成功");
            getItem();
            $('#newUse').modal('hide');
            $scope.form3.$setUntouched();
          }
        },function (response) {
          NoticeSrv.error("新建分配错误,http状态码:"+response.status);
        });
      };

    }]);



