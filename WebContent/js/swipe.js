var hiddenOptions = function() {
    return {
        open: function(elm) {
            elm.style.WebkitTransform = 'translateX(-140px)';
            elm.style.msTransform = 'translateX(-140px)';
            elm.style.transform = 'translateX(-140px)';
        },
        close: function(elm) {
            elm.parentNode.style.cursor = 'default';
            elm.style.WebkitTransform = 'translateX(0px)';
            elm.style.msTransform = 'translateX(0px)';
            elm.style.transform = 'translateX(0px)';
        },
        startMoving: function(elm, container, evt) {
            evt = evt || window.event;
            var _this = this,
                posX = evt.clientX || evt.changedTouches[0].clientX,
                eWi = parseInt(elm.style.width),
                cWi = parseInt(container.style.width);
            container.style.cursor = 'move';
            var startX = posX;
            
            var detectMovement = function(evt) {
                evt = evt || window.event;
                var posX = evt.clientX || evt.changedTouches[0].clientX,
                aX = posX - startX;
                if (aX < -20) _this.open(elm);
                if (aX > 20) _this.close(elm);
            };
            document.onmousemove = detectMovement;
            document.ontouchmove = detectMovement;
            
            elm.addEventListener('mouseup', this.stopMoving);
            elm.addEventListener('touchend', this.stopMoving);
        },
        stopMoving: function() {
            this.parentNode.style.cursor = 'default';
            document.onmousemove = function() {};
            document.ontouchmove = function() {};
        },
    }
}();

jQuery(document).ready(function($) {
    $('.list-item .list-item-wrapper').on('mousedown touchstart', function(event) {
        hiddenOptions.startMoving(this, this.parentNode, event);
    });
    $('.list-item-options .cancel').on('click', function(event) {
        hiddenOptions.close($(this).closest('.list-item').find('.list-item-wrapper')[0]);
    });
});