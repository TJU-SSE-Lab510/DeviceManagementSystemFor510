'use strict';
labsystem.factory('TokenSrv', function () {
        return {

          token: '',
          auth:'',
          url:'',
          setToken: function (token) {
            this.token = token;
          },
          getToken: function () {
            return this.token;
          },
          setAuth: function (auth) {
            this.auth = auth;
          },
          getAuth: function () {
            return this.auth;
          },
          setUrl: function (url) {
            this.url = url;
          },
          getUrl: function () {
            return this.url;
          }
        };
    });
