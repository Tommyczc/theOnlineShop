var app = new Vue({
    el: '#app',
    created() {
        this.initWebSocket();
    },
    destroyed() {
        this.websock.close(); //离开路由之后断开websocket连接
    },
    methods:{

        async initWebSocket() {
            const userInformation= await this.requestConnectionDetail();
            if(userInformation.userName===undefined){
                return;
            }
            //初始化weosocket
            const wsuri = "ws://"+userInformation.ip+":"+userInformation.port+"/Web/"+userInformation.userName;
            this.websock = new WebSocket(wsuri);
            // 客户端接收服务端数据时触发
            this.websock.onmessage = this.websocketonmessage;
            // 连接建立时触发
            this.websock.onopen = this.websocketonopen;
            // 通信发生错误时触发
            this.websock.onerror = this.websocketonerror;
            // 连接关闭时触发
            this.websock.onclose = this.websocketclose;
        },
        requestConnectionDetail(){
            return new Promise((resolve, reject) => {
                axios({
                    method: 'post',
                    url: '/admin/getSocketConnection',
                    //data: formData,
                })
                    .then(function (response) {
                        console.log("this is the response: -----\n" + JSON.stringify(response));
                        if (response.data.SocketConnection.normalize() == "allow".normalize()) {
                            console.log("Find user connection detail: ", response.data.userName);
                            const finalData={userName:response.data.userName,ip:response.data.ip,port:response.data.port}
                            resolve(finalData);
                        } else {
                            console.log("Cannot find user name");
                            reject(undefined);
                        }
                    })
                    .catch(function (error) {
                        //alert(error);
                        reject(undefined);
                    });
            })
        },
        // 连接建立时触发
        websocketonopen() {
            console.log("WebSocket Connected")
            //连接建立之后执行send方法发送数据
            // let actions = {"room":"007854ce7b93476487c7ca8826d17eba","info":"1121212"};
            // this.websocketsend(JSON.stringify(actions));
        },
        // 通信发生错误时触发
        websocketonerror() {
            console.log("出现错误");
            this.reconnect();
        },
        // 客户端接收服务端数据时触发
        websocketonmessage(e) {
            console.log(e.data);
        },
        websocketsend(Data) {
            //数据发送
            this.websock.send(Data);
        },
        // 连接关闭时触发
        websocketclose(e) {
            //关闭
            console.log("断开连接", e);
        },
        reconnect() {
            //重新连接
            var that = this;
            if (that.lockReconnect) {
                return;
            }
            that.lockReconnect = true;
            //没连接上会一直重连，设置延迟避免请求过多
            that.timeoutnum && clearTimeout(that.timeoutnum);
            that.timeoutnum = setTimeout(function () {
                //新连接
                that.initWebSocket();
                that.lockReconnect = false;
            }, 5000);
        },
        // reset() {
        //     //重置心跳
        //     var that = this;
        //     //清除时间
        //     clearTimeout(that.timeoutObj);
        //     clearTimeout(that.serverTimeoutObj);
        //     //重启心跳
        //     that.start();
        // },

    },
    data(){
        return{
            tableData: [{
                id: 1,
                date: '2016-05-02',
                account: 'Tommy',
                node: '165.35.435.234',
                numOdInstance:0,
                status:'connected'
            }, {
                id: 2,
                date: '2016-05-04',
                account: 'Tommy',
                node: '165.35.435.234',
                numOdInstance:0,
                status:'connected'
            }, {
                id: 3,
                date: '2016-05-01',
                account: 'Tommy',
                node: '165.35.435.234',
                numOdInstance:0,
                status:'connected',
                children: [{
                    id: 31,
                    date: '2016-05-01',
                    account: 'Tommy',
                    node: '165.35.435.234',
                    numOdInstance:0,
                    status:'connected'
                }, {
                    id: 32,
                    date: '2016-05-01',
                    account: 'Tommy',
                    node: '165.35.435.234',
                    numOdInstance:0,
                    status:'connected'
                }]
            }, {
                id: 4,
                date: '2016-05-03',
                account: 'Tommy',
                node: '165.35.435.234',
                numOdInstance:0,
                status:'connected'
            }]

        }
    }
});