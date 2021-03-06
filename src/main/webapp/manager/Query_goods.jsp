<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="v-on" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>查询商品</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="robots" content="all,follow">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <link href="${pageContext.request.contextPath}/manager/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/manager/assets/css/ace.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/manager/assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/manager/Widget/zTree/css/zTreeStyle/zTreeStyle.css"
          type="text/css">
    <link href="${pageContext.request.contextPath}/manager/Widget/icheck/icheck.css" rel="stylesheet" type="text/css"/>
    <!--[if IE 7]>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/manager/assets/css/font-awesome-ie7.min.css" />
    <![endif]-->
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/manager/assets/css/ace-ie.min.css" />
    <![endif]-->
    <script src="${pageContext.request.contextPath}/manager/js/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/manager/assets/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/manager/assets/js/typeahead-bs2.min.js"></script>
    <!-- page specific plugin scripts -->
    <script src="${pageContext.request.contextPath}/manager/assets/js/jquery.dataTables.min.js"></script>
    <script src="${pageContext.request.contextPath}/manager/assets/js/jquery.dataTables.bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/manager/js/H-ui.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/manager/js/H-ui.admin.js"></script>
    <script src="${pageContext.request.contextPath}/manager/assets/layer/layer.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/manager/assets/laydate/laydate.js" type="text/javascript"></script>
    <script type="text/javascript"
            src="${pageContext.request.contextPath}/manager/Widget/zTree/js/jquery.ztree.all-3.5.min.js"></script>
    <script src="${pageContext.request.contextPath}/manager/js/lrtk.js" type="text/javascript"></script>
    <script src="${pageContext.request.contextPath}/manager/assets/js/jquery.min.js?t=1484026799191"
            charset="utf-8"></script>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/manager/css/style.css"/>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/manager/res/bootstrap-4.4.1-dist/css/bootstrap.min.css"/>
    <script src="${pageContext.request.contextPath}/manager/res/js/jquery-3.5.1.min.js" type=""></script>
    <script src="${pageContext.request.contextPath}/manager/res/js/popper.min.js" type=""></script>
    <script src="${pageContext.request.contextPath}/manager/res/bootstrap-4.4.1-dist/js/bootstrap.min.js"
            type=""></script>

    <style type="text/css">
        #wrap {
            width: 590px;
            margin: 0 auto;
            border: 1px;
        }
    </style>
</head>

<body>
<a id="map"></a>
<div id="wrap">
    <div class="search_style divCenter">
        <ul class="search_content clearfix">
            <li>
            </li>
            <li>
                <label class="l_f">查询商品：</label>
                <label class="l_f">
                    <input id="queryCommodity" name="shopId" type="text" class="text_add" placeholder="输入商品信息"
                           style=" width:195px"/>
                </label>
            </li>
            <li style="width:90px;" for="select ">
                <button type="button" class="btn_search" onclick="mr_verify()" name="queryButton"><i
                        class="icon-search"></i>查询
                </button>
            </li>
            <%--            <li style="width:100px;" for="select ">--%>
            <%--                <button type="button" class="btn btn-danger" onclick="mr_verify()" name="deleteButton">--%>
            <%--                    <i class="icon-trash">批量删除</i></button>--%>
            <%--            </li>--%>
        </ul>
    </div>
