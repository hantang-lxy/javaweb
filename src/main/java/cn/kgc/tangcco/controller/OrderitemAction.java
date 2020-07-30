package cn.kgc.tangcco.controller;

import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.service.impl.OrderItemServiceImpl;
import cn.kgc.tangcco.service.impl.OrderServiceImpl;
import cn.kgc.tangcco.util.servlet.BaseServlet;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/21 上午9:34
 */
@WebServlet(urlPatterns = "orderItem.action")
public class OrderitemAction extends BaseServlet {
    private static final long serialVersionUID = 5405993915821775871L;
    private static Properties properties = null;
    private static String aesKey = "lihaozhelihaozhe";
    private static String desKey = "lihaozhelihaozhelihaozhelihaozhelihaozhelihaozhelihaozhelihaozhe";
    //实例化业务逻辑层接口
    private static OrderItemServiceImpl orderItemService = new OrderItemServiceImpl();

    static {
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("druid.properties");
            //aesKey = properties.getProperty("aeskey");
            //desKey = properties.getProperty("deskey");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void showOrderItems(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        Person person = (Person)request.getSession().getAttribute("operator");
        String uUuid = person.getUUuid();
        map.put("uuid",uUuid);
        String oid = request.getParameter("oid");
        map.put("oid",oid);
        try {
            map = orderItemService.queryOrderItems(map);
            /*if(map.get("result").toString().equals("fail")){
                request.setAttribute("result","未搜索到商品！");
            }*/
            request.setAttribute("orderItemMap", map);
            request.getRequestDispatcher("/orderItems.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
