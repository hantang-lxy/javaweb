package cn.kgc.tangcco.service.impl;

import cn.kgc.tangcco.dao.impl.OrderItemDaoImpl;
import cn.kgc.tangcco.kjde1021.vo.OrderItemsVo;
import cn.kgc.tangcco.service.OrderItemService;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/21 上午9:56
 */
public class OrderItemServiceImpl implements OrderItemService {
    private static OrderItemDaoImpl orderDao = new OrderItemDaoImpl();

    /**
     * 查询订单对应的商品列表
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> queryOrderItems(Map<String, Object> map) throws Exception {
        String oid = map.get("oid").toString();//,o.item_num*c.c_price as item_money
        StringBuilder sql = new StringBuilder();
        BigDecimal totalMoney = new BigDecimal(0);
        sql.append("SELECT c.c_id as item_cid,c.c_url,c.c_name,o.item_num,c.c_price,o.item_num*c.c_price as item_money FROM commodity as c,orderitems as o WHERE o.item_cid = c.c_id AND o.item_oid = ? order by o.item_cid");
        Object[] params = new Object[]{oid};
        map.put("params", params);
        map.put("sql", sql);
        List<OrderItemsVo> orderitems = null;
        try {
            orderitems = orderDao.queryOrderItems(map);
            Iterator<OrderItemsVo> it = orderitems.iterator();
            OrderItemsVo orderItemsVo = null;
            while (it.hasNext()) {
                orderItemsVo = it.next();
                totalMoney = totalMoney.add(orderItemsVo.getItemMoney());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("oid", oid);
        map.put("data", orderitems);
        map.put("tMoney", totalMoney);
        return map;
    }
}
