package cn.kgc.tangcco.dao.impl;

import cn.kgc.tangcco.dao.PersonDao;
import cn.kgc.tangcco.kjde1021.pojo.Commodity;
import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.util.jdbc.BaseJdbc;
import com.alibaba.fastjson.JSON;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/20 下午4:31
 */
public class PersonDaoImpl implements PersonDao {

    /**
     * 查询Person的手机号
     *
     * @param map
     * @return Person的信息
     */
    @Override
    public Person selectPersonByMobileOrUsername(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        //编写sql
        String sql = map.get("sql").toString();
        //查询参数,这里是个数组，不要搞错了！
        Object[] params = null;
        //从map获取Commodity对象
        Person person = (Person) map.get("person");
        //编写动态sql
        String uPhone = person.getUPhone();
        String uUuid = person.getUUuid();
        if (!StringUtils.isEmpty(uPhone)) {
            params = new Object[]{uPhone};
        } else if (!StringUtils.isEmpty(uUuid)) {
            params = new Object[]{uUuid};
        }
        //使用工具类进行查询。有可能返回null，表示未查询到结果
        person = BaseJdbc.queryCovertEntity(sql, Person.class, params);
        //返回查询结果
        return person;
    }

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
    @Override
    public Map<String, Object> register(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        QueryRunner queryRunner = null;
        try {
            // 声明参数数组
            Object[][] params = new Object[1][];
            Person person = (Person) map.get("person");
            params[0] = new Object[]{
                    person.getUUuid(),
                    person.getUPhone(),
                    person.getUPassword()
            };
            String sql = map.get("sql").toString();
            queryRunner = BaseJdbc.newUpdateIntance();
            queryRunner.batch(BaseJdbc.getConnection(), sql, params);
            BaseJdbc.commitAndClose();
            //新增数据成功
            map.put("code", 0);
            map.put("msg", "success");
            System.out.println("新增OK");
        } catch (SQLException throwables) {
            try {
                BaseJdbc.rollbackAndClose();
                System.out.println("事务回滚，新增失败！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return map;
    }

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
    @Override
    public Person selectPersonByPerson(Map<String, Object> map) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, SQLException {
        //编写sql
        String sql = map.get("sql").toString();
        //查询参数,这里是个数组，不要搞错了！
        Object[] params = null;
        //从map获取Person对象
        Person person = (Person) map.get("person");
        String uuid = person.getUUuid();
        String mobile = person.getUPhone();
        String password = person.getUPassword();
        params = new Object[]{mobile, uuid, password};
        //使用工具类进行查询。有可能返回null，表示未查询到结果
        person = BaseJdbc.queryCovertEntity(sql, Person.class, params);
        //返回查询结果
        return person;
    }

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
    @Override
    public Map<String, Object> updatePasswordByPhone(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        QueryRunner queryRunner = null;
        try {
            // 声明参数数组
            Object[][] params = new Object[1][];
            Person person = (Person) map.get("person");
            params[0] = new Object[]{
                    person.getUPassword(),
                    person.getUPhone()
            };
            String sql = map.get("sql").toString();
            queryRunner = BaseJdbc.newUpdateIntance();
            queryRunner.batch(BaseJdbc.getConnection(), sql, params);
            BaseJdbc.commitAndClose();
            //新增数据成功
            map.put("code", 0);
            map.put("msg", "success");
            System.out.println("更新OK");
        } catch (SQLException throwables) {
            try {
                BaseJdbc.rollbackAndClose();
                System.out.println("事务回滚，更新失败！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return map;
    }
}
