package cn.kgc.tangcco.dao.impl;

import cn.kgc.tangcco.dao.UserinfoDao;
import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.util.jdbc.BaseJdbcSearch;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/17 下午3:28
 */
public class UserinfoDaoImpl implements UserinfoDao {
    /**
     * 通过uuid查询用户
     * @param map
     * @return Person对象
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    @Override
    public Person findPersonByUuid(Map<String,Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        String sql = map.get("sql").toString();
        Object[] params = (Object[])map.get("params");
        return BaseJdbcSearch.queryCovertEntity(sql, Person.class, params);
    }

    /**
     * 执行修改语句
     * @param map
     */
    @Override
    public void setInfo(Map<String,Object> map){
        String sql = map.get("sql").toString();
        Object[] params = (Object[])map.get("params");
        try {
            BaseJdbcSearch.startTransaction();
            PreparedStatement pst = BaseJdbcSearch.getPreparedStatement(sql);
            BaseJdbcSearch.executeUpdate(pst, params);
            BaseJdbcSearch.commitAndClose();
        } catch (SQLException throwables) {
            try {
                BaseJdbcSearch.rollbackAndClose();
                System.out.println("事务回滚！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
    }
}