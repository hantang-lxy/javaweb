package cn.kgc.tangcco.dao.impl;

import cn.kgc.tangcco.dao.CommoditySortDao;
import cn.kgc.tangcco.kjde1021.pojo.CommoditySort;
import cn.kgc.tangcco.util.jdbc.BaseJdbc;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author ShangYanshuo
 * @version 1.0
 * @date 2020/6/14下午8:22
 */
public class CommoditySortDaoImpl implements CommoditySortDao {
    /**
     * 查询commoditySort
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public CommoditySort selectCommoditySort(Map<String, Object> map) throws Exception {
        // 获取SQL语句
        String sql = map.get("selectCommoditySortSql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Person对象
        CommoditySort commoditySort = (CommoditySort) map.get("commoditySort");
        if (!StringUtils.isEmpty(commoditySort.getSortCid().toString())) {
            params = new Object[]{
                    commoditySort.getSortCid(),
            };
        }
        // 使用工具类进行查询 返回查询结果
        return BaseJdbc.queryCovertEntity(sql, CommoditySort.class, params);
    }

    @Override
    public List<CommoditySort> selectCommodityAll(String sql) throws Exception {
        List<CommoditySort> categoryList = BaseJdbc.queryCovertList(sql, CommoditySort.class, null);
        return categoryList;
    }

    /**
     * 查询commoditySort
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public CommoditySort selectCommoditySortToName(Map<String, Object> map) throws Exception {
        // 获取SQL语句
        String sql = map.get("selectCommoditySortSql").toString();
        // 查询参数
        Object[] params = null;
        // 从map获取Person对象
        CommoditySort commoditySort = (CommoditySort) map.get("commoditySort");
        if (!StringUtils.isEmpty(commoditySort.getSortName())) {
            params = new Object[]{
                    commoditySort.getSortName()
            };
        }
        // 使用工具类进行查询 返回查询结果
        return BaseJdbc.queryCovertEntity(sql, CommoditySort.class, params);
    }
}
