<%--
  Created by IntelliJ IDEA.
  User: kgc
  Date: 2020/6/13
  Time: 下午6:48
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome CSS-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/font-awesome/css/font-awesome.min.css">
    <!-- Google fonts - Roboto -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,700">
    <!-- owl carousel-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/owl.carousel/assets/owl.carousel.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/vendor/owl.carousel/assets/owl.theme.default.css">
    <!-- theme stylesheet-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.default.css" id="theme-stylesheet">
    <!-- Custom stylesheet - for your changes-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css">
    <!-- Favicon-->
    <link rel="shortcut icon" href="favicon.png">
    <!-- Tweaks for older IEs-->
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
    <!--<script src="${pageContext.request.contextPath}/js/layui.all.js"></script>-->


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
                    <li class="nav-item"><a href="index.jsp" class="nav-link">主页</a></li>
                    <li class="nav-item dropdown menu-large">
                        <a href="#" onclick="toComs()" data-toggle="dropdown" data-hover="dropdown" data-delay="200"
                           class="nav-link active">全部商品</a>
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
                    <input type="text" placeholder="Search" id="searchText" value="${requestScope.search}" class="form-control">
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
                <div class="col-lg-12">
                    <!-- breadcrumb-->
                    <div class="box info-bar">
                        <div class="row">
                            <div class="col-md-16 col-lg-12 products-number-sort">
                                <form class="form-inline d-block d-lg-flex justify-content-between flex-column flex-md-row">
                                    <div class="products-number">
                                        <strong>分类选择</strong>
                                        <c:if test="${requestScope.sortMap.active == 99999}">
                                            <a href="commoditySearch.action?methodName=showCommodities&sort=99999"
                                               class="btn btn-sm btn-primary">所有商品</a>
                                        </c:if>
                                        <c:if test="${requestScope.sortMap.active != 99999}">
                                            <a href="commoditySearch.action?methodName=showCommodities&sort=99999"
                                               class="btn btn-outline-secondary btn-sm">所有商品</a>
                                        </c:if>
                                        <c:forEach items="${requestScope.sortMap.data}" var="SortVo" varStatus="status">
                                            <c:if test="${requestScope.sortMap.active == SortVo.sortCid}">
                                                <a href="commoditySearch.action?methodName=showCommodities&sort=${SortVo.sortCid}"
                                                   class="btn btn-sm btn-primary">${SortVo.sortName}</a>
                                            </c:if>
                                            <c:if test="${requestScope.sortMap.active != SortVo.sortCid}">
                                                <a href="commoditySearch.action?methodName=showCommodities&sort=${SortVo.sortCid}"
                                                   class="btn btn-outline-secondary btn-sm">${SortVo.sortName}</a>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="row products">
                        <c:forEach items="${requestScope.commodityMap.data}" var="CommoditySearchVo" varStatus="status">
                            <div class="col-lg-3 col-md-4">
                                <div class="product">
                                    <div class="flip-container">
                                        <div class="flipper">
                                            <div class="front">
                                                <a href="detail.action?methodName=showDetail&cId=${CommoditySearchVo.CId}"><img
                                                        src="${CommoditySearchVo.CUrl}" alt="" class="img-fluid"></a>
                                            </div>
                                            <div class="back">
                                                <a href="detail.action?methodName=showDetail&cId=${CommoditySearchVo.CId}"><img
                                                        src="${CommoditySearchVo.CUrl}" alt="" class="img-fluid"></a>
                                            </div>
                                        </div>
                                    </div>
                                    <a href="detail.action?methodName=showDetail&cId=${CommoditySearchVo.CId}"
                                       class="invisible"><img src="img/product1.jpg" alt="" class="img-fluid"></a>
                                    <div class="text">
                                        <input type="hidden" id="cId" value="${CommoditySearchVo.CId}">
                                        <h3>
                                            <a href="detail.action?methodName=showDetail&cId=${CommoditySearchVo.CId}">${CommoditySearchVo.CName}</a>
                                        </h3>
                                        <p class="price">
                                            <del></del>
                                                ${CommoditySearchVo.CPrice}元
                                        </p>
                                        <p class="buttons">

                                            <a href="${pageContext.request.contextPath}/detail.action?methodName=showDetail&cId=${CommoditySearchVo.CId}"
                                               class="btn btn-outline-secondary">查看详情</a>
                                            <!--    加入购物车        -->
                                            <!--<a href="commoditySearch.action?methodName=addToCart&cId=${CommoditySearchVo.CId}&sort=${requestScope.sortMap.active}" class="btn btn-primary" onclick="$.addToCart(${CommoditySearchVo.CId})">加入购物车</a>-->
                                            <a href="#" onclick="toAdd(${CommoditySearchVo.CId})"
                                               class="btn btn-primary">加入购物车</a>
                                        </p>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="pages">
                        <nav aria-label="Page navigation example" class="d-flex justify-content-center">
                            <ul class="pagination">
                                <c:if test="${requestScope.commodityMap.active == 1}">
                                    <li class="page-item disabled"><a class="page-link"
                                                                      href="${pageContext.request.contextPath}/commoditySearch.action?methodName=showCommodities&sort=${requestScope.sortMap.active}&page=1">首页</a>
                                    </li>
                                    <li class="page-item disabled"><a class="page-link"
                                                                      href="${pageContext.request.contextPath}/commoditySearch.action?methodName=showCommodities&sort=${requestScope.sortMap.active}&page=${requestScope.commodityMap.active-1}">上一页</a>
                                    </li>
                                </c:if>
                                <c:if test="${requestScope.commodityMap.active != 1}">
                                    <li class="page-item"><a class="page-link"
                                                             href="${pageContext.request.contextPath}/commoditySearch.action?methodName=showCommodities&sort=${requestScope.sortMap.active}&page=1">首页</a>
                                    </li>
                                    <li class="page-item"><a class="page-link"
                                                             href="${pageContext.request.contextPath}/commoditySearch.action?methodName=showCommodities&sort=${requestScope.sortMap.active}&page=${requestScope.commodityMap.active-1}">上一页</a>
                                    </li>
                                </c:if>
                                <c:forEach items="${requestScope.commodityMap.rainbow}" var="pageNum">
                                    <c:if test="${requestScope.commodityMap.active == pageNum}">
                                        <li class="page-item active">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/commoditySearch.action?methodName=showCommodities&sort=${requestScope.sortMap.active}&page=${pageNum}">${pageNum}</a>
                                        </li>
                                        <c:set var="activePage" scope="session" value="${pageNum}"/>
                                    </c:if>
                                    <c:if test="${requestScope.commodityMap.active != pageNum}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="${pageContext.request.contextPath}/commoditySearch.action?methodName=showCommodities&sort=${requestScope.sortMap.active}&page=${pageNum}">${pageNum}</a>
                                        </li>
                                    </c:if>
                                </c:forEach>

                                <c:if test="${requestScope.commodityMap.active == requestScope.commodityMap.count}">
                                    <li class="page-item disabled">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/commoditySearch.action?methodName=showCommodities&sort=${requestScope.sortMap.active}&page=${requestScope.commodityMap.active+1}">下一页</a>
                                    </li>
                                    <li class="page-item disabled">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/commoditySearch.action?methodName=showCommodities&sort=${requestScope.sortMap.active}&page=${requestScope.commodityMap.count}">末页</a>
                                    </li>
                                </c:if>
                                <c:if test="${requestScope.commodityMap.active != requestScope.commodityMap.count}">
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/commoditySearch.action?methodName=showCommodities&sort=${requestScope.sortMap.active}&page=${requestScope.commodityMap.active+1}">下一页</a>
                                    </li>
                                    <li class="page-item">
                                        <a class="page-link"
                                           href="${pageContext.request.contextPath}/commoditySearch.action?methodName=showCommodities&sort=${requestScope.sortMap.active}&page=${requestScope.commodityMap.count}">末页</a>
                                    </li>
                                </c:if>
                                <li class="page-item disabled"><a class="page-link"
                                                                  href="#">第${requestScope.commodityMap.active}页|共${requestScope.commodityMap.count}页</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
                <!-- /.col-lg-9-->
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


