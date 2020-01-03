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
    <%--引入goeasy--%>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 1000px;height:400px;"></div>
<script type="text/javascript">

    $(function () {

        //使用goeasy
        var goEasy = new GoEasy({
            host: "hangzhou.goeasy.io",  //所在的区域 hangzhou.goeasy.io表示杭州
            appkey: "BS-dffda1bffe934592979601e7316ef2d0"           //应用的kay是否的只订阅还是 可以订阅和发布
        });

        //接收消息
        goEasy.subscribe({
            channel: "banner",  //替换为自己的channel域
            onMessage: function (message) {
                console.log("数据：" + message.content);
                //将数据转换
                var parse = JSON.parse(message.content);
                console.log("转换后的数据：" + parse);
                //将数据加进去
                //动态修改数据
                myChart.setOption({
                    series: [{
                        data: parse
                    }]
                })
            }
        });
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            tooltip: {},
            legend: {
                data: ['月份轮播图数据']
            },
            xAxis: {
                type: 'category',
                data: ['1月份', '2月份', '3月份', '4月份', '5月份', '6月份', '7月份', '8月份', '9月份', '10月份', '11月份', '12月份']
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                name: "月份轮播图数据",
                type: 'line'
            }]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        $.ajax({
            url: "${app}/banner/bannardate",
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

    })

</script>
</body>
</html>