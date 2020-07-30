package cn.kgc.tangcco.listener;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.useragent.UserAgentUtil;
import cn.kgc.tangcco.kjde1021.pojo.Category;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.service.CategoryService;
import cn.kgc.tangcco.service.CommodityService;
import cn.kgc.tangcco.service.impl.CommodityServiceImpl;
import cn.kgc.tangcco.util.localdatetime.BaseLocalDateTime;

import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/27  20:19
 */
@WebListener
public class UserListener implements ServletRequestListener, ServletRequestAttributeListener {
    private final static CommodityService cs = new CommodityServiceImpl();

    @Override
    public void attributeAdded(ServletRequestAttributeEvent srae) {

    }

    @Override
    public void attributeRemoved(ServletRequestAttributeEvent srae) {

    }

    @Override
    public void attributeReplaced(ServletRequestAttributeEvent srae) {

    }

    @Override
    public void requestDestroyed(ServletRequestEvent sre) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        //通过请求时间获取HttpServletRequest对象
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        String remoteIP = request.getRemoteAddr();
        String url = request.getRequestURL().toString();
        //我的网址给我加密传输了，我要解密还原
        url = URLUtil.decode(url);
        System.out.println("监视器>>>" + url);
        //请求的时间
        String requestTime = BaseLocalDateTime.getStringByCurrentLocalDateTime();
        boolean isMobile = UserAgentUtil.parse(request.getHeader("User-Agent")).isMobile();
        if (isMobile) {
            System.out.println("访问时间" + requestTime + "\t" + "手机端IP地址：" + remoteIP + "\t" + "访问页面：" + url);
        } else {
            System.out.println("访问时间" + requestTime + "\t" + "PC端IP地址：" + remoteIP + "\t" + "访问页面：" + url);
        }

        try {
            HttpSession session = request.getSession();
            List<Category> categories = CategoryService.queryCategory();
            List<Commodity> hotGoodsLise = cs.selectHotGoods();
            List<Commodity> likeGoodsLise = cs.selectLikeGoods();
            //返回的结果为null，则目前没有分类.也就不会存在商品。防止页面空指针异常
            if (categories == null) {
                return;
            }
            session.setAttribute("category", categories);
            session.setAttribute("hotGoods", hotGoodsLise);
            session.setAttribute("likeGoods", likeGoodsLise);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | SQLException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }


    }
}
