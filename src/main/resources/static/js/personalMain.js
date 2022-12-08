var app = new Vue({
    el: '#app',
    data(){
        return {
            show: true,
            dialogFormVisible: false,
            form: {
                name: '',
                region: '',
                date1: '',
                date2: '',
                delivery: false,
                type: [],
                resource: '',
                desc: '',
                age:'',
                address:''
            },
            formLabelWidth: '120px',
        }
    },
    methods:{
        handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },

        onSuccess(response, file, fileList){
            top.location.reload();
            if(response['messageType']=="success"){
                this.$message.success(response['messageBody'])
            }
            else(this.$message.error(response['messageBody']))
        },
        noteMessage(message,type) {
            this.$message({
                showClose: true,
                message:message,
                type:type
            });
        },

        submitForm(){
            this.dialogFormVisible=false;
            let formData = new FormData();
            formData.append("age",this.form.age);
            formData.append("address",this.form.address);
            if(this.form.age.trim()!="" && this.form.address.trim()!="") {

                axios({
                    method: 'post',
                    url: '/user/updateInformation',
                    data: formData,
                })
                    .then(function (response) {
                        app.noteMessage(response['data']['messageBody'],response['data']['messageType']);

                    })
                    .catch(function (error) {
                        alert(error);
                    });
            }
            else{this.noteMessage("please enter message!","warm")}
        }
    }
});