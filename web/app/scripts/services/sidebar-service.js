'use strict';
mrmedia.service('SidebarSrv',  [function() {
  this.getSidebar = function() {
    return [
      {
        id:0,
        name: "APP用户管理",
        icon: "glyphicon glyphicon-user",
        collapse: true,
        subMenu: [{
          name: "用户列表",
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
      },
      {
        id:1,
        name: "任务管理",
        icon: "glyphicon glyphicon-list-alt",
        collapse: true,
        subMenu: [{
          name: "任务列表",
          url: "app.task.list"
        }]
      },
      {
        id:2,
        name: "工单审核",
        icon: "glyphicon glyphicon-pencil",
        collapse: true,
        subMenu: [{
          name: "工单领取",
          url: "app.orders"
        }]
      },
      {
        id:3,
        name: "支付管理",
        icon: "glyphicon glyphicon-usd",
        collapse: true,
        subMenu: [{
          name: "提现管理",
          url: "app.cash-apply-assess"
        },
        {
          name: "财务对账",
          url: "app.cash-reconciliation"
        },
        {
          name: "工单结算",
          url: "app.order-settle"
        }]
      },
      {
        id:4,
        name: "后台管理",
        icon: "glyphicon glyphicon-list",
        collapse: true,
        subMenu: [{
          name: "后台用户列表",
          url: "app.backuser.list"
        },
        {
          name: "创建后台用户",
          url: "app.backuser.create"
        }]
      },
      {
        id:5,
        name: "商城管理",
        icon: "glyphicon glyphicon-shopping-cart",
        collapse: true,
        subMenu: [{
          name: "新增商品",
          url: "app.item.edit"
        },
        {
          name: "商品管理",
          url: "app.item.manage"
        },
        {
          name: "商品发货",
          url: "app.item.deliver"
        }]
      },
      {
        id:6,
        name: "广告管理",
        icon: "glyphicon glyphicon-credit-card",
        collapse: true,
        subMenu: [{
          name: "广告列表",
          url: "app.ad.list"
        }]
      }];
    };
}]);
