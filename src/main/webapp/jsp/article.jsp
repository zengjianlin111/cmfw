<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<style type="text/css">
    #ye {
        margin-top: -49px;
        margin-bottom: 10px;
    }
</style>

<script charset="utf-8" src="${app}/kindeditor/kindeditor-all-min.js"></script>
<script charset="utf-8" src="${app}/kindeditor/lang/zh-CN.js"></script>

<script>
    var ss;  //内容显示的id
    var dangqianid;  //修改回显的id
    $(function () {
        <%--文章管理--%>
        $("#list").jqGrid({
            url: "${app}/article/selectPage",    //查询文章的路径
            styleUI: "Bootstrap",   //使用Bootstrap样式
            datatype: "json",        //数据类型是json 默认是xml
            autowidth: true,         //宽度自适应父容器
            records: true,           //显示总条数
            rowNum: 3,               //没页显示的几条数据
            rowList: [3, 6, 9],        //可选显示多少条数据
            height: 400,             //表格高度
            caption: "文章",          //表格标题
            pager: "#gongju",        //分页工具栏显示的位置
            editurl: "${app}/article/addupdatedel",
            multiselect: true,
            colNames: [
                "id", "标题", "作者", "作者", "大师id", "上传时间", "激活状态", "操作" //显示的字段
            ],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "author", editable: true},
                {name: "content", hidden: true},
                /*{name:"content",editable:true,edittype:'file',
                    formatter:function (cellvalue,options,rowObject) {


                        return "<a href='#' class='btn btn-danger'  onclick=\"xianshis(ss)\">查看内容</a>";
                    }
                },*/
                {name: "guru_id", editable: true},
                {name: "create_date"},
                {name: "status", editable: true, edittype: 'select', editoptions: {value: "激活:激活;未激活:未激活"}},
                {
                    name: "", edittype: 'file',
                    formatter: function (cellvalue, options, rowObject) {
                        //第一个参数是具体的值
                        //该方法用于自定义显示标签  把显示的格式直接返回即可
                        return "<a href='#' class='glyphicon glyphicon-list' onclick=\"xianshis('" + rowObject.id + "')\"></a>" +
                            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                            "<a href='#' class='glyphicon glyphicon-pencil' onclick=\"huixian('" + rowObject.id + "')\"></a>";
                    }
                }
            ]
        }).jqGrid("navGrid", "#gongju", {search: false, add: false, addtext: "添加", edittext: "修改", deltext: "删除"},
            {
                //修改
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    return response;
                }
            },
            {
                //添加
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    return response;
                }
            },
            {
                //删除
            }
        )


    });
    //富文本编辑器
    var editor = KindEditor.create("textarea[name='contents']", {
        minWidth: 500,  //最小宽  类型int
        minHeight: 300,
        resizeType: 0,       //设置是否可以拖动 0表示不可以拖动 1表示只可以拖动高 2可以任意拖动
        allowFileManager: true,    //是否展示 图片空间
        filePostName: 'img',       //上传是后台接收的名字
        uploadJson: '${app}/article/richtext', //上传后台的路径
        fileManagerJson: "${app}/article/imagespace",  //图片空间的访问路径
        afterBlur: function (url) {       //失去焦点时触发
            //刷新富文本的文本域
            editor.sync();
            //将富文本中的数据添加的上传的隐藏文本域中
            //获取隐藏文本域对象
            $("#texs").val($("#tex").val());
        }
    });


    //修改富文本编辑器
    var editor1 = KindEditor.create("textarea[name='contents1']", {
        minWidth: 500,  //最小宽  类型int
        minHeight: 300,
        resizeType: 0,       //设置是否可以拖动 0表示不可以拖动 1表示只可以拖动高 2可以任意拖动
        allowFileManager: true,    //是否展示 图片空间
        filePostName: 'img',       //上传是后台接收的名字
        uploadJson: '${app}/article/richtext', //上传后台的路径
        fileManagerJson: "${app}/article/imagespace",  //图片空间的访问路径
        afterBlur: function (url) {       //失去焦点时触发
            //刷新修改富文本的文本域
            editor1.sync();
            //将富文本中的数据添加的上传的隐藏文本域中
            //获取隐藏文本域对象
            $("#texss").val($("#tex1").val());
        }
    });


    //文章添加
    function sssit() {
        $.ajax({
            url: "${app}/article/suit",
            datatype: "json",
            data: $("#biaodao").serialize(),//表单序列化
            success: function (dates) {
                //关闭模态框
                $("#article").modal('hide');
                //刷新表格
                $("#list").jqGrid().trigger("reloadGrid");
                //清除表单数据(要指定下标)
                $('#biaodao')[0].reset();
                //清除富文本的内容
                editor.html("");
            }
        })
    }


    //显示
    function xianshis(ss) {
        //获取选中行的数据
        //通过选中行的id获取一行的数据
        var date = $("#list").jqGrid("getRowData", ss);
        // //获取模态框中的文本域
        var shanshi = $("#shanshi");
        // //向模态框中添加数据
        // shanshi.html(grid.dat);  // //向模态框中添加数据
        shanshi.html(date.content);
        //展示模态框
        $("#details").modal('show');
    }

    //修改回显
    function huixian(dangqianid) {
        //通过选中行的id获取一行的数据
        var date = $("#list").jqGrid("getRowData", dangqianid);
        //获取标题显示对象
        var title = $("#title1");
        //作者
        var author = $("#author1");
        //上师id
        var guru_id = $("#guru_id1");
        //激活状态
        var status = $("#status1");
        //id该字段为隐藏属性
        var updateid = $("#updateid");


        //数据
        //向表单中添加数据
        title.val(date.title);
        author.val(date.author);
        guru_id.val(date.guru_id);
        status.val(date.status);
        updateid.val(date.id);


        //富文本
        var tex = $("#tex1");
        //向富文本中添加数据
        editor1.html(date.content);
        //显示模态框
        $("#updateModel").modal('show');
    }


    //修改回显模态框关闭时清除数据
    $('#updateModel').on('hidden.bs.modal', function () {
        console.log("关闭模态框");
        //清除表单数据(要指定下标)
        $('#biaodao1')[0].reset();
        //清除富文本的内容
        editor1.html("");
    })


    //修改提交数据
    function submit() {
        $.ajax({
            url: "${app}/article/updatearticle",
            datatype: "json",
            data: $("#biaodao1").serialize(),//表单序列化
            success: function (dates) {
                //关闭模态框
                $("#updateModel").modal('hide');
                //刷新表格
                $("#list").jqGrid().trigger("reloadGrid");
                //清除表单数据(要指定下标)
                $('#biaodao1')[0].reset();
                //清除富文本的内容
                editor1.html("");
            }
        })
    }
