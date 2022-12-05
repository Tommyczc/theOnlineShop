let loading;
var app = new Vue({
    el: '#app',
    methods: {
        tableRowClassName({row, rowIndex}) {
            if (rowIndex === 1) {
                return 'warning-row';
            } else if (rowIndex === 3) {
                return 'success-row';
            }
            return '';
        },

        noteSuccess(title,message,type) {
            this.$notify({
                title: '成功',
                message: '这是一条成功的提示消息',
                type: 'success'
            });
        },

        noteMessage(message,type) {
            this.$message({
                showClose: true,
                message:message,
                type:type
            });
        },

        openFullScreen() {
            loading=this.$loading({
                lock: false,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.6)'
            });
            // setTimeout(() => {
            //     loading.close();
            //     this.noteMessage("Sorry, there is something wrong","warning")
            // }, 5000);
        },

        closeLoading(){
            // let loading=this.$loading();
            loading.close();
        },
    },
    data() {
        return {
            // tableData: [{
            //     date: '2016-05-02',
            //     name: '王小虎',
            //     address: '上海市普陀区金沙江路 1518 弄',
            // }, {
            //     date: '2016-05-04',
            //     name: '王小虎',
            //     address: '上海市普陀区金沙江路 1518 弄'
            // }, {
            //     date: '2016-05-01',
            //     name: '王小虎',
            //     address: '上海市普陀区金沙江路 1518 弄',
            // }, {
            //     date: '2016-05-03',
            //     name: '王小虎',
            //     address: '上海市普陀区金沙江路 1518 弄'
            // }]
        }
    }
});



var storage = {
    set: function(key, value) {
        window.localStorage.setItem(key, value);
    },
    get: function(key) {
        return window.localStorage.getItem(key);
    },
    remove: function(key) {
        window.localStorage.removeItem(key);
    },
    clear: function() {
        window.localStorage.clear();
    }
};



$(function(){
    $('.menuItem').on('click', menuItem);
    if(storage.get("url")!=null){
        var isFound=false;
        $('.mainContent .RuoYi_iframe').each(function() {
            if ($(this).data('id') == storage.get("url")) {
                isFound=true;
            }
        });
        if(!isFound) {
            insertIframe(storage.get("url"));
        }
        refreshTab();
    }

    function getMainIframe(){
        var main;
        var i=0;
        $('.mainContent .RuoYi_iframe').each(function() {
            i++;
            if($(this).attr("name")=="iframe0"){
                main=$(this).clone();
            }
        });
        if(main!=undefined) {
            main.attr("name","iframe" + (i+1));
        }
        return main;
    }


    function menuItem(){
        var href=$(this).attr('href');
        var isfound=false;
        //登出
        if(href=="/logout"){
            top.location.href=href;
            isfound=true;
            return true;
        }
        $('.mainContent .RuoYi_iframe').each(function() {
            if ($(this).data('id') == href) {
                $(this).show().siblings('.RuoYi_iframe').hide();
                // app.openFullScreen();
                //
                // $('.mainContent iframe:visible').on('load', function() {
                //     app.closeLoading();
                // });
                //isRefresh = $(this).data('refresh');
                isfound = true;
                storage.set("url", href);
                return false;
            }
        });
        if(!isfound) {
            insertIframe(href);
        }
    }

    function refreshTab() {
        var currentId;
        $('.mainContent .RuoYi_iframe').each(function() {
            if($(this).is(":visible")){
                currentId=$(this).data('id');
            }
        });

        if(currentId!=null || currentId!=undefined){
            $('.menuItem').each(function(){
                if($(this).attr('href')==currentId){
                    $(this).click();
                }
            });
        }
    }

    function loadTimeOut(){
        app.noteMessage("Sorry, there is something wrong","warning");
        app.closeLoading();
    }

    function insertIframe(url){
        let iframe=getMainIframe();
        if(iframe==undefined){
            alert("empty iframe");
            return;
        }
        iframe.attr("data-id",url);
        iframe.attr("src",url);

        $('.mainContent').find('iframe.RuoYi_iframe').hide().parents('.mainContent').append(iframe.show());

        //加载页面
        app.openFullScreen();
        var kill = setTimeout(loadTimeOut, 5000);
        iframe.on('load', function() {
            clearTimeout(kill);
            app.closeLoading();
        });
        storage.set("url", url);
        // iframe.show().siblings('.RuoYi_iframe').hide();
    }


});

function logout(){
    var href=$(this).attr('href');
    top.location.href=href;
}







