<%--
  Created by IntelliJ IDEA.
  User: wangyuannan
  Date: 2015/12/16
  Time: 21:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title>多级船闸调度系统Demo</title>
    <meta name="keywords" content="多级船闸调度系统, 调度系统"/>
    <meta name="description" content="多级船闸调度系统, 调度系统"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

    <%@ include file="../include/head.jsp" %>

</head>

<body>

<div class="navbar navbar-default" id="navbar">
    <script type="text/javascript">
        //    try {
        //      ace.settings.check('navbar', 'fixed')
        //    } catch (e) {
        //    }
    </script>

    <div class="navbar-container" id="navbar-container">
        <div class="navbar-header pull-left">
            <a href="#" class="navbar-brand">
                <small>
                    <i class="icon-leaf"></i>
                    多船闸联合调度系统Demo
                </small>
            </a><!-- /.brand -->
        </div>
        <!-- /.navbar-header -->

        <div class="navbar-header pull-right" role="navigation">
            <ul class="nav ace-nav">
                <li class="light-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="<c:url value="/resources/ace/assets/avatars/user.jpg"/>"
                             alt="Jason's Photo"/>
								<span class="user-info">
									<small>欢迎光临,</small>
									王源楠
								</span>

                        <i class="icon-caret-down"></i>
                    </a>

                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
                        <li>
                            <a href="#">
                                <i class="icon-cog"></i>
                                设置
                            </a>
                        </li>

                        <li>
                            <a href="#">
                                <i class="icon-user"></i>
                                个人资料
                            </a>
                        </li>

                        <li class="divider"></li>

                        <li>
                            <a href="#">
                                <i class="icon-off"></i>
                                退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!-- /.ace-nav -->
        </div>
        <!-- /.navbar-header -->
    </div>
    <!-- /.container -->
</div>

