package cn.kgc.tangcco.service;

import cn.kgc.tangcco.kjde1021.pojo.Person;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/20 下午7:23
 */
public interface PersonService {
    /**
     * 查询Person信息
     *
     * @param map
     * @return Map信息
     */
    public Map<String, Object> findPersonByMobileOrUsername(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException;

    /**
     * 将注册信息写入数据库
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
     * 查询person信息
     *
     * @param map 查询参数
     * @return Person信息
     */
    public Map<String, Object> findPersonByPerson(Map<String, Object> map) throws NoSuchMethodException, InstantiationException, SQLException, IllegalAccessException, InvocationTargetException, NoSuchFieldException;

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
