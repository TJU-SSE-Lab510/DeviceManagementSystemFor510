# 实验室资产管理系统


## 使用css框架

1. Bootstrap
2. AdminLTE（基于Bootstrap构建）

## 使用angular插件

1. angular-ui-router
2. angular-resource
3. angular-messages
4. angular-bootstrap
5. angular-smart-table
6. angular-sanitize
7. textAngular


## 项目构建

项目采用bower做为js库的包管理工具，npm做为开发工具库的管理工具。

在命令行输入以下指令安装项目依赖库：

```
bower install
```

在命令行输入以下指令安装项目开发工具库：

```
(sudo) npm install
```

使用以下命令可以编译工程项目：

```
gulp
```

使用以下命令可以模拟运行项目

```
gulp serve
```



## 项目目录

网页代码在`app/`目录下，css文件位于`app/styles`文件夹下，js文件位于`app/scripts`目录下的对应文件夹中

网页子页面位于`app/views`中

`app.js`中配置整个项目的route和angular，子页面的controller位于scripts/controllers文件夹中
