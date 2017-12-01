'use strict';

/**
 * @ngdoc overview
 * @name shuapiaoBackWebApp
 * @description
 * # shuapiaoBackWebApp
 *
 * Main module of the application.
 */

var labsystem = angular.module('BackWebApp', [
    'ngAnimate',
    'ngMessages',
    'ngResource',
    'ui.router',
    'smart-table',
    'ngFileUpload',
    'ui.bootstrap'
]);
labsystem.config(function($httpProvider) {
  $httpProvider.interceptors.push('myInterceptor');
});
labsystem.value('baseURL', 'http://127.0.0.1:8080');

labsystem.controller('MainCtrl', function ($state) {
  // $state.go('app.back.home');

});


labsystem.config(function ($stateProvider, $urlRouterProvider) {


  $urlRouterProvider.otherwise("/itemList");
  $stateProvider
    .state('app', {
      url: '/',
      templateUrl: 'views/main.html'
    })


    .state('app.userlist', {
      url: 'borrowedList',
      templateUrl: 'views/borrowed-list.html',
      controller: 'BorrowListCtrl'
    })

    .state('app.itemlist', {
      url: 'itemList',
      templateUrl: 'views/item-list.html',
      controller: 'ItemListCtrl'
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
