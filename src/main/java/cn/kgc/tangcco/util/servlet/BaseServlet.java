package cn.kgc.tangcco.util.servlet;

import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/21 下午9:22
 */
public abstract class BaseServlet extends HttpServlet {
    private static final long serialVersionUID = -9087367189544367009L;

    @SneakyThrows
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mName = request.getParameter("methodName").trim();
        if (!StringUtils.isEmpty(mName)) {
            if (StringUtils.isNotEmpty(request.getHeader("content-type"))) {
                if (getContentType(request).contains("application/json")) {
                    // ajax方式
                    BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
                    StringBuffer sb = new StringBuffer();
                    String temp;
                    while ((temp = br.readLine()) != null) {
                        sb.append(temp);
                    }
                    br.close();
                    Method method = this.getClass().getDeclaredMethod(mName, HttpServletRequest.class, HttpServletResponse.class, String.class);
                    method.setAccessible(true);
                    method.invoke(this, request, response, sb.toString());
                } else {
                    // 传统表单方式
                    Method method = this.getClass().getDeclaredMethod(mName, HttpServletRequest.class,
                            HttpServletResponse.class);
                    method.setAccessible(true);
                    method.invoke(this, request, response);
                }

            } else {
                // RequestHeader中没有Content-Type 使用传统表单方式
                Method method = this.getClass().getDeclaredMethod(mName, HttpServletRequest.class, HttpServletResponse.class);
                method.setAccessible(true);
                method.invoke(this, request, response);
            }
        } else {
            toIndex(request, response);
        }
    }

    /**
     * 获取ContentType
     *
     * @param request
     * @return
     */
    private String getContentType(HttpServletRequest request) {
        Optional<String> ofNullable = Optional.ofNullable(request.getHeader("content-type"));
        return ofNullable.orElse("");
    }

    /**
     * 登录出错，返回登录页
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void toIndex(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }


    /**
     * 向页面输出json字符串
     *
     * @param response HttpServletResponse
     * @param json     json字符串
     * @throws IOException IOException
     */
    public void printJson(HttpServletResponse response, String json) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.print(json);
        writer.flush();
    }

    /**
     * 向页面输出json字符串
     *
     * @param response HttpServletResponse
     * @param json     对象
     * @throws IOException IOException
     */
    public void printJson(HttpServletResponse response, Object json) throws IOException {
        printJson(response, JSON.toJSONString(json));
    }

}
