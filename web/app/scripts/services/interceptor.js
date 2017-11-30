'use strict';
mrmedia.factory('myInterceptor',
        ['$rootScope', '$injector', 'TokenSrv', 'baseURL',
         function($rootScope, $injector, token, baseURL) {
            var myInterceptor = {
                request: function (config) {
                    // 通过配置拦截器在发送请求前注入token
                    if (arguments[0].url.indexOf(baseURL) !== -1) {
                        // 2016.6.3改 所有的请求都在url中注入token
                        arguments[0].params = arguments[0].params || {};
                        arguments[0].params.token = token.getToken();
                        if (arguments[0].method === 'GET' || arguments[0].method === 'DELETE' || arguments[0].method === 'OPTIONS'||arguments[0].method === 'POST' || arguments[0].method === 'PUT') {
                            arguments[0].params = arguments[0].params || {};
                            arguments[0].params.token = token.getToken();
                        }
                    }

                    var $state = $injector.get('$state');
                    return config;
                },
                response: function (response) {
                    if (response.data.errCode && response.data.errCode !== 0) {
                        $injector.get('NoticeSrv').error($injector.get('ErrorSrv').getError(response.data.errCode + ""));
                        console.log(response);
                    }


                    return response;
                }
            };
            return myInterceptor;
    }]);
