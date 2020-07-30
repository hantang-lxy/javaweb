package cn.kgc.tangcco.service.impl;


import cn.kgc.tangcco.dao.impl.OrderDaoImpl;
import cn.kgc.tangcco.kjde1021.pojo.Orders;
import cn.kgc.tangcco.kjde1021.vo.OrdersVo;
import cn.kgc.tangcco.service.OrderService;
import cn.kgc.tangcco.util.reflect.BaseReflect;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/21 上午8:24
 */
public class OrderServiceImpl implements OrderService {
    private static OrderDaoImpl orderDao = new OrderDaoImpl();

    /**
     * 查询订单
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> queryOrders(Map<String, Object> map) throws Exception {
        String uuid = map.get("uuid").toString();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT orders.o_num,orders.o_date,SUM(orderitems.item_money*orderitems.item_num) as o_money,orders.o_state FROM orders,orderitems WHERE orders.o_num = orderitems.item_oid AND orders.o_uuid = ? GROUP BY orders.o_num order by orders.o_state ");
        Object[] params = new Object[]{uuid};
        map.put("params",params);
        map.put("sql",sql);
        List<Orders> orders = null;
        List<OrdersVo> ordersVos = new ArrayList<>();
        try{
            orders = orderDao.queryOrders(map);
            OrdersVo ordersVo = null;
            ListIterator<Orders> it = orders.listIterator();
            Orders order = null;
            //遍历所有订单
            while (it.hasNext()) {
                order = it.next();
                ordersVo = (OrdersVo) BaseReflect.fatherToChildWithFiled(order, new OrdersVo());
                ordersVos.add(ordersVo);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        map.put("data",ordersVos);
        return map;
    }
}
