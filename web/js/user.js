function getAllUser() {
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/user/getAllUser',	//web.xml中注册的Servlet的url-pattern
        data : {},
        dataType : 'json', //返回数据形式为json
        success : function(data) {
            var length = getJsonLength(data);
            if (data.success == false) {
                alert("查询失败");
            }else{
                setSuccessInfo("查询学生信息成功");
                var str = "";
                for (var i = 0; i < length; i++){
                    str += "<tr id='" + data.user[i].id +"'>"+
                        "<td>" + data.user[i].id + "</td>" +
                        "<td>" + data.user[i].userName + "</td>" +
                        "<td>" + data.user[i].userSex + "</td>" +
                        "<td>" + data.user[i].className + "</td>" +
                        "<td><button id='m" + data.user[i].id + "' type='button' style='margin-left: -15%' class='btn btn-info'  data-target=\"#modifyModal\" data-toggle=\"modal\" onclick='getUserById("+ data.user[i].id + ")'>编辑</button>" +
                        "    <button id='d" + data.user[i].id +"' type='button' style='margin-left: 10px' class='btn btn-info'  onclick='deleteUser("+ data.user[i].id + ")'>删除</button></td>"
                        + "</tr>";
                }
                document.getElementById("showData").innerHTML = str;
            }
        },
        error : function(errorMsg) {
            console.log("Login Erro !");
        }
    });
}

function getAllUserWithoutInfo() {
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/user/getAllUser',	//web.xml中注册的Servlet的url-pattern
        data : {},
        dataType : 'json', //返回数据形式为json
        success : function(data) {
            var length = getJsonLength(data);
            if (data.success == false) {
                setErrorInfo("查询信息失败");
            }else{
                var str = "";
                for (var i = 0; i < length; i++){
                    str += "<tr id='" + data.user[i].id +"'>"+
                        "<td>" + data.user[i].id + "</td>" +
                        "<td>" + data.user[i].userName + "</td>" +
                        "<td>" + data.user[i].userSex + "</td>" +
                        "<td>" + data.user[i].className + "</td>" +
                        "<td><button id='m" + data.user[i].id + "' type='button' style='margin-left: -15%' class='btn btn-info' data-target=\"#modifyModal\" data-toggle=\"modal\" onclick='getUserById("+ data.user[i].id + ")'>编辑</button>" +
                        "    <button id='d" + data.user[i].id +"' type='button' style='margin-left: 10px' class='btn btn-info'  onclick='deleteUser("+ data.user[i].id + ")'>删除</button></td>"
                        + "</tr>";
                }
                document.getElementById("showData").innerHTML = str;
            }
        },
        error : function(errorMsg) {
            setErrorInfo("查询信息失败");
            console.log("QueryError !");
        }
    });
}

function deleteUser(userId) {
    console.log(userId);
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/user/deleteUserById',
        data : {"userId":userId},
        dataType : 'json', //返回数据形式为json
        success : function(data) {
            if(data.success = false){
                setErrorInfo("删除学生信息数据失败");
            }else {
                setSuccessInfo("删除数据成功");
                getAllUserWithoutInfo();
            }
        },
        error : function(errorMsg) {
            setErrorInfo("删除学生信息数据失败");
            console.log("deleteErrorException !");
        }
    });
}

function insertUser() {
    var userId = document.getElementById("userId").value;
    var userName = document.getElementById("userName").value;
    var userSex = document.getElementById("userSex").value;
    var className = document.getElementById("className").value;
    var databaseName = document.getElementById("option").value;
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/user/insertUser',	//web.xml中注册的Servlet的url-pattern
        data : {"userId":userId, "userName":userName, "userSex":userSex, "className":className, "databaseName": databaseName},
        dataType : 'json', //返回数据形式为json
        success : function(data) {
            if(data.success = false){
                setErrorInfo("插入学生信息数据失败");
            }else {
                setSuccessInfo("插入学生信息成功");
                getAllUserWithoutInfo();
                $('#btn-insert-close').trigger("click");
            }
        },
        error : function(errorMsg) {
            setErrorInfo("插入学生信息数据失败");
            console.log("InsertErrorException !");
        }
    });
}

function updateUser() {
    var userId = document.getElementById("userIdOfModify").value;
    var userName = document.getElementById("userNameOfModify").value;
    var userSex = document.getElementById("userSexOfModify").value;
    var className = document.getElementById("classNameOfModify").value;
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/user/updateUser',	//web.xml中注册的Servlet的url-pattern
        data : {"userId":userId, "userName":userName, "userSex":userSex, "className":className},
        dataType : 'json', //返回数据形式为json
        success : function(data) {
            if(data.success = false){
                setErrorInfo("修改学生信息数据失败");
            }else {
                setSuccessInfo("修改学生信息成功");
                getAllUserWithoutInfo();
                $('#btn-query-close').trigger("click");
            }
        },
        error : function(errorMsg) {
            setErrorInfo("修改学生信息数据失败");
            console.log("UpdateErrorException !");
        }
    });
}

function getUserById(userId) {
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/user/getUserById',	//web.xml中注册的Servlet的url-pattern
        data : {"userId":userId},
        dataType : 'json', //返回数据形式为json
        success : function(data) {
            var length = getJsonLength(data);
            if (data.success == false) {
                alert("查询失败");
            }else{
                console.log(data.user.id);
                document.getElementById("userIdOfModify").value = data.user.id;
                document.getElementById("userNameOfModify").value = data.user.userName;
                document.getElementById("userSexOfModify").value = data.user.userSex;
                document.getElementById("classNameOfModify").value = data.user.className;

            }
        },
        error : function(errorMsg) {
            console.log("Login Erro !");
        }
    });
}

function getJsonLength(jsonData){
    var jsonLength = 0;
    for(var item in jsonData){
        if(item == 'user'){
            for(var x in jsonData[item]){
                jsonLength++;
            }
        }
    }
    return jsonLength;
}

function hideSuccessInfo() {
    document.getElementById("success-info").style.display = "none";
}

function setSuccessInfo(info) {
    document.getElementById("success-info").style.display = "none";
    document.getElementById("error-info").style.display = "none";
    document.getElementById("success-info").innerHTML = "" +
        "<button type=\"button\" class=\"close\" onclick=\"hideSuccessInfo()\"><span aria-hidden=\"true\">&times;</span></button>" +
        "<strong>Success：</strong> " + info;
    document.getElementById("success-info").style.display = "block";
}

function setErrorInfo(info) {
    document.getElementById("success-info").style.display = "none";
    document.getElementById("error-info").style.display = "none";
    document.getElementById("error-info").innerHTML = "" +
        "<button type=\"button\" class=\"close\" onclick=\"hideSuccessInfo()\"><span aria-hidden=\"true\">&times;</span></button>" +
        "<strong>Error：</strong> " + info;
    document.getElementById("error-info").style.display = "block";
}
