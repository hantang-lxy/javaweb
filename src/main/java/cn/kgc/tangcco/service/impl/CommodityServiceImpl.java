package cn.kgc.tangcco.service.impl;

import cn.hutool.core.util.PageUtil;
import cn.kgc.tangcco.dao.CommodityDao;
import cn.kgc.tangcco.dao.impl.CommodityDaoImpl;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.kjde1021.pojo.OrderItems;
import cn.kgc.tangcco.kjde1021.pojo.Orders;
import cn.kgc.tangcco.kjde1021.vo.CommodityVo;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.service.CommodityService;
import cn.kgc.tangcco.util.jdbc.BaseJdbc;
import cn.kgc.tangcco.util.reflect.BaseReflect;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/20 下午7:23
 */
public class CommodityServiceImpl implements CommodityService {
    private static CommodityDao cd = new CommodityDaoImpl();


    /**
     * 查询Commodity信息
     * 状态码：50001 未查到信息
     * 状态码：0  查询到信息
     * "msg", "" 未查到信息
     * "msg", "success" 查到信息
     *
     * @param map
     * @return Map信息
     */
    @Override
    public Map<String, Object> findGoodsById(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        //初始化默认返回值。作为查询结果的判断，50001代表没有查询到结果
        map.put("code", 50001);
        //空字符串代表有没有查询到信息
        map.put("msg", "");
        Commodity commodity = (Commodity) map.get("commodity");
        StringBuffer sql = new StringBuffer();
        sql.append(" select c_id,c_name,c_sort,c_price,c_sales,c_stock,c_url,c_detail1,c_detail2,c_detail3 from commodity where 1 ");
        //编写动态sql
        if (commodity.getCId() != null) {
            sql.append(" and c_id = ? ");
        } else {
            //如果查询的条件有空值直接返回，不再进数据库查询
            return map;
        }
        //封装查询参数
        map.put("sql", sql);
        //调用持久层方法，获取查询结果封装到Person对象里面。
        commodity = cd.selectGoodsById(map);
        //关闭数据库连接
        BaseJdbc.close();
        if (commodity != null) {
            CommodityVo commodityVo = new CommodityVo();
            commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, commodityVo);
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("commodity", commodityVo);
        }
        return map;
    }

    /**
     * 返回降序排列10条商品数据集合
     *
     * @return List<Commodity> 或者 null
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    @Override
    public List<Commodity> selectHotGoods() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        List<Commodity> commodities = cd.selectGoodsAndDesc();

        BaseJdbc.close();
        return commodities;
    }

    /**
     * 返回降序排列3条喜欢商品数据集合
     *
     * @return List<Commodity> 或者 null
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    @Override
    public List<Commodity> selectLikeGoods() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        List<Commodity> commodities = cd.selectLikeGoods();
        BaseJdbc.close();
        return commodities;
    }

    /**
     * 加入购物车
     *
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> addToCart(Map<String, Object> map) {
        map.put("code", 0);
        map.put("msg", "success");
        try {
            cd.addToCart(map);
        } catch (SQLException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException | NoSuchFieldException e) {
            map.put("code", 500001);
            map.put("msg", "插入失败");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 单件添加商品
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> addCommodity(Map<String, Object> map) throws Exception {
        map.put("code", 50001);
        //空字符串代表有没有查询到信息
        map.put("msg", "");
        Commodity commodity = (Commodity) map.get("commodity");
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO commodity (c_sort ,c_name ,c_price ,c_sales ,c_stock ,c_url,c_detail1,c_detail2,c_detail3) ");

        if (commodity.getCSort() != 0) {
            sql.append(" VALUES (?,?,?,?,?,?,?,?,?)");
        }


        // 封装查询参数
        map.put("insertCommoditySql", sql);
        map.put("commodity", commodity);
        // 调用持久层方法
        map = cd.insertCommodity(map);
        // 关闭数据库连接
        BaseJdbc.close();
        Boolean isOk = false;
        if ((Boolean) map.get("isOk")) {
            isOk = true;
        }
        if ((Boolean) map.get("isOk")) {
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("isOk", isOk);
        }
        return map;
    }


    @Override
    public Map<String, Object> queryCommodity(Map<String, Object> map) throws Exception {
        map.put("code", 50001);
        //空字符串代表有没有查询到信息
        map.put("msg", "");
        Commodity commodity = (Commodity) map.get("commodity");
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT c.c_name,c.c_sort,c.c_price,c.c_sales,c.c_stock FROM commodity c where 1 And c_stock !=-1 ");

        if (commodity.getCSort() != 0) {
            sql.append(" AND c_id = ? ");
        }

        // 封装查询参数
        map.put("selectCommoditySql", sql);
        map.put("commodity", commodity);
        // 调用持久层方法
        List<Commodity> commodityList = cd.selectCommodity(map);
        // 关闭数据库连接
        BaseJdbc.close();
        if (commodityList != null) {
            CommodityVo commodityVo = new CommodityVo();
            BaseReflect.fatherToChildWithFiled(commodityList, commodityVo);
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("commodity", commodityVo);
        }
        return map;
    }

    @Override
    public Map<String, Object> soldOutOneType(Map<String, Object> map) throws Exception {
        map.put("code", 50001);
        //空字符串代表有没有查询到信息
        map.put("msg", "");
        Commodity commodity = (Commodity) map.get("commodity");
        commodity = new Commodity(commodity.getCId(), commodity.getCSort(), -1);
        StringBuilder sql = new StringBuilder();
        sql.append("update commodity ");
        if (commodity.getCStock() != null && commodity.getCSort() != null) {
            sql.append("set c_stock = ? where 1 and c_Sort = ?");
        }


        // 封装查询参数
        map.put("soldOutOneCommoditySql", sql);
        map.put("commodity", commodity);
        // 调用持久层方法
        boolean isSoldOut = cd.soldOutOneType(map);
        // 关闭数据库连接
        BaseJdbc.close();
        if (isSoldOut) {
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("isSoldOut", true);
        }
        return map;
    }

    @Override
    public Map<String, Object> soldOutOne(Map<String, Object> map) throws Exception {
        map.put("code", 50001);
        //空字符串代表有没有查询到信息
        map.put("msg", "");
        Commodity commodity = (Commodity) map.get("commodity");
        commodity = new Commodity(commodity.getCId(), commodity.getCSort(), -1);
        StringBuilder sql = new StringBuilder();
        sql.append("update commodity ");
        if (commodity.getCStock() != null && commodity.getCId() != null) {
            sql.append("set c_stock = ? where 1 and c_id = ?");
        } else if (commodity.getCName() != null && commodity.getCStock() != null) {
            sql.append("set c_stock = ? where 1 and c_name = ? ");
        } else {
            throw new NullPointerException("所选数值为空");
        }


        // 封装查询参数
        map.put("soldOutBatchCommoditySql", sql);
        map.put("commodity", commodity);
        // 调用持久层方法
        boolean isSoldOutOne = cd.updateOne(map);
        // 关闭数据库连接
        BaseJdbc.close();
        if (isSoldOutOne) {
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("isSoldOutOne", true);
        }
        return map;

    }

    @Override
    public Map<String, Object> updateCSortOne(Map<String, Object> map) throws Exception {
        map.put("code", 50001);
        //空字符串代表有没有查询到信息
        map.put("msg", "");
        Commodity commodity = (Commodity) map.get("commodity");
        StringBuilder sql = new StringBuilder();
        sql.append("update commodity ");
        if (commodity.getCStock() != null && commodity.getCSort() != null) {
            sql.append("set c_stock = ? where 1 and c_Sort = ?");
        }


        // 封装查询参数
        map.put("soldOutOneCommoditySql", sql);
        map.put("commodity", commodity);
        // 调用持久层方法
        boolean isSoutOne = cd.soldOutOneType(map);
        // 关闭数据库连接
        BaseJdbc.close();
        if (isSoutOne) {
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("updateCSortOne", true);
        }
        return map;
    }

    @Override
    public Map<String, Object> update_One(Map<String, Object> map) throws Exception {
        map.put("code", 50001);
        //空字符串代表有没有查询到信息
        map.put("msg", "");
        Commodity commodity = (Commodity) map.get("commodity");
        StringBuilder sql = new StringBuilder();
        sql.append("update commodity ");
        if (commodity.getCStock() != null && commodity.getCId() != null) {
            sql.append("set c_stock = ? where 1 and c_id = ?");
        } else if (commodity.getCName() != null && commodity.getCStock() != null) {
            sql.append("set c_stock = ? where 1 and c_name = ? ");
        } else {
            throw new NullPointerException("所选数值为空");
        }


        // 封装查询参数
        map.put("soldOutBatchCommoditySql", sql);
        map.put("commodity", commodity);
        // 调用持久层方法
        boolean isUpdate_One = cd.updateOne(map);
        // 关闭数据库连接
        BaseJdbc.close();
        if (isUpdate_One) {
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("isUpdate_One", true);
        }
        return map;
    }

    @Override
    public Map<String, Object> updateBatch(Map<String, Object> map) throws SQLException, IllegalAccessException {
        map.put("code", 50001);
        //空字符串代表有没有查询到信息
        map.put("msg", "");
        List<Object[]> list = new ArrayList<>();
        List<Commodity> commodityList = (List<Commodity>) map.get("commodity");
        Object[] objects = new Object[commodityList.size()];
        Commodity commodity = null;
        StringBuilder sql = new StringBuilder();
        sql.append("update commodity set c_stock = ? where 1 and c_id = ?");
        for (int i = 0; i < commodityList.size(); i++) {
            commodity = commodityList.get(i);
            commodity = new Commodity(commodity.getCId(), commodity.getCSort(), -1);
            objects[i] = commodity;
        }

        list.add(objects);
        map.put("updateCommoditySql", sql);
        map.put("params", list);
        int count = 0;
        List<Boolean> listBoolean = cd.updateBatch(map);
        for (int i = 0; i < listBoolean.size(); i++) {
            if (list.get(i) != null) {
                count++;
            } else {
                listBoolean.remove(i);
            }
        }
        if (count > 0) {
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("updateBatch", listBoolean);
        }
        return map;
    }

    @Override
    public Map<String, Object> update_Batch(Map<String, Object> map) throws SQLException {
        map.put("code", 50001);
        //空字符串代表有没有查询到信息
        map.put("msg", "");
        List<Object[]> list = new ArrayList<>();
        List<Commodity> commodityList = (List<Commodity>) map.get("commodity");
        Object[] objects = new Object[commodityList.size()];
        Commodity commodity = null;
        StringBuilder sql = new StringBuilder();
        sql.append("update commodity set c_stock = ? where 1 and c_id = ?");
        for (int i = 0; i < commodityList.size(); i++) {
            commodity = commodityList.get(i);
            commodity = new Commodity(commodity.getCId(), commodity.getCSort(), commodity.getCStock());
            objects[i] = commodity;
        }

        list.add(objects);
        map.put("updateCommoditySql", sql);
        map.put("params", list);
        int count = 0;
        List<Boolean> listBoolean = cd.updateBatch(map);
        for (int i = 0; i < listBoolean.size(); i++) {
            if (list.get(i) != null) {
                count++;
            } else {
                listBoolean.remove(i);
            }
        }
        if (count > 0) {
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("updateBatch", listBoolean);
        }
        return map;
    }

    @Override
    public Map<String, Object> queryAlls(Map<String, Object> map) throws SQLException {
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        Integer count = 0;
        commodityMap.put("code", 0);
        commodityMap.put("message", "");
        commodityMap.put("count", count);
        commodityMap.put("data", new ArrayList<Commodity>());
        try {
            // 一共有多少人
            count = cd.selectCommodityCount(map);
            if (count != null && count > 0) {
                PageParam pageParam = (PageParam) map.get("page");
                map.put("page", pageParam);
                // 员工列表
                List<Commodity> commoditys = cd.selectCommoditys(map);
                List<CommodityVo> commodities = new ArrayList<>();
                CommodityVo commodityVo = null;
                ListIterator<Commodity> it = commoditys.listIterator();
                Commodity commodity = null;
                while (it.hasNext()) {
                    commodity = it.next();
                    commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, new CommodityVo());

                    commodities.add(commodityVo);
                }
                // 计算最大页码号
                Integer pageCount = PageUtil.totalPage(count, pageParam.getPageSize());
                commodityMap.put("count", pageCount);
                //参数意义分别为：当前页、总页数、每屏展示的页数
                int[] rainbow = PageUtil.rainbow(pageParam.getPageNum(), pageCount, 7);
                commodityMap.put("rainbow", rainbow);
                commodityMap.put("active", pageParam.getPageNum());
                commodityMap.put("data", commodities);
            }
        } finally {
            // 关闭连接
            BaseJdbc.close();
            // 返回结果
            return commodityMap;
        }
    }

    @Override
    public Integer queryCommodityCount() throws SQLException {
        Map<String, Object> map = new HashMap<>();
        Integer integer = cd.selectCommodityCount(map);

        return integer;
    }

    @Override
    public List<Commodity> queryCommodity() throws Exception {
        Map<String, Object> map = new HashMap<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM commodity c where 1 ");
        map.put("sql", sql);
        // 调用持久层方法
        List<Commodity> commodityList = cd.selectCommodity(map);
        for (int i = 0; i < commodityList.size(); i++) {
            if (commodityList.get(i).getCStock() == -1) {
                commodityList.remove(i);
            }
        }
        // 关闭数据库连接
        BaseJdbc.close();
        if (commodityList != null) {
            //修改查询参数
            return commodityList;
        }
        return null;
    }


    @Override
    public Map<String, Object> queryType(Map<String, Object> map, String commodityCId) throws SQLException {
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        Integer count = 0;
        commodityMap.put("code", 0);
        commodityMap.put("message", "");
        commodityMap.put("count", count);
        commodityMap.put("data", new ArrayList<Commodity>());
        try {
            // 一共有多少人
            count = cd.selectTypeCount(map);
            if (count != null && count > 0) {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT * FROM commodity c where 1 and c_sort = ?");

                PageParam pageParam = (PageParam) map.get("page");
                map.put("page", pageParam);
                map.put("select", sql);
                // 员工列表
                List<Commodity> commoditys = cd.selectSortCommodity(map);
                List<CommodityVo> commodities = new ArrayList<>();
                CommodityVo commodityVo = null;
                ListIterator<Commodity> it = commoditys.listIterator();
                Commodity commodity = null;
                while (it.hasNext()) {
                    commodity = it.next();
                    if (commodity.getCStock() != -1) {
                        commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, new CommodityVo());
                        commodities.add(commodityVo);
                    }
                }
                // 计算最大页码号
                Integer pageCount = PageUtil.totalPage(count, pageParam.getPageSize());
                commodityMap.put("count", pageCount);
                //参数意义分别为：当前页、总页数、每屏展示的页数
                int[] rainbow = PageUtil.rainbow(pageParam.getPageNum(), pageCount, 7);
                commodityMap.put("rainbow", rainbow);
                commodityMap.put("active", pageParam.getPageNum());
                commodityMap.put("data", commodities);
                commodityMap.put("commodityCId", commodityCId);
            }
        } finally {
            // 关闭连接
            BaseJdbc.close();
            // 返回结果
            return commodityMap;
        }
    }


    @Override
    public Map<String, Object> queryId(Map<String, Object> map, String commodityCId) throws SQLException {
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        Integer count = 0;
        commodityMap.put("commodityCId", commodityCId);
        commodityMap.put("code", 0);
        commodityMap.put("message", "");
        commodityMap.put("count", count);
        commodityMap.put("data", new ArrayList<Commodity>());
        try {
            // 一共有多少人
            count = cd.selectCIdOrNameCount(map);
            if (count != null && count > 0) {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT * FROM commodity c where 1 ");
                Commodity commodity = (Commodity) map.get("commodity");
                if (commodity.getCId() != null && commodity.getCId() != 0) {
                    sql.append(" and c_id = ?");
                } else {
                    return map;
                }
                PageParam pageParam = (PageParam) map.get("page");
                map.put("page", pageParam);
                map.put("sql", sql);
                // 员工列表
                Commodity commoditys = cd.selectIdOrNameCommodity(map);
                List<CommodityVo> commodities = new ArrayList<>();
                CommodityVo commodityVo = null;
                commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commoditys, new CommodityVo());

                if (commoditys.getCStock() != -1) {
                    commodities.add(commodityVo);
                }
                // 计算最大页码号
                Integer pageCount = PageUtil.totalPage(count, pageParam.getPageSize());
                commodityMap.put("count", pageCount);
                //参数意义分别为：当前页、总页数、每屏展示的页数
                int[] rainbow = PageUtil.rainbow(pageParam.getPageNum(), pageCount, 10);
                commodityMap.put("rainbow", rainbow);
                commodityMap.put("active", pageParam.getPageNum());
                commodityMap.put("data", commodities);
            }
        } finally {
            // 关闭连接
            BaseJdbc.close();
            // 返回结果
            return commodityMap;
        }
    }

    @Override
    public Map<String, Object> queryName(Map<String, Object> map, String commodityCId) throws Exception {
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        Commodity commodity = (Commodity) map.get("commodity");
        Integer count = 0;
        commodityMap.put("commodityCId", commodityCId);
        commodityMap.put("code", 0);
        commodityMap.put("message", "");
        commodityMap.put("count", count);
        commodityMap.put("data", new ArrayList<Commodity>());
        try {
            List<Commodity> stringList = new ArrayList<>();
            //判断输入的名称是否存在，是否是用的模糊查询
            if (commodity.getCName() != null && commodity.getCName().length() != 0) {
                List<Commodity> list = new ArrayList<>();

                list = this.queryAll();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getCName().toUpperCase().indexOf(commodity.getCName().toUpperCase()) != -1) {
                        stringList.add(list.get(i));
                    }
                }
            }
            for (int i = 0; i < stringList.size(); i++) {
                // 一共有多少人
                map.put("commodity", stringList.get(i));
                Integer nameCount = cd.selectCIdOrNameCount(map);
                count += nameCount;
            }
            if (count != null && count > 0) {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT * FROM commodity c where 1 ");

                if (commodity.getCName() != null && commodity.getCName().length() != 0) {
                    sql.append(" and c_name = ?");
                } else {
                    return map;
                }

                PageParam pageParam = (PageParam) map.get("page");
                map.put("page", pageParam);
                map.put("sql", sql);
                // 员工列表
                List<Commodity> commoditys = new ArrayList<>();
                for (int i = 0; i < stringList.size(); i++) {
                    map.put("commodity", stringList.get(i));
                    commoditys.add(cd.selectIdOrNameCommodity(map));
                }
                List<CommodityVo> commodities = new ArrayList<>();
                CommodityVo commodityVo = null;
                ListIterator<Commodity> it = commoditys.listIterator();
                commodity = null;
                while (it.hasNext()) {
                    commodity = it.next();
                    if (commodity.getCStock() != -1) {
                        commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, new CommodityVo());
                        commodities.add(commodityVo);
                    }
                }
                // 计算最大页码号
                Integer pageCount = PageUtil.totalPage(count, pageParam.getPageSize());
                commodityMap.put("count", pageCount);
                //参数意义分别为：当前页、总页数、每屏展示的页数
                int[] rainbow = PageUtil.rainbow(pageParam.getPageNum(), pageCount, 10);
                commodityMap.put("rainbow", rainbow);
                commodityMap.put("active", pageParam.getPageNum());
                commodityMap.put("data", commodities);
            }
        } finally {
            // 关闭连接
            BaseJdbc.close();
            // 返回结果
            return commodityMap;
        }
    }

    @Override
    public List<Commodity> queryAll() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        return cd.selectAll();
    }

    @Override
    public Map<String, Object> updateQueryId(Map<String, Object> map) {
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        Commodity commodity = (Commodity) map.get("commodity");
        commodityMap.put("code", "50001");
        //空字符串代表有没有查询到信息
        commodityMap.put("msg", "");
        commodityMap.put("data", new ArrayList<Commodity>());
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM commodity c where 1 And c_stock != -1");

            if (commodity.getCId() != null && commodity.getCId() != 0) {
                sql.append(" and c_id = ?");
            } else {
                return map;
            }

            map.put("sql", sql);
            // 员工列表
            commodity = cd.selectIdCommodity(map);
            if (commodity != null) {
                CommodityVo commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, new CommodityVo());
                commodityMap.put("code", 0);
                commodityMap.put("msg", "success");
                commodityMap.put("data", commodityVo);
            }
            // 关闭连接
            BaseJdbc.close();
            // 返回结果
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return commodityMap;
        }
    }


    @Override
    public Map<String, Object> updateAll(Map<String, Object> map) throws Exception {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("code", 50001);
        //空字符串代表有没有查询到信息
        updateMap.put("msg", "");
        Commodity commodity = (Commodity) map.get("commodity");
        StringBuilder sql = new StringBuilder();
        if (commodity.getCId() != null && commodity.getCId() != 0) {
            sql.append("update commodity set c_name =?,c_price=?,c_sales=? ,c_sort=?, c_stock=?, c_url=?, c_detail1=?, c_detail2=? ,c_detail3=? where 1 and c_id =?  ");
        } else {
            throw new NullPointerException("所选数值为空");
        }


        // 封装查询参数
        updateMap.put("updateCommoditySql", sql);
        updateMap.put("commodity", commodity);
        // 调用持久层方法
        Integer isOkCount = cd.updateAll(updateMap);
        // 关闭数据库连接
        BaseJdbc.close();
        if (isOkCount > 0) {
            //修改查询参数
            updateMap.put("code", 0);
            updateMap.put("msg", "success");
            updateMap.put("isOkCount", isOkCount);
        }
        return updateMap;
    }

    @Override
    public Map<String, Object> updateOne(Map<String, Object> map) throws Exception {
        Map<String, Object> updateMap = new HashMap<>();
        updateMap.put("code", 50001);
        //空字符串代表有没有查询到信息
        updateMap.put("msg", "");
        Commodity commodity = (Commodity) map.get("commodity");
        StringBuilder sql = new StringBuilder();
        sql.append("update commodity ");
        if (commodity.getCStock() != null && commodity.getCId() != null) {
            sql.append("set c_stock = ? where 1 and c_id = ?");
        }else {
            throw new NullPointerException("所选数值为空");
        }


        // 封装查询参数
        updateMap.put("updateOneCommoditySql", sql);
        updateMap.put("commodity", commodity);
        // 调用持久层方法
        Integer isOkCount = cd.updateOne1(updateMap);
        // 关闭数据库连接
        BaseJdbc.close();
        if (isOkCount > 0) {
            //修改查询参数
            updateMap.put("code", 0);
            updateMap.put("msg", "success");
            updateMap.put("isOkCount", isOkCount);
        }
        return updateMap;
    }


    /*
    ----------------------------------------------------------------------------------------
     */


    /**
     * 查询下架
     *
     * @param map
     * @return
     * @throws SQLException
     */
    @Override
    public Map<String, Object> queryShelves(Map<String, Object> map) throws SQLException {
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        commodityMap.put("code", "50001");
        //空字符串代表有没有查询到信息
        commodityMap.put("msg", "");
        Integer count = 0;
        commodityMap.put("count", count);
        commodityMap.put("data", new ArrayList<Commodity>());
        try {
            // 一共有多少人
            count = cd.selectCountShelves(map);
            if (count != null && count > 0) {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT * FROM commodity c where 1 and c_stock = -1");

                PageParam pageParam = (PageParam) map.get("page");
                map.put("page", pageParam);
                map.put("select", sql);
                // 员工列表
                List<Commodity> commoditys = cd.selectSortShelves(map);
                List<CommodityVo> commodities = new ArrayList<>();
                CommodityVo commodityVo = null;
                ListIterator<Commodity> it = commoditys.listIterator();
                Commodity commodity = null;
                while (it.hasNext()) {
                    commodity = it.next();
                    commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, new CommodityVo());
                    commodities.add(commodityVo);
                }
                // 计算最大页码号
                Integer pageCount = PageUtil.totalPage(count, pageParam.getPageSize());
                commodityMap.put("count", pageCount);
                //参数意义分别为：当前页、总页数、每屏展示的页数
                int[] rainbow = PageUtil.rainbow(pageParam.getPageNum(), pageCount, 7);
                commodityMap.put("code", "0");
                commodityMap.put("msg", "success");
                commodityMap.put("rainbow", rainbow);
                commodityMap.put("active", pageParam.getPageNum());
                commodityMap.put("data", commodities);
            }
        } finally {
            // 关闭连接
            BaseJdbc.close();
            // 返回结果
            return commodityMap;
        }
    }

    @Override
    public Map<String, Object> updateQueryName(Map<String, Object> map) {
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        Commodity commodity = (Commodity) map.get("commodity");
        commodityMap.put("code", "50001");
        //空字符串代表有没有查询到信息
        commodityMap.put("msg", "");
        commodityMap.put("data", new ArrayList<Commodity>());
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM commodity c where 1 And c_stock != -1");

            if (commodity.getCName() != null && commodity.getCName().length() != 0) {
                sql.append(" and c_name = ?");
            } else {
                return map;
            }

            map.put("sql", sql);
            // 员工列表
            commodity = cd.selectIdOrNameCommodity(map);
            if (commodity != null) {
                CommodityVo commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, new CommodityVo());
                commodityMap.put("code", 0);
                commodityMap.put("msg", "success");
                commodityMap.put("data", commodityVo);
            }
            // 关闭连接
            BaseJdbc.close();
            // 返回结果
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return commodityMap;
        }
    }


    @Override
    public Map<String, Object> queryTypeShelves(Map<String, Object> map, String commodityCId) throws SQLException {
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        commodityMap.put("code", "50001");
        //空字符串代表有没有查询到信息
        commodityMap.put("msg", "");
        Integer count = 0;
        commodityMap.put("count", count);
        commodityMap.put("data", new ArrayList<Commodity>());
        try {
            // 一共有多少人
            count = cd.selectTypeCountShelves(map);
            if (count != null && count > 0) {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT * FROM commodity c where 1 and c_stock = -1 and c_sort = ?");

                PageParam pageParam = (PageParam) map.get("page");
                map.put("page", pageParam);
                map.put("select", sql);
                // 员工列表
                List<Commodity> commoditys = cd.selectSortCommodity(map);
                List<CommodityVo> commodities = new ArrayList<>();
                CommodityVo commodityVo = null;
                ListIterator<Commodity> it = commoditys.listIterator();
                Commodity commodity = null;
                while (it.hasNext()) {
                    commodity = it.next();
                    if (commodity.getCStock() != -1) {
                        commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, new CommodityVo());
                        commodities.add(commodityVo);
                    }
                }
                // 计算最大页码号
                Integer pageCount = PageUtil.totalPage(count, pageParam.getPageSize());
                commodityMap.put("count", pageCount);
                //参数意义分别为：当前页、总页数、每屏展示的页数
                int[] rainbow = PageUtil.rainbow(pageParam.getPageNum(), pageCount, 7);
                commodityMap.put("code", 0);
                commodityMap.put("msg", "success");
                commodityMap.put("rainbow", rainbow);
                commodityMap.put("active", pageParam.getPageNum());
                commodityMap.put("data", commodities);
                commodityMap.put("commodityCId", commodityCId);
            }
        } finally {
            // 关闭连接
            BaseJdbc.close();
            // 返回结果
            return commodityMap;
        }
    }

    @Override
    public Map<String, Object> queryIdShelves(Map<String, Object> map, String commodityCId) throws SQLException {
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        Integer count = 0;
        commodityMap.put("commodityCId", commodityCId);
        commodityMap.put("code", "50001");
        //空字符串代表有没有查询到信息
        commodityMap.put("msg", "");
        commodityMap.put("count", count);
        commodityMap.put("data", new ArrayList<Commodity>());
        try {
            // 一共有多少人
            count = cd.selectCIdOrNameCountShelves(map);
            if (count != null && count > 0) {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT * FROM commodity c where 1 And c_stock = -1 ");
                Commodity commodity = (Commodity) map.get("commodity");
                if (commodity.getCId() != null && commodity.getCId() != 0) {
                    sql.append(" and c_id = ?");
                } else {
                    return map;
                }
                PageParam pageParam = (PageParam) map.get("page");
                map.put("page", pageParam);
                map.put("sql", sql);
                // 员工列表
                Commodity commoditys = cd.selectIdOrNameCommodity(map);
                List<CommodityVo> commodities = new ArrayList<>();
                CommodityVo commodityVo = null;
                commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commoditys, new CommodityVo());

                if (commoditys.getCStock() == -1) {
                    commodities.add(commodityVo);
                }
                // 计算最大页码号
                Integer pageCount = PageUtil.totalPage(count, pageParam.getPageSize());
                commodityMap.put("count", pageCount);
                //参数意义分别为：当前页、总页数、每屏展示的页数
                int[] rainbow = PageUtil.rainbow(pageParam.getPageNum(), pageCount, 10);
                commodityMap.put("code", 0);
                commodityMap.put("msg", "success");
                commodityMap.put("rainbow", rainbow);
                commodityMap.put("active", pageParam.getPageNum());
                commodityMap.put("data", commodities);
            }
        } finally {
            // 关闭连接
            BaseJdbc.close();
            // 返回结果
            return commodityMap;
        }
    }

    @Override
    public Map<String, Object> queryNameShelves(Map<String, Object> map, String commodityCId) throws Exception {
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        Commodity commodity = (Commodity) map.get("commodity");
        Integer count = 0;
        commodityMap.put("commodityCId", commodityCId);
        commodityMap.put("code", "50001");
        //空字符串代表有没有查询到信息
        commodityMap.put("msg", "");

        commodityMap.put("count", count);
        commodityMap.put("data", new ArrayList<Commodity>());
        try {
            List<Commodity> stringList = new ArrayList<>();
            //判断输入的名称是否存在，是否是用的模糊查询
            if (commodity.getCName() != null && commodity.getCName().length() != 0) {
                List<Commodity> list = new ArrayList<>();

                list = this.queryAll();
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getCName().toUpperCase().indexOf(commodity.getCName().toUpperCase()) != -1) {
                        stringList.add(list.get(i));
                    }
                }
            }
            for (int i = 0; i < stringList.size(); i++) {
                // 一共有多少人
                map.put("commodity", stringList.get(i));
                Integer nameCount = cd.selectCIdOrNameCountShelves(map);
                count += nameCount;
            }
            if (count != null && count > 0) {
                StringBuilder sql = new StringBuilder();
                sql.append("SELECT * FROM commodity c where 1 And c_stock = -1");

                if (commodity.getCName() != null && commodity.getCName().length() != 0) {
                    sql.append(" and c_name = ?");
                } else {
                    return map;
                }

                PageParam pageParam = (PageParam) map.get("page");
                map.put("page", pageParam);
                map.put("sql", sql);
                // 员工列表
                List<Commodity> commoditys = new ArrayList<>();
                for (int i = 0; i < stringList.size(); i++) {
                    map.put("commodity", stringList.get(i));
                    commoditys.add(cd.selectIdOrNameCommodity(map));
                }
                List<CommodityVo> commodities = new ArrayList<>();
                CommodityVo commodityVo = null;
                ListIterator<Commodity> it = commoditys.listIterator();
                commodity = null;
                while (it.hasNext()) {
                    commodity = it.next();
                    if (commodity.getCStock() == -1) {
                        commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, new CommodityVo());
                        commodities.add(commodityVo);
                    }
                }
                // 计算最大页码号
                Integer pageCount = PageUtil.totalPage(count, pageParam.getPageSize());
                commodityMap.put("count", pageCount);
                //参数意义分别为：当前页、总页数、每屏展示的页数
                int[] rainbow = PageUtil.rainbow(pageParam.getPageNum(), pageCount, 10);
                commodityMap.put("rainbow", rainbow);
                commodityMap.put("active", pageParam.getPageNum());
                commodityMap.put("data", commodities);
                commodityMap.put("code", 0);
                commodityMap.put("msg", "success");
            }
        } finally {
            // 关闭连接
            BaseJdbc.close();
            // 返回结果
            return commodityMap;
        }
    }

    /**
     * 首页的显示
     * @param map
     * @return
     */
    @Override
    public Map<String, Object> queryHome(Map<String, Object> map) {
        System.out.println("queryHomeService");
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        commodityMap.put("code", "50001");
        //空字符串代表有没有查询到信息
        commodityMap.put("msg", "");
        try {
            map = cd.selectHome(map);
            // 用户数量
            Integer userCount = (Integer)map.get("userCount");
            commodityMap.put("userCount",userCount);
            //商品是否处理
            int undoneCount = 0;//未完成
            int doneCount = 0;//已完成
            int noShipped =0;//未发货
            int shipped =0;//已发货
            List<Orders> untreatedCount = (List<Orders>)map.get("untreatedCount");
            for (int i = 0; i < untreatedCount.size(); i++) {
                if (untreatedCount.get(i).getOState() == 1){
                    undoneCount++;
                }else if (untreatedCount.get(i).getOState() == 2){
                    doneCount++;
                }else if (untreatedCount.get(i).getOState() == 3){
                    noShipped++;
                }else{
                    shipped++;
                }
            }
            Integer[] untreated = new Integer[]{undoneCount,doneCount,noShipped,shipped};
            commodityMap.put("untreated",untreated);

            //商品统计
            Integer notShelves = 0;//未下架
            Integer shelves = 0;//已下架
            List<Commodity> commodityList = (List<Commodity>)map.get("statisticsCount");
            for (int i = 0; i < commodityList.size(); i++) {
                if (commodityList.get(i).getCStock() != -1){
                    notShelves++;
                }else{
                    shelves++;
                }
            }
            Integer[] isShelves = new Integer[]{notShelves,shelves};
            commodityMap.put("isShelves",isShelves);

            //查询所有完成的商品
            List<Orders> orders = (List<Orders>) map.get("Orders");
            //交易记录
            Integer deal = 0;//交易记录
            List<OrderItems> orderItemsList = (List<OrderItems>) map.get("dealCount");
            for (int i = 0; i < orderItemsList.size(); i++) {
                for (int j = 0; j < orders.size(); j++) {
                    if (orders.get(j).getONum().intValue() == orderItemsList.get(i).getItemOid().intValue()) {
                        deal+=orderItemsList.get(i).getItemMoney().intValue();
                    }
                }
            }
            commodityMap.put("deal",deal);
            //参数意义分别为：当前页、总页数、每屏展示的页数
            commodityMap.put("code", "0");
            commodityMap.put("msg", "success");
        } finally {
            // 关闭连接
            try {
                BaseJdbc.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            // 返回结果
            return commodityMap;
        }

    }








}
