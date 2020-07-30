package cn.kgc.tangcco.controller;

import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.kjde1021.vo.CommodityVo;
import cn.kgc.tangcco.service.impl.CommoditySearchServiceImpl;
import cn.kgc.tangcco.util.servlet.BaseServlet;
import com.alibaba.druid.util.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/13 下午5:49
 */
@WebServlet(urlPatterns = "/commoditySearch.action")
public class CommoditySearchAction extends BaseServlet {
    private static final long serialVersionUID = -7200132041461344364L;
    private static Properties properties = null;
    private static String aesKey = "lihaozhelihaozhe";
    private static String desKey = "lihaozhelihaozhelihaozhelihaozhelihaozhelihaozhelihaozhelihaozhe";
    //实例化业务逻辑层接口
    private static CommoditySearchServiceImpl commoditySearchService = new CommoditySearchServiceImpl();

    static {
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("druid.properties");
            //aesKey = properties.getProperty("aeskey");
            //desKey = properties.getProperty("deskey");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showCommodities(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        map.put("sort",99999);
        //获取页码信息
        PageParam pageParam = new PageParam();
        String page = request.getParameter("page");
        String limit = request.getParameter("limit");
        String sort = request.getParameter("sort");
        String search = request.getParameter("search");
        if (!StringUtils.isEmpty(page)) {
            pageParam.setPageNum(Integer.parseInt(page));
        }
        if (!StringUtils.isEmpty(limit)) {
            pageParam.setPageSize(Integer.parseInt(limit));
        }
        if (!StringUtils.isEmpty(sort)) {
            map.put("sort", Integer.parseInt(sort));
        }
        if (!StringUtils.isEmpty(search)) {
            map.put("search",search);
        }
        map.put("page", pageParam);
        try {
            map = commoditySearchService.queryCommodities(map);
            if(map.get("result").toString().equals("fail")){
                request.setAttribute("result","未搜索到商品！");
            }
            request.setAttribute("sortMap", (Map<String,Object>)map.get("sortMap"));
            request.setAttribute("commodityMap", (Map<String,Object>)map.get("commodityMap"));
            request.setAttribute("search",search);
            request.getRequestDispatcher("/search-goods.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addToCart(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<>();
        String cId = request.getParameter("cId");
        Person person = (Person)request.getSession().getAttribute("operator");
        String uUuid = person.getUUuid();
        String sort = request.getParameter("sort");
        map.put("cId",cId);
        map.put("uUuid",uUuid);
        map.put("cNum","1");
        try {
            map = commoditySearchService.addToCart(map);
            request.setAttribute("addtocartresult","添加成功!");
            //request.getRequestDispatcher("/log.jsp").forward(request, response);
            request.getRequestDispatcher("/commoditySearch.action?methodName=showCommodities&sort="+sort).forward(request, response);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
