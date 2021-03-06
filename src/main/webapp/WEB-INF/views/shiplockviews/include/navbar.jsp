<%--
  Created by IntelliJ IDEA.
  User: wangyuannan
  Date: 2016/1/7
  Time: 13:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-default" id="navbar">
  <script type="text/javascript">
    try {
      ace.settings.check('navbar', 'fixed')
    } catch (e) {
    }
  </script>

  <div class="navbar-container" id="navbar-container">
    <div class="navbar-header pull-left">
      <a href="#" class="navbar-brand">
        <small>
          <i class="icon-leaf"></i>
          三峡多船闸联合调度模拟分析系统
        </small>
      </a><!-- /.brand -->
    </div>
    <!-- /.navbar-header -->

    <div class="navbar-header pull-right" role="navigation">
      <ul class="nav ace-nav">
        <li class="light-blue">
          <a data-toggle="dropdown" href="#" class="dropdown-toggle">
            <img class="<c:url value="/resources/vendor/ace/assets/avatars/nav-user-photo"/>" src="<c:url value="/resources/vendor/ace/assets/avatars/user.jpg" />"
                 alt="Jason's Photo"/>
								<span class="user-info">
									<small>欢迎光临,</small>
									Jason
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
