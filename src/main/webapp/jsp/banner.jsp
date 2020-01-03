<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<script type="text/javascript">
    <%--使用jqgrad创建表格--%>
    $(function () {
        $("#tab").jqGrid({
            url: "${app}/banner/paging",   //查询时的url访问路径
            datatype: "json",  //接收的数据类型是json默认是xml
            styleUI: "Bootstrap",   //使用的技术的bootstrap
            pager: "#daohang",      //分页导航条显示的位置
            colNames: ["id", "title", "img", "createDate", "status"], //表格上面的列
            caption: "轮播图",        //子表格标题
            colModel: [{name: "id"},   //显示的id对应上面的id  id
                {name: "title", editable: true},  //默认可以操作(用于添加)  名字
                {
                    name: "img", editable: true, edittype: 'file',
                    formatter: function (cellvalue, options, rowObject) {
                        //第一个参数是具体的值
                        //该方法用于自定义显示标签  把显示的格式直接返回即可
                        return "<img style='width:100%;height:100px' src='${pageContext.request.contextPath}/img/" + cellvalue + "'/>";
                    }
                }, //默认显示并且类型是file  图片
                {name: "createDate", edittype: 'date',},
                {
                    name: "status",
                    editable: true,
                    editable: true,
                    edittype: 'select',
                    editoptions: {value: "激活:激活;未激活:未激活"}
                }
            ],
            rowNum: 2,       //每页显示的条数
            rowList: [2, 4, 8],   //选择每页显示的条数
            autowidth: true,     //自适应父容器
            viewrecords: true,  //显示总记录数
            multiselect: true,   //是否多选
            height: '400px',       //设置高度
            editurl: "${app}/banner/addbanner"         //增删改的路径
        }).jqGrid("navGrid", "#daohang", {search: false, addtext: "添加", edittext: "修改", deltext: "删除"},
            {
                //修改
                closeAfterEdit: true,        //提交后关闭显示框
                afterSubmit: function (response) {
                    //获取添加成功后发送过来的id
                    var bannerId = response.responseJSON.bannerId;
                    //使用文件上传的ajax
                    $.ajaxFileUpload({
                        url: "${app}/banner/fileload",      //文件上传的位置
                        fileElementId: "img",        //上面字段的id
                        data: {bannerId: bannerId},         //数据
                        type: "post",                //数据提交方式 文件上传必须使用post
                        success: function () {
                            //刷新表格
                            $("#tab").jqGrid().trigger("reloadGrid");
                        }
                    })
                    return response;
                }
            },
            {
                closeAfterAdd: true,        //提交后关闭显示框
                //添加
                afterSubmit: function (response) {
                    console.log("添加的id:" + response.responseJSON.bannerId)
                    //获取添加成功后发送过来的id
                    var bannerId = response.responseJSON.bannerId;
                    //使用文件上传的ajax
                    $.ajaxFileUpload({
                        url: "${app}/banner/fileload",      //文件上传的位置
                        fileElementId: "img",        //上面字段的id
                        data: {bannerId: bannerId},         //数据
                        type: "post",                //数据提交方式 文件上传必须使用post
                        success: function () {
                            //刷新表格
                            $("#tab").jqGrid().trigger("reloadGrid");
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
        //下面显示的删除修改添加的小图标 第一个参数固定 第二个参数是显示的位置
        //后面是图标的参数
    })


    //导入表格数据
    function uploading() {
        $.ajaxFileUpload({
            url: "${app}/banner/uploading",      //文件上传的位置
            fileElementId: "file",        //上面字段的id
            type: "post",                //数据提交方式 文件上传必须使用post
            success: function () {

            }
        })
    }
</script>
<a href="${app}/banner/derivedtable">导出数据</a>
<form id="uploading" enctype="multipart/form-data" method="post">
    导入数据<input id="file" type="file" name="file">
    <input type="button" value="确认" onclick="uploading()">
</form>
<table id="tab"></table>
<%--分页导航条的位置--%>
<div id="daohang"></div>
<div id="tishi" style="display: none;height: 50px;color: green;">
    <%--默认不显示--%>
    添加成功
</div>