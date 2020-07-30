package cn.kgc.tangcco.dao;

import cn.kgc.tangcco.kjde1021.pojo.Person;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/20 下午4:27
 */
public interface PersonDao {
    /**
     * 查询Person的手机号或者用户名
     *
     * @param map
     * @return Person的信息
     */
    public Person selectPersonByMobileOrUsername(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    /**
     * 注册信息写入数据库
     *
     * @param map
     * @return Map<String, Object>
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    public Map<String, Object> register(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    /**
     * 查询正在登录的用户
     *
     * @param map
     * @return Person或者null
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws NoSuchFieldException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException
     */
    public Person selectPersonByPerson(Map<String, Object> map) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, SQLException;

    /**
     * 通过手机号更新密码
     *
     * @param map
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     * @throws InvocationTargetException
     * @throws NoSuchFieldException
     */
    public Map<String, Object> updatePasswordByPhone(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

}
