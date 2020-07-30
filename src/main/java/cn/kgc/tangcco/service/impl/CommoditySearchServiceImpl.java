package cn.kgc.tangcco.service.impl;

import cn.hutool.core.util.PageUtil;
import cn.kgc.tangcco.dao.impl.CommoditySearchDaoImpl;
import cn.kgc.tangcco.kjde1021.pojo.CommoditySort;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.kjde1021.vo.CommodityVo;
import cn.kgc.tangcco.service.CommoditySearchService;
import cn.kgc.tangcco.util.jdbc.BaseJdbcSearch;
import cn.kgc.tangcco.util.reflect.BaseReflect;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/13 下午4:34
 */
public class CommoditySearchServiceImpl implements CommoditySearchService {
    private static CommoditySearchDaoImpl commodityDao = new CommoditySearchDaoImpl();

    /**
     * 查询商品
     *
     * @param map
     * @return map
     * @throws Exception
     */
    @Override
    public Map<String, Object> queryCommodities(Map<String, Object> map) throws Exception {
        //所有map
        Map<String, Object> allMap = new HashMap<>();
        //单个map
        Map<String, Object> commodityMap = new HashMap<>();
        Map<String, Object> sortMap = new HashMap<>();
        Integer count = 0;
        commodityMap.put("count", count);
        commodityMap.put("data", new ArrayList<Commodity>());
        sortMap.put("data", new ArrayList<CommoditySort>());
        sortMap.put("active", 99999);
        try {
            //查询类别
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM commodity_sort WHERE 1 ");
            map.put("sql", sql);
            List<CommoditySort> sorts = commodityDao.querySorts(map);
            sortMap.put("data", sorts);
            //查询商品
            sql = new StringBuilder();
            if (Integer.parseInt(map.get("sort").toString()) != 99999) {
                //sortMap.put("active",Integer.valueOf(map.get("sort").toString()));
                sql.append("SELECT count(c_id) FROM commodity WHERE 1 AND c_stock>0 ");
                sql.append(" AND c_sort = " + "'" + map.get("sort").toString() + "' ");
            } else {
                sql.append("SELECT count(c_id) FROM commodity WHERE 1 AND c_stock>0 ");
            }
            if (map.get("search") != null && map.get("search") != "") {
                //除掉空格和制表符
                String search = map.get("search").toString().replaceAll(" ", "").replaceAll("  ", "");
                char[] searchArr = search.toCharArray();
                sql.append(" AND c_name LIKE '");
                for (char c : searchArr) {
                    sql.append("%%" + c);
                }
                sql.append("%%' ");
            }
            map.put("sql", sql);
            try {
                count = commodityDao.queryCommoditiesCount(map);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (count != null && count > 0) {
                PageParam pageParam = (PageParam) map.get("page");
                map.put("page", pageParam);
                sortMap.put("active", (Integer) map.get("sort"));
                //准备sql语句
                sql = new StringBuilder();
                if (Integer.parseInt(map.get("sort").toString()) != 99999) {
                    //sortMap.put("active",Integer.valueOf(map.get("sort").toString()));
                    sql.append("SELECT * FROM commodity WHERE 1 AND c_stock>0 ");
                    sql.append(" AND c_sort = " + "'" + map.get("sort").toString() + "' ");
                } else {
                    sql.append("SELECT * FROM commodity WHERE 1 AND c_stock>0 ");
                }
                if (map.get("search") != null && map.get("search") != "") {
                    //除掉空格和制表符
                    String search = map.get("search").toString().replaceAll(" ", "").replaceAll("  ", "");
                    char[] searchArr = search.toCharArray();
                    sql.append(" AND c_name LIKE '");
                    for (char c : searchArr) {
                        sql.append("%%" + c);
                    }
                    sql.append("%%' ");
                }
                map.put("sql", sql);
                //查询所有商品
                List<Commodity> commodities = null;
                try {
                    commodities = commodityDao.queryCommodities(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    allMap.put("result", "fail");
                    sql = new StringBuilder();
                    if (Integer.parseInt(map.get("sort").toString()) != 99999) {
                        //sortMap.put("active",Integer.valueOf(map.get("sort").toString()));
                        sql.append("SELECT * FROM commodity WHERE 1 AND c_stock>0 ");
                        sql.append(" AND c_sort = " + "'" + map.get("sort").toString() + "' ");
                    } else {
                        sql.append("SELECT * FROM commodity WHERE 1 AND c_stock>0 ");
                    }
                    map.put("sql", sql);
                    commodities = commodityDao.queryCommodities(map);
                }
                List<CommodityVo> commodityVos = new ArrayList<>();
                CommodityVo commodityVo = null;
                ListIterator<Commodity> it = commodities.listIterator();
                Commodity commodity = null;
                //遍历所有商品
                while (it.hasNext()) {
                    commodity = it.next();
                    commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, new CommodityVo());
                    commodityVos.add(commodityVo);
                }
                /*ListIterator<CommoditySearchVo> itt = commoditySearchVos.listIterator();
                CommodityVo commoditySearchVo1 = new CommoditySearchVo();
                while (itt.hasNext()) {
                    commoditySearchVo1 = itt.next();
                    System.out.println(commoditySearchVo1.getCName() + "+++" + commoditySearchVo1.getCPrice() + "+++" + commoditySearchVo1.getCUrl());
                }*/
                //计算最大页码
                Integer pageCount = PageUtil.totalPage(count, pageParam.getPageSize());
                commodityMap.put("count", pageCount);
                //参数意义分别为：当前页、总页数、每屏展示的页数
                int[] rainbow = PageUtil.rainbow(pageParam.getPageNum(), pageCount, 7);
                commodityMap.put("rainbow", rainbow);
                commodityMap.put("active", pageParam.getPageNum());
                commodityMap.put("data", commodityVos);
                allMap.put("result", "success");
                allMap.put("sortMap", sortMap);
                allMap.put("commodityMap", commodityMap);
            } else {//////////////////////////////////////////////////////////////////////////////////////////////////业务逻辑有待改进
                PageParam pageParam = (PageParam) map.get("page");
                map.put("page", pageParam);
                sortMap.put("active", (Integer) map.get("sort"));
                //准备sql语句
                sql = new StringBuilder();
                if (Integer.parseInt(map.get("sort").toString()) != 99999) {
                    //sortMap.put("active",Integer.valueOf(map.get("sort").toString()));
                    sql.append("SELECT * FROM commodity WHERE 1 AND c_stock>0 ");
                    sql.append(" AND c_sort = " + "'" + map.get("sort").toString() + "' ");
                } else {
                    sql.append("SELECT * FROM commodity WHERE 1 AND c_stock>0 ");
                }
                map.put("sql", sql);
                //查询所有商品
                List<Commodity> commodities = null;
                try {
                    commodities = commodityDao.queryCommodities(map);
                } catch (Exception e) {
                    e.printStackTrace();
                    sql = new StringBuilder();
                    if (Integer.parseInt(map.get("sort").toString()) != 99999) {
                        //sortMap.put("active",Integer.valueOf(map.get("sort").toString()));
                        sql.append("SELECT * FROM commodity WHERE 1 AND c_stock>0 ");
                        sql.append(" AND c_sort = " + "'" + map.get("sort").toString() + "' ");
                    } else {
                        sql.append("SELECT * FROM commodity WHERE 1 AND c_stock>0 ");
                    }
                    map.put("sql", sql);
                    commodities = commodityDao.queryCommodities(map);
                }
                List<CommodityVo> commodityVos = new ArrayList<>();
                CommodityVo commodityVo = null;
                ListIterator<Commodity> it = commodities.listIterator();
                Commodity commodity = null;
                //遍历所有商品
                while (it.hasNext()) {
                    commodity = it.next();
                    commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, new CommodityVo());
                    commodityVos.add(commodityVo);
                }
                //计算最大页码
                Integer pageCount = PageUtil.totalPage(count, pageParam.getPageSize());
                commodityMap.put("count", pageCount);
                //参数意义分别为：当前页、总页数、每屏展示的页数
                int[] rainbow = PageUtil.rainbow(pageParam.getPageNum(), pageCount, 7);
                commodityMap.put("rainbow", rainbow);
                commodityMap.put("active", pageParam.getPageNum());
                commodityMap.put("data", commodityVos);
                allMap.put("result", "fail");
                allMap.put("sortMap", sortMap);
                allMap.put("commodityMap", commodityMap);
            }
        } catch (SQLException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException | InstantiationException | IllegalAccessException throwables) {
            throwables.printStackTrace();
        } finally {
            BaseJdbcSearch.close();
            return allMap;
            //return sortMap;
        }
        /*StringBuilder sql = new StringBuilder();

        if(map.get("sort") != null){
            sql.append("SELECT * FROM commodity WHERE 1 ");
            sql.append(" AND c_sort = "+map.get("sort").toString());
        } else {
            sql.append("SELECT * FROM commodity WHERE 1 ");
        }

        map.put("sql",sql);
        List<Commodity> commodities = commodityDao.selectCommodities(map);
        return null;*/
    }

    /**
     * 将商品添加到购物车
     *
     * @param map
     * @return map
     */
    @Override
    public Map<String, Object> addToCart(Map<String, Object> map) {
        map.put("code", 0);
        map.put("msg", "success");
        try {
            commodityDao.addToCart(map);
        } catch (SQLException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException e) {
            map.put("code", 500001);
            map.put("msg", "插入失败");
            e.printStackTrace();
        }
        return map;
    }
}
