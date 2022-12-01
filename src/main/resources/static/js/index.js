
var app = new Vue({
    el: '#app',
    loading:undefined,
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
            loading = this.$loading({
                lock: false,
                text: 'Loading',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.6)'
            });
            setTimeout(() => {
                loading.close();
                this.noteMessage("Sorry, there is something wrong","warning")
            }, 5000);
        },

        closeLoading(){
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
        insertIframe(storage.get("url"));
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
        $('.mainContent .RuoYi_iframe').each(function() {
            if ($(this).data('id') == href) {
                if($(this).data('id')=="/logout"){
                    window.open($(this).data('id'));
                    isfound=true;
                }
                else {
                    $(this).show().siblings('.RuoYi_iframe').hide();
                    // app.openFullScreen();
                    //
                    // $('.mainContent iframe:visible').on('load', function() {
                    //     app.closeLoading();
                    // });
                    //isRefresh = $(this).data('refresh');
                    //alert($(this).data('id'));
                    isfound = true;
                    storage.set("url", href);
                    return false;
                }
            }
        });
        if(!isfound) {
            insertIframe(href);
        }
    }

    function refreshTab() {
        var currentId = $('.page-tabs-content').find('.active').attr('data-id');
        var target = $('.RuoYi_iframe[data-id="' + currentId + '"]');
        var url = target.attr('src');
        target.attr('src', url).ready();
    }

    function updateIframe(url){

    }

    function insertIframe(url){
        var iframe=getMainIframe();
        if(iframe==undefined){
            alert("empty iframe");
            return;
        }
        iframe.attr("data-id",url);
        iframe.attr("src",url);
        $('.mainContent').find('iframe.RuoYi_iframe').hide().parents('.mainContent').append(iframe);

        //加载页面
        app.openFullScreen();

        $('.mainContent iframe:visible').on('load', function() {
            app.closeLoading();
        });
        storage.set("url", url);
        // iframe.show().siblings('.RuoYi_iframe').hide();
    }
});







