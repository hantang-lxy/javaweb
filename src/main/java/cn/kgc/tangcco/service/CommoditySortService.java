package cn.kgc.tangcco.service;


import cn.kgc.tangcco.kjde1021.pojo.CommoditySort;

import java.util.List;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/20 下午7:23
 */
public interface CommoditySortService {
    /**
     *              查询CommoditySort
     * @param map
     * @return      CommoditySort信息
     * @throws Exception Exception
     */
    public Map<String ,Object> findPersonByCommoditySort(Map<String , Object> map) throws Exception;


    List<CommoditySort> queryCommodityAll() throws Exception;

    CommoditySort findCommoditySortToName(CommoditySort commoditySort) throws Exception;
}
