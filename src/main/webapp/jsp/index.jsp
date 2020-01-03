<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <%--1.bootstrap 核心 css--%>
    <link rel="stylesheet" href="${app}/boot/css/bootstrap.min.css">
    <%--2.jqgrid相关css--%>
    <link rel="stylesheet" href="${app}/jqgrid/css/trirand/ui.jqgrid-bootstrap.css">
    <%--3.jquery核心js--%>
    <script src="${app}/boot/js/jquery-2.2.1.min.js"></script>
    <%--4.bootstrap相关js--%>
    <script src="${app}/boot/js/bootstrap.min.js"></script>
    <%--5.jqgrid相关js--%>
    <script src="${app}/jqgrid/js/trirand/jquery.jqGrid.min.js"></script>
    <script src="${app}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <%--上传--%>
    <script src="${app}/boot/js/ajaxfileupload.js"></script>
    <script type="text/javascript">
        $(function () {
            //轮播自动播放
            $('#myCarousel').carousel({
                //自动1秒播放
                interval: 2000,
            });
        })

    </script>
</head>
<body>
<!--input框组-->
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">持明法洲后台管理系统</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">持明法洲后台管理系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a href="#">欢迎小黑</a></li>
                <li><a href="#">退出登录<span class="glyphicon glyphicon-log-out"></span></a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


<div class="container-fluid">
    <div class="row">
        <div class="col-md-3">
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingOne">
                        <h4 class="panel-title">
                            <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                               aria-expanded="true" aria-controls="collapseOne">
                                <center>用户管理</center>
                            </a>
                        </h4>
                    </div>
                    <div id="collapseOne" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingOne">
                        <!--面板-->
                        <!--面板-->
                        <div class="panel-body">

                            <!-- button -->
                            <a href="${pageContext.request.contextPath}/user/select"></a>
                            <center>
                                <%--
                                    javascript:void(0);阻止页面提交
                                    ('#yemian').load()跳转到页面

                                --%>
                                <a onclick="$('#yemian').load('zuoye2.jsp')"
                                   class="btn btn-primary btn-danger btn-block" data-toggle="button"
                                   href="javascript:void(0);" id="tiao">
                                    用户列表
                                </a>
                                <%-- <button type="button" class="btn btn-primary btn-danger"  aria-pressed="false" autocomplete="off">
                                     用户列表
                                 </button>--%>
                            </center>

                        </div>
                    </div>
                </div>

                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                <center>上师管理</center>
                            </a>
                        </h4>
                    </div>
                    <div id="shangshi" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <%--显示的内容--%>
                        </div>
                    </div>
                </div>
                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingThree">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#wenzhang" aria-expanded="false" aria-controls="collapseThree">
                                <center>文章管理</center>
                            </a>
                        </h4>
                    </div>
                    <div id="wenzhang" class="panel-collapse collapse" role="tabpanel"
                         aria-labelledby="headingThree">
                        <div class="panel-body">
                            <%--显示的内容--%>
                            <center><a class="btn btn-default btn-lg"
                                       href="javascript:$('#yemian').load('${pageContext.request.contextPath}/jsp/article.jsp')">
                                专辑列表
                            </a></center>
                        </div>
                    </div>
                </div>


                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#zhuanji" aria-expanded="false" aria-controls="collapseTwo">
                                <center>专辑管理</center>
                            </a>
                        </h4>
                    </div>
                    <div id="zhuanji" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <%--显示的内容--%>
                            <center><a class="btn btn-default btn-lg"
                                       href="javascript:$('#yemian').load('${pageContext.request.contextPath}/jsp/slideshow.jsp')">
                                专辑列表
                            </a></center>
                        </div>
                    </div>
                </div>


                <div class="panel panel-default">
                    <div class="panel-heading" role="tab" id="headingTwo">
                        <h4 class="panel-title">
                            <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                               href="#luobo" aria-expanded="false" aria-controls="collapseTwo">
                                <center>轮播图管理</center>
                            </a>
                        </h4>
                    </div>
                    <div id="luobo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                        <div class="panel-body">
                            <%--显示的内容--%>
                            <center><a class="btn btn-default btn-lg"
                                       href="javascript:$('#yemian').load('${pageContext.request.contextPath}/jsp/banner.jsp')">
                                轮播图列表
                            </a></center>
                        </div>
                    </div>
                </div>
            </div>
        </div>


        <%--左边的--%>
        <!--主页-->
        <div class="col-md-9" id="yemian">
            <!--页头-->
            <div class="jumbotron">
                <%--<h3><a href="#" class="list-group-item disabled">--%>
                <%--欢迎来到持明法洲后台管理系统</a></h3>--%>
                <!--input框组-->
                <h3 style="text-align: left"><span style="color: #8c8c8c">欢迎来到持明法洲后台管理系统</span></h3>
            </div>


            <%--轮播图--%>
            <div id="myCarousel" class="carousel slide">

                <ol class="carousel-indicators">

                    <li data-target="#myCarousel" data-slide-to="0" class="active"></li>

                    <li data-target="#myCarousel" data-slide-to="1"></li>

                    <li data-target="#myCarousel" data-slide-to="2"></li>

                </ol>
                <div class="carousel-inner">

                    <div class="item active" style="background:#223240">

                        <img style="width: 1249px;height: 400px;"
                             src="${pageContext.request.contextPath}/img/shouye.jpg" alt="first img">
                        <!--图片不居中，让图片居中给这个img设置margin:0 auto-->

                    </div>

                    <div class="item" style="background:#F5E4DC;">

                        <img style="width: 1249px;height: 400px;"
                             src="${pageContext.request.contextPath}/img/shouye.jpg" alt="second img">

                    </div>

                    <div class="item" style="background:#DE2A2D;">

                        <img style="width: 1250px;height: 400px;"
                             src="${pageContext.request.contextPath}/img/shouye.jpg" alt="third img">

                    </div>

                </div>

                <a href="#myCarousel" data-slide="prev" class="carousel-control left">

                    <span class="glyphicon glyphicon-chevron-left"></span>

                </a>

                <a href="#myCarousel" data-slide="next" class="carousel-control right">

                    <span class="glyphicon glyphicon-chevron-right"></span>

                </a>
            </div>
        </div>


        <%--<div class="row" style="height: 10px;">--%>
        <%--<div class="col-md-12" >--%>
        <%--&lt;%&ndash;下面的标识部分&ndash;%&gt;--%>
        <%--<nav class="navbar navbar-default navbar-fixed-bottom">--%>

        <%----%>
        <%--</nav>--%>

        <%--</div>--%>
        <%--</div>--%>

        <nav class="navbar navbar-default navbar-fixed-bottom">
            <div class="container">
                <p class="navbar-text" style="float: none;text-align: center"> &#64;百知教育&#64;www.250.com</p>
            </div>
        </nav>

    </div>
</div>


</body>
</html>