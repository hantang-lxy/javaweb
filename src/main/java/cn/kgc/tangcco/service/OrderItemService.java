package cn.kgc.tangcco.service;

import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/21 下午1:09
 */
public interface OrderItemService {
    /**
     * 查询订单对应的商品列表
     * @param map
     * @return
     * @throws Exception
     */
    public Map<String, Object> queryOrderItems(Map<String, Object> map) throws Exception;
}
