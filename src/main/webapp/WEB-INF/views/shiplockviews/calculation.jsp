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
<%--<% List shipCategories = (List) request.getAttribute("shipCategories");%>--%>
<%--<c:set var="shipCategories" value="${shipCategories}" scope="page"/>--%>
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

<%@include file="include/navbar.jsp"%>

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
                                    <div class="form-group" >
                                            <label for="name">方案名称:</label>
                                            <input type="text" class="form-control" id="name" placeholder="wyn_test">
                                    </div>
                                    <%--<div class="form-group">--%>
                                        <%--<label for="year">计算年份:</label>--%>
                                        <%--<input type="text" class="form-control" id="year" placeholder="2016" >--%>
                                    <%--</div>--%>
                                </form>
                            </div>
                            <div class="col-md-4 col-sm-4 col-xs-12">
                                <form class="form-inline" style="text-align :center">
                                    <div class="form-group">
                                        <label >排挡计算时间段:</label>
                                        <input type="text" class="form-control datetime-picker" id="startTime" placeholder="2016-01-01" data-date-format="yyyy-mm-dd">
                                        —<input type="text" class="form-control datetime-picker" id="endTime" placeholder="2016-12-31" data-date-format="yyyy-mm-dd">
                                    </div>
                                </form>
                            </div>
                            <div class="col-md-4 col-sm-4 col-xs-12" >
                                <form class="form-inline" style="text-align :center">
                                    <div class="form-group">
                                        <label for="total">年货运总量:</label>
                                        <input type="text" class="form-control" id="total" placeholder="0.7">
                                    </div>
                                </form>
                            </div>

                        </div>

                        <br>
                        <br>
                        <br>

                        <div class="row">
                            <div class="col-xs-12">
                                <table id="shipCategory" class="display" cellspacing="0" width="100%">
                                    <thead>
                                    <tr>
                                        <th>船型</th>
                                        <th>宽度</th>
                                        <th>长度</th>
                                        <th>设计吃水位</th>
                                        <th>设计载货吨位</th>
                                        <th>占比</th>
                                        <th>波动系数</th>
                                        <th>负载系数</th>
                                        <th>波动系数</th>
                                        <th>编辑</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:if test="${not empty shipCategories}">
                                        <c:forEach var="category" items="${shipCategories}">
                                            <tr>
                                                <td>${category.get("category_name")}</td>
                                                <td>${category.get("width")}</td>
                                                <td>${category.get("length")}</td>
                                                <td>${category.get("maxInWater")}</td>
                                                <td>${category.get("capacity")}</td>
                                                <td>${category.get("ratio")}</td>
                                                <td>${category.get("fluctuation")}</td>
                                                <td>${category.get("designLoad")}</td>
                                                <td>${category.get("fluctuation")}</td>
                                                <td>0</td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                    </tbody>
                                </table>
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



<%@include file="include/foot.jsp"%>



<!-- inline CSS related to this page -->
<style>
    .form-group input{
        width: auto;
    }

</style>

<!-- inline scripts related to this page -->
<script>

    $(document).ready(function() {
        $('#shipCategory').dataTable( {
            paging: true
        } );
    } );


</script>
<div style="display:none">
    <script src='http://v7.cnzz.com/stat.php?id=155540&web_id=155540' language='JavaScript' charset='gb2312'></script>
</div>
</body>
</html>