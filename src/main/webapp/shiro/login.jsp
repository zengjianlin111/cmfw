<%@page contentType="text/html; utf-8" pageEncoding="UTF-8" isELIgnored="false" autoFlush="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<h1>登录页面</h1>
<form method="post" action="${pageContext.request.contextPath}/shiro/login">
    username<input type="text" name="username" placeholder="用户名"><br>
    password<input type="text" name="password" placeholder="密码"><br>
    <input type="submit" value="登录">
</form>
</body>
</html>
