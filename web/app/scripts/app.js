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
  $httpProvider.defaults.withCredentials = true;
});
labsystem.value('baseURL', 'http://100.64.236.97:8080/labsystem');

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


    .state('app.borrowlist', {
      url: 'borrowedList',
      templateUrl: 'views/borrowed-list.html',
      controller: 'BorrowListCtrl'
    })

    .state('app.uselist', {
      url: 'useList',
      templateUrl: 'views/use-list.html',
      controller: 'UseListCtrl'
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

    .state('app.profile', {
      url: 'profile',
      templateUrl: 'views/profile.html',
      controller: 'ProfileCtrl'
    })

    .state('login', {
      url:'/login',
      templateUrl : 'views/login.html',
      controller: 'LoginCtrl'
    })

    .state('app.userDetail', {
      url: 'user/:id',
          templateUrl: 'views/user-detail.html',
          controller: 'UserDetailCtrl'
    })

});
