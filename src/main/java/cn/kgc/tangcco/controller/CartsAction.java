package cn.kgc.tangcco.controller;

import cn.kgc.tangcco.dao.impl.CartsDaoImpl;
import cn.kgc.tangcco.kjde1021.pojo.*;
import cn.kgc.tangcco.service.CategoryService;
import cn.kgc.tangcco.service.CommodityService;
import cn.kgc.tangcco.service.impl.CartsService;
import cn.kgc.tangcco.service.impl.CategoryServiceImpl;
import cn.kgc.tangcco.service.impl.CommoditySearchServiceImpl;
import cn.kgc.tangcco.service.impl.CommodityServiceImpl;
import cn.kgc.tangcco.util.servlet.BaseServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/carts.action")
public class CartsAction extends BaseServlet {
    private static CartsDaoImpl cdi = new CartsDaoImpl();
    private static CartsService cartsService = new CartsService();
    //实例化业务逻辑层接口
    private static CategoryService categoryService = new CategoryServiceImpl();
    //实例化业务逻辑层接口
    private static CommodityService commodityService = new CommodityServiceImpl();
    //实例化业务逻辑层接口
    private static CommoditySearchServiceImpl commoditySearchService = new CommoditySearchServiceImpl();
    //此页面分工合作 逻辑比较复杂 故用了全局变量
    private String sortName;
    private String scid;
    private String count;
    private String pageNum;
    private String orderBy;
    private String value;

