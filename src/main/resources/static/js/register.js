function submitEmailCheckRequest(emailData){
    $.ajax({
        type: "post",
        url: "/emailCheck",
        dataType: "json",
        //async: false,
        data:emailData,
        cache: false,
        processData : false,//用于对data参数进行序列化处理 这里必须false
        contentType: false,
        //contentType: "application/json;charset=utf8",
        success: function(data) {
            if(data['messageType']=='success') {
                alert(data["messageBody"]);
                document.getElementById('codeLabel').style.display="block";
            }
            else{
                alert("error: "+data['messageBody']);
            }
            //return false;
        },

        error:function(e){
            alert("error: "+e.responseText);
            return false;
        }
    });
    //return false;
}

function submitRegisterRequest(formData){
    $.ajax({
        type: "post",
        url: "/registerLogic",
        dataType: "json",
        //async: false,
        data:formData,
        cache: false,
        processData : false,//用于对data参数进行序列化处理 这里必须false
        contentType: false,
        //contentType: "application/json;charset=utf8",
        success: function(data) {
            if(data['messageType']=='success') {
                alert(data["responseBody"]);
            }
        },

        error:function(e){
            alert("error: "+e.responseText);
        }
    });
}