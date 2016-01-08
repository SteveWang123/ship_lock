<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.List" %>
<%--
  Created by IntelliJ IDEA.
  User: wangyuannan
  Date: 2016/1/7
  Time: 10:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>三峡多船闸联合调度模拟分析系统</title>
    <meta name="keywords" content="Bootstrap模版,Bootstrap模版下载,Bootstrap教程,Bootstrap中文"/>
    <meta name="description" content="站长素材提供Bootstrap模版,Bootstrap教程,Bootstrap中文翻译等相关Bootstrap插件下载"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <%@ include file="include/head.jsp" %>


</head>

<body>

<%@include file="include/navbar.jsp" %>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        try {
            ace.settings.check('main-container', 'fixed')
        } catch (e) {
        }
    </script>

    <div class="main-container-inner">
        <a class="menu-toggler" id="menu-toggler" href="#">
            <span class="menu-text"></span>
        </a>

        <%@include file="include/sidebar.jsp" %>

        <div class="main-content">

            <div class="page-content">

                <div class="page-header"><h1 style="margin-left: 65px;">请输入计算参数</h1></div>

                <div class="row">
                    <div class="col-xs-12">

                        <div class="row">
                            <div class="col-md-4 col-sm-4 col-xs-12">
                                <form class="form-inline" style="text-align :center">
                                    <div class="form-group">
                                        <label for="name">方案名称:</label>
                                        <input type="text" class="form-control" id="name" placeholder="wyn_test" value="wyn_test0108">
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-4 col-sm-4 col-xs-12">
                                <form class="form-inline" style="text-align :center">
                                    <div class="form-group">
                                        <label>排挡计算时间段:</label>
                                        <input type="text" class="form-control datetime-picker" id="startDay" value="2016-01-01"
                                               placeholder="2016-01-01" data-date-format="yyyy-mm-dd">
                                        —<input type="text" class="form-control datetime-picker" id="endDay" value="2016-12-31"
                                                placeholder="2016-12-31" data-date-format="yyyy-mm-dd">
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-4 col-sm-4 col-xs-12">
                                <form class="form-inline" style="text-align :center">
                                    <div class="form-group">
                                        <label for="total">年货运总量:</label>
                                        <input type="text" class="form-control" id="total" placeholder="0.7" value="0.7">亿吨
                                    </div>
                                </form>
                            </div>

                        </div>

                        <br>

                        <div class="row">
                            <div class="col-xs-12" style="padding-left: 75px;padding-right: 75px">
                                <table id="shipCategory" class="display" cellspacing="0" width="100%">
                                    <thead>
                                    <tr>
                                        <th>船型ID</th>
                                        <th>船型</th>
                                        <th>宽度</th>
                                        <th>长度</th>
                                        <th>设计吃水位</th>
                                        <th>设计载货吨位</th>
                                        <th>占比</th>
                                        <th>占比波动系数</th>
                                        <th>负载系数</th>
                                        <th>负载系数波动系数</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${not empty shipCategories}">
                                        <c:forEach var="category" items="${shipCategories}">
                                            <tr>
                                                <td name="category_id">${category.get("category_id")}</td>
                                                <td name="category_name">${category.get("category_name")}</td>
                                                <td name="width">${category.get("width")}</td>
                                                <td name="length">${category.get("length")}</td>
                                                <td name="maxInWater">${category.get("maxInWater")}</td>
                                                <td name="capacity">${category.get("capacity")}</td>
                                                <td>
                                                    <input name="ratio" type="text" value="${category.get("ratio")}"/>
                                                </td>
                                                <td><input name="ratio_fluc" type="text" name="row-2-age"
                                                           value="0.001"/></td>
                                                <td><input name="designLoad" type="text" name="row-2-age"
                                                           value="${category.get("designLoad")}"/>
                                                </td>
                                                <td><input name="load_fluc" type="text" name="row-2-age"
                                                           value="${category.get("fluctuation")}"/>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    </tbody>
                                </table>

                                <button id="button">测试</button>
                            </div>
                        </div>

                        <div class="space-4"></div>

                        <div class="row">
                            <div class="col-xs-12" style="padding-left: 75px;padding-right: 75px">
                                <table id="pmonth" class="display" cellspacing="0" width="100%">
                                    <thead>
                                    <tr>
                                        <c:if test="${not empty pmonths}">
                                            <th></th>
                                            <c:forEach var="pmonth" items="${pmonths}">
                                                <th>${pmonth.get("month")} 月</th>
                                            </c:forEach>
                                        </c:if>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${not empty pmonths}" var="pmonth">
                                        <tr>
                                            <td>比例(%)</td>
                                            <c:forEach var="pmonth" items="${pmonths}">
                                                <td>${pmonth.get("ratio")}</td>
                                            </c:forEach>
                                        </tr>
                                        <tr>
                                            <td>波动系数</td>
                                            <c:forEach var="pmonth" items="${pmonths}">
                                                <td ><input name="pmonth_ratio" type="text" value="0.01" /></td>
                                            </c:forEach>
                                        </tr>
                                    </c:if>
                                    </tbody>
                                </table>

                                <button id="button2">测试2</button>
                            </div>
                        </div>

                        <div class="space-4"></div>

                        <div class="row">
                            <div class="col-xs-12" style="padding-left: 75px;padding-right: 75px">
                                闸室利用率比率: <input type="text" id="index_ratio" value="60"/>%
                            </div>
                        </div>

                        <div class="row">
                            <div class="col-xs-12" style="text-align: center; padding-left: 75px;padding-right: 75px">
                                <button class="btn" id="startCal">计算 </button>
                            </div>
                        </div>




                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
        <!-- /.main-content -->

    </div>
    <!-- /.main-container-inner -->
