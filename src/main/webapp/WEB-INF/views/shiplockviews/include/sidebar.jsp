<%--
  Created by IntelliJ IDEA.
  User: wangyuannan
  Date: 2016/1/7
  Time: 13:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="sidebar" id="sidebar">
  <script type="text/javascript">
    try {
      ace.settings.check('sidebar', 'fixed')
    } catch (e) {
    }
  </script>

  <ul class="nav nav-list">

    <li><a href="<c:url value="/calculation"/>"><i class="icon-dashboard"></i><span class="menu-text">创建计算方案 </span></a></li>

    <li>
      <a href="#" class="dropdown-toggle">
        <i class="icon-tag"></i>
        <span class="menu-text"> 方案结果展示 </span>
        <b class="arrow icon-angle-down"></b>
      </a>
      <ul class="submenu">
        <c:if test="${not empty schemes}">
          <c:forEach var="scheme" items="${schemes}">
            <li>
              <a href="<c:url value="/results/${scheme.id}" />">
                <i class="icon-double-angle-right"></i>
                  ${scheme.name}
              </a>
            </li>
          </c:forEach>
        </c:if>
      </ul>
    </li>

  </ul>
  <!-- /.nav-list -->

  <div class="sidebar-collapse" id="sidebar-collapse">
    <i class="icon-double-angle-left" data-icon1="icon-double-angle-left"
       data-icon2="icon-double-angle-right"></i>
  </div>

  <script type="text/javascript">
    try {
      ace.settings.check('sidebar', 'collapsed')
    } catch (e) {
    }
  </script>
</div>

