<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
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
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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
                <div class="col-md-12">
                    <div id="main-slider" class="owl-carousel owl-theme">
                        <div class="item"><img style="width: 1200px; height: 515px"
                                               src="https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/peripherals/output_devices/dell/monitors/u2520dr/pdp/u2520dr_monitor_responsive_module_1.jpg?fmt=jpg"
                                               alt="" class="img-fluid"></div>
                        <div class="item"><img style="width: 1200px;height: 515px"
                                               src="https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/peripherals/output_devices/dell/monitors/aw2521hf/pdp/65102-alienware-aw2521hf-monitor-pdp-mod5.jpg?fmt=jpg"
                                               alt="" class="img-fluid"></div>
                        <div class="item"><img style="width: 1200px;height: 515px"
                                               src="https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/peripherals/output_devices/dell/monitors/aw2720hf/pdp/responsive/monitors-alienware-aw2720hf-pdp-campaign.jpg?fmt=jpg"
                                               alt="" class="img-fluid"></div>
                        <div class="item"><img style="width: 1200px;height: 515px"
                                               src="https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/peripherals/output_devices/dell/monitors/u2720qm/pdp/u2720qm_monitor_responsive_module_1.jpg?fmt=jpg"
                                               alt="" class="img-fluid"></div>
                    </div>
                    <!-- /#main-slider-->
                </div>
            </div>
        </div>
        <!--
        *** ADVANTAGES HOMEPAGE ***
        _________________________________________________________
        -->
        <div id="advantages">
            <div class="container">
                <div class="row mb-4">
                    <div class="col-md-4">
                        <div class="box clickable d-flex flex-column justify-content-center mb-0 h-100">
                            <div class="icon"><i class="fa fa-heart"></i></div>
                            <h3><a href="#">我们爱我们的客户</a></h3>
                            <p class="mb-0">我们以提供最好的服务而闻名</p>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="box clickable d-flex flex-column justify-content-center mb-0 h-100">
                            <div class="icon"><i class="fa fa-tags"></i></div>
                            <h3><a href="#">最佳价格</a></h3>
                            <p class="mb-0">我们以优惠的价格来吸引客户</p>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="box clickable d-flex flex-column justify-content-center mb-0 h-100">
                            <div class="icon"><i class="fa fa-thumbs-up"></i></div>
                            <h3><a href="#">100%的满意保证</a></h3>
                            <p class="mb-0">三年质保，三个月无理由退换货</p>
                        </div>
                    </div>
                </div>
                <!-- /.row-->
            </div>
            <!-- /.container-->
        </div>
        <!-- /#advantages-->
        <div class="tlinks">Collect from <a href="http://www.cssmoban.com/" title="网站模板">网站模板</a></div>
        <!-- *** ADVANTAGES END ***-->
        <!--
        *** HOT PRODUCT SLIDESHOW ***
        _________________________________________________________
        -->
        <div id="hot">
            <div class="box py-4">
                <div class="container">
                    <div class="row">
                        <div class="col-md-12">
                            <h2 class="mb-0">Hot this week</h2>
                        </div>
                    </div>
                </div>
            </div>
            <div class="container">
                <div class="product-slider owl-carousel owl-theme">
                    <c:set var="detaileurl" scope="request"
                           value="${pageContext.request.contextPath}/detail.action?methodName=showDetail&cId="/>
                    <c:forEach items="${sessionScope.hotGoods}" var="hotGood" varStatus="status">

                        <div class="item">
                            <div class="product">
                                <div class="flip-container">
                                    <div class="flipper">
                                        <div class="front"><a href="${detaileurl}${hotGood.CId}"><img
                                                src="${hotGood.CUrl}"
                                                alt=""
                                                class="img-fluid"></a>
                                        </div>
                                        <div class="back"><a href="${detaileurl}${hotGood.CId}"><img
                                                src="${hotGood.CUrl}"
                                                alt=""
                                                class="img-fluid"></a>
                                        </div>
                                    </div>
                                </div>
                                <a href="${detaileurl}${hotGood.CId}" class="invisible"><img src="${hotGood.CUrl}"
                                                                                             alt=""
                                                                                             class="img-fluid"></a>
                                <div class="text">
                                    <h3><a href="${detaileurl}${hotGood.CId}">${hotGood.CName}</a></h3>
                                    <p class="price">
                                        <del></del>
                                        $${hotGood.CPrice}
                                    </p>
                                </div>
                                <!-- /.text-->
                                <div class="ribbon sale">
                                    <div class="theribbon">SALE</div>
                                    <div class="ribbon-background"></div>
                                </div>
                                <!-- /.ribbon-->
                                <div class="ribbon new">
                                    <div class="theribbon">NEW</div>
                                    <div class="ribbon-background"></div>
                                </div>
                                <!-- /.ribbon-->
                                <div class="ribbon gift">
                                    <div class="theribbon">GIFT</div>
                                    <div class="ribbon-background"></div>
                                </div>
                                <!-- /.ribbon-->
                            </div>
                            <!-- /.product-->
                        </div>


                    </c:forEach>

                    <!-- /.product-slider-->
                </div>
                <!-- /.container-->
            </div>
            <!-- /#hot-->
            <!-- *** HOT END ***-->
        </div>
        <!--
        *** GET INSPIRED ***
        _________________________________________________________
        -->
        <div class="container">
            <div class="col-md-12">
                <div class="box slideshow">
                    <h3>你是不是要找</h3>
                    <p class="lead">臻彩视界</p>
                    <div id="get-inspired" class="owl-carousel owl-theme">
                        <div class="item"><a href="${detaileurl}21"><img style="height: 420px"
                                                                         src="http://www.mmd-p.com.cn/Philipsmonitor/uploads/20200609155904_976.png"
                                                                         alt="Get inspired"
                                                                         class="img-fluid"></a></div>
                        <div class="item"><a href="${detaileurl}22"><img style="height: 420px"
                                                                         src="http://www.mmd-p.com.cn/Philipsmonitor/uploads/20200408153346_240.jpg"
                                                                         alt="Get inspired"
                                                                         class="img-fluid"></a></div>
                        <div class="item"><a href="${detaileurl}23"><img style="height: 420px"
                                                                         src="https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/peripherals/output_devices/dell/monitors/aw5520qf/pdp/monitors_alienware_aw5520qf_pdp_mod-02.jpg?fmt=jpg"
                                                                         alt="Get inspired"
                                                                         class="img-fluid"></a></div>
                    </div>
                </div>
            </div>
        </div>
        <!-- *** GET INSPIRED END ***-->
        <!--
        *** BLOG HOMEPAGE ***
        _________________________________________________________
        -->
        <div class="box text-center">
            <div class="container">
                <div class="col-md-12">
                    <h3 class="text-uppercase">From our blog</h3>
                    <p class="lead mb-0">发烧界有什么新鲜事吗？<a href="blog.html">奥里给!</a></p>
                </div>
            </div>
        </div>
        <div class="container">
            <div class="col-md-12">
                <div id="blog-homepage" class="row">
                    <div class="col-sm-6">
                        <div class="post">
                            <h4><a href="post.html">Fashion now</a></h4>
                            <p class="author-category">By <a href="#">John Slim</a> in <a href="">Fashion and style</a>
                            </p>
                            <hr>
                            <p class="intro">Pellentesque habitant morbi tristique senectus et netus et malesuada fames
                                ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit
                                amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est.
                                Mauris placerat eleifend leo.</p>
                            <p class="read-more"><a href="post.html" class="btn btn-primary">挑灯夜读</a></p>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="post">
                            <h4><a href="post.html">Who is who - example blog post</a></h4>
                            <p class="author-category">By <a href="#">John Slim</a> in <a href="">About Minimal</a></p>
                            <hr>
                            <p class="intro">Pellentesque habitant morbi tristique senectus et netus et malesuada fames
                                ac turpis egestas. Vestibulum tortor quam, feugiat vitae, ultricies eget, tempor sit
                                amet, ante. Donec eu libero sit amet quam egestas semper. Aenean ultricies mi vitae est.
                                Mauris placerat eleifend leo.</p>
                            <p class="read-more"><a href="post.html" class="btn btn-primary">挑灯夜读</a></p>
                        </div>
                    </div>
                </div>
                <!-- /#blog-homepage-->
            </div>
        </div>
        <!-- /.container-->
        <!-- *** BLOG HOMEPAGE END ***-->
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
</script>
</body>
</html>