<%--
  Created by IntelliJ IDEA.
  User: kgc
  Date: 2020/6/21
  Time: 上午8:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Obaju : e-commerce template</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <!-- Bootstrap CSS-->
    <link rel="stylesheet" href="vendor/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="vendor/font-awesome/css/font-awesome.min.css">
    <!-- Google fonts - Roboto -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,700">
    <!-- owl carousel-->
    <link rel="stylesheet" href="vendor/owl.carousel/assets/owl.carousel.css">
    <link rel="stylesheet" href="vendor/owl.carousel/assets/owl.theme.default.css">
    <!-- theme stylesheet-->
    <link rel="stylesheet" href="css/style.default.css" id="theme-stylesheet">
    <!-- Custom stylesheet - for your changes-->
    <link rel="stylesheet" href="css/custom.css">
    <!-- Favicon-->
    <link rel="shortcut icon" href="favicon.png">
    <!-- Tweaks for older IEs--><!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script><![endif]-->
</head>
<body>
<!-- navbar-->
<header class="header mb-5">
    <!--
    *** TOPBAR ***
    _________________________________________________________
    -->
    <div id="top">
        <div class="container">
            <div class="row">
                <div class="col-lg-6 text-lg-right" style="float: right">
                    <ul class="menu list-inline mb-0">
                        <li id="shownickname" class="list-inline-item">
                        </li>
                        <li id="online" class="list-inline-item"></li>
                        <li id="quitlogin" class="list-inline-item"></li>
                    </ul>
                </div>
            </div>
        </div>
        <!-- *** TOP BAR END ***-->


    </div>
    <nav class="navbar navbar-expand-lg">
        <div class="container"><a href="index.jsp" class="navbar-brand home"><img src="img/logo.png" alt="Obaju logo"
                                                                                  class="d-none d-md-inline-block"><img
                src="img/logo-small.png" alt="Obaju logo" class="d-inline-block d-md-none"><span class="sr-only">Obaju - go to homepage</span></a>
            <div class="navbar-buttons">
                <button type="button" data-toggle="collapse" data-target="#navigation"
                        class="btn btn-outline-secondary navbar-toggler"><span
                        class="sr-only">Toggle navigation</span><i class="fa fa-align-justify"></i></button>
                <button type="button" data-toggle="collapse" data-target="#search"
                        class="btn btn-outline-secondary navbar-toggler"><span class="sr-only">Toggle search</span><i
                        class="fa fa-search"></i></button>
                <a href="${pageContext.request.contextPath}/Carts.jsp" class="btn btn-outline-secondary navbar-toggler"><i
                        class="fa fa-shopping-cart"></i></a>
            </div>
            <div id="navigation" class="collapse navbar-collapse">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item"><a href="index.jsp" class="nav-link active">主页</a></li>
                    <li class="nav-item dropdown menu-large">
                        <a href="#" onclick="toComs()" data-toggle="dropdown" data-hover="dropdown" data-delay="200"
                           class="nav-link">全部商品</a>
                    </li>
                    <li class="nav-item dropdown menu-large">
                        <a href="#" onclick="toInfo()" data-toggle="dropdown" data-hover="dropdown" data-delay="200"
                           class="nav-link">账户信息</a>
                    </li>
                    <li class="nav-item dropdown menu-large">
                        <a href="#" onclick="toOrders()" data-toggle="dropdown" data-hover="dropdown" data-delay="200"
                           class="nav-link">我的订单</a>
                    </li>
                </ul>
                <div class="navbar-buttons d-flex justify-content-end">
                    <!-- /.nav-collapse-->
                    <div id="search-not-mobile" class="navbar-collapse collapse"></div>
                    <a data-toggle="collapse" href="#search" class="btn navbar-btn btn-primary d-none d-lg-inline-block"
                       onclick="getFocus()"><span class="sr-only">Toggle search</span><i class="fa fa-search"></i></a>
                    <div id="basket-overview" class="navbar-collapse collapse d-none d-lg-block"><a
                            href="${pageContext.request.contextPath}/carts.action?methodName=getCarts"
                            class="btn btn-primary navbar-btn"><i
                            class="fa fa-shopping-cart"></i><span>购物车</span></a></div>
                </div>
            </div>
        </div>
    </nav>
    <div id="search" class="collapse">
        <div class="container">
            <form role="search" class="ml-auto">
                <div class="input-group">
                    <input type="text" placeholder="Search" id="searchText" class="form-control">
                    <div class="input-group-append">
                        <button type="button" class="btn btn-primary" onclick="toSearch()"><i class="fa fa-search"></i>
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</header>
<div id="all">
    <div id="content">
        <div class="container">
            <div class="row">
                <div class="col-lg-3">
                    <!--
                    *** CUSTOMER MENU ***
                    _________________________________________________________
                    -->
                    <div class="card sidebar-menu">
                        <div class="card-header">
                            <h3 class="h4 card-title">客户服务</h3>
                        </div>
                        <div class="card-body">
                            <ul class="nav nav-pills flex-column">
                                <a href="${pageContext.request.contextPath}/userInfo.action?methodName=getUserInfo"
                                   class="nav-link"><i class="fa fa-user"></i>我的账户</a>
                                <a href="${pageContext.request.contextPath}/order.action?methodName=showOrders"
                                   class="nav-link active"><i class="fa fa-list"></i>我的订单</a>
                                <a id="logout" href="${pageContext.request.contextPath}/person.action?methodName=logout"
                                   class="nav-link"><i class="fa fa-sign-out"></i>注销</a>
                            </ul>
                        </div>
                    </div>
                    <!-- /.col-lg-3-->
                    <!-- *** CUSTOMER MENU END ***-->
                </div>
                <div id="customer-order" class="col-lg-9">
                    <div class="box">
                        <h1>订单 #${requestScope.orderItemMap.oid}</h1>
                        <div class="table-responsive mb-4">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th colspan="2">商品</th>
                                    <th width="80px">数量</th>
                                    <th>单价</th>
                                    <th>总计</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${requestScope.orderItemMap.data}" var="OrderItem" varStatus="status">
                                    <tr>
                                        <td>
                                                <%--                                                ${pageContext.request.contextPath}/detail.action?methodName=showDetail&cId=${OrderItem.itemCid}--%>
                                            <a href="#"><img
                                                    src="${OrderItem.CUrl}" alt="White Blouse Armani"></a></td>
                                        <td>
                                            <a href="#">${OrderItem.CName}</a>
                                        </td>
                                        <td>${OrderItem.itemNum}</td>
                                        <td>${OrderItem.CPrice}</td>
                                        <td>${OrderItem.itemMoney}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th colspan="5" class="text-right">总计</th>
                                    <th>${requestScope.orderItemMap.tMoney}</th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                        <!-- /.table-responsive-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--
