package cn.kgc.tangcco.dao.impl;

import cn.kgc.tangcco.dao.CommoditySearchDao;
import cn.kgc.tangcco.kjde1021.pojo.Carts;
import cn.kgc.tangcco.kjde1021.pojo.CommoditySort;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.util.jdbc.BaseJdbcSearch;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/13 下午3:29
 */
public class CommoditySearchDaoImpl implements CommoditySearchDao {
    /**
     * 查询所有商品类别
     * @param map
     * @return 类别列表
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public List<CommoditySort> querySorts(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String sql = map.get("sql").toString();
        Object[] params = null;
        return BaseJdbcSearch.queryCovertList(sql, CommoditySort.class, new PageParam(1, 30), params);
    }

    /**
     * 按条件查询商品
     * @param map
     * @return 商品列表
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public List<Commodity> queryCommodities(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String sql = map.get("sql").toString();
        Object[] params = null;
        return BaseJdbcSearch.queryCovertList(sql, Commodity.class, (PageParam) map.get("page"), params);
    }

    /**
     * 查询商品数量
     * @param map
     * @return 商品数量
     * @throws SQLException
     */
    @Override
    public Integer queryCommoditiesCount(Map<String, Object> map) throws SQLException {
        String sql = map.get("sql").toString();
        Object[] params = null;
        return BaseJdbcSearch.queryCount(sql, params);
    }

    /**
     * 将商品添加到购物车
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
        //String date = BaseDate.getDateString(new Date());
        /*
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT s_id FROM `cartitems` WHERE 1 ");
        Object[] params = null;
        Carts carts = BaseJdbc.queryCovertEntity(sql.toString(), Carts.class, params);
        */

        StringBuilder sql = new StringBuilder();
        Object[] params = null;
        try {
            sql.append("SELECT * FROM carts WHERE s_uuid=? AND s_cid=? ");
            params = new Object[]{uUuid, cId};
            Carts carts = BaseJdbcSearch.queryCovertEntity(sql.toString(), Carts.class,params);
            Integer sId = carts.getSId();
            System.out.println("sId>>>"+sId);
            //增加数量
            sql = new StringBuilder();
            sql.append("UPDATE carts SET s_num=? WHERE s_uuid=? AND s_cid=? ");
            params = new Object[]{sNum+1, uUuid, cId};
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
}
