var app = new Vue({
    el: '#app'
});

$(function(){
    $('.openWindow').on('click', openWindow);

    function openWindow(){
        var href=$(this).attr('href');
        top.location.href=href;
    }
})