*** FOOTER ***
_________________________________________________________
-->
<div id="footer">
    <div class="container">
        <div class="row">
            <div class="col-lg-3 col-md-6">
                <h4 class="mb-3">页数</h4>
                <ul class="list-unstyled">
                    <li><a href="text.html">关于我们</a></li>
                    <li><a href="text.html">条件条款</a></li>
                    <li><a href="faq.html">FAQ</a></li>
                    <li><a href="contact.html">联系我们</a></li>
                </ul>
                <hr>
                <h4 class="mb-3">用户手册</h4>
                <ul class="list-unstyled">
                    <li><a href="#" data-toggle="modal" data-target="#login-modal">登录</a></li>
                    <li><a href="register.html">注册</a></li>
                </ul>
            </div>
            <!-- /.col-lg-3-->
            <div class="col-lg-3 col-md-6">
                <h4 class="mb-3">类别</h4>
                <h5>指导者</h5>
                <ul class="list-unstyled">
                    <li><a href="category.jsp">显卡发烧间</a></li>
                    <li><a href="category.jsp">动力节点间</a></li>
                    <li><a href="category.jsp">奥里给</a></li>
                </ul>
                <h5>挑战者</h5>
                <ul class="list-unstyled">
                    <li><a href="category.jsp">生活防水5米</a></li>
                    <li><a href="category.jsp">抗蓝光</a></li>
                    <li><a href="category.jsp">抗击打</a></li>
                    <li><a href="category.jsp">抗震动</a></li>
                </ul>
            </div>
            <!-- /.col-lg-3-->
            <div class="col-lg-3 col-md-6">
                <h4 class="mb-3">在哪里找到我们</h4>
                <p><strong>中科电商谷9号楼</strong><br>13/25 New Avenue<br>旧宫镇<br>45Y 73J<br>中国<br><strong>北京市</strong></p><a
                    href="contact.html">进入联系页面</a>
                <hr class="d-block d-md-none">
            </div>
            <!-- /.col-lg-3-->
            <div class="col-lg-3 col-md-6">
                <h4 class="mb-3">得到这个消息</h4>
                <p class="text-muted">佩伦特式的居住者以他的死亡和死亡而闻名于世。</p>
                <form>
                    <div class="input-group">
                        <input type="text" class="form-control"><span class="input-group-append">
                  <button type="button" class="btn btn-outline-secondary">订阅!</button></span>
                    </div>
                    <!-- /input-group-->
                </form>
                <hr>
                <h4 class="mb-3">保持联系</h4>
                <p class="social"><a href="#" class="facebook external"><i class="fa fa-facebook"></i></a><a href="#"
                                                                                                             class="twitter external"><i
                        class="fa fa-twitter"></i></a><a href="#" class="instagram external"><i
                        class="fa fa-instagram"></i></a><a href="#" class="gplus external"><i
                        class="fa fa-google-plus"></i></a><a href="#" class="email external"><i
                        class="fa fa-envelope"></i></a></p>
            </div>
            <!-- /.col-lg-3-->
        </div>
        <!-- /.row-->
    </div>
    <!-- /.container-->
