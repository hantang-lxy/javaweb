package cn.kgc.tangcco.dao;

import cn.kgc.tangcco.kjde1021.pojo.Commodity;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/14  11:32
 */
public interface CommodityDao {
    /**
     * 查询商品Commodity信息
     *
     * @param map
     * @return Commodity信息
     */
    public Commodity selectGoodsById(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    /**
     * 返回10条热卖商品数据
     *
     * @return List<Commodity> 或者 null
     */
    public List<Commodity> selectGoodsAndDesc() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

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
    public List<Commodity> selectLikeGoods() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

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
    public void addToCart(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException;

    /**
     * 添加商品
     *
     * @param map
     * @return 返回一个Commodity
     * @throws Exception Exception
     */
    public Map<String, Object> insertCommodity(Map<String, Object> map) throws Exception;


    /**
     * 查询Commodity
     *
     * @param map
     * @return
     * @throws Exception Exception
     */
    public List<Commodity> selectCommodity(Map<String, Object> map) throws Exception;

    /**
     * 根据类型删除一条记录(不需要了)
     *
     * @param map
     * @return
     * @throws Exception
     */
    public Map<String, Boolean> deleteCommodity(Map<String, Object> map) throws Exception;

    /**
     * 删除多条记录(不需要了)
     *
     * @param map
     * @return
     * @throws Exception
     */
    public List<Object> deleteCommoditys(Map<String, Object> map) throws Exception;

    /**
     * 根据类型下架商品
     *
     * @param map
     * @return
     * @throws Exception
     */
    public boolean soldOutOneType(Map<String, Object> map) throws Exception;

    /**
     * 根据商品名称或者id选择单件下架
     *
     * @param map
     * @return
     * @throws Exception
     */
    public boolean updateOne(Map<String, Object> map) throws Exception;

    /**
     * 下架多条商品
     *
     * @param map
     * @return
     * @throws SQLException
     */
    public List<Boolean> updateBatch(Map<String, Object> map) throws SQLException;

    /**
     * 根据类型修改商品
     *
     * @param map
     * @return
     * @throws Exception
     */
    public boolean updateCSortOne(Map<String, Object> map) throws Exception;

    /**
     * 根据商品名称或者id选择单件修改
     *
     * @param map
     * @return
     * @throws Exception
     */
    public boolean update_One(Map<String, Object> map) throws Exception;

    /**
     * 修改、下架多条商品
     *
     * @param map
     * @return
     * @throws SQLException
     */
    public List<Boolean> update_Batch(Map<String, Object> map) throws SQLException;

    /**
     * 查询Person信息
     *
     * @param map 查询参数
     * @return Person信息
     */
    public List<Commodity> selectCommoditys(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    /**
     * 查询Person信息
     *
     * @param map 查询参数
     * @return Person信息
     */
    public Integer selectCommodityCount(Map<String, Object> map) throws SQLException;

    /**
     * 查询所有的商品，返回一个集合
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
    public List<Commodity> selectAllCommodity(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    /**
     * 查询确定类型的数量
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
    public Integer selectTypeCount(Map<String, Object> map) throws SQLException;


    List<Commodity> selectSortCommodity(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    Integer selectCIdOrNameCount(Map<String, Object> map) throws SQLException;

    Commodity selectIdOrNameCommodity(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    List<Commodity> selectAll() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    Commodity selectIdCommodity(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    public Integer updateAll(Map<String, Object> map) throws SQLException;

    /**
     *              根据商品名称或者id选择单件下架
     * @param map
     * @return
     * @throws Exception
     */
    public Integer updateOne1(Map<String, Object> map) throws Exception;

    Integer selectCountShelves(Map<String, Object> map) throws SQLException;

    List<Commodity> selectSortShelves(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    Integer selectTypeCountShelves(Map<String, Object> map) throws SQLException;

    Integer selectCIdOrNameCountShelves(Map<String, Object> map) throws SQLException;

    Map<String, Object> selectHome(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException;
}
