(function(d) {
    [].slice.call(d.querySelectorAll('a.fb-share')).forEach(el => {
        el.href = 'https://www.facebook.com/sharer/sharer.php?u=' + location.href;
        el.target = '_blank';
    });
    [].slice.call(d.querySelectorAll('a.do-bookmark')).forEach(el => {
        el.href = location.href;
        el.addEventListener('click', function(e) {
            e.preventDefault();

            if (window.sidebar && window.sidebar.addPanel) { // Firefox <23
                window.sidebar.addPanel(document.title,window.location.href,'');
            } else if(window.external && ('AddFavorite' in window.external)) { // Internet Explorer
                window.external.AddFavorite(location.href,document.title); 
            } else if(window.opera && window.print || window.sidebar && ! (window.sidebar instanceof Node)) { // Opera <15 and Firefox >23
                triggerBookmark.attr('rel', 'sidebar').attr('title', document.title).attr('href', window.location.href);
                return true;
            } else { // For the other browsers (mainly WebKit) we use a simple alert to inform users that they can add to bookmarks with ctrl+D/cmd+D
                alert('You can add this page to your bookmarks by pressing ' + (navigator.userAgent.toLowerCase().indexOf('mac') != - 1 ? 'Command/Cmd' : 'CTRL') + ' + D on your keyboard.');
            }
            return false;
        });
    });
    [].slice.call(d.querySelectorAll('a.do-save')).forEach(el => {
        el.href = location.href;
        el.download = document.title;
    });
})(document);