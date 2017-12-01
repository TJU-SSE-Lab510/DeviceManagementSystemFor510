'use strict';
labsystem.factory('TokenSrv', function () {
        return {

          token: '',
          setToken: function (token) {
              this.token = token;
          },
          getToken: function () {
              return this.token;
          }
        };
    });
