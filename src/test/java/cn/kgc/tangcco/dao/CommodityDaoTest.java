package cn.kgc.tangcco.dao;

import cn.kgc.tangcco.dao.impl.CommodityDaoImpl;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.kjde1021.pojo.OrderItems;
import cn.kgc.tangcco.kjde1021.pojo.Orders;
import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.kjde1021.vo.CommodityVo;
import cn.kgc.tangcco.kjde1021.vo.PersonVo;
import cn.kgc.tangcco.util.jdbc.BaseJdbc;
import cn.kgc.tangcco.util.reflect.BaseReflect;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/14  12:11
 */
public class CommodityDaoTest {
    CommodityDao cd = new CommodityDaoImpl();

    @Test
    public void findGoodsById() throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        Commodity commodity = new Commodity(2);
        Map<String, Object> map = new HashMap<>();
        map.put("commodity", commodity);
        //初始化默认返回值。作为查询结果的判断，50001代表没有查询到结果
        map.put("code", 50001);
        //空字符串代表有没有查询到信息
        map.put("msg", "");
        commodity = (Commodity) map.get("commodity");
        StringBuffer sql = new StringBuffer();
        sql.append(" select c_name,c_sort,c_price,c_sales,c_stock,c_url from commodity where 1 ");
        //编写动态sql
        if (commodity.getCId() != null) {
            sql.append(" and c_id = ? ");
        } else {
            //如果查询的条件有空值直接返回，不再进数据库查询
            return;
        }
        //封装查询参数
        map.put("sql", sql);
        //调用持久层方法，获取查询结果封装到Person对象里面。
        commodity = cd.selectGoodsById(map);
        //关闭数据库连接
        BaseJdbc.close();
        if (commodity != null) {
            CommodityVo commodityVo = new CommodityVo();
            commodityVo = (CommodityVo) BaseReflect.fatherToChildWithFiled(commodity, commodityVo);
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("commodity", commodityVo);
        }
        System.out.println(JSON.toJSONString(map));
        return;
    }

    @Test
    public void queryHome() {
        Map<String, Object> map = new HashMap<>();
        // 存储返回结果
        Map<String, Object> commodityMap = new HashMap<>();
        commodityMap.put("code", "50001");
        //空字符串代表有没有查询到信息
        commodityMap.put("msg", "");
        try {
                map = cd.selectHome(map);
            // 用户数量
            Integer userCount = (Integer)map.get("userCount");
            commodityMap.put("userCount",userCount);
            //商品是否处理
            int undoneCount = 0;//未完成
            int doneCount = 0;//已完成
            int noShipped =0;//未发货
            int shipped =0;//已发货
            List<Orders> untreatedCount = (List<Orders>)map.get("untreatedCount");
            for (int i = 0; i < untreatedCount.size(); i++) {
                if (untreatedCount.get(i).getOState() == 1){
                    undoneCount++;
                }else if (untreatedCount.get(i).getOState() == 2){
                    doneCount++;
                }else if (untreatedCount.get(i).getOState() == 3){
                    noShipped++;
                }else{
                    shipped++;
                }
            }
            Integer[] untreated = new Integer[]{undoneCount,doneCount,noShipped,shipped};
            commodityMap.put("untreated",untreated);

            //商品统计
            Integer notShelves = 0;//未下架
            Integer shelves = 0;//已下架
            List<Commodity> commodityList = (List<Commodity>)map.get("statisticsCount");
            for (int i = 0; i < commodityList.size(); i++) {
                if (commodityList.get(i).getCStock() != -1){
                    notShelves++;
                }else{
                    shelves++;
                }
            }
            Integer[] isShelves = new Integer[]{notShelves,shelves};
            commodityMap.put("isShelves",isShelves);

            //查询所有完成的商品
            List<Orders> orders = (List<Orders>) map.get("Orders");
            //交易记录
            Integer deal = 0;//交易记录
            List<OrderItems> orderItemsList = (List<OrderItems>) map.get("dealCount");
            for (int i = 0; i < orderItemsList.size(); i++) {
                for (int j = 0; j < orders.size(); j++) {
                    if (orders.get(j).getONum().intValue() == orderItemsList.get(i).getItemOid().intValue()) {
                        deal+=orderItemsList.get(i).getItemMoney().intValue();
                    }
                }
            }
            commodityMap.put("deal",deal);
            //参数意义分别为：当前页、总页数、每屏展示的页数
            commodityMap.put("code", 0);
            commodityMap.put("msg", "success");
                BaseJdbc.close();
        }catch (Exception throwables) {
            throwables.printStackTrace();
        } finally {
            // 关闭连接
            // 返回结果
            System.out.println(commodityMap.get("code"));
        }

    }
}