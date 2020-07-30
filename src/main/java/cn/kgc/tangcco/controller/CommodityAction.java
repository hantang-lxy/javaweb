package cn.kgc.tangcco.controller;

import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.kjde1021.pojo.CommoditySort;
import cn.kgc.tangcco.kjde1021.vo.CommodityVo;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.service.CommodityService;
import cn.kgc.tangcco.service.CommoditySortService;
import cn.kgc.tangcco.service.impl.CommodityServiceImpl;
import cn.kgc.tangcco.service.impl.CommoditySortServiceImpl;
import cn.kgc.tangcco.util.exciption.ProcedureException;
import cn.kgc.tangcco.util.servlet.BaseServlet;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

import static java.util.regex.Pattern.compile;

/**
 * @author ShangYanshuo
 * @version 1.0
 * @date 2020/6/1717:12
 */
@WebServlet(urlPatterns = "/manager/commodity.action")
public class CommodityAction extends BaseServlet {
    private static final CommoditySortService csService = new CommoditySortServiceImpl();
    private static final CommodityService commodityService = new CommodityServiceImpl();
    private static final long serialVersionUID = 697028904139984319L;

    static {
        try {
            // 从类路径中加载文件
            Properties properties = PropertiesLoaderUtils.loadAllProperties("druid.properties");
//            String aesKey = properties.getProperty("aeskey");
//            String desKey = properties.getProperty("deskey");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有，分页查收
     *
     * @param request
     * @param response
     * @throws SQLException
     * @throws ServletException
     * @throws IOException
     */

    public void queryAll(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入到queryAll方法了");

        Map<String, Object> map = new HashMap<>();
        PageParam pageParam = new PageParam();
        // 获取查询页码
        String page = request.getParameter("page");
        // 获取每页记录数
        String limit = request.getParameter("limit");
        if (!StringUtils.isEmpty(page)) {
            System.out.println("page >>> " + page);
            pageParam.setPageNum(Integer.parseInt(page));
        }
        if (!StringUtils.isEmpty(limit)) {
            pageParam.setPageSize(Integer.parseInt(limit));
        }
        map.put("page", pageParam);
        try {
            Map<String, Object> commodityMap = commodityService.queryAlls(map);
            request.setAttribute("commodityMap", commodityMap);
//            response.sendRedirect(request.getContextPath() + "/manager/Query_goods.jsp");
            request.getRequestDispatcher("/manager/Query_goods.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    /**
//     * 查询所有上架、下架商品
//     *
//     * @param request
//     * @param response
//     */
//    public void queryAllCommodity(HttpServletRequest request, HttpServletResponse response) {
//        try {
//            List<Commodity> commodityList = commodityService.queryCommodity();
//            int isUp = 0;
//            int isDown = 0;
//            for (int i = 0; i < commodityList.size(); i++) {
//                if (commodityList.get(i).getCStock() != -1) {
//                    isUp++;
//                } else {
//                    isDown++;
//                }
//            }
//            Integer[] integer = new Integer[2];
//            integer[0] = isUp;
//            integer[1] = isDown;
//            request.setAttribute("queryIsUpOrDown", integer);
//            request.getRequestDispatcher("/home.jsp").forward(request, response);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 修改或下架单件商品 其中里面的isOk返回的是Boolean类型的值，true 代表成功， false代表失败
     *
     * @param request
     * @param response
     */
    public void soldOutOrUpdateOne(HttpServletRequest request, HttpServletResponse response) {
        try {
            String soldOutId = request.getParameter("soldOutId");
            String soldOutName = request.getParameter("");
            String soldOutStock = request.getParameter("");
            Commodity commodity = new Commodity(Integer.getInteger(soldOutId), soldOutName, Integer.getInteger(soldOutStock));
            Map<String, Object> map = new HashMap<>();
            map.put("commodity", commodity);
            boolean isOk = false;
            if (Integer.parseInt(soldOutStock) == -1) {
                //下架
                map = commodityService.soldOutOne(map);
                isOk = (boolean) map.get("isSoldOutOne");
            } else {
                //修改
                map = commodityService.update_One(map);
                isOk = (boolean) map.get("isUpdate_One");
            }
            //获取状态码
            String code = map.get("code").toString();
            // 如果状态码不是空的
            switch (code.trim()) {
                case "50001":
                    request.getRequestDispatcher("/404.html").forward(request, response);
                    return;
                case "0":
                    //  登录成功 获取Person对象
                    System.out.println("总人数 >>> " + isOk);
                    request.setAttribute("isOk", isOk);
                    request.getRequestDispatcher("/").forward(request, response);
                    break;
                default:
                    throw new ProcedureException("程序异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 批量下架商品 其中里面的isOk返回的是Boolean类型的值，true 代表成功， false代表失败
     *
     * @param request
     * @param response
     */
    public void soldOutBatch(HttpServletRequest request, HttpServletResponse response) {
        try {
            String[] soldOutBatchCId = request.getParameterValues("");
            String soldOutStock = request.getParameter("");

            Integer[] soldOutOneCId = new Integer[soldOutBatchCId.length];
            for (int i = 0; i < soldOutBatchCId.length; i++) {
                soldOutOneCId[i] = Integer.parseInt(soldOutBatchCId[i]);
            }
            List<Commodity> commodityList = new ArrayList<>();
            for (int i = 0; i < soldOutBatchCId.length; i++) {
                Commodity commodity = new Commodity(soldOutOneCId[i], Integer.parseInt(soldOutStock));
                commodityList.add(i, commodity);
            }

            Map<String, Object> map = new HashMap<>();
            map.put("commodity", commodityList);


            if (Integer.parseInt(soldOutStock) == -1) {
                //下架
                commodityService.updateBatch(map);
            } else {
                commodityService.update_Batch(map);
            }

            List<Boolean> params = (List<Boolean>) map.get("updataBatch");
            int count = 0;
            for (int i = 0; i < params.size(); i++) {
                if (params.get(i)) {
                    count++;
                }
            }
            boolean isOk = false;
            if (count == params.size()) {
                isOk = true;
            }
            String code = map.get("code").toString();
            // 如果状态码不是空的
            switch (code.trim()) {
                case "50001":
                    request.getRequestDispatcher("/404.html").forward(request, response);
                    return;
                case "0":
                    //  登录成功 获取Person对象
                    System.out.println("总人数 >>> " + isOk);
                    request.setAttribute("isOk", isOk);
                    request.getRequestDispatcher("/").forward(request, response);
                    break;
                default:
                    throw new ProcedureException("程序异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加商品
     *
     * @param request
     * @param response
     */
    public void addCommodity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入addCommodity方法>>>");
        //c_sort ,c_name ,c_price ,c_sales ,c_stock ,c_url
        String cSort = request.getParameter("c_sort");
        String cName = request.getParameter("c_name");
        String cPrice = request.getParameter("c_price");
        String cSales = request.getParameter("c_sales");
        String cStock = request.getParameter("c_stock");
        String cUrl = request.getParameter("c_url");
        String c_detail1 = request.getParameter("c_detail1");
        String c_detail2 = request.getParameter("c_detail2");
        String c_detail3 = request.getParameter("c_detail3");
        System.out.println(c_detail1);
        CommoditySort commoditySort = new CommoditySort(Integer.parseInt(cSort));
        Commodity commodity = new Commodity();

        Map<String, Object> map = new HashMap<>();
        map.put("commoditySort", commoditySort);
        Map<String, Object> mapCommoditySort = null;
        try {
            mapCommoditySort = csService.findPersonByCommoditySort(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mapCommoditySort != null) {
            commodity = new Commodity(null, cName, Integer.parseInt(cSort), new BigDecimal(cPrice), Integer.parseInt(cSales), Integer.parseInt(cStock), cUrl, c_detail1, c_detail2, c_detail3);
            map.put("commodity", commodity);
            try {
                System.out.println("添加的结果是：");
                map = commodityService.addCommodity(map);
                String code = map.get("code").toString();
                // 如果状态码不是空的
                switch (code.trim()) {
                    case "50001":
                        printJson(response, "50001");
                        return;
                    case "0":
                        printJson(response, "0");
                        break;
                    default:
                        throw new ProcedureException("程序异常");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * if根据类型查询
     * else根据姓名或者cid查询
     *
     * @param request
     * @param response
     */
    public void queryCommodity(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("是否进入queryCommodity");
        try {
            Map<String, Object> map = new HashMap<>();
            String commodityCId = request.getParameter("queryC");
            Commodity commodity = new Commodity();

            CommoditySort commoditySort;
            List<Commodity> commodityList = new ArrayList<>();
            switch (commodityCId) {
                case "10086":
                case "10000":
                case "329897":
                case "35062":
                case "2306":
                case "7749":
                case "3685":
                case "38389":
                case "2731":
                case "2233":
                case "110217":
                case "38479":
                    commodity = new Commodity(Integer.parseInt(commodityCId));
                    break;
                case "主板":
                case "光驱":
                case "网卡":
                case "显卡":
                case "显示器":
                case "鼠标":
                case "键盘":
                case "机箱":
                case "风扇/散热片":
                case "硬盘":
                case "内存":
                case "CPU":
                    commodity = new Commodity(csService.findCommoditySortToName(new CommoditySort(commodityCId)).getSortCid());
                    break;
                default:
                    if (compile("^[-\\+]?[\\d]*$").matcher(commodityCId).matches()) {
                        commodity = new Commodity(Integer.parseInt(commodityCId), 'a');
                    } else {
                        commodity = new Commodity(commodityCId);
                    }
                    break;
            }

            PageParam pageParam = new PageParam();
            // 获取查询页码
            String page = request.getParameter("page");
            // 获取每页记录数
            String limit = request.getParameter("limit");
            if (!StringUtils.isEmpty(page)) {
                System.out.println("page >>> " + page);
                pageParam.setPageNum(Integer.parseInt(page));
            }
            if (!StringUtils.isEmpty(limit)) {
                pageParam.setPageSize(Integer.parseInt(limit));
            }
            map.put("page", pageParam);
            Map<String, Object> commodityMap = new HashMap<>();
            if (commodity.getCSort() != null) {
                System.out.println("commodity:" + commodity.getCSort());
                map.put("commodity", commodity);
                commodityMap = commodityService.queryType(map, commodityCId);
            } else {
                map.put("commodity", commodity);
                if (compile("^[-\\+]?[\\d]*$").matcher(commodityCId).matches()) {
                    commodityMap = commodityService.queryId(map, commodityCId);
                } else {
                    commodityMap = commodityService.queryName(map, commodityCId);
                }

            }
            request.setAttribute("queryCommodity", commodityMap);
//            response.sendRedirect(request.getContextPath() + "/manager/Query_goods.jsp");
            request.getRequestDispatcher("/manager/Query_goods.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改商品(根据商品ID查询)
     *
     * @param request
     * @param response
     */
    public void updateQuery(HttpServletRequest request, HttpServletResponse response) {
        try {
            String updateQueryId = request.getParameter("updateQueryId");
            Commodity commodity = new Commodity(Integer.parseInt(updateQueryId), '1');
            Map<String, Object> map = new HashMap<>();
            map.put("commodity", commodity);
            Map<String, Object> updateQueryMap = commodityService.updateQueryId(map);
            //获取状态码
            String code = updateQueryMap.get("code").toString();
            // 如果状态码不是空的
            switch (code.trim()) {
                case "50001":
                    request.getRequestDispatcher("/manager/404.html").forward(request, response);
                    return;
                case "0":
                    //  登录成功 获取Person对象
                    request.setAttribute("updateQueryMap", updateQueryMap);
                    request.getRequestDispatcher("/manager/updateGoods.jsp").forward(request, response);
                    break;
                default:
                    throw new ProcedureException("程序异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改     单件商品
     *
     * @param request
     * @param response
     */
    public void updateOne(HttpServletRequest request, HttpServletResponse response) {
        try {
            String cId = request.getParameter("c_id");
            String cSort = request.getParameter("c_sort");
            String cName = request.getParameter("c_name");
            String cPrice = request.getParameter("c_price");
            String cSales = request.getParameter("c_sales");
            String cStock = request.getParameter("c_stock");
            String cUrl = request.getParameter("c_url");
            String cDetail1 = request.getParameter("c_detail1");
            String cDetail2 = request.getParameter("c_detail2");
            String cDetail3 = request.getParameter("c_detail3");
            System.out.println(cDetail1);
            Commodity commodity = new Commodity(Integer.parseInt(cId), cName, Integer.parseInt(cSort), new BigDecimal(cPrice), Integer.parseInt(cSales), Integer.parseInt(cStock), cUrl, cDetail1, cDetail2, cDetail3);

            Map<String, Object> map = new HashMap<>();
            map.put("commodity", commodity);
            Map<String, Object> updateQueryName = commodityService.updateQueryId(map);
            CommodityVo commodityVo = (CommodityVo) updateQueryName.get("data");
            String cNameVo = commodityVo.getCName();
            BigDecimal cPriceVo = commodityVo.getCPrice();
            Integer cSalesVo = commodityVo.getCSales();
            Integer cSortVo = commodityVo.getCSort();
            Integer cStockVo = commodityVo.getCStock();
            String cUrlVo = commodityVo.getCUrl();
            String cDetail1Vo = commodityVo.getCDetail1();
            String cDetail2Vo = commodityVo.getCDetail1();
            String cDetail3Vo = commodityVo.getCDetail1();


            if (!cNameVo.equals(cName)) {
                cNameVo = cName;
            }
            if (cPriceVo.intValue() != new BigDecimal(cPrice).intValue()) {
                cPriceVo = new BigDecimal(cPrice);
            }
            if (cSalesVo != Integer.parseInt(cSales)) {
                cSalesVo = Integer.parseInt(cSales);
            }
            if (cSortVo != Integer.parseInt(cSort)) {
                cSortVo = Integer.parseInt(cSort);
            }
            if (cStockVo != Integer.parseInt(cStock)) {
                cStockVo = Integer.parseInt(cStock);
            }
            if (!cUrlVo.equals(cUrl)) {
                cUrlVo = cUrl;
            }
            if (!cDetail1Vo.equals(cDetail1)) {
                cDetail1Vo = cDetail1;
            }
            if (!cDetail2Vo.equals(cDetail2)) {
                cDetail2Vo = cDetail2;
            }
            if (!cDetail3Vo.equals(cDetail3)) {
                cDetail3Vo = cDetail3;
            }

            commodity = new Commodity(Integer.parseInt(cId), cNameVo, cSortVo, cPriceVo, cSalesVo, cStockVo, cUrlVo, cDetail1Vo, cDetail2Vo, cDetail3Vo);

            map.put("commodity", commodity);
            Map<String, Object> updateQueryMap = commodityService.updateAll(map);


            //获取状态码
            String code = updateQueryMap.get("code").toString();
            // 如果状态码不是空的
            switch (code.trim()) {
                case "50001":
                    request.getRequestDispatcher("/manager/404.html").forward(request, response);
                    return;
                case "0":
                    //  登录成功 获取Person对象
                    request.setAttribute("updateQueryMap", updateQueryMap);
                    request.getRequestDispatcher("/manager/updateGoods.jsp").forward(request, response);
                    break;
                default:
                    throw new ProcedureException("程序异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 下架单件商品
     *
     * @param request
     * @param response
     */
    public void soldOut(HttpServletRequest request, HttpServletResponse response) {
        try {
            String soldOutId = request.getParameter("soldOutId");
            Commodity commodity = new Commodity(Integer.parseInt(soldOutId), -1);
            Map<String, Object> map = new HashMap<>();

            map.put("commodity", commodity);
            List<Commodity> list = commodityService.queryAll();
            Map<String, Object> soldOutMap = new HashMap<>();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCId().equals(Integer.parseInt(soldOutId))) {
                    soldOutMap = commodityService.updateOne(map);
                }
            }
            //获取状态码
            String code = soldOutMap.get("code").toString();
            // 如果状态码不是空的
            switch (code.trim()) {
                case "50001":
                    request.getRequestDispatcher("/manager/404.html").forward(request, response);
                    return;
                case "0":
                    //  登录成功 获取Person对象
                    request.setAttribute("updateMap", soldOutMap);
                    request.getRequestDispatcher("/manager/Query_goods.jsp").forward(request, response);
                    break;
                default:
                    throw new ProcedureException("程序异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ----------------------------------------------------------------------------------------
     */


    /**
     * 查询所有下架商品
     *
     * @param request
     * @param response
     */
    public void queryShelves(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        PageParam pageParam = new PageParam();
        // 获取查询页码
        String page = request.getParameter("page");
        // 获取每页记录数
        String limit = request.getParameter("limit");
        if (!StringUtils.isEmpty(page)) {
            System.out.println("page >>> " + page);
            pageParam.setPageNum(Integer.parseInt(page));
        }
        if (!StringUtils.isEmpty(limit)) {
            pageParam.setPageSize(Integer.parseInt(limit));
        }
        map.put("page", pageParam);
        try {
            Map<String, Object> commodityMap = commodityService.queryShelves(map);
            String code = commodityMap.get("code").toString();
            switch (code.trim()) {
                case "50001":
                    request.getRequestDispatcher("/manager/404.html").forward(request, response);
                    return;
                case "0":
                    //  登录成功 获取Person对象
                    request.setAttribute("commodityMapShelves", commodityMap);
                    request.getRequestDispatcher("/manager/putaway.jsp").forward(request, response);
                    break;
                default:
                    throw new ProcedureException("程序异常");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *              根据类型查询下架的商品
     * @param request
     * @param response
     */
    public void queryShelvesBatch(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, Object> map = new HashMap<>();
            String commodityCId = request.getParameter("queryC");
            Commodity commodity = new Commodity();

            CommoditySort commoditySort;
            List<Commodity> commodityList = new ArrayList<>();
            switch (commodityCId) {
                case "10086":
                case "10000":
                case "329897":
                case "35062":
                case "2306":
                case "7749":
                case "3685":
                case "38389":
                case "2731":
                case "2233":
                case "110217":
                case "38479":
                    commodity = new Commodity(Integer.parseInt(commodityCId));
                    break;
                case "主板":
                case "光驱":
                case "网卡":
                case "显卡":
                case "显示器":
                case "鼠标":
                case "键盘":
                case "机箱":
                case "风扇/散热片":
                case "硬盘":
                case "内存":
                case "CPU":
                    commodity = new Commodity(csService.findCommoditySortToName(new CommoditySort(commodityCId)).getSortCid());
                    break;
                default:
                    if (compile("^[-\\+]?[\\d]*$").matcher(commodityCId).matches()) {
                        commodity = new Commodity(Integer.parseInt(commodityCId), 'a');
                    } else {
                        commodity = new Commodity(commodityCId);
                    }
                    break;
            }

            PageParam pageParam = new PageParam();
            // 获取查询页码
            String page = request.getParameter("page");
            // 获取每页记录数
            String limit = request.getParameter("limit");
            if (!StringUtils.isEmpty(page)) {
                System.out.println("page >>> " + page);
                pageParam.setPageNum(Integer.parseInt(page));
            }
            if (!StringUtils.isEmpty(limit)) {
                pageParam.setPageSize(Integer.parseInt(limit));
            }
            map.put("page", pageParam);
            Map<String, Object> commodityMap = new HashMap<>();
            if (commodity.getCSort() != null) {
                System.out.println("commodity:" + commodity.getCSort());
                map.put("commodity", commodity);
                commodityMap = commodityService.queryTypeShelves(map, commodityCId);
            } else {
                map.put("commodity", commodity);
                if (compile("^[-\\+]?[\\d]*$").matcher(commodityCId).matches()) {
                    commodityMap = commodityService.queryIdShelves(map, commodityCId);
                } else {
                    commodityMap = commodityService.queryNameShelves(map, commodityCId);
                }

            }
            String code = commodityMap.get("code").toString();
            switch (code.trim()) {
                case "50001":
                    request.getRequestDispatcher("/manager/404.html").forward(request, response);
                    return;
                case "0":
                    //  登录成功 获取Person对象
                    request.setAttribute("queryShelves", commodityMap);
                    request.getRequestDispatcher("/manager/putaway.jsp").forward(request, response);
                    break;
                default:
                    throw new ProcedureException("程序异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 上架单件商品，但库存为0
     *
     * @param request
     * @param response
     */
    public void shelves(HttpServletRequest request, HttpServletResponse response) {
        try {
            String soldOutId = request.getParameter("shelvesId");
            Commodity commodity = new Commodity(Integer.parseInt(soldOutId), 0);
            Map<String, Object> map = new HashMap<>();

            map.put("commodity", commodity);
            Map<String, Object> shelvesMap = new HashMap<>();
            shelvesMap = commodityService.updateOne(map);
            //获取状态码
            String code = shelvesMap.get("code").toString();
            // 如果状态码不是空的
            switch (code.trim()) {
                case "50001":
                    request.getRequestDispatcher("/manager/404.html").forward(request, response);
                    return;
                case "0":
                    //  登录成功 获取Person对象
                    request.setAttribute("updateMap", shelvesMap);
                    request.getRequestDispatcher("/manager/putaway.jsp").forward(request, response);
                    break;
                default:
                    throw new ProcedureException("程序异常");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 首页显示
     */
    public void queryHome(HttpServletRequest request, HttpServletResponse response){
        try {
            Map<String, Object> map = new HashMap<>();
            System.out.println("进入了queryHome");
            Map<String, Object> queryHomeMap = commodityService.queryHome(map);
            request.setAttribute("queryHomeMap", queryHomeMap);
            request.getRequestDispatcher("/manager/home.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
