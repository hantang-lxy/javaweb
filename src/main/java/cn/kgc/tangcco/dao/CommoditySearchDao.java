package cn.kgc.tangcco.dao;

import cn.kgc.tangcco.kjde1021.pojo.Carts;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.kjde1021.pojo.CommoditySort;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.util.jdbc.BaseJdbcSearch;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/21 上午11:59
 */
public interface CommoditySearchDao {
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
    public List<CommoditySort> querySorts(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException;

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
    public List<Commodity> queryCommodities(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException;

    /**
     * 查询商品数量
     * @param map
     * @return 商品数量
     * @throws SQLException
     */
    public Integer queryCommoditiesCount(Map<String, Object> map) throws SQLException;

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
    public void addToCart(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException;
}