</div>
<div>
    <from name="fm">
        <table class="table table-bordered table-hover">
            <thead>
            <tr style='text-align: center;'>
                <th width="25px"><label><input type="checkbox" class="ace"><span class="lbl"></span></label></th>
                <th width="90px">商品编号</th>
                <th width="250px">商品名称</th>
                <th width="95px">商品类型</th>
                <th width="100px">商品单价</th>
                <th width="100px">商品销量</th>
                <th width="100px">商品库存</th>
                <th width="300px">商品详细信息</th>
                <%--            <th width="80px">状态</th>--%>
                <th width="200px">操作</th>
            </tr>
            </thead>
            <tbody name="tb">
            <c:if test="${requestScope.commodityMap ne null}">
                <c:forEach items="${requestScope.commodityMap.data}" var="commodityVo" varStatus="status">
                    <tr style='text-align: center;' name="tr">
                        <td width="25px"><label><input type="checkbox" class="ace"><span class="lbl"></span></label>
                        </td>
                        <td width="90px" name="commodityId">${commodityVo.CId}</td>
                        <td width="250px">${commodityVo.CName}</td>
                        <td width="95px">
                            <c:if test="${commodityVo.CSort == 10086}">主板</c:if>
                            <c:if test="${commodityVo.CSort == 10000}">光驱</c:if>
                            <c:if test="${commodityVo.CSort == 329897}">网卡</c:if>
                            <c:if test="${commodityVo.CSort == 35062}">显卡</c:if>
                            <c:if test="${commodityVo.CSort == 2306}">显示器</c:if>
                            <c:if test="${commodityVo.CSort == 3685}">鼠标</c:if>
                            <c:if test="${commodityVo.CSort == 38389}">键盘</c:if>
                            <c:if test="${commodityVo.CSort == 2731}">风扇/散热片</c:if>
                            <c:if test="${commodityVo.CSort == 2233}">硬盘</c:if>
                            <c:if test="${commodityVo.CSort == 110217}">内存</c:if>
                            <c:if test="${commodityVo.CSort == 38479}">CPU</c:if>
                        </td>
                        <td width="100px">${commodityVo.CPrice}￥</td>
                        <td width="100px">${commodityVo.CSales}件</td>
                        <td width="100px">${commodityVo.CStock}件</td>
                        <td width="300px">
                            <c:if test="${commodityVo.CDetail1 == null}">
                                没有详细信息
                            </c:if>
                            <c:if test="${commodityVo.CDetail1 ne null}">
                                ${commodityVo.CDetail1}
                            </c:if>
                        </td>
                        <td width="250px">

                            <button type="button" class="btn btn-xs btn-info" name="updateButton"
                                    onclick="updateCommodity(this)">
                                <i class="icon-edit bigger-120">修改</i></button>
                            <button type="button" class="btn btn-xs btn-warning" onclick="deleteCommodity(this)"
                                    name="">
                                <i class="icon-trash  bigger-120">下架</i></button>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>

            <c:if test="${requestScope.queryCommodity ne null}">

                <c:forEach items="${requestScope.queryCommodity.data}" var="queryCommodity" varStatus="status">
                    <tr style='text-align: center;'>
                        <td width="25px"><label><input type="checkbox" class="ace"><span class="lbl"></span></label>
                        </td>
                        <td width="90px">${queryCommodity.CId}</td>
                        <td width="250px">${queryCommodity.CName}</td>
                        <td width="95px">
                            <c:if test="${queryCommodity.CSort == 10086}">主板</c:if>
                            <c:if test="${queryCommodity.CSort == 10000}">光驱</c:if>
                            <c:if test="${queryCommodity.CSort == 329897}">网卡</c:if>
                            <c:if test="${queryCommodity.CSort == 35062}">显卡</c:if>
                            <c:if test="${queryCommodity.CSort == 2306}">显示器</c:if>
                            <c:if test="${queryCommodity.CSort == 3685}">鼠标</c:if>
                            <c:if test="${queryCommodity.CSort == 38389}">键盘</c:if>
                            <c:if test="${queryCommodity.CSort == 2731}">风扇/散热片</c:if>
                            <c:if test="${queryCommodity.CSort == 2233}">硬盘</c:if>
                            <c:if test="${queryCommodity.CSort == 110217}">内存</c:if>
                            <c:if test="${queryCommodity.CSort == 38479}">CPU</c:if></td>
                        <td width="100px">${queryCommodity.CPrice}</td>
                        <td width="100px">${queryCommodity.CSales}</td>
                        <td width="100px">${queryCommodity.CStock}</td>
                        <td width="300px">
                            <c:if test="${queryCommodity.CDetail1 == null}">
                                没有详细信息
                            </c:if>
                            <c:if test="${queryCommodity.CDetail1 ne null}">
                                ${queryCommodity.CDetail1}
                            </c:if>
                        </td>
                        <td width="250px">

                            <button type="button" class="btn btn-xs btn-info" name="updateButton"
                                    onclick="updateCommodity(this)">
                                <i class="icon-edit bigger-120">修改</i></button>
                            <button type="button" class="btn btn-xs btn-warning" onclick="deleteCommodity(this)"
                                    name="">
                                <i class="icon-trash  bigger-120">下架</i></button>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </from>
    <nav>
        <ul class="pagination justify-content-center">
            <c:if test="${requestScope.commodityMap ne null}">
                <c:if test="${requestScope.commodityMap.active -1 >0 }">
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryAll&page=1">首页</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryAll&page=${requestScope.commodityMap.active -1}">上一页</a>
                    </li>
                </c:if>
                <c:forEach items="${requestScope.commodityMap.rainbow}" var="pageNum">
                    <c:if test="${requestScope.commodityMap.active == pageNum}">
                        <li class="page-item active">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryAll&page=${pageNum}">${pageNum}</a>
                        </li>
                        <c:set var="activePage" scope="session" value="${pageNum}"/>
                    </c:if>
                    <c:if test="${requestScope.commodityMap.active != pageNum}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryAll&page=${pageNum}">${pageNum}</a>
                        </li>
                    </c:if>
                </c:forEach>
                <c:if test="${requestScope.commodityMap.active +1 <= requestScope.commodityMap.count}">
                    <li class="page-item"><a class="page-link"
                                             href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryAll&page=${requestScope.commodityMap.active +1}">下一页</a>
                    </li>
                    <li class="page-item"><a class="page-link"
                                             href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryAll&page=${requestScope.commodityMap.count}">末页</a>
                    </li>
                </c:if>
                <li class="page-item disabled"><a class="page-link" href="#">第<c:out
                        value="${requestScope.commodityMap.active}"/>页|共${requestScope.commodityMap.count}页</a></li>
            </c:if>

            <c:if test="${requestScope.queryCommodity ne null}">
                <c:if test="${requestScope.queryCommodity.active -1 >0 }">
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryCommodity&queryC=${requestScope.queryCommodity.commodityCId}&page=1">首页</a>
                    </li>
                    <li class="page-item">
                        <a class="page-link"
                           href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryCommodity&queryC=${requestScope.queryCommodity.commodityCId}&page=${requestScope.queryCommodity.active -1}">上一页</a>
                    </li>
                </c:if>
                <c:forEach items="${requestScope.queryCommodity.rainbow}" var="pageNum">
                    <c:if test="${requestScope.queryCommodity.active == pageNum}">
                        <li class="page-item active">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryCommodity&queryC=${requestScope.queryCommodity.commodityCId}&page=${pageNum}">${pageNum}</a>
                        </li>
                        <c:set var="activePage" scope="session" value="${pageNum}"/>
                    </c:if>
                    <c:if test="${requestScope.queryCommodity.active != pageNum}">
                        <li class="page-item">
                            <a class="page-link"
                               href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryCommodity&queryC=${requestScope.queryCommodity.commodityCId}&page=${pageNum}">${pageNum}</a>
                        </li>
                    </c:if>
                </c:forEach>
                <c:if test="${requestScope.queryCommodity.active +1 <= requestScope.queryCommodity.count}">
                    <li class="page-item"><a class="page-link"
                                             href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryCommodity&queryC=${requestScope.queryCommodity.commodityCId}&page=${requestScope.queryCommodity.active +1}">下一页</a>
                    </li>
                    <li class="page-item"><a class="page-link"
                                             href="${pageContext.request.contextPath}/manager/commodity.action?methodName=queryCommodity&queryC=${requestScope.queryCommodity.commodityCId}&page=${requestScope.queryCommodity.count}">末页</a>
                    </li>
                </c:if>
                <li class="page-item disabled"><a class="page-link" href="#">第<c:out
                        value="${requestScope.queryCommodity.active}"/>页|共${requestScope.queryCommodity.count}页</a></li>
            </c:if>

        </ul>
    </nav>
