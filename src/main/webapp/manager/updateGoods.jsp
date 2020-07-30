<%@ page import="cn.kgc.tangcco.kjde1021.vo.CommodityVo" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="v-on" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="js/html5.js"></script>
    <script type="text/javascript" src="js/respond.min.js"></script>
    <script type="text/javascript" src="js/PIE_IE678.js"></script>
    <![endif]-->
    <link href="assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="css/style.css"/>
    <link href="assets/css/codemirror.css" rel="stylesheet">
    <link rel="stylesheet" href="assets/css/ace.min.css"/>
    <link rel="stylesheet" href="Widget/zTree/css/zTreeStyle/zTreeStyle.css" type="text/css">
    <link rel="stylesheet" href="assets/css/font-awesome.min.css"/>
    <!--[if IE 7]>
    <link rel="stylesheet" href="assets/css/font-awesome-ie7.min.css"/>
    <![endif]-->
    <link href="Widget/icheck/icheck.css" rel="stylesheet" type="text/css"/>
    <link href="Widget/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"/>

    <title>修改商品</title>
</head>
<body on>
<div class="clearfix" id="add_picture" onclick="getParam()">
    <div class="page_right_style">
        <div class="type_title">修改商品</div>
        <form action="" method="post" class="form form-horizontal" id="form-article-add">
            <div class="clearfix cl">
                <label class="form-label col-2">商品名称：</label>
                <div class="formControls col-10"><input type="text" class="input-text"
                                                        value="${requestScope.updateQueryMap.data.CName}" placeholder=""
                                                        id="c_name" name=""></div>
            </div>
            <div class=" clearfix cl">
                <label class="form-label col-2">图片地址：</label>
                <div class="formControls col-10"><input type="text" class="input-text"
                                                        value="${requestScope.updateQueryMap.data.CUrl}" placeholder=""
                                                        id="c_url" name=""></div>
            </div>
            <div class=" clearfix cl">
                <div class="Add_p_s">
                    <label class="form-label col-2">商品分类：</label>
                    <div class="formControls col-2"><span class="select-box">
				<select id="c_sort" class="select">
					<option>请选择</option>
					<option value="10086">主板</option>
					<option value="10000">光驱</option>
					<option value="329897">网卡</option>
					<option value="35062">显卡</option>
					<option value="2306">显示器</option>
					<option value="7749">鼠标</option>
					<option value="3685">键盘</option>
					<option value="38389">机箱</option>
					<option value="2731">风扇/散热片</option>
					<option value="2233">硬盘</option>
					<option value="110217">内存</option>
					<option value="38479">CPU</option>
				</select>
				</span></div>
                </div>

                <div class="Add_p_s">
                    <label class="form-label col-2">商品单价：</label>
                    <div class="formControls col-2"><input type="text" class="input-text"
                                                           value="${requestScope.updateQueryMap.data.CPrice}"
                                                           placeholder=""
                                                           id="c_price" name=""></div>
                </div>
                <div class="Add_p_s">
                    <label class="form-label col-2">商品销量：</label>
                    <div class="formControls col-2"><input type="text" class="input-text"
                                                           value="${requestScope.updateQueryMap.data.CSales}"
                                                           placeholder=""
                                                           id="c_sales"
                                                           name=""></div>
                </div>
                <div class="Add_p_s">
                    <label class="form-label col-2">库&nbsp;&nbsp;&nbsp;&nbsp;存：</label>
                    <div class="formControls col-2"><input type="text" class="input-text"
                                                           value="${requestScope.updateQueryMap.data.CStock}"
                                                           placeholder=""
                                                           id="c_stock"
                                                           name=""></div>
                </div>


            </div>
            <div class="clearfix cl">
                <label class="form-label col-2">产品参数：</label>
                <div class="formControls col-10">
                    <textarea id="c_detail1" name="" cols="" rows="" class="textarea"
                              value="${requestScope.updateQueryMap.data.CDetail1}" placeholder="直接将写好的html元素放入即可~"
                              datatype="*10-100"
                              dragonfly="true" nullmsg="备注不能为空！"></textarea>
                    <p class="textarea-numberbar"><em class="textarea-length"></em>最多5000字</p>
                </div>
            </div>
            <div class="clearfix cl">
                <label class="form-label col-2">产品详情：</label>
                <div class="formControls col-10">
                    <textarea id="c_detail2" name="" cols="" rows="" class="textarea"
                              value="${requestScope.updateQueryMap.data.CDetail2}" placeholder="直接将写好的html元素放入即可~"
                              datatype="*10-100"
                              dragonfly="true" nullmsg="备注不能为空！"></textarea>
                    <p class="textarea-numberbar"><em class="textarea-length"></em>最多5000字</p>
                </div>
            </div>
            <div class="clearfix cl">
                <label class="form-label col-2">服务保障：</label>
                <div class="formControls col-10">
                    <textarea id="c_detail3" name="" cols="" rows="" class="textarea"
                              value="${requestScope.updateQueryMap.data.CDetail3}" placeholder="直接将写好的html元素放入即可~"
                              datatype="*10-100"
                              dragonfly="true" nullmsg="备注不能为空！"></textarea>
                    <p class="textarea-numberbar"><em class="textarea-length"></em>最多2000字</p>
                </div>
            </div>
            <div class="clearfix cl">
                <div class="Button_operation">
                    <button onclick="addgoodssubmit()" class="btn btn-primary radius" type="submit"><i
                            class="icon-save "></i>提交
                    </button>
                    <button class="btn btn-default radius" type="button"
                            onclick="goBack()">&nbsp;&nbsp;取消&nbsp;&nbsp;
                    </button>
                </div>
            </div>
        </form>
    </div>
