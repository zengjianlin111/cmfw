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
    <script charset="utf-8" src="${app}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${app}/kindeditor/lang/zh-CN.js"></script>
    <%--引入goeasy--%>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        $(function () {
            //富文本编辑器
            var editor = KindEditor.create("textarea[name='contents']", {
                minWidth: 500,  //最小宽  类型int
                minHeight: 300,
                resizeType: 0,       //设置是否可以拖动 0表示不可以拖动 1表示只可以拖动高 2可以任意拖动
                afterBlur: function (url) {       //失去焦点时触发
                    //刷新富文本的文本域
                    editor.sync();
                    alert("触发");
                }
            });

            //使用goeasy
            var goEasy = new GoEasy({
                host: "hangzhou.goeasy.io",  //所在的区域 hangzhou.goeasy.io表示杭州
                appkey: "BC-935895745dd747ce98b34c40433d9679"           //应用的kay是否的只订阅还是 可以订阅和发布
            });

            //接收消息
            goEasy.subscribe({
                channel: "liantian",  //替换为自己的channel域
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


            function fasong() {
                //发送消息
                goEasy.publish({
                    channel: "liantian", //替换为您自己的channel
                    message: "测试1" //替换为您想要发送的消息内容
                });
            }


        })


    </script>
</head>
<body>
<script type="text/javascript">
</script>
<textarea id="tt" name="content" style="width: 500px;height: 500px"></textarea>
<textarea id="text" name="contents"></textarea>
<button onclick="fasong">发送</button>
</body>
</html>