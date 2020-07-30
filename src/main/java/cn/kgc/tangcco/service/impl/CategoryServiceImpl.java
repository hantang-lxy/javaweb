package cn.kgc.tangcco.service.impl;

import cn.kgc.tangcco.kjde1021.pojo.Category;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.service.CategoryService;
import cn.kgc.tangcco.util.jdbc.BaseJdbc;
import com.alibaba.fastjson.JSON;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/16  13:19
 */
public class CategoryServiceImpl implements CategoryService {

    /**
     * 获取该商品分类下面的所有商品信息
     *
     * @param map
     * @return Category
     */
    @Override
    public Category selectCategoryByCategory(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        Category category = (Category) map.get("category");
        category.setCommodityList(new ArrayList<>());
        StringBuffer sql = new StringBuffer();
        sql.append(" select c_id,c_name,c_sort,c_price,c_sales,c_stock,c_url,c_detail1,c_detail2,c_detail3 from commodity where 1  and c_sort = ? and c_stock != -1 ");
        map.put("sql", sql);
        List<Commodity> commodityList = cd.selectGoodsByCategory(map);
        BaseJdbc.close();
        if (commodityList != null) {
            //倒序输出
            Collections.reverse(commodityList);
            category.setCommodityList(commodityList);
        }
        return category;
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
}
