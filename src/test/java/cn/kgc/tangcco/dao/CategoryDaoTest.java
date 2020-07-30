package cn.kgc.tangcco.dao;

import cn.kgc.tangcco.dao.impl.CategoryDaoImpl;
import cn.kgc.tangcco.kjde1021.pojo.Category;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/15  1:32
 */
public class CategoryDaoTest {
    CategoryDao cd = new CategoryDaoImpl();

    @Test
    public void selectCategory() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        String sql = null;
        List<Category> categoryList = cd.selectCategory(sql);
        System.out.println(JSON.toJSONString(categoryList));
    }

    @Test
    public void selectGoodsByCategory() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        Category category = new Category(10086);
        StringBuffer sql = new StringBuffer();
        Map<String, Object> map = new HashMap<>();
        sql.append(" select c_id,c_name,c_sort,c_price,c_sales,c_stock,c_url from commodity where 1 ");
        sql.append(" and c_sort = ? ");
        map.put("category", category);
        map.put("sql", sql);
        List<Commodity> commodityList = cd.selectGoodsByCategory(map);
        if (commodityList != null) {
            category.setCommodityList(commodityList);
        }
        System.out.println(JSON.toJSONString(commodityList));
    }

    @Test
    public void testSelectGoodsByCategory() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        Category category = new Category(10086);
        StringBuffer sql = new StringBuffer();
        Map<String, Object> map = new HashMap<>();
        sql.append(" select c_id,c_name,c_sort,c_price,c_sales,c_stock,c_url from commodity where 1 ");
        sql.append(" and c_sort = ? ");
        map.put("category", category);
        map.put("sql", sql);
        List<Commodity> commodityList = cd.selectGoodsByCategory(map);
        if (commodityList != null) {
            category.setCommodityList(commodityList);
        }
        System.out.println(JSON.toJSONString(commodityList));
    }
}