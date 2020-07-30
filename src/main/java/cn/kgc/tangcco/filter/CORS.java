package cn.kgc.tangcco.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CORS
 */
@WebFilter("*.action")
public class CORS implements Filter {

    /**
     * Default constructor.
     */
    public CORS() {
    }

    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 强制转换参数的数据类型
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String requestMethod = req.getMethod();
        String remoteHost = req.getRemoteHost();
        System.out.println("请求方式 >>> " + requestMethod);
        if ("OPTIONS".equalsIgnoreCase(requestMethod)) {
            // ajax跨域
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            resp.setHeader("Access-Control-Max-Age", "3600");
            resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            return;
        } else {
            // ajax跨域
            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
            resp.setHeader("Access-Control-Max-Age", "3600");
            resp.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
            resp.setHeader("Access-Control-Allow-Credentials", "true");
            // 放行通过过滤器
            chain.doFilter(request, response);
        }
    }

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }

}