    //使用用户uuid查询购物车
    //查询购物车
    public void getCarts(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, IOException {
        HttpSession session = servletRequest.getSession();
        Person person = (Person) session.getAttribute("operator");
        List<Carts> CartsList = cartsService.getCartsByUuid(person.getUUuid());
        session.setAttribute("CartsList", CartsList);
        servletResponse.sendRedirect("Carts.jsp");
    }


    //使用用户uuid，商品cid  添加购物车
    public void addCarts(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, IOException {
        HttpSession session = servletRequest.getSession();
        Person person = (Person) session.getAttribute("operator");
        int code = cartsService.addCarts(person.getUUuid(), Integer.parseInt(servletRequest.getParameter("cId")));

        System.out.println("servlet中添加商品成功");
        //添加商品成功后，再次查询购物车信息
        List<Carts> CartsList = cartsService.getCartsByUuid(person.getUUuid());
        session.setAttribute("CartsList", CartsList);
        servletResponse.sendRedirect("Carts.jsp");
    }

    //使用用户uuid和购物车sid删除购物车中的条目
    public void deleteCarts(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, IOException {
        int code = cartsService.deleteCarts(Integer.parseInt(servletRequest.getParameter("sid")));
        HttpSession session = servletRequest.getSession();
        System.out.println("servlet中删除商品成功");
        //删除商品成功后，再次查询购物车信息
        List<Carts> CartsList = cartsService.getCartsByUuid(servletRequest.getParameter("uuid"));
//        session.setAttribute("CartsList", CartsList);
        getCarts(servletRequest,servletResponse);
//        servletResponse.sendRedirect("Carts.jsp");
    }

    //提交订单，清空该用户购物车
    public void submitCarts(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException, IOException, ServletException {
        HttpSession session = servletRequest.getSession();
        Person person = (Person) session.getAttribute("operator");
        String message = "初始字符串";
        double total = 0;
        if (person == null) {
            System.out.println("未获得用户数据");
            return;
        }
        List<Carts> CartsList = cartsService.getCartsByUuid(person.getUUuid());
        for (Carts carts : CartsList
        ) {
            total += carts.getCommodity().getCPrice().doubleValue() * carts.getSNum();
        }
        System.out.println("购物车中商品总价：" + total);
        //总价大于用户余额
        if (total > person.getUFortune().doubleValue()) {
            message = "您的余额不足~";
            servletRequest.setAttribute("message", message);
            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("Carts.jsp");
            //调用forward()方法，转发请求
            requestDispatcher.forward(servletRequest, servletResponse);
            return;
        }
        //总价小于用户余额
        if (total <= 0) {
            session.setAttribute("CartsList", CartsList);
            message = "您的购物车为空，快去选购些商品吧~";
            servletRequest.setAttribute("message", message);
            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("Carts.jsp");
            //调用forward()方法，转发请求
            requestDispatcher.forward(servletRequest, servletResponse);
        } else {
            //修改用户余额
            person.setUFortune(BigDecimal.valueOf(person.getUFortune().doubleValue() - total));
            //更新数据库中余额
            cartsService.updateFortune(person);
            //更新session中用户信息
            session.setAttribute("operator", person);
            //添加商品到订单表
            cartsService.addOrders(new Orders(1, new Date(), person.getUUuid(), 3, "默认收货地址", person.getUPhone(), person.getUNickname()));
            for (Carts carts : CartsList
            ) {
                //添加商品到订单明细表
                cartsService.addOrderItems(new OrderItems(carts.getSCid(), carts.getCommodity().getCPrice(), carts.getSNum()));
                //删除购物车中该商品
                cartsService.deleteCarts(carts.getSId());
            }
            //更新session中的购物车
            List<Carts> CartsList2 = cartsService.getCartsByUuid(person.getUUuid());
            session.setAttribute("CartsList", CartsList2);
            message = "提交订单成功~";
            servletRequest.setAttribute("message", message);
            RequestDispatcher requestDispatcher = servletRequest.getRequestDispatcher("Carts.jsp");
            //调用forward()方法，转发请求
            requestDispatcher.forward(servletRequest, servletResponse);
        }

    }


    /**
     * 添加到购物车
     *
     * @param request
     * @param response
     */
    public void addToCart(HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        Map<String, Object> map = new HashMap<>();
        String cId = request.getParameter("cId");
        Person person = (Person) request.getSession().getAttribute("operator");
        String uUuid = person.getUUuid();
        map.put("cId", cId);
        map.put("uUuid", uUuid);
        map.put("cNum", "1");
        try {
            map = commodityService.addToCart(map);
            List<Carts> CartsList = cartsService.getCartsByUuid(uUuid);
            HttpSession session = request.getSession();
            session.setAttribute("CartsList", CartsList);
            request.setAttribute("addtocartresult", "添加成功!");
            request.getRequestDispatcher("/detail.action?methodName=showDetail&cId=" + cId).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从搜索页添加购物车
     *
     * @param request
     * @param response
     */
    public void addToCartFromSearch(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String cId = request.getParameter("cId");
        Person person = (Person) request.getSession().getAttribute("operator");
        String uUuid = person.getUUuid();
        String sort = request.getParameter("sort");
        String search = request.getParameter("search");
        map.put("cId", cId);
        map.put("uUuid", uUuid);
        map.put("cNum", "1");
        try {
            map = commoditySearchService.addToCart(map);
            request.setAttribute("addtocartresult", "添加成功!");
            //request.getRequestDispatcher("/log.jsp").forward(request, response);
            request.getRequestDispatcher("/commoditySearch.action?methodName=showCommodities&sort=" + sort + "&search="+search).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCartFromCategory(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String cId = request.getParameter("cId");
        sortName = request.getParameter("sortName");
        scid = request.getParameter("scid");
        count = request.getParameter("count");
        pageNum = request.getParameter("pageNum");
        Person person = (Person) request.getSession().getAttribute("operator");
        String uUuid = person.getUUuid();
        map.put("cId", cId);
        map.put("uUuid", uUuid);
        map.put("cNum", "1");
        try {
            map = categoryService.addToCart(map);
            request.setAttribute("addtocartresult", "添加成功!");
            request.getRequestDispatcher("/category.action?methodName=showGoods&sortName=" + sortName + "&scid=" + scid + "&count=" + count + "&pageNum=" + pageNum).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkstock(HttpServletRequest request, HttpServletResponse response) {
        try {
            String cid = request.getParameter("cid");
            Integer cidInt = Integer.parseInt(cid);
            Commodity commodity = new Commodity(cidInt);
            Map<String, Object> map = new HashMap<>();
            map.put("code", "50001");
            map.put("commodity", commodity);
            String sql = " SELECT c_stock FROM commodity WHERE 1 and c_id = ? ";
            map.put("sql", sql);
            cdi.checkstockByCid(map);
            String code = map.get("code").toString();
            commodity = (Commodity) map.get("commodity");
            switch (code.trim()) {
                case "50001":
                    //该商品不存在
                    printJson(response, "failed");
                    return;
                case "0":
                    //商品存在,已下架。
                    if (commodity.getCStock() == -1) {
                        printJson(response, "failed");
                        return;
                    }
                    printJson(response, cidInt);
                    return;
                default:
                    break;
            }


        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | SQLException | InvocationTargetException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
        }
    }

}