</div>


<%@include file="include/foot.jsp" %>


<!-- inline CSS related to this page -->
<style>
    .form-group input {
        width: auto;
    }

    table tbody input {
        width: 85px;
        height: 20px;
    }

</style>

<!-- inline scripts related to this page -->
<script>

    temp = [];



    $(document).ready(function () {

        showMessage("document start rendering");

        var categoryTable = $('#shipCategory').DataTable({
            paging: true,
            autoWidth: true,
            scrollY: '400px',
            scrollCollapse: true,
//            stateSave:true,
            searching: false,
            lengthMenu: [10, 15, 20, 25, 50],
            sPaginationType: 'full_numbers',
            aaSorting: [[0, 'asc']], //默认的排序方式
            aoColumns: [
                {"sClass": "categoryId"},
                {"sClass": "categoryName"},
                {"sClass": "width"},
                {"sClass": "length"},
                {"sClass": "maxInWater"},
                {"sClass": "capacity"},
                {"sClass": "ratio"},
                {"sClass": "fluctuation"},
                {"sClass": "designLoad"},
                {"sClass": "fluctuation2"}
            ],
            "aoColumnDefs": [{"bSortable": false, "aTargets": [6, 7, 8, 9]}],
//            initComplete: function(settings, json) {
//                alert( 'DataTables has finished its initialisation.' );
//            },

            "dom": 'l<"toolbar">frtip',

            oLanguage: {
                sLengthMenu: '每页显示 _MENU_ 条记录',
                sZeroRecords: '抱歉， 没有找到',
                sInfo: '从 _START_ 到 _END_ /共 _TOTAL_ 条数据',
                sInfoEmpty: '没有数据',
                sInfoFiltered: '(从 _MAX_ 条数据中检索)',
                oPaginate: {
                    sFirst: '首页',
                    sPrevious: '前一页',
                    sNext: '后一页',
                    sLast: '尾页'
                },
                sZeroRecords: '没有检索到数据',
                sProcessing: '<img src=\'#\'" /loading.gif’ />'
            }
        });

        $("div.toolbar").html("<button class='btn btn-xs btn-light' style='margin-left: 10px; margin-right: 10px;' id='select_all'>全选</button><button class='btn btn-xs btn-light' id='clear_all'>全部清除</button>");

        //全选按钮的事件
        $('#select_all').click(function () {
            $('#shipCategory tr').addClass('selected');
        });

        //全部清除按钮事件
        $('#clear_all').click(function () {
            $('#shipCategory tr').removeClass('selected');
        });



        //时间选择器
        $('.datetime-picker').datepicker({
            dateFormat: 'yyyy-mm-dd',
            showButtonPanel: true,
            changeMonth: true,
            changeYear: true
        });

        //选中一行事件
        $('#shipCategory tbody').on('click', 'tr', function () {
            $(this).toggleClass('selected');
        });


        var pmonthTable = $('#pmonth').DataTable({
            autoWidth: true,
            searching: false,
            aaSorting: [[0, 'asc']], //默认的排序方式
            "dom": 't',
            oLanguage: {
                sLengthMenu: '每页显示 _MENU_ 条记录',
                sZeroRecords: '抱歉， 没有找到',
                sInfo: '从 _START_ 到 _END_ /共 _TOTAL_ 条数据',
                sInfoEmpty: '没有数据',
                sInfoFiltered: '(从 _MAX_ 条数据中检索)',
                oPaginate: {
                    sFirst: '首页',
                    sPrevious: '前一页',
                    sNext: '后一页',
                    sLast: '尾页'
                },
                sZeroRecords: '没有检索到数据',
            }
        });

        $('#button2').click(function () {
            $("input[name='pmonth_ratio']").each(function(){
                var p = ($(this).val());
                temp.push(p);
            });
            showMessage(temp);
        });

    });

    $('#startCal').click(function(){
        var scheme = {"name":$('#name').val(), "createTime":new Date(),
                      "year":$('#startDay').val().split("-")[0],
                      "total":$('#total').val(),
                      "startDay":$("#startDay").val(),
                      "endDay":$("#endDay").val(),
                      "scheduleWeight_ratio":$("#index_ratio").val()};
        $.ajax({
            type:"GET",
            url:"api/startCal",
            dataType:"json",
            contentType:"application/json",
            data:JSON.stringify(scheme),
            success:function(data){
                showMessage(data);
            }
        });

    });


</script>
<div style="display:none">
    <script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script>
</div>
</body>
</html>