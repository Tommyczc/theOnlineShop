var app = new Vue({
    el: '#app',

data() {
    return {
        form: {
            name: '',
            region: '',
            date1: '',
            date2: '',
            delivery: false,
            type: [],
            resource: '',
            desc: '',

            dialogImageUrl: '',
            dialogVisible: false,
            disabled: false
        }
    }
},
methods: {
    noteMessage(message,type) {
        this.$message({
            showClose: true,
            message:message,
            type:type
        });
    },
    onSubmit() {
        console.log('submit!');

        let formData=new FormData();
        for(let key in this.form){
            formData.append(key,this.form[key]);
            console.log(formData.get(key));
        }

        axios({
            method: 'post',
            url: '/system/uploadProduct/upload',
            data: formData,
        })
            .then(function (response) {
                console.log(response);
                app.noteMessage(response['data']['messageBody'],response['data']['messageType']);
                // top.location.reload();
            })
            .catch(function (error) {
                alert(error);
            });
    },
    handleRemove(file) {
        console.log(file);
    },
    handlePictureCardPreview(file) {
        this.dialogImageUrl = file.url;
        this.dialogVisible = true;
    },
    handleDownload(file) {
        console.log(file);
    }
}
})