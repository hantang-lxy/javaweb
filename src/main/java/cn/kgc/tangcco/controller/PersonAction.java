package cn.kgc.tangcco.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.kjde1021.vo.CommodityVo;
import cn.kgc.tangcco.service.PersonService;
import cn.kgc.tangcco.service.impl.PersonServiceImpl;
import cn.kgc.tangcco.util.cryptography.BaseCryptographyUtils;
import cn.kgc.tangcco.util.servlet.BaseServlet;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/11  12:21
 */
@WebServlet(urlPatterns = "person.action")
public class PersonAction extends BaseServlet {
    private static final long serialVersionUID = 4923567125307296706L;
    //从类路径中加载文件
    private static Properties properties = null;
    private static String aesKey = "lihaozhelihaozhe";
    private static String desKey = "lihaozhelihaozhelihaozhelihaozhelihaozhelihaozhelihaozhelihaozhe";

    //实例化业务逻辑层接口
    private static PersonService ps = new PersonServiceImpl();

    static {
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("key.properties");
            aesKey = properties.getProperty("aeskey");
            desKey = properties.getProperty("deskey");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册时验证手机号和用户名是否已存在
     * 存在返回"no"
     * 不存在返回"success"
     *
     * @param request
     * @param response
     */
    public void verify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String mobile = request.getParameter("mobile");
            String username = request.getParameter("username");


            Map<String, Object> map = new HashMap<>();
            Person person = new Person(username, mobile);
            map.put("person", person);
            //查找

            map = ps.findPersonByMobileOrUsername(map);
            System.out.println(JSON.toJSONString(map));
            //获取查询结果状态码
            String code = map.get("code").toString();
            System.out.println(code);
            switch (code.trim()) {
                case "50001":
                    //没有相同的手机号或者用户名，可以注册
                    printJson(response, "50001");
                    return;
                case "0":
                    //有相同的手机号或者用户名，不可以注册
                    printJson(response, "0");
                    return;
                default:
                    break;
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | SQLException | InvocationTargetException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 注册
     *
     * @param request
     * @param response
     */
    public void register(HttpServletRequest request, HttpServletResponse response) {
        try {
            String username = request.getParameter("account");
            String password = request.getParameter("password");
            String mobile = request.getParameter("phone");
            //加密获取到的密码
            password = BaseCryptographyUtils.desEncodeHexString(password, desKey);
            Person person = new Person(username, mobile, password);
            Map<String, Object> map = new HashMap<>();
            map.put("person", person);
            ps.register(map);
            //获取查询结果状态码
            String code = map.get("code").toString();
            switch (code.trim()) {
                case "50001":
                    //注册失败
                    printJson(response, "no");
                    return;
                case "0":
                    //注册成功
                    printJson(response, "success");
                    return;
                default:
                    break;
            }

        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | SQLException | InvocationTargetException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 登录
     *
     * @param request
     * @param response
     */
    public void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = new HashMap<>();
            //先验证图片验证码是否正确，不正确直接返回重新输入
            String vercode = request.getParameter("vercode");
            LineCaptcha captcha = (LineCaptcha) request.getSession().getAttribute("captcha");
            String captchaCode = captcha.getCode();
            //初始化验证码验证结果：wrong：错误 right：正确
            if (!captchaCode.equalsIgnoreCase(vercode)) {
                //验证码错误
                map.put("captchaResult", "wrong");
                printJson(response, map);
                return;
            }
            //验证码正确
            map.put("captchaResult", "right");
            //接收表单传过来的值
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            String mobile = username;
            //加密获取到的密码
            password = BaseCryptographyUtils.desEncodeHexString(password, desKey);
            Person person = new Person(username, mobile, password);
            map.put("person", person);
            ps.findPersonByPerson(map);
            //验证是否查询成功
            String code = map.get("code").toString();
            //登陆成功
            if (code.equals("0")) {
                person = (Person) map.get("person");
                //将登录的当前用户存入session域
                request.getSession().setAttribute("operator", person);
                //登录状态码
                request.getSession().setAttribute("loginCode", 0);
            }
            printJson(response, map);
        } catch (NoSuchMethodException | InstantiationException | SQLException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 向登录页面显示验证码
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(90, 38);
        //输出code
        System.out.println(lineCaptcha.getCode());
        String captchaId = request.getParameter("captcha_id");
        if (captchaId.equals("0")) {
            //将登录请求生成的图像验证码对象存储到session中
            request.getSession().setAttribute("captcha", lineCaptcha);
        } else if (captchaId.equals("1")) {
            //将找回密码请求生成的图像验证码对象存储到session中
            request.getSession().setAttribute("imgvercode", lineCaptcha);
        }

        //将验证码向页面输出
        lineCaptcha.write(response.getOutputStream());
        return;
    }

    /**
     * 验证找回密码的图片验证码是否正确
     * "wrong"不正确
     * "right" 正确
     *
     * @param request
     * @param response
     */
    public void captchaVerify(HttpServletRequest request, HttpServletResponse response) {
        try {
            String imgvercode = request.getParameter("imgvercode");
            LineCaptcha captcha = (LineCaptcha) request.getSession().getAttribute("imgvercode");
            String captchaCode = captcha.getCode();
            //初始化验证码验证结果：wrong：错误 right：正确
            if (!captchaCode.equalsIgnoreCase(imgvercode)) {
                //验证码错误
                printJson(response, "wrong");
                return;
            }
            printJson(response, "right");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重置密码
     *
     * @param request
     * @param response
     */
    public void resetPassword(HttpServletRequest request, HttpServletResponse response) {
        try {
            String mobile = request.getParameter("mobile");
            String password = request.getParameter("password");
            if (StringUtils.isEmpty(mobile)) {
                //重置密码失败
                printJson(response, "failed");
                return;
            }
            //加密获取到的密码
            password = BaseCryptographyUtils.desEncodeHexString(password, desKey);
            Person person = new Person(null, mobile, password);
            Map<String, Object> map = new HashMap<>();
            map.put("person", person);
            ps.updatePasswordByPhone(map);
            //获取查询结果状态码
            String code = map.get("code").toString();
            switch (code.trim()) {
                case "50001":
                    //重置密码失败
                    printJson(response, "failed");
                    return;
                case "0":
                    //重置密码成功
                    printJson(response, "success");
                    return;
                default:
                    break;
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | SQLException | InvocationTargetException | NoSuchFieldException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 退出系统，返回首页
     *
     * @param request
     * @param response
     * @throws IOException
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    public void logoutByRepass(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath() + "/register.html");
        return;
    }

    /**
     * 返回登录前的页面
     *
     * @param request
     * @param response
     */
    public void toPreURL(HttpServletRequest request, HttpServletResponse response) {
        try {
            String currentURL = (String) request.getSession().getAttribute("currentURL");
            if (StringUtils.isEmpty(currentURL)) {
                //如果session域是空值就跳转到首页
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            response.sendRedirect(currentURL);
            return;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

