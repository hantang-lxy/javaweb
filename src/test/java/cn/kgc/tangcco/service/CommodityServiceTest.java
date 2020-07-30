package cn.kgc.tangcco.service;

import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.service.impl.CommodityServiceImpl;
import cn.kgc.tangcco.util.exciption.ProcedureException;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/14  13:11
 */
public class CommodityServiceTest {

    CommodityService cs = new CommodityServiceImpl();

    @Test
    public void findGoodsById() {
        try {
            Commodity commodity = new Commodity(2);
            Map<String, Object> map = new HashMap<>();
            map.put("commodity", commodity);
            map = cs.findGoodsById(map);
            System.out.println(JSON.toJSONString(map));
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | SQLException | InvocationTargetException | NoSuchFieldException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void queryHome() {
        try {
            Map<String, Object> map = new HashMap<>();
            System.out.println("进入了queryHome");
            Map<String, Object> queryHomeMap = cs.queryHome(map);
                    System.out.println(queryHomeMap.get("userCount"));
                    System.out.println(queryHomeMap.get("untreated"));
                    System.out.println(queryHomeMap.get("isShelves"));
                    System.out.println(queryHomeMap.get("deal"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}