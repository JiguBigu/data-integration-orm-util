function getAllLesson() {
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/lesson/getAllLesson',	//web.xml中注册的Servlet的url-pattern
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
                    str += "<tr id='" + data.lesson[i].lessonId +"'>"+
                        "<td>" + data.lesson[i].lessonId + "</td>" +
                        "<td>" + data.lesson[i].lessonName + "</td>" +
                        "<td>" + data.lesson[i].teacherName + "</td>" +
                        "<td>" + data.lesson[i].hours + "</td>" +
                        "<td><button id='m" + data.lesson[i].lessonId + "' type='button' style='margin-left: -15%' class='btn btn-info'  data-target=\"#modifyModal\" data-toggle=\"modal\" onclick='getLessonById("+ data.lesson[i].lessonId + ")'>编辑</button>" +
                        "    <button id='d" + data.lesson[i].lessonId +"' type='button' style='margin-left: 10px' class='btn btn-info'  onclick='deleteLesson("+ data.lesson[i].lessonId + ")'>删除</button></td>"
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

function getAllLessonWithoutInfo() {
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/lesson/getAllLesson',	//web.xml中注册的Servlet的url-pattern
        data : {},
        dataType : 'json', //返回数据形式为json
        success : function(data) {
            var length = getJsonLength(data);
            if (data.success == false) {
                setErrorInfo("查询信息失败");
            }else{
                var str = "";
                for (var i = 0; i < length; i++){
                    str += "<tr id='" + data.lesson[i].lessonId +"'>"+
                        "<td>" + data.lesson[i].lessonId + "</td>" +
                        "<td>" + data.lesson[i].lessonName + "</td>" +
                        "<td>" + data.lesson[i].teacherName + "</td>" +
                        "<td>" + data.lesson[i].hours + "</td>" +
                        "<td><button id='m" + data.lesson[i].lessonId + "' type='button' style='margin-left: -15%' class='btn btn-info'  data-target=\"#modifyModal\" data-toggle=\"modal\" onclick='getLessonById("+ data.lesson[i].lessonId + ")'>编辑</button>" +
                        "    <button id='d" + data.lesson[i].lessonId +"' type='button' style='margin-left: 10px' class='btn btn-info'  onclick='deleteLesson("+ data.lesson[i].lessonId + ")'>删除</button></td>"
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

function deleteLesson(lessonId) {
    console.log(lessonId);
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/lesson/deleteLessonById',
        data : {"lessonId":lessonId},
        dataType : 'json', //返回数据形式为json
        success : function(data) {
            if(data.success = false){
                setErrorInfo("删除课程信息数据失败");
            }else {
                setSuccessInfo("删除课程成功");
                getAllLessonWithoutInfo();
            }
        },
        error : function(errorMsg) {
            setErrorInfo("删除课程信息数据失败");
            console.log("deleteErrorException !");
        }
    });
}

function insertLesson() {
    var lessonId = document.getElementById("lessonId").value;
    var lessonName = document.getElementById("lessonName").value;
    var teacherName = document.getElementById("teacherName").value;
    var hours = document.getElementById("hours").value;
    var databaseName = document.getElementById("option").value;
    console.log(databaseName);
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/lesson/insertLesson',	//web.xml中注册的Servlet的url-pattern
        data : {"lessonId":lessonId, "lessonName":lessonName, "teacherName":teacherName, "hours":hours, "databaseName": databaseName},
        dataType : 'json', //返回数据形式为json
        success : function(data) {
            if(data.success = false){
                setErrorInfo("插入课程数据失败");
            }else {
                setSuccessInfo("插入课程成功");
                getAllLessonWithoutInfo();
                $('#btn-insert-close').trigger("click");
            }
        },
        error : function(errorMsg) {
            setErrorInfo("插入课程失败");
            console.log("InsertErrorException !");
        }
    });
}

function updateLesson() {
    var lessonId = document.getElementById("lessonIdOfModify").value;
    var lessonName = document.getElementById("lessonNameOfModify").value;
    var teacherName = document.getElementById("teacherNameOfModify").value;
    var hours = document.getElementById("hoursOfModify").value;
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/lesson/updateLesson',	//web.xml中注册的Servlet的url-pattern
        data : {"lessonId":lessonId, "lessonName":lessonName, "teacherName":teacherName, "hours":hours},
        dataType : 'json', //返回数据形式为json
        success : function(data) {
            if(data.success = false){
                setErrorInfo("修改课程信息数据失败");
            }else {
                setSuccessInfo("修改课程信息成功");
                getAllLessonWithoutInfo();
                $('#btn-query-close').trigger("click");
            }
        },
        error : function(errorMsg) {
            setErrorInfo("修改课程信息数据失败");
            console.log("UpdateErrorException !");
        }
    });
}

function getLessonById(lessonId) {
    $.ajax({
        type : 'post',	//传输类型
        async : false,	//同步执行
        url : '/lesson/getLessonById',	//web.xml中注册的Servlet的url-pattern
        data : {"lessonId":lessonId},
        dataType : 'json', //返回数据形式为json
        success : function(data) {
            if (data.success == false) {
                alert("查询失败");
            }else{
                console.log(data.lesson.lessonId);
                document.getElementById("lessonIdOfModify").value = data.lesson.lessonId;
                document.getElementById("lessonNameOfModify").value = data.lesson.lessonName;
                document.getElementById("teacherNameOfModify").value = data.lesson.teacherName;
                document.getElementById("hoursOfModify").value = data.lesson.hours;

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
        if(item == 'lesson'){
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
