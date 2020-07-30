package cn.kgc.tangcco.controller;

import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.service.impl.CommoditySearchServiceImpl;
import cn.kgc.tangcco.service.impl.OrderServiceImpl;
import cn.kgc.tangcco.util.servlet.BaseServlet;
import com.alibaba.druid.util.StringUtils;
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
 * @date 2020/6/21 上午8:17
 */
@WebServlet(urlPatterns = "/order.action")
public class OrderAction extends BaseServlet {
    private static final long serialVersionUID = -3006869453976188229L;
    private static Properties properties = null;
    private static String aesKey = "lihaozhelihaozhe";
    private static String desKey = "lihaozhelihaozhelihaozhelihaozhelihaozhelihaozhelihaozhelihaozhe";
    //实例化业务逻辑层接口
    private static OrderServiceImpl orderService = new OrderServiceImpl();

    static {
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("druid.properties");
            //aesKey = properties.getProperty("aeskey");
            //desKey = properties.getProperty("deskey");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showOrders(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        Person person = (Person) request.getSession().getAttribute("operator");
        String uUuid = person.getUUuid();
        map.put("uuid", uUuid);
        try {
            map = orderService.queryOrders(map);
            /*if(map.get("result").toString().equals("fail")){
                request.setAttribute("result","未搜索到商品！");
            }*/
            request.setAttribute("orderMap", map);
            request.getRequestDispatcher("/order.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
