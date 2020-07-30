package cn.kgc.tangcco.dao;

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
 * @date 2020/6/21 上午11:59
 */
public interface OrderDao {
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
    public List<Orders> queryOrders(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException;
}
