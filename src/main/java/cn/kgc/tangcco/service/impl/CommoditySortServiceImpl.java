package cn.kgc.tangcco.service.impl;

import cn.kgc.tangcco.dao.CommoditySortDao;
import cn.kgc.tangcco.dao.impl.CommoditySortDaoImpl;
import cn.kgc.tangcco.kjde1021.pojo.CommoditySort;
import cn.kgc.tangcco.kjde1021.vo.CommoditySortVo;
import cn.kgc.tangcco.service.CommoditySortService;
import cn.kgc.tangcco.util.jdbc.BaseJdbc;
import cn.kgc.tangcco.util.reflect.BaseReflect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/20 下午7:29
 */
public class CommoditySortServiceImpl implements CommoditySortService {
    // 实例化接口
    private static CommoditySortDao commoditySortDao = new CommoditySortDaoImpl();

    @Override
    public Map<String, Object> findPersonByCommoditySort(Map<String, Object> map) throws Exception {
        // 初始化默认返回值
        map.put("code", 50001);
        map.put("msg", "");
        CommoditySort commoditySort = (CommoditySort) map.get("commoditySort");
        StringBuilder sql = new StringBuilder();
        if (commoditySort.getSortCid() != 0) {
            sql.append("SELECT cs.sort_cid ,cs.sort_name FROM commodity_sort cs where 1 ");
            sql.append(" AND cs.sort_cid = ? ");
        }


            // 封装查询参数
            map.put("selectCommoditySortSql", sql);
            map.put("commoditySort", commoditySort);
//            map.put("commodity",commodity);
            // 调用持久层方法
            
            commoditySort = commoditySortDao.selectCommoditySort(map);
            // 关闭数据库连接
            BaseJdbc.close();
        if (commoditySort != null) {
            CommoditySortVo commoditySortVo = (CommoditySortVo) BaseReflect.fatherToChildWithFiled(commoditySort, new CommoditySortVo());
            // 修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("commoditySort", commoditySortVo);
        }
        return map;
    }
    @Override
    public List<CommoditySort> queryCommodityAll() throws Exception {
        Map<String ,Object> map = new HashMap<>();
        StringBuffer sql = new StringBuffer();
        sql.append(" select c_id,c_name,c_sort,c_price,c_sales,c_stock,c_url,c_detail1,c_detail2,c_detail3 from commodity where 1  and c_sort = ? order by ? DESC  ");
        List<CommoditySort> commodityList = commoditySortDao.selectCommodityAll(sql.toString());
        BaseJdbc.close();
        return commodityList;
    }

    @Override
    public CommoditySort findCommoditySortToName(CommoditySort commoditySort) throws Exception {
        Map<String ,Object> map = new HashMap<>();
        // 初始化默认返回值
        map.put("code", 50001);
        map.put("msg", "");
        map.put("commoditySort", commoditySort);
        StringBuilder sql = new StringBuilder();
        if (commoditySort.getSortName().length() != 0 && commoditySort.getSortName() != null) {
            sql.append("SELECT cs.sort_cid ,cs.sort_name FROM commodity_sort cs where 1 ");
            sql.append(" AND cs.sort_name = ? ");
        }

        // 封装查询参数
        map.put("selectCommoditySortSql", sql);
        // 调用持久层方法

        commoditySort = commoditySortDao.selectCommoditySortToName(map);
        // 关闭数据库连接
        BaseJdbc.close();
        if (commoditySort != null) {
            return (CommoditySortVo) BaseReflect.fatherToChildWithFiled(commoditySort, new CommoditySortVo());

        }
        return null;
    }
}
