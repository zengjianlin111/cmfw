<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap Login Form Template</title>
    <!-- CSS -->
    <link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="assets/css/form-elements.css">
    <link rel="stylesheet" href="assets/css/style.css">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="assets/ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="assets/ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="assets/ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="assets/ico/apple-touch-icon-57-precomposed.png">
    <script src="../boot/js/jquery-2.2.1.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.backstretch.min.js"></script>
    <script src="assets/js/scripts.js"></script>
    <script src="../boot/js/jquery.validate.min.js"></script>
    <script src="../boot/js/jquery.validate.min.js"></script>
    <script>
        $(function () {
            //点击切换验证码
            $("#captchaImage").click(function () {
                //改变图片路径
                $("#captchaImage").prop("src", "${pageContext.request.contextPath}/vcodeimg/img?ss=" + new Date().getTime())
            });


            $("#loginButtonId").click(function () {

                //获取表单里面input的对象
                //验证码
                var code = $("#form-code").val();
                //密码
                var password = $("#form-password").val();
                //用户名
                var username = $("#form-username").val();
                console.log("验证码:" + code + "  密码:" + password + "  用户名:" + username)
                //判断用户是否输入数据
                if (code != "" && password != "" && username != "") {

                    //改变图片路径
                    $("#captchaImage").prop("src", "${pageContext.request.contextPath}/vcodeimg/img?ss=" + new Date().getTime())

                    $.ajax(
                        {
                            url: "${pageContext.request.contextPath}/admin/login",
                            data: $("#loginForm").serialize(),
                            datatype: "json",
                            success: function (data) {
                                //date是数据
                                //判断是否登录成功 是否跳转到首页
                                console.log("数据:" + data)
                                if (data == "登录成功") {
                                    //登录成功跳转到首页
                                    location.href = "${pageContext.request.contextPath}/jsp/index.jsp";
                                }
                                //获取显示文字的font
                                $("#fon").text(data);
                            }
                        }
                    )
                }
                else {
                    //获取显示文字的font
                    $("#fon").text("请输入必须要输入的数据不然腿打断");
                }


            })

        })
    </script>
</head>

<body>

<!-- Top content -->
<div class="top-content">

    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="col-sm-8 col-sm-offset-2 text">
                    <h1><strong>CMFZ</strong> Login Form</h1>
                    <div class="description">
                        <p>
                            <a href="#"><strong>CMFZ</strong></a>
                        </p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top" style="width: 450px">
                        <div class="form-top-left">
                            <h3>Login to showall</h3>
                            <p>Enter your username and password to log on:</p>
                            <font id="fon" color="red"></font>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-key"></i>
                        </div>
                    </div>
                    <div class="form-bottom" style="width: 450px">
                        <form role="form" action="${pageContext.request.contextPath}/admin/login" method="post"
                              class="login-form" id="loginForm">
                            <span id="msgDiv"></span>
                            <div class="form-group">
                                <label class="sr-only" for="form-username">Username</label>
                                <input type="text" name="username" placeholder="请输入用户名..."
                                       class="form-username form-control" id="form-username">
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Password</label>
                                <input type="password" name="password" placeholder="请输入密码..."
                                       class="form-password form-control" id="form-password">
                            </div>
                            <div class="form-group">
                                <img id="captchaImage" style="height: 48px" class="captchaImage"
                                     src="${pageContext.request.contextPath}/vcodeimg/img">
                                <input style="width: 289px;height: 50px;border:3px solid #ddd;border-radius: 4px;"
                                       type="test" name="vode" id="form-code">
                            </div>
                            <input type="button" style="width: 400px;border:1px solid #9d9d9d;border-radius: 4px;"
                                   id="loginButtonId" value="登录">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>


</body>

</html>