</div>
<!-- /#footer-->
<!-- *** FOOTER END ***-->


<!--
*** COPYRIGHT ***
_________________________________________________________
-->
<div id="copyright">
    <div class="container">
        <div class="row">
            <div class="col-lg-6 mb-2 mb-lg-0">
                <p class="text-center text-lg-left">©2019 Bootstrapious.</p>
            </div>
            <div class="col-lg-6">
                <p class="text-center text-lg-right">More Templates <a href="http://www.cssmoban.com/" target="_blank"
                                                                       title="模板之家">模板之家</a> - Collect from <a
                        href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a></p>

                </p>
            </div>
        </div>
    </div>
</div>
<!-- *** COPYRIGHT END ***-->
<!-- JavaScript files-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="vendor/jquery.cookie/jquery.cookie.js"></script>
<script src="vendor/owl.carousel/owl.carousel.min.js"></script>
<script src="vendor/owl.carousel2.thumbs/owl.carousel2.thumbs.js"></script>
<script src="js/front.js"></script>
<script src="res/js/checklogin.js"></script>
<script>
    //执行查询
    function toSearch() {
        location.assign("commoditySearch.action?methodName=showCommodities&search=" + $("#searchText").val());
    }

    //跳转到全部商品页面
    function toComs() {
        location.assign("commoditySearch.action?methodName=showCommodities");
    }

    //跳转到个人信息页面
    function toInfo() {
        location.assign("userInfo.action?methodName=getUserInfo");
    }

    //获取焦点
    setTimeout("getFocus();", 2000);

    function getFocus() {
        document.getElementById('searchText').focus();
    }

    //查看订单
    function toOrders() {
        location.assign("order.action?methodName=showOrders");
    }

    document.onkeyup = function (e) {//按键信息对象以函数参数的形式传递进来了，就是那个e
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
            //此处编写用户敲回车后的代码
            toSearch();
        }
    }
    //判断密码修改是否成功
    let pwdResult = "${requestScope.pwdResult}";
    if (pwdResult == "fail") {
        alert("原密码错误！");
    } else if (pwdResult == "databaseError") {
        alert("数据库错误");
    } else if (pwdResult == "success") {
        alert("密码修改成功！");
        //返回到登录页，重新登录。
        location.replace("${pageContext.request.contextPath}/register.html");
    }
    let infoResult = "${requestScope.infoResult}";
    if (infoResult == "success") {
        alert("基本信息修改成功！");
    } else if (infoResult == "fail") {
        alert("基本信息修改失败！");
    }
    //判断用户信息是否修改成功
    //修改密码
    function resetPwd() {
        let password0 = window.document.getElementById("password_0").value;
        let password1 = window.document.getElementById("password_1").value;
        let password2 = window.document.getElementById("password_2").value;
        if (password1 != password2) {
            alert("两次输入的密码不相等");
        } else {
            location.assign("userInfo.action?methodName=setPassword&pwd0=" + password0 + "&pwd1=" + password1);
        }
    }

    //重置个人信息
    function resetInfo() {
        window.document.getElementById("userImg").src = "${requestScope.userInfo.data.UImage}";
        window.document.getElementById("nickname").value = "${requestScope.userInfo.data.UNickname}";
        window.document.getElementById("mobile").value = "${requestScope.userInfo.data.UPhone}";
        window.document.getElementById("email").value = "${requestScope.userInfo.data.UMail}";
    }

    //提交信息修改
    function submitInfo() {
        let nickname = window.document.getElementById("nickname").value;
        let mobile = window.document.getElementById("mobile").value;
        let email = window.document.getElementById("email").value;
        location.assign("userInfo.action?methodName=setInfo&nickname=" + nickname + "&mobile=" + mobile + "&email=" + email);
    }
</script>
<script>
    $('#logout').on('click', function () {
        localStorage.removeItem("person");
        localStorage.removeItem("nickname");
    })
</script>
</body>
</html>