</div>
<a href="#map" target="_self"><img src="images/top.jpg" class="right_top" alt="点击到顶部"/></a>

<%--<input type="button" onclick="black()" value="产品查询">--%>
</body>
</html>
<script type="text/javascript">
    //获取表单对象
    let queryCommodity = document.getElementById("queryCommodity");
    let count = 0;

    function mr_verify() {
        //验证文本框是否为空
        if (queryCommodity.value === '' || queryCommodity.value === null) {
            toQueryAll()
        } else {
            toQueryCommodity(queryCommodity.value)
        }
    }

    function toQueryAll() {
        location.assign("commodity.action?methodName=queryAll");
    }

    function toQueryCommodity(queryCommodity) {
        location.assign("commodity.action?methodName=queryCommodity&queryC=" + queryCommodity);
    }

    //下架商品
    function deleteCommodity(this1) {
        //获取当前行的cId
        let x = $(this1).parent().parent().find("td");
        let y = x.eq(1).text()
        if (confirm("确认要下架？")) {
            location.assign("commodity.action?methodName=soldOut&soldOutId=" + y)
            if (!${requestScope.updateMap.isOkCount > 0}) {
                alert("下架成功！")
            }
        } else {
            window.event.returnValue = false;
        }
        toQueryAll()
    }

    //上架商品
    function shelvesCommodity(this1) {
        // <button type="button" class="btn btn-xs btn-success">
        //         <i class="icon-ok bigger-120" onclick="shelvesCommodity(this)">上架</i>
        //         </button>
        let x = $(this1).parent().parent().find("td");
        let y = x.eq(1).text()
        if (confirm("确认要删除？")) {
            location.assign("commodity.action?methodName=shelves&shelvesId=" + y)
        } else {
            window.event.returnValue = false;
        }
        if (!${requestScope.updateMap.isOkCount > 0}) {
            alert("上架成功！")
            toQueryAll()
        } else {
            alert("上架失败！")
            toQueryAll()
        }
    }

    function updateCommodity(this1) {
        let x = $(this1).parent().parent().find("td");
        let id = x.eq(1).text()
        // alert(id)
        // let detail1 = x.eq(7).text()
        window.open('commodity.action?methodName=updateQuery&updateQueryId=' + id, 'updateGoods', 'height=700, width=3000, top=0, left=0, toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no');

    }

    // function countId(this1) {
    //     let x = $(this1).parent().parent().find("td");
    //     let one = x.eq(1).text()
    //
    //     count++;
    // }
    //
    // function deleteBatch() {
    //
    // }

</script>
