package cn.kgc.tangcco.service;

import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/21 下午1:08
 */
public interface CommoditySearchService {
    /**
     * 查询商品
     * @param map
     * @return map
     * @throws Exception
     */
    public Map<String, Object> queryCommodities(Map<String, Object> map) throws Exception;
    /**
     * 将商品添加到购物车
     * @param map
     * @return map
     */
    public Map<String, Object> addToCart(Map<String, Object> map);
}
