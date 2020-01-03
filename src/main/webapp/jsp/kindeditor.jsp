<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" contentType="text/html; utf-8" pageEncoding="UTF-8" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <script charset="utf-8" src="${app}/kindeditor/kindeditor-all-min.js"></script>
    <script charset="utf-8" src="${app}/kindeditor/lang/zh-CN.js"></script>
    <script>
        KindEditor.ready(function (K) {
            K.create('#editor_id', {
                width: '400px',
                height: '500px',
                minHeight: 400,
                resizeType: 0,
                allowFileManager: true,    //是否展示 图片空间
                filePostName: 'img',       //上传是后台接收的名字
                uploadJson: '${app}/kindeditor/uploadImg', //上传后台的路径
                fileManagerJson: "${app}/kindeditor/getAllImgs"
            });
        });
    </script>
</head>
<body>
<form>
    <textarea id="editor_id" name="content" style="width:700px;height:300px;">
        请输入内容

    </textarea>
</form>
</body>
</html>