</div>
</div>

<script src="res/js/jquery-3.5.1.min.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script>

    //获取所有文本框中的值，判断是否被修改过

    function addgoodssubmit() {
        let c_name = $('#c_name').val();
        let c_url = $('#c_url').val();
        let c_sort = $('#c_sort').val();
        let c_price = $('#c_price').val();
        let c_sales = $('#c_sales').val();
        let c_stock = $('#c_stock').val();
        let c_detail1 = $('#c_detail1').val();
        let c_detail2 = $('#c_detail2').val();
        let c_detail3 = $('#c_detail3').val();
        let c_id = ${requestScope.updateQueryMap.data.CId};

        $.ajax({
            type: "post",
            url: "http://localhost:8080/javaweb01/manager/commodity.action?methodName=updateOne",
            data: {
                "c_id": c_id,
                "c_name": c_name,
                "c_url": c_url,
                "c_sort": c_sort,
                "c_price": c_price,
                "c_sales": c_sales,
                "c_stock": c_stock,
                "c_detail1": c_detail1,
                "c_detail2": c_detail2,
                "c_detail3": c_detail3
            },
            dataType: "text",
            success: function (response) {
                alert("修改成功");
            },
            error: function () {
                alert("修改成功");
            }
        });
        window.close();
    }


    function goBack() {
        //IE
        if ((navigator.userAgent.indexOf('MSIE') >= 0) && (navigator.userAgent.indexOf('Opera') < 0)) {
            if (history.length > 0) {
                window.history.go(-1);
            } else {
                window.location.href = 'backstage_index.jsp';
            }
        } else {
            //非IE
            if (navigator.userAgent.indexOf('Firefox') >= 0 ||
                navigator.userAgent.indexOf('Opera') >= 0 ||
                navigator.userAgent.indexOf('Safari') >= 0 ||
                navigator.userAgent.indexOf('Chrome') >= 0 ||
                navigator.userAgent.indexOf('WebKit') >= 0) {
                if (window.history.length > 2) {
                    window.history.go(-1);
                } else {
                    window.location.href = 'backstage_index.jsp';
                }
            } else {//未知的浏览器
                if (history.length > 1) {
                    window.history.go(-1);
                } else {
                    window.location.href = 'backstage_index.jsp';
                }
            }
        }
    }
</script>
</body>
</html>
<script type="text/javascript">
    <%
    Integer c_sort = ((CommodityVo)((Map<String, Object>)request.getAttribute("updateQueryMap")).get("data")).getCSort(); //获取值
    %>
    let obj = document.getElementsByTagName("option")
    //遍历option
    for (let i = 0; i < obj.length; i++) {
        if (obj[i].value == '<%=c_sort%>') {
            obj[i].selected = true;  //相等则选中
        }
    }
</script>