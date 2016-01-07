<%--
  Created by IntelliJ IDEA.
  User: wangyuannan
  Date: 2016/1/7
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- basic scripts -->

<!--[if !IE]> -->

<script src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>

<!-- <![endif]-->

<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

<!--[if !IE]> -->

<script type="text/javascript">
  window.jQuery || document.write("<script src='assets/js/jquery-2.0.3.min.js'>" + "<" + "script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>" + "<" + "script>");
</script>
<![endif]-->

<script type="text/javascript">
  if ("ontouchend" in document) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>" + "<" + "script>");
</script>
<script src="<c:url value="/resources/vendor/ace/assets/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/typeahead-bs2.min.js"/>"></script>

<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="<c:url value="/resources/vendor/ace/assets/js/excanvas.min.js"/>"></script>
<![endif]-->
<script src="<c:url value="/resources/vendor/ace/assets/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/jquery.ui.touch-punch.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/jquery.slimscroll.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/jquery.easy-pie-chart.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/jquery.sparkline.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/flot/jquery.flot.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/flot/jquery.flot.pie.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/flot/jquery.flot.resize.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/date-time/bootstrap-datepicker.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/date-time/bootstrap-timepicker.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/date-time/moment.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/date-time/daterangepicker.min.js"/>"></script>
<%--<script src="<c:url value="/resources/vendor/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"/>" charset="UTF-8"></script>--%>
<script src="<c:url value="/resources/vendor/ace/assets/js/jqGrid/jquery.jqGrid.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/jqGrid/i18n/grid.locale-en.js"/>"></script>
<!--data Tables-->
<script src="<c:url value="/resources/vendor/datatables/js/dataTables.bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/datatables/js/dataTables.foundation.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/datatables/js/dataTables.jqueryui.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/datatables/js/jquery.dataTables.min.js"/>"></script>

<!-- ace scripts -->

<script src="<c:url value="/resources/vendor/ace/assets/js/ace-elements.min.js"/>"></script>
<script src="<c:url value="/resources/vendor/ace/assets/js/ace.min.js"/>"></script>

