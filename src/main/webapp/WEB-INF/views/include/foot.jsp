<%--
  Created by IntelliJ IDEA.
  User: wangyuannan
  Date: 2015/12/16
  Time: 19:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- basic scripts -->

<script type="text/javascript">
    function showMessage(msg) {
        console.log(msg);
    }
</script>


<!--[if !IE]> -->
<script src="<c:url value="/resources/ace/assets/js/ajax-googleapis.jquery.min_2.0.3.js"/>"></script>
<!-- <![endif]-->

<script type="text/javascript">
    window.jQuery || document.write('<script src="<c:url value="/resources/ace/assets/js/jquery-2.0.3.min.js"/> "/>');
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
window.jQuery || document.write('<script src="<c:url value="/resources/ace/assets/js/jquery-1.10.2.min.js"/>" />');
</script>
<![endif]-->

<script type="text/javascript">
    if ("ontouchend" in document) document.write('<script src="<c:url value="/resources/ace/assets/js/jquery.mobile.custom.min.js"/>" />');
</script>
<script src="<c:url value="/resources/ace/assets/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/typeahead-bs2.min.js"/>"></script>
<!-- page specific plugin scripts -->

<!--[if lte IE 8]>
<script src="<c:url value="/resources/ace/assets/js/excanvas.min.js"/>" type="text/javascript"></script>
<![endif]-->

<script src="<c:url value="/resources/ace/assets/js/jquery-ui-1.10.3.custom.min.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/jquery.ui.touch-punch.min.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/jquery.slimscroll.min.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/jquery.easy-pie-chart.min.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/jquery.sparkline.min.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/flot/jquery.flot.min.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/flot/jquery.flot.pie.min.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/flot/jquery.flot.resize.min.js"/>"></script>

<!-- ace scripts -->

<script src="<c:url value="/resources/ace/assets/js/ace-elements.min.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/ace.min.js"/>"></script>

<!--echart JS-->
<script src="<c:url value="/resources/ace/assets/js/echart/dist/echarts.js"/>"></script>

<!--basic_schedule scripts-->
<script src="<c:url value="/resources/ace/assets/js/basic_schedule/calculation.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/basic_schedule/packer.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/basic_schedule/packer.growing.js"/>"></script>
<script src="<c:url value="/resources/ace/assets/js/basic_schedule/demo.js"/>"></script>



<div style="display:none">
    <script src="<c:url value="/resources/ace/assets/js/v7.cnzz.com.js" />" language='JavaScript'
            charset='gb2312'></script>
</div>