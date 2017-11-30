'use strict';

/**
 * @ngdoc overview
 * @name shuapiaoBackWebApp
 * @description
 * # shuapiaoBackWebApp
 *
 * Main module of the application.
 */

var mrmedia = angular.module('BackWebApp', [
    'ngAnimate',
    'ngMessages',
    'ngResource',
    'ui.router',
    'smart-table',
    'ngFileUpload',
    'ui.bootstrap'
]);
mrmedia.config(function($httpProvider) {
  $httpProvider.interceptors.push('myInterceptor');
});
mrmedia.value('baseURL', 'http://127.0.0.1:8080');

mrmedia.controller('MainCtrl', function ($state) {
  // $state.go('app.back.home');

});


mrmedia.config(function ($stateProvider, $urlRouterProvider) {


  $urlRouterProvider.otherwise("/itemList");
  $stateProvider
    .state('app', {
      url: '/',
      templateUrl: 'views/main.html'
    })


    .state('app.userlist', {
      url: 'itemList',
      templateUrl: 'views/user-list.html',
      controller: 'UserListCtrl'
    })

    .state('app.backuser', {
      url: 'backuser',
      templateUrl: 'views/backuser.html',
      controller: 'BackuserCtrl'
    })

    .state('app.modifyPWD', {
      url: 'modifyPWD',
      templateUrl: 'views/modify-pwd.html',
      controller: 'ModifyPWDCtrl'
    })

    .state('login', {
      url:'/login',
      templateUrl : 'views/login.html',
      controller: 'LoginCtrl'
    })

});
