package cn.kgc.tangcco.dao.impl;

import cn.kgc.tangcco.dao.CommodityDao;
import cn.kgc.tangcco.kjde1021.pojo.Carts;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.kjde1021.pojo.OrderItems;
import cn.kgc.tangcco.kjde1021.pojo.Orders;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.util.jdbc.BaseJdbc;
import cn.kgc.tangcco.util.jdbc.BaseJdbcSearch;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/14  11:43
 */
public class CommodityDaoImpl implements CommodityDao {
    /**
     * 查询商品Commodity信息
     *
     * @param map
     * @return Commodity对象或者null
     */
    @Override
    public Commodity selectGoodsById(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        //编写sql
        String sql = map.get("sql").toString();
        //查询参数,这里是个数组，不要搞错了！
        Object[] params = null;
        //从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        Integer cId = commodity.getCId();
        params = new Object[]{cId};
        //使用工具类进行查询。有可能返回null，表示未查询到结果
        commodity = BaseJdbc.queryCovertEntity(sql, Commodity.class, params);
        //返回查询结果
        return commodity;
    }

    /**
     * 返回降序排列10条数据
     *
     * @return 返回降序排列10条商品数据集合
     */
    @Override
    public List<Commodity> selectGoodsAndDesc() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        PageParam pageParam = new PageParam(1, 10);
        StringBuffer sql = new StringBuffer();
        sql.append(" select c_id,c_name,c_sort,c_price,c_sales,c_stock,c_url from commodity where 1 and c_stock != -1 ORDER BY c_sales DESC ");
        //返回容量为10的集合
        List<Commodity> commodityList = BaseJdbc.queryCovertList(sql.toString(), Commodity.class, pageParam, null);
        return commodityList;
    }

    /**
     * 返回3条猜你喜欢商品数据
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
        PageParam pageParam = new PageParam(1, 3);
        StringBuffer sql = new StringBuffer();
        sql.append(" select c_id,c_name,c_sort,c_price,c_sales,c_stock,c_url from commodity where 1 and c_stock != -1 ORDER BY c_sales DESC ");
        //返回容量为3的集合
        List<Commodity> commodityList = BaseJdbc.queryCovertList(sql.toString(), Commodity.class, pageParam, null);
        return commodityList;
    }

    /**
     * 加入购物车
     *
     * @param map
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public void addToCart(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String uUuid = map.get("uUuid").toString();
        String cId = map.get("cId").toString();
        Integer sNum = Integer.parseInt(map.get("cNum").toString());
        StringBuilder sql = new StringBuilder();
        Object[] params = null;
        try {
            sql.append("SELECT * FROM carts WHERE s_uuid=? AND s_cid=? ");
            params = new Object[]{uUuid, cId};
            Carts carts = BaseJdbcSearch.queryCovertEntity(sql.toString(), Carts.class, params);
            Integer sId = carts.getSId();
            System.out.println("sId>>>" + sId);
            //增加数量
            sql = new StringBuilder();
            sql.append("UPDATE carts SET s_num=? WHERE s_uuid=? AND s_cid=? ");
            params = new Object[]{sNum + 1, uUuid, cId};
            BaseJdbcSearch.startTransaction();
            PreparedStatement pst = BaseJdbcSearch.getPreparedStatement(sql.toString());
            BaseJdbcSearch.executeUpdate(pst, params);
            BaseJdbcSearch.commitAndClose();
        } catch (Exception throwables) {
            try {
                BaseJdbcSearch.rollbackAndClose();
                //添加记录
                System.out.println("事务回滚！");
                sql = new StringBuilder();
                sql.append("INSERT INTO carts(s_uuid,s_cid,s_num)");
                sql.append(" VALUES(?,?,?);");
                params = new Object[]{uUuid, cId, sNum};
                BaseJdbcSearch.startTransaction();
                PreparedStatement pst = BaseJdbcSearch.getPreparedStatement(sql.toString());
                BaseJdbcSearch.executeUpdate(pst, params);
                BaseJdbcSearch.commitAndClose();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> insertCommodity(Map<String, Object> map) throws Exception {
        // 获取SQL语句
        String commoditySql = map.get("insertCommoditySql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
//        c_sort ,c_name ,c_price ,c_sales ,c_stock ,c_url,c_detail1,c_detail2,c_detail3
        if (!StringUtils.isEmpty(commodity.getCName()) && !StringUtils.isEmpty(commodity.getCUrl())) {
            if (!StringUtils.isEmpty(commodity.getCPrice().toString()) && commodity.getCPrice().compareTo(BigDecimal.ZERO) != 0) {
                params = new Object[]{
                        commodity.getCSort(),
                        commodity.getCName(),
                        commodity.getCPrice(),
                        commodity.getCSales(),
                        commodity.getCStock(),
                        commodity.getCUrl(),
                        commodity.getCDetail1(),
                        commodity.getCDetail2(),
                        commodity.getCDetail3()
                };
            }
        }
        // 使用工具类进行查询 返回添加结果
        return BaseJdbc.addedEntity(commoditySql, params);
    }


    /**
     * 查询commodity
     *
     * @param map
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    @Override
    public List<Commodity> selectCommodity(Map<String, Object> map) throws Exception {
        // 获取SQL语句
        String sql = map.get("selectCommoditySql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Person对象
        Commodity commodity = (Commodity) map.get("commodity");
        if (commodity.getCId() != null && commodity.getCId() != 0) {
            params = new Object[]{
                    commodity.getCId()
            };
        } else if (commodity.getCSort() != null && commodity.getCSort() != 0) {
            params = new Object[]{
                    commodity.getCSort()
            };
        }
        // 使用工具类进行查询 返回查询结果
        return BaseJdbc.queryList(sql, Commodity.class, params);

    }

    @Override
    public Map<String, Boolean> deleteCommodity(Map<String, Object> map) throws Exception {
        // 获取SQL语句
        String commoditySql = map.get("deleteCommoditySql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        params = new Object[]{
                commodity.getCSort()
        };
        return BaseJdbc.deleteEntity(commoditySql, params);
    }

    @Override
    public List<Object> deleteCommoditys(Map<String, Object> map) throws Exception {
        String deleteCommoditysSql = map.get("deleteCommoditySql").toString();
        List<Commodity> list = (List<Commodity>) map.get("params");
        // 查询参数
        Object[] params = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            params[i] = list.get(i).getCId();
        }
        return Collections.singletonList(BaseJdbc.deleteCovertList(deleteCommoditysSql, params));
    }

    @Override
    public boolean soldOutOneType(Map<String, Object> map) throws Exception {
        // 获取SQL语句
        String commoditySql = map.get("soldOutOneCommoditySql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        if (!StringUtils.isEmpty(commodity.getCSort().toString())) {
            params = new Object[]{
                    commodity.getCStock(),
                    commodity.getCSort()
            };
        }
        return BaseJdbc.updateEntity1(commoditySql, params);
    }

    @Override
    public boolean updateOne(Map<String, Object> map) throws Exception {
        // 获取SQL语句
        String commoditySql = map.get("soldOutBatchCommoditySql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        if (commodity.getCId() != null && commodity.getCId() != 0) {
            params = new Object[]{
                    commodity.getCStock(),
                    commodity.getCId()
            };
        } else if (commodity.getCName() != null && commodity.getCName() != "" && commodity.getCName().length() != 0) {
            params = new Object[]{
                    commodity.getCStock(),
                    commodity.getCName()
            };
        }
        return BaseJdbc.updateEntity1(commoditySql, params);
    }

    @Override
    public List<Boolean> updateBatch(Map<String, Object> map) throws SQLException {
        String updateCommoditysSql = map.get("updateCommoditySql").toString();
        List<Object[]> list = (List<Object[]>) map.get("params");
        // 查询参数
        Object[] object = new Object[list.get(0).length];
        for (int i = 0; i < object.length; i++) {
            object[i] = list.get(0)[i];
        }
        Object[] params = null;
        List<Boolean> list1 = new ArrayList<>();
        for (int i = 0; i < object.length; i++) {
            Commodity commodity = (Commodity) object[i];
            if (commodity.getCStock() != null && commodity.getCId() != null) {
                params = new Object[]{
                        commodity.getCStock(),
                        commodity.getCId()
                };
            } else {
                params = new Object[]{
                        commodity.getCStock(),
                        commodity.getCName()
                };
            }
            list1.add(BaseJdbc.updateBatchList(updateCommoditysSql, params));
        }
        return list1;
    }

    @Override
    public boolean updateCSortOne(Map<String, Object> map) throws Exception {
        // 获取SQL语句
        String commoditySql = map.get("soldOutOneCommoditySql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        if (!!StringUtils.isEmpty(commodity.getCSort().toString())) {
            params = new Object[]{
                    commodity.getCStock(),
                    commodity.getCSort()
            };
        }
        return BaseJdbc.updateEntity1(commoditySql, params);
    }

    @Override
    public boolean update_One(Map<String, Object> map) throws Exception {
        // 获取SQL语句
        String commoditySql = map.get("soldOutBatchCommoditySql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        if (commodity.getCId() != null && commodity.getCId() != 0) {
            params = new Object[]{
                    commodity.getCStock(),
                    commodity.getCId()
            };
        } else if (commodity.getCName() != null && commodity.getCName() != "" && commodity.getCName().length() != 0) {
            params = new Object[]{
                    commodity.getCStock(),
                    commodity.getCName()
            };
        }
        return BaseJdbc.updateEntity1(commoditySql, params);
    }

    @Override
    public List<Boolean> update_Batch(Map<String, Object> map) throws SQLException {
        String updateCommoditysSql = map.get("updateCommoditySql").toString();
        List<Object[]> list = (List<Object[]>) map.get("params");
        // 查询参数
        Object[] object = new Object[list.get(0).length];
        for (int i = 0; i < object.length; i++) {
            object[i] = list.get(0)[i];
        }
        Object[] params = null;
        List<Boolean> list1 = new ArrayList<>();
        for (int i = 0; i < object.length; i++) {
            Commodity commodity = (Commodity) object[i];
            if (commodity.getCStock() != null && commodity.getCId() != null) {
                params = new Object[]{
                        commodity.getCStock(),
                        commodity.getCId()
                };
            } else {
                params = new Object[]{
                        commodity.getCStock(),
                        commodity.getCName()
                };
            }
            list1.add(BaseJdbc.updateBatchList(updateCommoditysSql, params));
        }
        return list1;
    }

    @Override
    public List<Commodity> selectCommoditys(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        // SQL语句
        StringBuilder sql = new StringBuilder();
        sql.append("select * from commodity where 1 And c_stock !=-1 ");
        // 查询参数
        Object[] params = null;
        return BaseJdbc.queryCovertList(sql.toString(), Commodity.class, (PageParam) map.get("page"), params);
    }

    @Override
    public Integer selectCommodityCount(Map<String, Object> map) throws SQLException {
        // SQL语句
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT count(c_id) FROM commodity WHERE 1 And c_stock !=-1 ");
        // 查询参数
        Object[] params = null;
        return BaseJdbc.queryCount(sql.toString(), params);
    }

    @Override
    public List<Commodity> selectAllCommodity(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        String sql = map.get("sql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        if (commodity.getCStock() != null) {
            params = new Object[]{
                    commodity.getCStock()
            };
        }
        return BaseJdbc.queryCovertList(sql, Commodity.class, params);
    }

    @Override
    public Integer selectTypeCount(Map<String, Object> map) throws SQLException {
        // SQL语句
        StringBuilder sql = new StringBuilder();
        sql.append("select count(c_sort) from commodity where 1 And c_stock !=-1 and c_sort = ? ");
        // 查询参数
        Object[] params = null;
        Commodity commodity = (Commodity) map.get("commodity");
        if (commodity.getCSort() != null) {
            params = new Object[]{
                    commodity.getCSort()
            };
        }
        return BaseJdbc.queryCount(sql.toString(), params);
    }

    @Override
    public List<Commodity> selectSortCommodity(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        String sql = map.get("select").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        if (commodity.getCSort() != null) {
            params = new Object[]{
                    commodity.getCSort()
            };
        }
        List<Commodity> list = BaseJdbc.queryCovertList(sql, Commodity.class, (PageParam) map.get("page"), params);
        System.out.println("List<Commodity> list = " + list);
        return list;
    }

    @Override
    public Integer selectCIdOrNameCount(Map<String, Object> map) throws SQLException {
        Commodity commodity = (Commodity) map.get("commodity");
        // SQL语句
        StringBuilder sql = new StringBuilder();
        if (commodity.getCId() != null && commodity.getCId() != 0) {
            sql.append("select count(c_id) from commodity where 1 And c_stock !=-1  and c_id = ? ");
        } else if (!StringUtils.isEmpty(commodity.getCName())) {
            sql.append("select count(c_name) from commodity where 1 And c_stock !=-1  and c_name = ?");
        }
        // 查询参数
        Object[] params = null;

        if (commodity.getCId() != null && commodity.getCId() != 0) {
            params = new Object[]{
                    commodity.getCId()
            };
        } else if (!StringUtils.isEmpty(commodity.getCName())) {
            params = new Object[]{
                    commodity.getCName()
            };
        }
        return BaseJdbc.queryCount(sql.toString(), params);
    }

    @Override
    public Commodity selectIdOrNameCommodity(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        String sql = map.get("sql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        if (!StringUtils.isEmpty(commodity.getCName())) {
            params = new Object[]{
                    commodity.getCName()
            };
        } else if (commodity.getCId() != null && commodity.getCId() != 0) {
            params = new Object[]{
                    commodity.getCId()
            };
        }
        return BaseJdbc.queryCovertEntity(sql, Commodity.class, params);
    }

    @Override
    /**
     * 查询所有
     */
    public List<Commodity> selectAll() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        // SQL语句
        StringBuilder sql = new StringBuilder();
        sql.append("select * from commodity where 1 And c_stock != -1");
        // 查询参数
        Object[] params = null;
        return BaseJdbc.queryCovertList(sql.toString(), Commodity.class, params);
    }

    @Override
    public Commodity selectIdCommodity(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        String sql = map.get("sql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        if (commodity.getCId() != null && commodity.getCId() != 0) {
            params = new Object[]{
                    commodity.getCId()
            };
        }
        return BaseJdbc.queryCovertEntity(sql, Commodity.class, params);
    }

    /**
     * 修改数据
     */
    @Override
    public Integer updateAll(Map<String, Object> map) throws SQLException {
        // 获取SQL语句
        String commoditySql = map.get("updateCommoditySql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        if (commodity.getCId() != 0 && commodity.getCId() != null) {
            params = new Object[]{
                    commodity.getCName(),
                    commodity.getCPrice(),
                    commodity.getCSales(),
                    commodity.getCSort(),
                    commodity.getCStock(),
                    commodity.getCUrl(),
                    commodity.getCDetail1(),
                    commodity.getCDetail2(),
                    commodity.getCDetail3(),
                    commodity.getCId()
            };
        }
        return BaseJdbc.updateEntity(commoditySql, params);
    }

    @Override
    public Integer updateOne1(Map<String, Object> map) throws Exception {
        // 获取SQL语句
        String commoditySql = map.get("updateOneCommoditySql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Commodity对象
        Commodity commodity = (Commodity) map.get("commodity");
        if (commodity.getCId() != null && commodity.getCId() != 0) {
            params = new Object[]{
                    commodity.getCStock(),
                    commodity.getCId()
            };
        }
        int a = BaseJdbc.updateEntity(commoditySql, params);
        System.out.println(a);
        return a;
    }

    /*
    --------------------------------------------------------------------------------------------
     */

    @Override
    public Integer selectCountShelves(Map<String, Object> map) throws SQLException {
        // SQL语句
        StringBuilder sql = new StringBuilder();
        sql.append("select count(c_id) from commodity where 1 And c_stock =-1");
        // 查询参数
        Object[] params = null;
//        Commodity commodity = (Commodity) map.get("commodity");
//        if (commodity.getCSort() != null) {
//            params = new Object[]{
//                    commodity.getCSort()
//            };
//        }
        return BaseJdbc.queryCount(sql.toString(), params);
    }

    @Override
    public List<Commodity> selectSortShelves(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        String sql = map.get("select").toString();
        // 查询参数
        Object[] params = null;
        List<Commodity> list = BaseJdbc.queryCovertList(sql, Commodity.class, (PageParam) map.get("page"), params);
        System.out.println("List<Commodity> list = " + list);
        return list;
    }

    @Override
    public Integer selectTypeCountShelves(Map<String, Object> map) throws SQLException {
        // SQL语句
        StringBuilder sql = new StringBuilder();
        sql.append("select count(c_id) from commodity where 1 And c_stock =-1 And c_id = ? ");
        // 查询参数
        Object[] params = null;
        Commodity commodity = (Commodity) map.get("commodity");
        if (commodity.getCId() != null && commodity.getCId() != 0) {
            params = new Object[]{
                    commodity.getCId()
            };
        }
        return BaseJdbc.queryCount(sql.toString(), params);
    }

    @Override
    public Integer selectCIdOrNameCountShelves(Map<String, Object> map) throws SQLException {
        Commodity commodity = (Commodity) map.get("commodity");
        // SQL语句
        StringBuilder sql = new StringBuilder();
        if (commodity.getCId() != null && commodity.getCId() != 0) {
            sql.append("select count(c_id) from commodity where 1 And c_stock =-1  and c_id = ? ");
        } else if (!StringUtils.isEmpty(commodity.getCName())) {
            sql.append("select count(c_name) from commodity where 1 And c_stock =-1  and c_name = ?");
        }
        // 查询参数
        Object[] params = null;

        if (commodity.getCId() != null && commodity.getCId() != 0) {
            params = new Object[]{
                    commodity.getCId()
            };
        } else if (!StringUtils.isEmpty(commodity.getCName())) {
            params = new Object[]{
                    commodity.getCName()
            };
        }
        Integer a = BaseJdbc.queryCount(sql.toString(), params);
        return a;
    }

    @Override
    public Map<String, Object> selectHome(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Map<String ,Object> homeMap = new HashMap<>();
        // 用户数量
        StringBuilder sqlUser = new StringBuilder();
        sqlUser.append("select count(u_id) from user where 1 and u_power = 2");
        Object[] params = null;
        Integer userCount = BaseJdbc.queryCount(sqlUser.toString(), params);
        homeMap.put("userCount",userCount);
        //商品是否处理
        StringBuilder sqlLogistics = new StringBuilder();
        sqlLogistics.append("select * from orders where 1");
        params = null;
        List<Orders> untreatedCount = BaseJdbc.queryCovertList(sqlLogistics.toString(),Orders.class, params);
        homeMap.put("untreatedCount",untreatedCount);

        //商品统计
        StringBuilder statistics = new StringBuilder();
        statistics.append("select * from commodity where 1");
        params = null;
        List<Commodity> statisticsCount = BaseJdbc.queryCovertList(statistics.toString(),Commodity.class, params);
        homeMap.put("statisticsCount",statisticsCount);
        //查询所有完成的商品
        StringBuilder sqlIsOK = new StringBuilder();
        sqlIsOK.append("select * from orders where 1 and o_state = 2");
        params = null;
        List<Orders> orders = BaseJdbc.queryCovertList(sqlIsOK.toString(), Orders.class, params);
        homeMap.put("Orders",orders);
        //交易记录
        StringBuilder sqlDeal = new StringBuilder();
        sqlDeal.append("select * from orderitems where 1");
        params = null;
        List<OrderItems> dealCount = BaseJdbc.queryCovertList(sqlDeal.toString(), OrderItems.class, params);
        homeMap.put("dealCount",dealCount);
        return homeMap;
    }

}
