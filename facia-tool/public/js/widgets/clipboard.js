/* global _ */
define([
    'knockout',
    'models/group',
    'modules/copied-article',
    'utils/mediator',
    'utils/update-scrollables'
], function (
    ko,
    Group,
    copiedArticle,
    mediator,
    updateScrollables
) {
    var updateClipboardScrollable = function (what) {
        var onClipboard = true;
        if (what && what.targetGroup) {
            onClipboard = what.targetGroup.parentType === 'Clipboard';
        }
        if (onClipboard) {
            _.defer(updateScrollables);
        }
    };

    mediator.on('collection:updates', updateClipboardScrollable);
    mediator.on('ui:close', updateClipboardScrollable);
    mediator.on('ui:omit', updateClipboardScrollable);
    mediator.on('ui:resize', updateClipboardScrollable);

    function Clipboard (params) {
        var listeners = mediator.scope();

        this.uiOpenElement = ko.observable();
        this.column = params.column;
        this.group = new Group({
            parentType: 'Clipboard',
            keepCopy:  true,
            front: null,
            elementHasFocus: this.elementHasFocus.bind(this)
        });
        this.listeners = listeners;

        listeners.on('ui:open', this.onUIOpen.bind(this));
        listeners.on('copied-article:change', this.onCopiedChange.bind(this));

        this.hasCopiedArticle = ko.observable(false).extend({ notify: 'always' });
        this.inCopiedArticle = ko.pureComputed(function () {
            var inMemory = this.hasCopiedArticle() && copiedArticle.peek();
            console.log(inMemory);
            return inMemory ? inMemory.article.id : null;
        }, this);
        this.dropdownOpen = ko.observable(false);
    }

    Clipboard.prototype.elementHasFocus = function (meta) {
        return meta === this.uiOpenElement();
    };

    Clipboard.prototype.onUIOpen = function (element, article, front) {
        if (!front) {
            this.uiOpenElement(element);
        }
        updateClipboardScrollable(article ? {
            targetGroup: article.group
        } : null);
    };

    Clipboard.prototype.onCopiedChange = function (hasArticle) {
        this.hasCopiedArticle(hasArticle);
    };

    return Clipboard;
});
