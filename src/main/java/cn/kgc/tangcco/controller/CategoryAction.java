package cn.kgc.tangcco.controller;

import cn.hutool.core.util.PageUtil;
import cn.kgc.tangcco.kjde1021.pojo.Category;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.kjde1021.vo.CommodityVo;
import cn.kgc.tangcco.model.Option;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.service.CategoryService;
import cn.kgc.tangcco.service.impl.CategoryServiceImpl;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/14  16:05
 */
@WebServlet(urlPatterns = "/category.action")
public class CategoryAction extends BaseServlet {
    private static final long serialVersionUID = -5918189907359799071L;
    //实例化业务逻辑层接口
    private static CategoryService categoryService = new CategoryServiceImpl();
    //此页面分工合作 逻辑比较复杂 故用了全局变量
    private String sortName;
    private String scid;
    private String count;
    private String pageNum;
    private String value;

    public void showGoods(HttpServletRequest request, HttpServletResponse response) {
        try {
            sortName = request.getParameter("sortName");
            scid = request.getParameter("scid");
            count = request.getParameter("count");
            pageNum = request.getParameter("pageNum");

            if (StringUtils.isEmpty(scid)) {
                request.getRequestDispatcher("/404.html").forward(request, response);
                return;
            }
            Map<String, Object> map = new HashMap<>();
            //当前页码数
            Integer pageNo = Integer.parseInt(pageNum);
            //每页展示6
            PageParam pageParam = new PageParam(pageNo, 6);
            map.put("page", pageParam);
            Category category = new Category(Integer.parseInt(scid));
            category.setName(sortName);
            //该类下面商品总数
            long parseLong = Long.parseLong(count);
            category.setCount(parseLong);
            //该类下总商品数
            Integer totalNum = (int) parseLong;
            map.put("category", category);
            //查找该类下所有商品，并返回结果集
            categoryService.selectCategoryByCategory(map);
            //每页显示个数
            Integer pageSize = pageParam.getPageSize();
            //参数意义分别为：当前页、总页数、每屏展示的页数
            //根据总数计算总页数
            int totalPage = PageUtil.totalPage(totalNum, pageSize);
            int[] rainbow = PageUtil.rainbow(pageNo, totalPage, 5);
            map.put("totalPage", totalPage);
            map.put("rainbow", rainbow);
            request.setAttribute("categoryMap", map);
            request.getRequestDispatcher("/category.jsp").forward(request, response);
            System.out.println("出口！");
            return;

        } catch (ServletException | NoSuchMethodException | IllegalAccessException | InstantiationException | SQLException | InvocationTargetException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
        }


    }

    public void addToCart(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String cId = request.getParameter("cId");
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
}
