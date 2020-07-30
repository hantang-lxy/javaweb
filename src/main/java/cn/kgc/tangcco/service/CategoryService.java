package cn.kgc.tangcco.service;

import cn.kgc.tangcco.dao.CategoryDao;
import cn.kgc.tangcco.dao.impl.CategoryDaoImpl;
import cn.kgc.tangcco.kjde1021.pojo.Category;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.util.jdbc.BaseJdbc;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/15  1:02
 */
public interface CategoryService {
    final static CategoryDao cd = new CategoryDaoImpl();

    /**
     * 查询商品分类
     *
     * @return 返回所有商品分类对象集合，没有值则返回null
     */
    public static List<Category> queryCategory() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        String sql = " SELECT cs.sort_cid AS scid,cs.sort_name AS name,count(c_id) AS count from commodity AS c INNER JOIN commodity_sort AS cs ON c.c_sort=cs.sort_cid and c.c_stock != -1 GROUP BY c.c_sort ";
        List<Category> categories = cd.selectCategory(sql);
        BaseJdbc.close();
        return categories;
    }

    /**
     * 获取该商品分类下面的所有商品信息
     *
     * @param map
     * @return Category
     */
    public Category selectCategoryByCategory(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    Map<String, Object> addToCart(Map<String, Object> map);
}