</script>
<%--标签页--%>
<ul class="nav nav-tabs" role="tablist">
    <li role="presentation" class="active">
        <a href="#home" aria-controls="home" role="tab" data-toggle="tab">文章信息
        </a>
    </li>
    <li role="presentation">
        <a href="#article" data-toggle="modal" aria-controls="profile" role="tab" data-toggle="tab">添加文章
        </a>
    </li>

</ul>


<%--模态框  tabindex="-1" 不能有不然会和富文本冲突--%>
<div class="modal fade" role="dialog" id="article">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">添加文章</h4>
            </div>
            <div class="modal-body">
                <p>添加文章</p>
                <center>
                    <form method="post" action="#" id="biaodao">
                        <div class="input-group" style="width: 250px">
                            <span class="input-group-addon">标题:</span>
                            <input class="form-control" type="text" name="title" id="title"><br>
                        </div>

                        <div class="input-group" style="width: 250px">
                            <span class="input-group-addon">作者:</span>
                            <input class="form-control" type="text" name="author" id="author"><br>
                        </div>


                        <div class="input-group" style="width: 250px">
                            <span class="input-group-addon">上师:</span>
                            <input class="form-control" type="text" name="guru_id" id="guru_id"><br>
                        </div>

                        激活状态:<select name="status" id="status">
                        <option value="激活">激活</option>
                        <option value="未激活">未激活</option>
                    </select><br>
                        <textarea id="tex" name="contents" style="width: 500px;height: 200px"></textarea>
                        <%--隐藏文本域用于上传数据--%>
                        <textarea id="texs" style="display:none;" name="content"></textarea>
                    </form>
                </center>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="sssit();">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<%--修改模态框  tabindex="-1" 不能有不然会和富文本冲突--%>
<div class="modal fade" role="dialog" id="updateModel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改文章</h4>
            </div>
            <div class="modal-body">
                <p>修改文章</p>
                <center>
                    <form method="post" action="#" id="biaodao1">
                        <input style="display:none;" type="text" id="updateid" name="id">
                        <div class="input-group" style="width: 250px">
                            <span class="input-group-addon">标题:</span>
                            <input class="form-control" type="text" name="title" id="title1"><br>
                        </div>

                        <div class="input-group" style="width: 250px">
                            <span class="input-group-addon">作者:</span>
                            <input class="form-control" type="text" name="author" id="author1"><br>
                        </div>

                        <div class="input-group" style="width: 250px">
                            <span class="input-group-addon">上师:</span>
                            <input class="form-control" type="text" name="guru_id" id="guru_id1"><br>
                        </div>

                        激活状态:<select name="status" id="status1">
                        <option value="激活">激活</option>
                        <option value="未激活">未激活</option>
                    </select><br>
                        <textarea id="tex1" name="contents1" style="width: 500px;height: 200px"></textarea>
                        <%--隐藏文本域用于上传数据--%>
                        <textarea id="texss" style="display:none;" name="content"></textarea>
                    </form>
                </center>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="submit();">修改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<%--展示文章内容--%>
<%--模态框  tabindex="-1" 不能有不然会和富文本冲突--%>
<div class="modal fade" role="dialog" id="details">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">文章内容</h4>
            </div>
            <div class="modal-body">
                <center>
                    <span id="shanshi"></span>
                </center>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->


<%--文章的表格--%>
<table id="list"></table>
<audio src="#" id="embed"></audio>
<%--文章的表格的工具栏--%>
<div id="gongju"></div>
<%--提示的div--%>
<div id="tishi"></div>
