<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<c:set value="${pageContext.request.contextPath}" var="app"></c:set>
<html>
<head>
    <meta charset="utf-8">
    <title>ECharts</title>
    <%--jquer环境--%>
    <script src="${app}/boot/js/jquery-2.2.1.min.js"></script>
    <!-- 引入 echarts.js -->
    <script src="${app}/echarts/echarts.min.js"></script>
    <%--地图需要的依赖--%>
    <script src="${app}/echarts/china.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        tooltip: {},
        legend: {
            data: ['注册人数']
        },
        xAxis: {
            type: 'category',
            data: ['第七天', '第六天', '第五天', '第四天', '第三天', '第二天', '第一天']
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            name: "注册人数",
            type: 'bar'
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    $.ajax({
        url: "${app}/user/recently",
        datatype: "json",
        type: "post",
        success: function (data) {
            //动态修改数据
            myChart.setOption({
                series: [{
                    data: data
                }]
            })
        }
    })
</script>
</body>
</html>