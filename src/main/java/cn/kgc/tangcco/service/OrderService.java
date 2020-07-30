package cn.kgc.tangcco.service;

import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/21 下午1:10
 */
public interface OrderService {
    /**
     * 查询订单
     * @param map
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryOrders(Map<String, Object> map) throws Exception;
}
