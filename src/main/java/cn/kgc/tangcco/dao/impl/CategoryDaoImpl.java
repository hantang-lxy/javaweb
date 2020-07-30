package cn.kgc.tangcco.dao.impl;

import cn.kgc.tangcco.dao.CategoryDao;
import cn.kgc.tangcco.kjde1021.pojo.Carts;
import cn.kgc.tangcco.kjde1021.pojo.Category;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.model.PageParam;
import cn.kgc.tangcco.util.jdbc.BaseJdbc;
import cn.kgc.tangcco.util.jdbc.BaseJdbcSearch;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/15  1:02
 */
public class CategoryDaoImpl implements CategoryDao {

    /**
     * 查询商品分类
     *
     * @return 返回所有商品分类对象集合，没有值则返回null
     */
    @Override
    public List<Category> selectCategory(String sql) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        List<Category> categoryList = BaseJdbc.queryCovertList(sql, Category.class, null);
        return categoryList;
    }

    /**
     * 查询商品Category对象下面的所有商品
     *
     * @param map
     * @return Category信息或者null
     */
    @Override
    public List<Commodity> selectGoodsByCategory(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        Category category = (Category) map.get("category");
        Integer scid = category.getScid();
        Object[] params = new Object[]{scid};
        String sql = map.get("sql").toString();
        PageParam page = (PageParam) map.get("page");
        List<Commodity> commodityList = BaseJdbc.queryCovertList(sql, Commodity.class, page, params);
        return commodityList;
    }

    /**
     * 加入购物车
     *
     * @param map
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @Override
    public void addToCart(Map<String, Object> map) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        String uUuid = map.get("uUuid").toString();
        String cId = map.get("cId").toString();
        Integer sNum = Integer.parseInt(map.get("cNum").toString());
        StringBuilder sql = new StringBuilder();
        Object[] params = null;
        try {
            sql.append("SELECT * FROM carts WHERE s_uuid=? AND s_cid=? ");
            params = new Object[]{uUuid, cId};
            Carts carts = BaseJdbcSearch.queryCovertEntity(sql.toString(), Carts.class, params);
            Integer sId = carts.getSId();
            System.out.println("sId>>>" + sId);
            //增加数量
            sql = new StringBuilder();
            sql.append("UPDATE carts SET s_num=? WHERE s_uuid=? AND s_cid=? ");
            params = new Object[]{sNum + 1, uUuid, cId};
            BaseJdbcSearch.startTransaction();
            PreparedStatement pst = BaseJdbcSearch.getPreparedStatement(sql.toString());
            BaseJdbcSearch.executeUpdate(pst, params);
            BaseJdbcSearch.commitAndClose();
        } catch (Exception throwables) {
            try {
                BaseJdbcSearch.rollbackAndClose();
                //添加记录
                System.out.println("事务回滚！");
                sql = new StringBuilder();
                sql.append("INSERT INTO carts(s_uuid,s_cid,s_num)");
                sql.append(" VALUES(?,?,?);");
                params = new Object[]{uUuid, cId, sNum};
                BaseJdbcSearch.startTransaction();
                PreparedStatement pst = BaseJdbcSearch.getPreparedStatement(sql.toString());
                BaseJdbcSearch.executeUpdate(pst, params);
                BaseJdbcSearch.commitAndClose();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
    }
}
