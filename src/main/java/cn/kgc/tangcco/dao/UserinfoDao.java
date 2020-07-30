package cn.kgc.tangcco.dao;

import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.util.jdbc.BaseJdbcSearch;

import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/21 上午11:58
 */
public interface UserinfoDao {
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
    public Person findPersonByUuid(Map<String,Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    /**
     * 执行修改语句
     * @param map
     */
    public void setInfo(Map<String,Object> map);
}
