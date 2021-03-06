package cn.kgc.tangcco.dao;

import cn.kgc.tangcco.kjde1021.vo.OrderItemsVo;
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
public interface OrderItemDao {
    /**
     * 查询某个订单编号对应的所有商品信息
     * @param map
     * @return 订单对应商品集合
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public List<OrderItemsVo> queryOrderItems(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException;
}