<div class="main-container" id="main-container">
    <script type="text/javascript">
        //    try {
        //      ace.settings.check('main-container', 'fixed')
        //    } catch (e) {
        //    }
    </script>

    <div class="main-container-inner">


        <div class="main-content" style="margin-left: 0px">
            <div class="breadcrumbs" id="breadcrumbs">
                <script type="text/javascript">
                    try {
                        ace.settings.check('breadcrumbs', 'fixed')
                    } catch (e) {
                    }
                </script>

                <ul class="breadcrumb">
                    <li>
                        <i class="icon-home home-icon"></i>
                        <a href="#">首页</a>
                    </li>
                    <li class="active">通货能力计算</li>
                </ul>
                <!-- .breadcrumb -->
            </div>

            <div class="page-content">
                <div class="page-header">
                    <h1>
                        通货能力计算
                    </h1>
                </div>
                <!-- /.page-header -->

                <div class="row">
                    <div class="col-xs-12">
                        <!-- PAGE CONTENT BEGINS -->

                        <div class="alert alert-block alert-success">
                            <button type="button" class="close" data-dismiss="alert">
                                <i class="icon-remove"></i>
                            </button>

                            <i class="icon-ok green"></i>

                            欢迎使用
                            <strong class="green">
                                多船闸联合调度系统Demo
                                <small>(v1.0)</small>
                            </strong>
                        </div>

                        <div class="row" id="input_div">

                            <div class="col-xs-12">
                                <div class="widget-box">
                                    <div class="widget-header">
                                        <h3>请输入计算参数</h3>
                                        <span class="widget-toolbar">
                                            <a href="#" data-action="collapse">
                                                <i class="icon-chevron-up"></i>
                                            </a>
                                        </span>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-body-inner" style="display: block;">
                                            <div class="widget-main">
                                                <div class="row">
                                                    <div class="col-xs-12">
                                                        <form class="form-horizontal" role="form">
                                                            <div class="form-group">
                                                                <div class="col-sm-12 col-lg-3">
                                                                    <label class="col-sm-4 control-label no-padding-right"
                                                                           for="form-field-1">
                                                                        年货运总量(亿吨)</label>

                                                                    <div class="col-sm-8">
                                                                        <input type="text" id="total"
                                                                               placeholder="年货运总量(亿吨)"
                                                                               class="col-xs-10 col-sm-5" value="0.7"/>
                                                                    </div>
                                                                </div>

                                                                <div class="col-sm-12 col-lg-3">
                                                                    <label class="col-sm-4 control-label no-padding-right"
                                                                           for="form-field-1">
                                                                        不均衡系数</label>

                                                                    <div class="col-sm-8">
                                                                        <input type="text" id="unstable"
                                                                               placeholder="1.12" value="1.12"
                                                                               class="col-xs-10 col-sm-5"/>
                                                                    </div>
                                                                </div>

                                                                <div class="col-sm-12 col-lg-3">
                                                                    <label class="col-sm-4 control-label no-padding-right"
                                                                           for="form-field-1">
                                                                        年工作日数</label>

                                                                    <div class="col-sm-8">
                                                                        <input type="text" id="days" placeholder="350"
                                                                               value="350"
                                                                               class="col-xs-10 col-sm-5"/>
                                                                    </div>
                                                                </div>

                                                            </div>

                                                            <div class="space-4"></div>

                                                            <div class="form-group">
                                                                <div class="col-xs-12">
                                                                    <div class="table-responsive">
                                                                        <table id="sample-table-2"
                                                                               class="table table-striped table-bordered table-hover">
                                                                            <thead>
                                                                            <tr>
                                                                                <th>
                                                                                    <center>船型</center>
                                                                                </th>
                                                                                <th>宽度（m）</th>
                                                                                <th>长度（m）</th>
                                                                                <th>参考设计吃水位（m）</th>
                                                                                <th>参考载货吨位（吨）</th>
                                                                                <th>所占比（%）</th>
                                                                            </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                            <tr>
                                                                                <td name="ship">
                                                                                    <center>
                                                                                        <bold>长江水系货-24</bold>
                                                                                    </center>
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="width"
                                                                                           placeholder="13" value="13">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="length_min"
                                                                                           placeholder="60" value="60">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="length_max"
                                                                                           placeholder="75" value="75">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="waterline_min"
                                                                                           placeholder="2.2"
                                                                                           value="2.2">
                                                                                    <label>-</label>
                                                                                    <input type="text"
                                                                                           name="waterline_max"
                                                                                           placeholder="3.0"
                                                                                           value="3.0">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="T_min"
                                                                                           placeholder="1500"
                                                                                           value="1500">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="T_max"
                                                                                           placeholder="1500"
                                                                                           value="1500">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_chuan"
                                                                                           placeholder="10" value="10">
                                                                                </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td name="ship">
                                                                                    <center>
                                                                                        <bold>长江水系货-29</bold>
                                                                                    </center>
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="width"
                                                                                           placeholder="13.8"
                                                                                           value="13.8">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="length_min"
                                                                                           placeholder="72" value="72">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="length_max"
                                                                                           placeholder="88" value="88">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="waterline_min"
                                                                                           placeholder="2.4"
                                                                                           value="2.4">
                                                                                    <label>-</label>
                                                                                    <input type="text"
                                                                                           name="waterline_max"
                                                                                           placeholder="3.5"
                                                                                           value="3.5">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="T_min"
                                                                                           placeholder="2000"
                                                                                           value="2000">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="T_max"
                                                                                           placeholder="2500"
                                                                                           value="2500">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_chuan"
                                                                                           placeholder="10" value="10">
                                                                                </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td name="ship">
                                                                                    <center>
                                                                                        <bold>长江水系货-35</bold>
                                                                                    </center>
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="width"
                                                                                           placeholder="16.3"
                                                                                           value="16.3">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="length_min"
                                                                                           placeholder="82" value="82">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="length_max"
                                                                                           placeholder="88" value="88">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="waterline_min"
                                                                                           placeholder="3.3"
                                                                                           value="3.3">
                                                                                    <label>-</label>
                                                                                    <input type="text"
                                                                                           name="waterline_max"
                                                                                           placeholder="4.3"
                                                                                           value="4.3">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="T_min"
                                                                                           placeholder="2500"
                                                                                           value="2500">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="T_max"
                                                                                           placeholder="3500"
                                                                                           value="3500">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_chuan"
                                                                                           placeholder="20" value="20">
                                                                                </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td name="ship">
                                                                                    <center>
                                                                                        <bold>长江水系货-36</bold>
                                                                                    </center>
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="width"
                                                                                           placeholder="16.3"
                                                                                           value="16.3">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="length_min"
                                                                                           placeholder="90" value="90">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="length_max"
                                                                                           placeholder="105"
                                                                                           value="105">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="waterline_min"
                                                                                           placeholder="4.1"
                                                                                           value="4.1">
                                                                                    <label>-</label>
                                                                                    <input type="text"
                                                                                           name="waterline_max"
                                                                                           placeholder="4.3"
                                                                                           value="4.3">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="T_min"
                                                                                           placeholder="3500"
                                                                                           value="3500">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="T_max"
                                                                                           placeholder="5000"
                                                                                           value="5000">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_chuan"
                                                                                           placeholder="20" value="20">
                                                                                </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td name="ship">
                                                                                    <center>
                                                                                        <bold>长江水系货-37</bold>
                                                                                    </center>
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="width"
                                                                                           placeholder="16.3"
                                                                                           value="16.3">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="length_min"
                                                                                           placeholder="125"
                                                                                           value="125">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="length_max"
                                                                                           placeholder="130"
                                                                                           value="130">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="waterline_min"
                                                                                           placeholder="4.1"
                                                                                           value="4.1">
                                                                                    <label>-</label>
                                                                                    <input type="text"
                                                                                           name="waterline_max"
                                                                                           placeholder="4.3"
                                                                                           value="4.3">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="T_min"
                                                                                           placeholder="5500"
                                                                                           value="5500">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="T_max"
                                                                                           placeholder="6000"
                                                                                           value="6000">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_chuan"
                                                                                           placeholder="10" value="10">
                                                                                </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td name="ship">
                                                                                    <center>
                                                                                        <bold>长江水系集-14</bold>
                                                                                    </center>
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="width"
                                                                                           placeholder="13.8"
                                                                                           value="13.8">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="length_min"
                                                                                           placeholder="75" value="75">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="length_max"
                                                                                           placeholder="88" value="88">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="waterline_min"
                                                                                           placeholder="2.2"
                                                                                           value="2.2">
                                                                                    <label>-</label>
                                                                                    <input type="text"
                                                                                           name="waterline_max"
                                                                                           placeholder="2.2"
                                                                                           value="3.5">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="T_min"
                                                                                           placeholder="150"
                                                                                           value="150">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="T_max"
                                                                                           placeholder="150"
                                                                                           value="150">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_chuan"
                                                                                           placeholder="10" value="10">
                                                                                </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td name="ship">
                                                                                    <center>
                                                                                        <bold>长江水系集-18</bold>
                                                                                    </center>
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="width"
                                                                                           placeholder="16.3"
                                                                                           value="16.3">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="length_min"
                                                                                           placeholder="105"
                                                                                           value="105">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="length_max"
                                                                                           placeholder="110"
                                                                                           value="110">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="waterline_min"
                                                                                           placeholder="2.8"
                                                                                           value="2.8">
                                                                                    <label>-</label>
                                                                                    <input type="text"
                                                                                           name="waterline_max"
                                                                                           placeholder="2.8"
                                                                                           value="4.3">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="T_min"
                                                                                           placeholder="300"
                                                                                           value="300">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="T_max"
                                                                                           placeholder="300"
                                                                                           value="300">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_chuan"
                                                                                           placeholder="10" value="10">
                                                                                </td>
                                                                            </tr>

                                                                            <tr>
                                                                                <td name="ship">
                                                                                    <center>
                                                                                        <bold>长江水系集-19</bold>
                                                                                    </center>
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="width"
                                                                                           placeholder="17.2"
                                                                                           value="17.2">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="length_min"
                                                                                           placeholder="105"
                                                                                           value="105">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="length_max"
                                                                                           placeholder="110"
                                                                                           value="110">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="waterline_min"
                                                                                           placeholder="3.0"
                                                                                           value="3.0">
                                                                                    <label>-</label>
                                                                                    <input type="text"
                                                                                           name="waterline_max"
                                                                                           placeholder="4.3"
                                                                                           value="4.3">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="T_min"
                                                                                           placeholder="350"
                                                                                           value="350">
                                                                                    <label>-</label>
                                                                                    <input type="text" name="T_max"
                                                                                           placeholder="350"
                                                                                           value="350">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_chuan"
                                                                                           placeholder="10" value="10">
                                                                                </td>
                                                                            </tr>

                                                                            </tbody>
                                                                        </table>
                                                                    </div>
                                                                </div>
                                                            </div>

                                                            <div class="form-group">
                                                                <div class="col-xs-12">
                                                                    <div class="table-responsive">
                                                                        <table id="sample-table-2"
                                                                               class="table table-striped table-bordered table-hover">
                                                                            <thead>
                                                                            <tr>
                                                                                <th></th>
                                                                                <th>1月</th>
                                                                                <th>2月</th>
                                                                                <th>3月</th>
                                                                                <th>4月</th>
                                                                                <th>5月</th>
                                                                                <th>6月</th>
                                                                                <th>7月</th>
                                                                                <th>8月</th>
                                                                                <th>9月</th>
                                                                                <th>10月</th>
                                                                                <th>11月</th>
                                                                                <th>12月</th>
                                                                            </tr>
                                                                            </thead>
                                                                            <tbody>
                                                                            <tr>
                                                                                <td>
                                                                                    <center>艘次按月占比</center>
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="9.0%"
                                                                                           value="9.0%">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="6.1%"
                                                                                           value="6.1%">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="7.9%"
                                                                                           value="7.9%">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="8.7%"
                                                                                           value="8.7%">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="8.5%"
                                                                                           value="8.5%">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="8.8%"
                                                                                           value="8.8%">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="7.8%"
                                                                                           value="7.8%">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="8.3%"
                                                                                           value="8.3%">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="7.2%"
                                                                                           value="7.2%">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="9.3%"
                                                                                           value="9.3%">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="9.4%"
                                                                                           value="9.4%">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text"
                                                                                           name="p_per_month"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="9.0%"
                                                                                           value="9.0%">
                                                                                </td>

                                                                            </tr>

                                                                            <tr>
                                                                                <td>
                                                                                    <center>
                                                                                        <bold>负载系数</bold>
                                                                                    </center>
                                                                                </td>

                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.82"
                                                                                           value="0.82">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.76"
                                                                                           value="0.76">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.76"
                                                                                           value="0.76">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.76"
                                                                                           value="0.76">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.76"
                                                                                           value="0.76">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.76"
                                                                                           value="0.76">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.76"
                                                                                           value="0.76">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.76"
                                                                                           value="0.82">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.76"
                                                                                           value="0.76">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.82"
                                                                                           value="0.82">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.82"
                                                                                           value="0.82">
                                                                                </td>
                                                                                <td>
                                                                                    <input type="text" name="load"
                                                                                           class="input-mini spinner-input"
                                                                                           placeholder="0.82"
                                                                                           value="0.82">
                                                                                </td>
                                                                            </tr>

                                                                            </tbody>
                                                                        </table>
                                                                    </div>
                                                                </div>
                                                                <div class="space-4"></div>
                                                            </div>

                                                            <div class="clearfix form-actions">
                                                                <div class="col-md-offset-5 col-md-9">
                                                                    <button class="btn btn-info" type="button"
                                                                            onclick="collectionParams();">
                                                                        <i class="icon-ok bigger-110"></i>
                                                                        计算
                                                                    </button>

                                                                    &nbsp; &nbsp; &nbsp;
                                                                    <button class="btn" type="reset">
                                                                        <i class="icon-undo bigger-110"></i>
                                                                        重置
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <hr/>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- /.col -->
                        </div>
                        <!-- /.row -->


                        <div class="row">
                            <div class="col-xs-12">
                                <div class="widget-box">
                                    <div class="widget-header">
                                        <h3>计算结果</h3>
                                        <span class="widget-toolbar">
                                            <a href="#" data-action="collapse">
                                                <i class="icon-chevron-up"></i>
                                            </a>
                                        </span>
                                    </div>
                                    <div class="widget-body">
                                        <div class="widget-body-inner" style="display: block;">
                                            <div class="widget-main">
                                                <div class="tabbable">

                                                    <ul class="nav nav-tabs padding-12 tab-color-blue background-blue"
                                                        id="result_tabs">
                                                        <li class="active">
                                                            <a data-toggle="tab" href="#chart">通货能力计算结果</a>
                                                        </li>
                                                        <li class>
                                                            <a data-toggle="tab" href="#basic_schedule">单日调度能力结果展示</a>
                                                        </li>

                                                    </ul>
                                                    <div class="tab-content">
                                                        <div id="chart" class="tab-pane active">
                                                            <div class="row" id="result_div" hidden="true">
                                                                <div class="space-6"></div>
                                                                <div>
                                                                    <div class="col-sm-12 infobox-container">

                                                                        <div class="infobox infobox-green">
                                                                            <div class="infobox-icon">
                                                                                <i class="icon-comments"></i>
                                                                            </div>
                                                                            <div class="infobox-data">
                                                                                <span class="infobox-data-number"
                                                                                      id="result_N_per_year">1000</span>

                                                                                <div class="infobox-content">平均每年所需总船数
                                                                                </div>
                                                                            </div>

                                                                        </div>

                                                                        <div class="infobox infobox-blue  ">
                                                                            <div class="infobox-icon">
                                                                                <i class="icon-twitter"></i>
                                                                            </div>

                                                                            <div class="infobox-data">
                                                                                <span class="infobox-data-number"
                                                                                      id="result_N_per_month">5000</span>

                                                                                <div class="infobox-content">平均每月所需总船数
                                                                                </div>
                                                                            </div>


                                                                        </div>

                                                                        <div class="infobox infobox-pink">
                                                                            <div class="infobox-icon">
                                                                                <i class="icon-shopping-cart"></i>
                                                                            </div>

                                                                            <div class="infobox-data">
                                                                                <span class="infobox-data-number"
                                                                                      id="result_N_per_day">500</span>

                                                                                <div class="infobox-content">平均每日所需总船数
                                                                                </div>
                                                                            </div>
                                                                        </div>

                                                                    </div>
                                                                </div>
                                                                <div class="row">
                                                                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12"
                                                                         id="chart1" style="height:400px"></div>
                                                                    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12"
                                                                         id="chart2" style="height:400px"></div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div id="basic_schedule" class="tab-pane">

                                                            <div class="row" id="basic_schedule_div" hidden="true">
                                                                <div class="col-lg-12 col-sm-12 col-xs-12">
                                                                    <div class="space-6"></div>
                                                                    <div>
                                                                        <ul>
                                                                            <li id="description"
                                                                                class="icon-circle black"></li>
                                                                        </ul>
                                                                    </div>
                                                                    <div id="schedule_table">

                                                                    </div>
                                                                </div>
                                                            </div>

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- /row -->

                        <div class="hr hr32 hr-dotted"></div>


                        <div class="hr hr32 hr-dotted"></div>

                        <!-- PAGE CONTENT ENDS -->
                    </div>
                    <!-- /.col -->
                </div>
                <!-- /.row -->
            </div>
            <!-- /.page-content -->
        </div>
        <!-- /.main-content -->

        <div class="ace-settings-container" id="ace-settings-container">
            <div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
                <i class="icon-cog bigger-150"></i>
            </div>

            <div class="ace-settings-box" id="ace-settings-box">
                <div>
                    <div class="pull-left">
                        <select id="skin-colorpicker" class="hide">
                            <option data-skin="default" value="#438EB9">#438EB9</option>
                            <option data-skin="skin-1" value="#222A2D">#222A2D</option>
                            <option data-skin="skin-2" value="#C6487E">#C6487E</option>
                            <option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
                        </select>
                    </div>
                    <span>&nbsp; 选择皮肤</span>
                </div>

                <div>
                    <input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-add-container"/>
                    <label class="lbl" for="ace-settings-add-container">
                        切换窄屏
                        <b></b>
                    </label>
                </div>
            </div>
        </div>
        <!-- /#ace-settings-container -->
    </div>
    <!-- /.main-container-inner -->

    <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
        <i class="icon-double-angle-up icon-only bigger-110"></i>
    </a>
</div>
<!-- /.main-container -->


<%@ include file="../include/foot.jsp" %>


<style>
    input[type="text"] {  /*表格宽度控制*/
        width: 45px;
    }
</style>

</body>
</html>