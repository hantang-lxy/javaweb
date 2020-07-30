package cn.kgc.tangcco.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/27  12:53
 */
@WebFilter(filterName = "/FilterLogin", urlPatterns = {"*.action"})
public class FilterLogin implements Filter {
    public FilterLogin() {
        System.out.println("我是FilterLogin的无参构造！");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("我是FilterLogin的init方法");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String uri = req.getRequestURI();
        //获取请求的uri
        System.out.println("uri>>>" + uri);
        StringBuffer url = req.getRequestURL();
        System.out.println("url>>>" + url.toString());
        String queryString = req.getQueryString();
        //获取请求时带的参数
        System.out.println("queryString>>>" + queryString);
        HttpSession session = req.getSession();
        //登录状态码，登录后会赋值。没有登陆是空
        Integer loginCode = (Integer) session.getAttribute("loginCode");
        if (loginCode == null) {
            //这里不能反过来写，因为是大包含小
            if (uri.contains("smsCode")) {
                //解除过滤符合条件的过滤
                chain.doFilter(request, response);
                return;
            }
            if (uri.contains("category")) {
                chain.doFilter(request, response);
                return;
            }
            if (uri.contains("detail")) {
                chain.doFilter(request, response);
                return;
            }
            if (uri.contains("commoditySearch")) {
                chain.doFilter(request, response);
                return;
            }
            if (uri.contains("commodity")) {
                chain.doFilter(request, response);
                return;
            }
//            if (uri.contains("/manager/commodity.action")) {
//                chain.doFilter(request, response);
//                return;
//            }
            if (!uri.contains("person")) {
                String currentURL = url.toString() + "?" + queryString;
                toLogin(req, resp, session, currentURL);
                return;
            }
        }
        System.out.println("过滤器出口");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("我是FilterLogin的destroy方法");
    }

    private void toLogin(HttpServletRequest req, HttpServletResponse resp, HttpSession session, String currentURL) throws IOException, ServletException {
        session.invalidate();
        req.getSession().setAttribute("currentURL", currentURL);
        resp.sendRedirect(req.getContextPath() + "/register.html");
        return;
    }
}

