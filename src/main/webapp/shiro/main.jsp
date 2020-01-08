<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" autoFlush="false" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--使用shiro提供的标签判断权限--%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<h1>主页页面</h1>
<%--判断是否有管理员身份--%>
<shiro:hasRole name="admin">
    <%--判断其他权限--%>
    <shiro:hasPermission name="admin:*">
        操作管理员<br>
    </shiro:hasPermission>
    <shiro:hasPermission name="banner:*">
        操作轮播图<br>
    </shiro:hasPermission>

    <shiro:hasPermission name="user:select:*">
        用户查询<br>
    </shiro:hasPermission>
    <shiro:hasPermission name="user:update:*">
        用户修改<br>
    </shiro:hasPermission>
    <shiro:hasPermission name="article:*">
        文章管理<br>
    </shiro:hasPermission>
</shiro:hasRole>
</body>
</html>
