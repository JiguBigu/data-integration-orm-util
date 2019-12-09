<%--
  Created by IntelliJ IDEA.
  User: Jigubigu
  Date: 2019/9/26
  Time: 18:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>数据集成</title>

    <!-- Bootstrap core CSS -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="../../assets/css/ie10-viewport-bug-workaround.css" rel="stylesheet">
    <!-- Custom styles for this template -->
    <link href="dashboard.css" rel="stylesheet">
    <link href="css/info.css" rel="stylesheet">
</head>
<body>
<%--插入信息弹框--%>
<div class="modal fade" id="insertModal" tabindex="-1" role="dialog" aria-labelledby="insertModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="insertModalLabel">
                    插入课程信息
                </h4>
            </div>
            <div class="modal-body">
                <label class="control-label">课程号:</label>
                <input type="text" id="lessonId" class="form-control">
                <label class="control-label">课程名:</label>
                <input type="text" id="lessonName" class="form-control">
                <label class="control-label">任课教师：</label>
                <input type="text" id="teacherName" class="form-control">
                <label class="control-label">学时：</label>
                <input type="text" id="hours" class="form-control">

                <label class="control-label">目标数据库：</label>
                <select id="option" class="form-control" style="width: 60%;">
                    <option>华中农业大学</option>
                    <option>中国地质大学(武汉)</option>
                    <option>所有库</option>
                </select>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="btn-insert-close" data-dismiss="modal">
                    关闭
                </button>
                <button type="button" class="btn btn-primary" onclick="insertLesson()">
                    插入
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%--编辑信息弹框--%>
<div class="modal fade" id="modifyModal" tabindex="3" role="dialog" aria-labelledby="modifyModal" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="modifyModalLabel">
                    编辑课程信息
                </h4>
            </div>
            <div class="modal-body">
                <label class="control-label">课程号:</label>
                <input type="text" disabled="disabled" id="lessonIdOfModify" class="form-control">
                <label class="control-label">课程名:</label>
                <input type="text" id="lessonNameOfModify" class="form-control">
                <label class="control-label">任课教师：</label>
                <input type="text" id="teacherNameOfModify" class="form-control">
                <label class="control-label">学时：</label>
                <input type="text" id="hoursOfModify" class="form-control">
            </div>
            <div class="modal-footer">
                <button type="button" id="btn-query-close" class="btn btn-default" data-dismiss="modal">
                    关闭
                </button>
                <button type="button" class="btn btn-primary" onclick="updateLesson()">
                    修改信息
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%--页面主体--%>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar" style="height: 100%">
            <ul class="nav nav-sidebar">
                <li style="font-size: large;text-align: left;margin:5%;"><span>应用集成-信息管理</span></li>
                <li class="active"><a href="#">首页<span class="sr-only">(current)</span></a></li>
                <li><a href="index.jsp">学生信息管理</a></li>
                <li><a href="lessonInfo.jsp">课程信息管理</a></li>
            </ul>
        </div>


        <div id="main">
            <div>
                <h2 class="sub-header">课程信息</h2>
            </div>

            <div class="alert alert-success" id="success-info">
                <button type="button" class="close" onclick="hideSuccessInfo()"><span aria-hidden="true">&times;</span>
                </button>
                <strong>Success：</strong> 查询数据成功
            </div>
            <div class="alert alert-success" id="error-info">
                <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <strong>Error：</strong> 操作发生错误
            </div>

            <div class="show">
                <div class="form-inline">
                    <button id="select_student" type="button" class="btn btn-info" onclick="getAllLesson()">查询课程信息
                    </button>
                    <button id="insert_student" type="button" class="btn btn-info" data-target="#insertModal"
                            data-toggle="modal" onclick="">插入信息
                    </button>
                </div>
                <div class="table-responsive">
                    <table class="table table-striped">
                        <thead>
                        <tr>
                            <th>课程号</th>
                            <th>课程名</th>
                            <th>任课教师</th>
                            <th>课时</th>
                            <th>编辑</th>
                        </tr>
                        </thead>
                        <tbody id="showData">
                        <tr>
                            <td>暂无数据</td>
                            <td>暂无数据</td>
                            <td>暂无数据</td>
                            <td>暂无数据</td>
                            <td>
                                <button id="" type="button" class="btn btn-info" onclick="deleteStuednt(this)">删除信息
                                </button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Placed at the end of the document so the pages load faster -->
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- Just to make our placeholder images work. Don't actually copy the next line! -->
<script src="../../assets/js/vendor/holder.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="../../assets/js/ie10-viewport-bug-workaround.js"></script>
<script src="js/lesson.js"></script>
</body>
</html>
