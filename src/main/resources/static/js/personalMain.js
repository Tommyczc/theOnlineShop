var app = new Vue({
    el: '#app',

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
    },
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
                desc: ''
            },
            formLabelWidth: '120px',
        }
    },
});