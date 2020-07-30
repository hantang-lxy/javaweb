package cn.kgc.tangcco.controller;

import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.service.impl.CommoditySearchServiceImpl;
import cn.kgc.tangcco.service.impl.UserinfoServiceImpl;
import cn.kgc.tangcco.util.cryptography.BaseCryptographyUtils;
import cn.kgc.tangcco.util.phone.BasePhone;
import cn.kgc.tangcco.util.servlet.BaseServlet;
import com.alibaba.druid.util.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/17 下午2:27
 */
@WebServlet(urlPatterns = "/userInfo.action")
public class UserInfoAction extends BaseServlet {
    private static final long serialVersionUID = 2108680984762742298L;
    private static Properties properties = null;
    private static String aesKey = "lihaozhelihaozhe";
    private static String desKey = "lihaozhelihaozhelihaozhelihaozhelihaozhelihaozhelihaozhelihaozhe";
    //实例化业务逻辑层接口
    private static UserinfoServiceImpl userinfoservice = new UserinfoServiceImpl();

    static {
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("druid.properties");
            //aesKey = properties.getProperty("aeskey");
            //desKey = properties.getProperty("deskey");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getUserInfo(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        Person person = (Person)request.getSession().getAttribute("operator");
        String uuid = person.getUUuid();
        map.put("uuid",uuid);
        try{
            map = userinfoservice.findUserInfo(map);
            request.setAttribute("userInfo", map);
            request.getRequestDispatcher("/user-info.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setPassword(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        Person person = (Person)request.getSession().getAttribute("operator");
        String uuid = person.getUUuid();
        String pwd0 = request.getParameter("pwd0");
        String pwd1 = request.getParameter("pwd1");
        map.put("uuid",uuid);
        map.put("pwd0", BaseCryptographyUtils.desEncodeHexString(pwd0, desKey));
        map.put("pwd1", BaseCryptographyUtils.desEncodeHexString(pwd1, desKey));
        try{
            map = userinfoservice.setPassword(map);
            if(map.get("result").toString().equals("fail")){
                request.setAttribute("pwdResult","fail");
            } else if(map.get("result").toString().equals("databaseError")){
                request.setAttribute("pwdResult","databaseError");
            } else if(map.get("result").toString().equals("success")){
                request.setAttribute("pwdResult","success");
            }
            request.getRequestDispatcher("/userInfo.action?methodName=getUserInfo").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setInfo(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        Person person = (Person)request.getSession().getAttribute("operator");
        String uuid = person.getUUuid();
        String userImg = request.getParameter("userImg");
        String nickname = request.getParameter("nickname");
        String mobile = request.getParameter("mobile");
        String email = request.getParameter("email");
        map.put("uuid",uuid);
        map.put("userImg",userImg);
        map.put("nickname",nickname);
        map.put("mobile",mobile);
        map.put("email",email);
        try{
            map = userinfoservice.setInfo(map);
            if(map.get("result").toString().equals("success")){
                request.setAttribute("infoResult","success");
            } else {
                request.setAttribute("infoResult","fail");
            }
            request.getRequestDispatcher("/userInfo.action?methodName=getUserInfo").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
