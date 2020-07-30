package cn.kgc.tangcco.controller;

import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.kjde1021.vo.CommodityVo;
import cn.kgc.tangcco.kjde1021.vo.PersonVo;
import cn.kgc.tangcco.service.CommodityService;
import cn.kgc.tangcco.service.PersonService;
import cn.kgc.tangcco.service.impl.CommodityServiceImpl;
import cn.kgc.tangcco.service.impl.PersonServiceImpl;
import cn.kgc.tangcco.util.servlet.BaseServlet;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/14  16:05
 */
@WebServlet(urlPatterns = "/detail.action")
public class DetailAction extends BaseServlet {
    private static final long serialVersionUID = -8312500196149930380L;
    //实例化业务逻辑层接口
    private static CommodityService commodityService = new CommodityServiceImpl();

    public void showDetail(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("是否进入showDetail方法");
        try {
            String cId = request.getParameter("cId");
            if (StringUtils.isEmpty(cId)) {
                request.getRequestDispatcher("/404.html").forward(request, response);
                return;
            }
            Map<String, Object> map = new HashMap<>();
            int cid = Integer.parseInt(cId);
            Commodity commodity = new Commodity(cid);
            map.put("commodity", commodity);
            //查找商品，并返回结果集
            map = commodityService.findGoodsById(map);
            //获取查询结果状态码
            String code = map.get("code").toString();
            switch (code.trim()) {
                case "50001":
                    request.getRequestDispatcher("/404.html").forward(request, response);
                    return;
                case "0":
                    //登陆成功，获取commodityVo对象
                    CommodityVo commodityVo = (CommodityVo) map.get("commodity");
                    //将商品信息存储在域中
                    request.setAttribute("goods", commodityVo);
                    //账号，密码一致，进入系统页面
                    request.getRequestDispatcher("/detail.jsp").forward(request, response);
                    return;
                default:
                    break;
            }
        } catch (ServletException | NoSuchMethodException | IllegalAccessException | InstantiationException | SQLException | InvocationTargetException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * 添加到购物车
     *
     * @param request
     * @param response
     */
    public void addToCart(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String cId = request.getParameter("cId");
        Person person = (Person) request.getSession().getAttribute("operator");
        String uUuid = person.getUUuid();
        map.put("cId", cId);
        map.put("uUuid", uUuid);
        map.put("cNum", "1");
        try {
            map = commodityService.addToCart(map);
            request.setAttribute("addtocartresult", "添加成功!");
            request.getRequestDispatcher("/detail.action?methodName=showDetail&cId=" + cId).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
