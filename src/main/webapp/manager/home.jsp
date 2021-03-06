<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<s:action name="person.action" executeResult="true" namespace="/"/>
<s:action name="orders.action" executeResult="true" namespace="/"/>
<s:property value="#attr.name"/>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link href="assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="css/style.css"/>
    <link rel="stylesheet" href="assets/css/ace.min.css"/>
    <link rel="stylesheet" href="assets/css/font-awesome.min.css"/>
    <link href="assets/css/codemirror.css" rel="stylesheet">
    <!--[if IE 7]>
    <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css"/>
    <![endif]-->
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="assets/css/ace-ie.min.css"/>
    <![endif]-->
    <script src="assets/js/ace-extra.min.js"></script>
    <!--[if lt IE 9]>
    <script src="assets/js/html5shiv.js"></script>
    <script src="assets/js/respond.min.js"></script>
    <![endif]-->
    <!--[if !IE]> -->
    <script src="assets/js/jquery.min.js"></script>
    <!-- <![endif]-->
    <script src="assets/dist/echarts.js"></script>
    <script src="assets/js/bootstrap.min.js"></script>
    <script>

    </script>

    <script>
        function currentTime() {
            let d = new Date(), str = '';
            str += d.getFullYear() + '年';
            str += d.getMonth() + 1 + '月';
            str += d.getDate() + '日';
            str += d.getHours() + '时';
            str += d.getMinutes() + '分';
            str += d.getSeconds() + '秒';
            return str;
        }

        setInterval(function () {
            $('#time').html(currentTime)
        }, 1000);
    </script>
    <title></title>
</head>
<body>
<div class="page-content clearfix">
    <div class="alert alert-block alert-success">
        <button type="button" class="close" data-dismiss="alert"><i class="icon-remove"></i></button>
        <i class="icon-ok green"></i>欢迎使用<strong class="green">后台管理系统</strong>,你本次登录时间为<span class="time"><em
            id="time"></em></span>
    </div>
    <div class="state-overview clearfix">
        <div class="col-lg-3 col-sm-6">
            <section class="panel">
                <div class="symbol terques">
                    <i class="icon-user"></i>
                </div>
                <div class="value">
                    <h1>
                        ${requestScope.queryHomeMap.userCount}名
                    </h1>
                    <p>商城用户</p>
                </div>
            </section>
        </div>
        <div class="col-lg-3 col-sm-6">
            <section class="panel">
                <div class="symbol yellow">
                    <i class="icon-shopping-cart"></i>
                </div>
                <div class="value">
                    <h1>
                        ${requestScope.queryHomeMap.untreated[0] + requestScope.queryHomeMap.untreated[2] + requestScope.queryHomeMap.untreated[3]}条
                    </h1>
                    <p>商城未完成订单</p>
                </div>
            </section>
        </div>
        <div class="col-lg-3 col-sm-6">
            <section class="panel">
                <div class="symbol yellow">
                    <i class="icon-shopping-cart"></i>
                </div>
                <div class="value">
                    <h1>
                        ${requestScope.queryHomeMap.untreated[0] + requestScope.queryHomeMap.untreated[1] + requestScope.queryHomeMap.untreated[2] + requestScope.queryHomeMap.untreated[3]}条</h1>
                    <p>商城订单</p>
                </div>
            </section>
        </div>
        <div class="col-lg-3 col-sm-6">
            <section class="panel">
                <div class="symbol blue">
                    <i class="icon-bar-chart"></i>
                </div>
                <div class="value">
                    <h1>${requestScope.queryHomeMap.deal}元</h1>
                    <p>交易记录</p>
                </div>
            </section>
        </div>
    </div>
    <!--实时交易记录-->
    <div class="clearfix">
        <div class="Order_Statistics ">
            <div class="title_name">订单统计信息</div>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <td class="name">未处理订单：</td>
                    <td class="munber"><a href="#">${requestScope.queryHomeMap.untreated[0]}</a>&nbsp;个</td>
                </tr>
                <tr>
                    <td class="name">待发货订单：</td>
                    <td class="munber"><a href="#">${requestScope.queryHomeMap.untreated[2]}</a>&nbsp;个</td>
                </tr>
                <tr>
                    <td class="name">待结算订单：</td>
                    <td class="munber"><a href="#">${requestScope.queryHomeMap.untreated[3]}</a>&nbsp;个</td>
                </tr>
                <tr>
                    <td class="name">已成交订单：</td>
                    <td class="munber"><a href="#">${requestScope.queryHomeMap.untreated[1]}</a>&nbsp;个</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="Order_Statistics">
            <div class="title_name">商品统计信息</div>
            <table class="table table-bordered">
                <tbody>
                <tr>
                    <td class="name">商品总数：</td>
                    <td class="munber"><a href="#">${requestScope.queryHomeMap.isShelves[0] + requestScope.queryHomeMap.isShelves[1]}</a>&nbsp;个</td>
                </tr>
                <tr>
                    <td class="name">上架商品：</td>
                    <td class="munber"><a href="#">${requestScope.queryHomeMap.isShelves[0]}</a>&nbsp;个</td>
                </tr>
                <tr>
                    <td class="name">下架商品：</td>
                    <td class="munber"><a href="#">${requestScope.queryHomeMap.isShelves[1]}</a>&nbsp;个</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <!--记录-->
    <div class="clearfix">

    </div>

</div>
</body>
</html>
<script type="text/javascript">
    //面包屑返回值
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.iframeAuto(index);
    $('.no-radius').on('click', function () {
        var cname = $(this).attr("title");
        var chref = $(this).attr("href");
        var cnames = parent.$('.Current_page').html();
        var herf = parent.$("#iframe").attr("src");
        parent.$('#parentIframe').html(cname);
        parent.$('#iframe').attr("src", chref).ready();
        ;
        parent.$('#parentIframe').css("display", "inline-block");
        parent.$('.Current_page').attr({"name": herf, "href": "javascript:void(0)"}).css({
            "color": "#4c8fbd",
            "cursor": "pointer"
        });
        parent.layer.close(index);

    });
    $(document).ready(function () {

        $(".t_Record").width($(window).width() - 640);
        //当文档窗口发生改变时 触发
        $(window).resize(function () {
            $(".t_Record").width($(window).width() - 640);
        });
    });
</script>