<!--``````````
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
<script src="vendor/jquery.cookie/jquery.cookie.js">
</script>
<script src="vendor/owl.carousel/owl.carousel.min.js"></script>
<script src="vendor/owl.carousel2.thumbs/owl.carousel2.thumbs.js"></script>
<script src="js/front.js"></script>
<script src="res/js/checklogin.js"></script>
<script>
    //判断添加到购物车是否成功
    let addses = "${requestScope.addtocartresult}";
    if (addses != "null" && addses != "" && addses != null) {
        alert(addses);
    }
    //判断是否搜索到了商品
    let result1 = "${requestScope.result}";
    if (result1 != "" && result1 != null && result1 != "null") {
        alert(result1);
    }
    ///
    function toAdd(cId){
        location.assign("carts.action?methodName=addToCartFromSearch&cId="+cId+"&sort=${requestScope.sortMap.active}&search=" + $("#searchText").val());
    }
    //执行查询
    function toSearch() {
        location.assign("commoditySearch.action?methodName=showCommodities&sort=${requestScope.sortMap.active}&search=" + $("#searchText").val());
    }

    //跳转到个人信息页面
    function toInfo() {
        location.assign("userInfo.action?methodName=getUserInfo");
    }

    //查看订单
    function toOrders() {
        location.assign("order.action?methodName=showOrders");
    }

    //获取焦点
    setTimeout("getFocus();", 2000);

    function getFocus() {
        document.getElementById('searchText').focus();
    }

    document.onkeyup = function (e) {//按键信息对象以函数参数的形式传递进来了，就是那个e
        var code = e.charCode || e.keyCode;  //取出按键信息中的按键代码(大部分浏览器通过keyCode属性获取按键代码，但少部分浏览器使用的却是charCode)
        if (code == 13) {
            //此处编写用户敲回车后的代码
            toSearch();
        }
    }
</script>
</body>

</html>