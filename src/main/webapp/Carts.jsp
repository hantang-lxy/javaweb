<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="cn.kgc.tangcco.kjde1021.pojo.Commodity" %>
<%@ page import="cn.kgc.tangcco.kjde1021.pojo.Carts" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/6/17
  Time: 21:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
<%
    String message = (String) request.getAttribute("message");
    if (message != null && message != "") {
%>
<script type="text/javascript">
    alert("<%=message%>");
</script>
<%
    }
%>
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
                            href="${pageContext.request.contextPath}/Carts.jsp"
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
                    <input type="text" placeholder="Search" class="form-control">
                    <div class="input-group-append">
                        <button type="button" class="btn btn-primary"><i class="fa fa-search"></i></button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</header>
<%
    List<Carts> cartsList = (List<Carts>) session.getAttribute("CartsList");
    int num = cartsList.size();
%>
<div id="all">
    <div id="content">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <!-- breadcrumb-->
                    <nav aria-label="breadcrumb">
                        <ol class="breadcrumb">
                            <li class="breadcrumb-item"><a href="#">主页</a></li>
                            <li aria-current="page" class="breadcrumb-item active">购物车</li>
                        </ol>
                    </nav>
                </div>
                <div id="basket" class="col-lg-9">
                    <div class="box">
                        <form method="post" action="carts.action?methodName=submitCarts">
                            <h1>购物车</h1>
                            <p class="text-muted">您目前有<%=num%>件宝贝在您的购物车。</p>
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                    <tr>
                                        <th colspan="2">产品</th>
                                        <th>数量&nbsp</th>
                                        <th>单价</th>

                                        <th colspan="2">总金额</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <%
                                        double sum = 0;
                                        if (cartsList != null) {
                                            for (Carts goods : cartsList) {
                                                sum += (goods.getCommodity().getCPrice().doubleValue()) * goods.getSNum();
                                    %>

                                    <tr>
                                        <%--                                        ${pageContext.request.contextPath}/detail.action?methodName=showDetail&cId=<%=goods.getCommodity().getCId()%>--%>
                                        <td><a href="#"><img
                                                src="<%=goods.getCommodity().getCUrl()%>"
                                                alt="White Blouse Armani"></a></td>
                                        <td><a href="#"><%=goods.getCommodity().getCName()%>
                                        </a></td>
                                        <td><%=goods.getSNum()%>
                                        </td>
                                        <td>￥<%=goods.getCommodity().getCPrice()%>
                                        </td>

                                        <td>￥<%=goods.getCommodity().getCPrice().doubleValue() * goods.getSNum()%>
                                        </td>
                                        <td>
                                            <a href="<%=request.getContextPath()%>/carts.action?methodName=deleteCarts&uuid=lxy&sid=<%=goods.getSId()%>"><i
                                                    class="fa fa-trash-o"></i></a></td>
                                    </tr>
                                    <%
                                            }
                                        }
                                    %>
                                    </tbody>
                                    <tfoot>
                                    <tr>
                                        <th colspan="5">总价</th>
                                        <th colspan="2">￥<%=sum%>
                                        </th>
                                    </tr>
                                    </tfoot>
                                </table>
                            </div>
                            <!-- /.table-responsive-->
                            <div class="box-footer d-flex justify-content-between flex-column flex-lg-row">
                                <div class="left"><a
                                        href="${pageContext.request.contextPath}/category.action?methodName=showGoods&sortName=主板&scid=10086&count=11&pageNum=1"
                                        class="btn btn-outline-secondary"><i class="fa fa-chevron-left"></i> 返回继续购物</a>
                                </div>
                                <div class="right">
                                    <button class="btn btn-outline-secondary"><i class="fa fa-refresh"></i> 刷新</button>
                                    <button type="submit" class="btn btn-primary">结算 <i class="fa fa-chevron-right"></i>
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <!-- /.box-->
                    <div class="row same-height-row">
                        <div class="col-lg-3 col-md-6">
                            <div class="box same-height">
                                <h3>您可能也喜欢这些产品</h3>
                            </div>
                        </div>

                        <c:forEach items="${sessionScope.likeGoods}" var="likeGood" varStatus="status">
                            <div class="col-md-3 col-sm-6">
                                <div class="product same-height">
                                    <div class="flip-container">
                                        <div class="flipper">
                                            <div class="front"><a
                                                    href="${pageContext.request.contextPath}/detail.action?methodName=showDetail&cId=${likeGood.CId}"><img
                                                    src="${likeGood.CUrl}" alt=""
                                                    class="img-fluid"></a></div>
                                            <div class="back"><a
                                                    href="${pageContext.request.contextPath}/detail.action?methodName=showDetail&cId=${likeGood.CId}"><img
                                                    src="${likeGood.CUrl}" alt=""
                                                    class="img-fluid"></a></div>
                                        </div>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/detail.action?methodName=showDetail&cId=${likeGood.CId}"
                                       class="invisible"><img src="${likeGood.CUrl}" alt=""
                                                              class="img-fluid"></a>
                                    <div class="text">
                                        <h3>${likeGood.CName}</h3>
                                        <p class="price">$${likeGood.CPrice}</p>
                                    </div>
                                </div>
                                <!-- /.product-->
                            </div>
                        </c:forEach>

                    </div>
                </div>
                <!-- /.col-lg-9-->

                <div class="col-lg-3">
                    <div id="order-summary" class="box">
                        <div class="box-header">
                            <h3 class="mb-0">确认您的配件</h3>
                        </div>
                        <p class="text-muted">以下配件是计算机最重要的部分</p>
                        <div class="table-responsive">
                            <table class="table">
                                <tbody>

                                <%if (cartsList != null) {%>
                                <tr>
                                    <td><a href="#">cpu</a></td>
                                    <th><%
                                        for (Carts goods1 : cartsList) {
                                            if (38479 == goods1.getCommodity().getCSort()) {
                                    %><i class="fa fa-check" aria-hidden="true"></i> <% break;
                                    }
                                    }%></th>
                                </tr>

                                <tr>
                                    <td><a href="#">硬盘</a></td>
                                    <th><%
                                        for (Carts goods1 : cartsList) {
                                            if (2233 == goods1.getCommodity().getCSort()) {
                                    %><i class="fa fa-check" aria-hidden="true"></i> <% break;
                                    }
                                    }%></th>
                                </tr>
                                <tr>
                                    <td><a href="#">内存</a></td>
                                    <th><%
                                        for (Carts goods1 : cartsList) {
                                            if (110217 == goods1.getCommodity().getCSort()) {
                                    %><i class="fa fa-check" aria-hidden="true"></i> <% break;
                                    }
                                    }%></th>
                                </tr>
                                <tr>
                                    <td><a href="#">主板</a></td>
                                    <th><%
                                        for (Carts goods1 : cartsList) {
                                            if (10086 == goods1.getCommodity().getCSort()) {
                                    %><i class="fa fa-check" aria-hidden="true"></i> <% break;
                                    }
                                    }%></th>
                                </tr>
                                <tr>
                                    <td><a href="#">显卡</a></td>
                                    <th><%
                                        for (Carts goods1 : cartsList) {
                                            if (35062 == goods1.getCommodity().getCSort()) {
                                    %><i class="fa fa-check" aria-hidden="true"></i> <% break;
                                    }
                                    }%></th>
                                </tr>
                                <tr>
                                    <td><a href="#">机箱</a></td>
                                    <th><%
                                        for (Carts goods1 : cartsList) {
                                            if (38389 == goods1.getCommodity().getCSort()) {
                                    %><i class="fa fa-check" aria-hidden="true"></i> <% break;
                                    }
                                    }%></th>
                                </tr>

                                <%} %>

                                <tr class="total">
                                    <td>总价</td>
                                    <th>￥<%=sum%>
                                    </th>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <div class="box">
                        <div class="box-header">
                            <h4 class="mb-0">优惠券</h4>
                        </div>
                        <p class="text-muted">如果您有优惠码，请在下面框中输入。</p>
                        <form>
                            <div class="input-group">
                                <input type="text" class="form-control"><span class="input-group-append">
                      <button type="button" class="btn btn-primary"><i class="fa fa-gift"></i></button></span>
                            </div>
                            <!-- /input-group-->
                        </form>
                    </div>
                </div>
                <!-- /.col-md-3-->
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
                    <li><a href="category.html">显卡发烧间</a></li>
                    <li><a href="category.html">动力节点间</a></li>
                    <li><a href="category.html">奥里给</a></li>
                </ul>
                <h5>挑战者</h5>
                <ul class="list-unstyled">
                    <li><a href="category.html">生活防水5米</a></li>
                    <li><a href="category.html">抗蓝光</a></li>
                    <li><a href="category.html">抗击打</a></li>
                    <li><a href="category.html">抗震动</a></li>
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

            </div>
            <div class="col-lg-6">


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
    function checkstock(cid) {
        window.console.log("cid>>>" + cid);
        $.ajax({
            type: "post",
            url: "http://localhost:8080/javaweb01/carts.action?methodName=checkstock",
            data: {"cid": cid},
            dataType: "text",
            success: function (response) {
                if (trim(response) == "failed") {
                    layer.msg('该商品已下架！', {
                        icon: 2,
                        time: 2000,
                    })
                    return;
                }
                location.replace("${pageContext.request.contextPath}/detail.action?methodName=showDetail&cId=" + trim(response));
            },
            error: function () {
                layer.msg('哎呦,服务器走丢了', {
                    icon: 2,
                    time: 1000,
                })
            }
        });
    }
</script>
</body>
</html>