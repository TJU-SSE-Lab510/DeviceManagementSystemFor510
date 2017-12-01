'use strict';
labsystem.service('SidebarSrv',  [function() {
  this.getSidebar = function() {
    return [
      {
        id:0,
        name: "APP用户管理",
        icon: "glyphicon glyphicon-user",
        collapse: true,
        subMenu: [{
          name: "借出列表",
          url: "app.user.list"
        },
        {
          name: "创建用户",
          url: "app.user.create"
        },
        {
          name: "黑名单列表",
          url: "app.user.blacklist"
        }]
      }]
    };
}]);
