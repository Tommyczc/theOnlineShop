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
    onSubmit() {
        console.log('submit!');

        axios({
            method: 'post',
            url: '/user/passwordChange',
            data: this.form,
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