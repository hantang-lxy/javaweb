package cn.kgc.tangcco.service;

import cn.kgc.tangcco.kjde1021.pojo.Commodity;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/20 下午7:23
 */
public interface CommodityService {
    /**
     * 查询Commodity信息
     *
     * @param map
     * @return Map信息
     */
    public Map<String, Object> findGoodsById(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    /**
     * 返回降序排列10条热卖商品数据集合
     *
     * @return List<Commodity> 或者 null
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    public List<Commodity> selectHotGoods() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

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
    public List<Commodity> selectLikeGoods() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    /**
     * 将商品添加到购物车
     *
     * @param map
     * @return map
     */
    public Map<String, Object> addToCart(Map<String, Object> map);

    /**
     * 添加商品
     *
     * @param map map
     * @return 返回一个Commodity
     * @throws Exception Exception
     */
    public Map<String, Object> addCommodity(Map<String, Object> map) throws Exception;


    /**
     * 查询Commodity
     *
     * @param map map
     * @return Map
     * @throws Exception Exception
     */
    public Map<String, Object> queryCommodity(Map<String, Object> map) throws Exception;

    /**
     * 根据类型下架商品
     *
     * @param map map
     * @return Map
     * @throws Exception Exception
     */
    public Map<String, Object> soldOutOneType(Map<String, Object> map) throws Exception;

    /**
     * 根据商品名称或者id选择单件下架
     *
     * @param map map
     * @return map
     * @throws Exception Exception
     */
    public Map<String, Object> soldOutOne(Map<String, Object> map) throws Exception;

    /**
     * 下架多条商品
     *
     * @param map map
     * @return map
     * @throws SQLException SQLException
     */
    public Map<String, Object> updateBatch(Map<String, Object> map) throws SQLException, IllegalAccessException;

    /**
     * 根据类型修改商品
     *
     * @param map map
     * @return map
     * @throws Exception Exception
     */
    public Map<String, Object> updateCSortOne(Map<String, Object> map) throws Exception;

    /**
     * 根据商品名称或者id选择单件修改
     *
     * @param map Map
     * @return Map
     * @throws Exception Exception
     */
    public Map<String, Object> update_One(Map<String, Object> map) throws Exception;

    /**
     * 修改多条商品
     *
     * @param map Map
     * @return Map
     * @throws SQLException SQLException
     */
    public Map<String, Object> update_Batch(Map<String, Object> map) throws SQLException;

    /**
     * 分页 查询所有商品
     *
     * @param map Map
     * @return Map
     * @throws SQLException SQLException
     */
    public Map<String, Object> queryAlls(Map<String, Object> map) throws SQLException;

    /**
     * 查询所有商品总数
     *
     * @return Integer
     * @throws SQLException SQLException
     */
    public Integer queryCommodityCount() throws SQLException;

    /**
     * 查询所有商品数量
     *
     * @return List
     */
    public List<Commodity> queryCommodity() throws Exception;

    /**
     * 分页 查询某类型商品
     *
     * @param map Map
     * @return Map
     * @throws SQLException SQLException
     */
    public Map<String, Object> queryType(Map<String, Object> map, String commodityCId) throws SQLException;

    /**
     * 分页 查询某id商品
     *
     * @param map Map
     * @return Map
     * @throws SQLException SQLException
     */
    public Map<String, Object> queryId(Map<String, Object> map, String commodityCId) throws SQLException;

    /**
     * 分页 查询某名称商品（支持模糊）
     *
     * @param map Map
     * @return Map
     * @throws SQLException SQLException
     */
    public Map<String, Object> queryName(Map<String, Object> map, String commodityCId) throws Exception;

    List<Commodity> queryAll() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    public Map<String, Object> updateQueryId(Map<String, Object> map) throws SQLException;

    Map<String, Object> updateAll(Map<String, Object> map) throws Exception;

    /**
     * 根据商品名称或者id选择单件修改,下架
     *
     * @param map Map
     * @return Map
     * @throws Exception Exception
     */
    public Map<String, Object> updateOne(Map<String, Object> map) throws Exception;

    Map<String, Object> queryShelves(Map<String, Object> map) throws SQLException;

    Map<String, Object> updateQueryName(Map<String, Object> map);

    Map<String, Object> queryTypeShelves(Map<String, Object> map, String commodityCId) throws SQLException;

    Map<String, Object> queryIdShelves(Map<String, Object> map, String commodityCId) throws SQLException;

    Map<String, Object> queryNameShelves(Map<String, Object> map, String commodityCId) throws Exception;

    Map<String, Object> queryHome(Map<String, Object> map);
}
