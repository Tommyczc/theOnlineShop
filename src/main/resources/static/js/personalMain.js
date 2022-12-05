var app = new Vue({
    el: '#app',

    methods:{
        handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
    },
    data(){
        return {
            show: true
        }
    },
});