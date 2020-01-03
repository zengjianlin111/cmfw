<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<style type="text/css">
    #ye {
        margin-top: -49px;
        margin-bottom: 10px;
    }
</style>
<script>
    $(function () {
        var subGridTableId;
        <%--专辑管理--%>
        $("#list").jqGrid({
            url: "${app}/album/selectPage",    //查询专辑的路径
            styleUI: "Bootstrap",   //使用Bootstrap样式
            datatype: "json",        //数据类型是json 默认是xml
            autowidth: true,         //宽度自适应父容器
            records: true,           //显示总条数
            rowNum: 3,               //没页显示的几条数据
            rowList: [3, 6, 9],        //可选显示多少条数据
            height: 400,             //表格高度
            caption: "专辑",          //表格标题
            pager: "#gongju",        //分页工具栏显示的位置
            colNames: [
                "id", "标题", "封面", "分数", "作者", "播音员",  //显示的字段
                "集数", "内容简介", "创建时间", "是否激活"
            ],
            colModel: [
                {name: "id"},
                {name: "title"},
                {
                    name: "img", editable: true, edittype: 'file',
                    formatter: function (cellvalue, options, rowObject) {
                        //第一个参数是具体的值
                        //该方法用于自定义显示标签  把显示的格式直接返回即可
                        return "<img style='width:100%;height:100px' src='${pageContext.request.contextPath}/img/" + cellvalue + "'/>";
                    }
                },
                {name: "score"},
                {name: "author"},
                {name: "broadcaster"},
                {name: "counts"},
                {name: "brief"},
                {name: "create_date"},
                {name: "status"}//数据
            ],
            subGrid: true,   //开启子表格
            subGridRowExpanded: function (subGridId, albumId) {
                //创建子表格的方法 第一个参数是子表格id 第二个参数是父类的id
                addSubGrid(subGridId, albumId);
            }
        })

        //添加子表格
        function addSubGrid(subGridId, albumId) {
            //动态表格 id
            subGridTableId = subGridId + "table";
            //动态divid(该div是子表格的工具栏)
            var subGridDivId = subGridId + "div";
            //动态创建表格
            $("#" + subGridId).html("<table id='" + subGridTableId + "'></table>" +
                "<div id='" + subGridDivId + "' style='height:50px'></div>");
            //创建子表格的jqgrid
            $("#" + subGridTableId).jqGrid({
                url: "${app}/chapter/selectPage?albmid=" + albumId,     //子表格查询的路径 需要拼接id
                styleUI: "Bootstrap",    //使用的样式是bootstrap
                datatype: "json",    //返回的数据类型是json
                autowidht: true,      //宽度自使用父容器
                records: true,           //显示总条数
                rowNum: 3,           //每页展示多少条数据
                height: 100,
                caption: "章节",        //子表格标题
                toolbar: [true, "top"],  //工具栏开启向上对齐
                pager: "#" + subGridDivId, //分页工具栏显示的位置是动态div的位置
                rowList: [3, 6, 9],        //选择每页显示的数据
                editurl: "${app}/chapter/addupdatedel?albmid=" + albumId,  //修改添加的url
                multiselect: true,
                colNames: [
                    "id", "标题", "专辑id", "音频大小", "时长", "播放地址",
                    "是否激活"              //子表格的字段名
                ],
                colModel: [
                    {name: "id"},         //子表格的数据
                    {name: "title", editable: true}, //
                    {name: "album_id"},
                    {name: "size"},
                    {name: "duration"},
                    {
                        name: "src", editable: true, edittype: 'file', width: 300,   //设置单个单元格的宽度
                        formatter: function (cellvalue, options, rowObject) {
                            //第一个参数是具体的值
                            //该方法用于自定义显示标签  把显示的格式直接返回即可
                            return "<audio style='width:100%;height:50px' controls src='${pageContext.request.contextPath}/mp3/" + cellvalue + "'/></audion>";
                        }
                    },
                    {name: "status", editable: true, edittype: 'select', editoptions: {value: "激活:激活;未激活:未激活"}}
                ] //下面这句用于显示增删改工具栏
            }).jqGrid("navGrid", "#" + subGridDivId, {search: false, addtext: "添加", edittext: "修改", deltext: "删除"},
                {
                    //修改
                    closeAfterEdit: true,        //提交后关闭显示框
                    afterSubmit: function (response) {
                        //获取添加成功后发送过来的id
                        var chapterId = response.responseJSON.chapterId;
                        if (chapterId != null) {
                            //使用文件上传的ajax
                            $.ajaxFileUpload({
                                url: "${app}/chapter/articUpload",      //文件上传的位置
                                fileElementId: "src",        //上面字段的id
                                data: {chapterId: chapterId},         //数据
                                type: "post",                //数据提交方式 文件上传必须使用post
                                success: function () {
                                    //刷新表格
                                    $("#" + subGridTableId).jqGrid().trigger("reloadGrid");
                                }
                            })
                        }

                        return response;
                    }
                },
                {
                    closeAfterAdd: true,        //提交后关闭显示框
                    //添加
                    afterSubmit: function (response) {

                        //获取添加成功后发送过来的id
                        var bannerId = response.responseJSON.chapterId;
                        //使用文件上传的ajax
                        $.ajaxFileUpload({
                            url: "${app}/chapter/articUpload",      //文件上传的位置
                            fileElementId: "src",        //上面字段的id
                            data: {chapterId: bannerId},         //数据
                            type: "post",                //数据提交方式 文件上传必须使用post
                            success: function () {
                                //刷新表格
                                $("#" + subGridTableId).jqGrid().trigger("reloadGrid");
                                //提示信息
                                //获取提示区域div的id
                                $("#tishi").show();//显示
                                //使用定时器设置显示3秒就不显示了
                                setTimeout(function () {
                                    $("#tishi").hide();//隐藏
                                }, 3000)
                            }
                        })
                        return response;
                    }
                },
                {
                    //删除
                })

            //添加按钮
            $("#t_" + subGridTableId).html("<button class='btn btn-danger' onclick=\"play('" + subGridTableId + "')\">播放 <span class='glyphicon glyphicon-play'></span></button>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "<button class='btn btn-danger' onclick=\"onloads('" + subGridTableId + "')\">下载 <span class='glyphicon glyphicon-arrow-down'></span></button>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "<button id='suspend' class='btn btn-danger' style='display: none' onclick=\"suspend('" + subGridTableId + "')\">暂停<span class='glyphicon glyphicon-pause'></span></button>" +
                "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "<button id='continues' class='btn btn-danger' style='display: none' onclick=\"continues('" + subGridTableId + "')\">继续播放<span class='glyphicon glyphicon-play'></span></button>"
            )
        }

    })

    //音频播放
    function play(subGridTableId) {
        //判断用户是否选中一行 未选中id=null 选中id被选中行的id
        var gr = $("#" + subGridTableId).jqGrid("getGridParam", 'selrow');
        if (gr == null) {
            alert("请选择要播放的音乐");
        }
        else {
            //请求后台
            //通过jegrid提供的方法根据id拿到对应的值
            var date = $("#" + subGridTableId).jqGrid("getRowData", gr);

            //由于src地址改变为一个audio标签将这个标签封装为一个对象
            var src = $(date.src).prop("src");
            var embed = $("#embed");
            //显示暂停按钮
            $("#suspend").show();
            // embed.prop("muted",false);
            // embed设置播放地址
            embed.prop("src", src);
            embed.prop("autoplay", true); //立马开始播放音乐

        }

    }

    //音频暂停
    function suspend(ss) {
        //获取音频标签对象
        var embed = $("#embed");
        //暂停播放
        embed[0].pause();
        //获取继续播放div的对象
        var continues = $("#continues");
        //删除继续播放的隐藏属性
        continues.show();
    }

    //音频继续播放
    function continues(ss) {
        //继续播放音乐
        document.getElementById('embed').play();
        //隐藏继续播放按钮
        $("#continues").hide();
    }


    //文件下载
    function onloads(subGridTableId) {
        console.log("文件下载进入")
        //判断用户是否选中一行 未选中id=null 选中id被选中行的id
        var gr = $("#" + subGridTableId).jqGrid("getGridParam", 'selrow');
        if (gr == null) {
            alert("请选择要下载的音乐");
        }
        else {
            //请求后台
            //通过jegrid提供的方法根据id拿到对应的值
            var date = $("#" + subGridTableId).jqGrid("getRowData", gr);
            //由于src地址改变为一个audio标签将这个标签封装为一个对象
            var src = $(date.src).prop("src");

            location.href = "${app}/chapter/fileonload?filename=" + src;
        }

    }
</script>
<%--页头--%>
<div class="page-header">
    <h1 id="ye">轮播图管理</h1>
</div>
<%--专辑的表格--%>
<table id="list"></table>
<audio src="#" id="embed"></audio>
<%--专辑的工具栏--%>
<div id="gongju"></div>
<%--提示的div--%>
<div id="tishi"></div>