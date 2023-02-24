var app = new Vue({
    el: '#app',
    data() {
        var checkCode = (rule, value, callback) => {
            if (!value) {
                return callback(new Error('年龄不能为空'));
            }
            // setTimeout(() => {
            //     if (!Number.isInteger(value)) {
            //         callback(new Error('请输入数字值'));
            //     } else {
            //         callback();
            //     }
            // }, 1000);
        };
        var validatePass = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (this.ruleForm.checkPass !== '') {
                    this.$refs.ruleForm.validateField('checkPass');
                }
                callback();
            }
        };
        var validatePass2 = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请再次输入密码'));
            } else if (value !== this.ruleForm.pass) {
                callback(new Error('两次输入密码不一致!'));
            } else {
                callback();
            }
        };
        return {
            ruleForm: {
                pass: '',
                checkPass: '',
                verificationCode: ''
            },
            rules: {
                pass: [
                    { validator: validatePass, trigger: 'blur' }
                ],
                checkPass: [
                    { validator: validatePass2, trigger: 'blur' }
                ],
                verificationCode: [
                    { validator: checkCode, trigger: 'blur' }
                ]
            }
        };
    },
    methods:{
        changePassword(){
            let formData = new FormData();
            formData.append("emailVerificationCode",this.ruleForm.verificationCode);
            formData.append("pass",this.ruleForm.pass);
            formData.append("checkPass",this.ruleForm.checkPass);

            axios({
                method: 'post',
                url: '/user/passwordChange',
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

        noteMessage(message,type) {
            this.$message({
                showClose: true,
                message:message,
                type:type
            });
        },
        getCode(){
            let formData = new FormData();
            // formData.append("email",this.form.age);
            // formData.append("address",this.form.address);
            axios({
                method: 'post',
                url: '/user/updatePasswordVerificationCode',
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
        }
    }
})