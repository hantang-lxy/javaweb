package cn.kgc.tangcco.dao.impl;

import cn.kgc.tangcco.dao.OrderDao;
import cn.kgc.tangcco.kjde1021.pojo.Orders;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.util.jdbc.BaseJdbcSearch;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/21 上午8:58
 */
public class OrderDaoImpl implements OrderDao {
    /**
     * 查询用户所有订单
     * @param map
     * @return 订单列表
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public List<Orders> queryOrders(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String sql = map.get("sql").toString();
        Object[] params = (Object[])map.get("params");
        return BaseJdbcSearch.queryCovertList(sql, Orders.class, new PageParam(1,200), params);
    }
}
