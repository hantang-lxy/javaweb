package cn.kgc.tangcco.service;

import cn.kgc.tangcco.kjde1021.pojo.Category;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.service.impl.CategoryServiceImpl;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/16  13:33
 */
public class CategoryServiceTest {
    CategoryService cs = new CategoryServiceImpl();

    @Test
    public void selectCategoryByCategory() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        Category category = new Category(10086);
        PageParam pageParam = new PageParam();
        Map<String, Object> map = new HashMap<>();
        map.put("category", category);
        map.put("page", pageParam);
        category = cs.selectCategoryByCategory(map);
        System.out.println(JSON.toJSONString(category));
    }
}