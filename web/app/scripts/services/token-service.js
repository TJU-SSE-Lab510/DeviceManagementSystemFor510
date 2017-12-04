'use strict';
labsystem.factory('TokenSrv', function () {
        return {

          token: '',
          auth:'',
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
          }
        };
    });
