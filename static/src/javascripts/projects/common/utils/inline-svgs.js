define([
    'common/utils/template',
    'text!common/views/inline-svgs.html',
], function (
    template,
    inlineSvgTemplate
) {
    return function (file, name, type, classes) {
        type = type || '';
        classes = (classes || []).map(function (_class) {
            return 'inline-' + _class;
        }).join(' ');

        return template(inlineSvgTemplate, {file: file, name: name, type: type, classes: classes});
    }
})
