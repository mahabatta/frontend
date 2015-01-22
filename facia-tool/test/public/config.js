System.amdDefine('test-config', [
    'jquery',
    'jquery-mockjax'
], function (
    $,
    mockjax
) {
    // curl.config({
    //     baseUrl: "base/public/js/",
    //     paths: {
    //         views:         '../../app/views',
    //         css:           '../css',
    //         test:          '../../test/public'
    //     }
    // });

    $.mockjaxSettings.logging = false;
    $.mockjaxSettings.responseTime = 50;
});
