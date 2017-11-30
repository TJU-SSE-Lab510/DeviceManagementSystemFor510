'use strict';
mrmedia.service('UploadSrv',[
        'Upload',
        'baseURL',
        function (Upload, baseURL) {
            this.upload = function (file, options) {
                var data = {file: file};
                if (options.type === "image/*") {
                    data = {image: file};
                }
                if (options.type === "video/*") {
                    data = {video: file};
                }
                return Upload.upload({
                            url: baseURL + options.url,
                            headers: {'Content-Type': 'mutipart/form-data'},
                            data: data,
                        });
            };

    }]);
