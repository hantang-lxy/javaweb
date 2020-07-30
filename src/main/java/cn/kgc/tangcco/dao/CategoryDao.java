package cn.kgc.tangcco.dao;

import cn.kgc.tangcco.kjde1021.pojo.Category;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/15  1:02
 */
public interface CategoryDao {
    /**
     * 查询商品分类
     *
     * @return 返回所有商品分类对象集合，没有值则返回null
     */
    public List<Category> selectCategory(String sql) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    /**
     * 查询商品Category对象下面的所有商品List<Commodity>
     *
     * @param map
     * @return List<Commodity>或者null
     */
    public List<Commodity> selectGoodsByCategory(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    void addToCart(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException;
}
