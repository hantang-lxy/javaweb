package cn.kgc.tangcco.filter;

import cn.kgc.tangcco.kjde1021.pojo.Category;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.service.CategoryService;
import cn.kgc.tangcco.service.CommodityService;
import cn.kgc.tangcco.service.impl.CommodityServiceImpl;
import com.alibaba.fastjson.JSON;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/28  16:59
 */
@WebFilter(filterName = "A_FilterEncoding", urlPatterns = {"*.action", "*.jsp"})
public class FilterEncoding implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {

        // 转发数据类型
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        // 处理中文乱码
        // 处理post请求的中文乱码
        request.setCharacterEncoding("utf-8");
        // 处理响应的字符集中文乱码
        request.setCharacterEncoding("utf-8");
        // 处理浏览器字符集中文乱码
        response.setContentType("text/html;charset=utf-8");
        chain.doFilter(req, resp);

    }

    @Override
    public void destroy() {

    }
}
