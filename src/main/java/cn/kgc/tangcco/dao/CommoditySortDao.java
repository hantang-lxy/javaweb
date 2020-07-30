package cn.kgc.tangcco.dao;


import cn.kgc.tangcco.kjde1021.pojo.CommoditySort;

import java.util.List;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/20 下午4:27
 */
public interface CommoditySortDao {
    /**
     * 查询CommoditySort
     *
     * @param map
     * @return CommoditySort信息
     * @throws Exception Exception
     */
     public CommoditySort selectCommoditySort(Map<String, Object> map) throws Exception;

    /**
     * 查询商品分类
     *
     * @return 返回所有商品分类对象集合，没有值则返回null
     */
    public List<CommoditySort> selectCommodityAll(String sql) throws Exception;

    CommoditySort selectCommoditySortToName(Map<String, Object> map) throws Exception;
}
