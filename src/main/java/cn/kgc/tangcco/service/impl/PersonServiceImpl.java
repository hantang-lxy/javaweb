package cn.kgc.tangcco.service.impl;

import cn.kgc.tangcco.dao.PersonDao;
import cn.kgc.tangcco.dao.impl.PersonDaoImpl;
import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.kjde1021.vo.PersonVo;
import cn.kgc.tangcco.service.PersonService;
import cn.kgc.tangcco.util.jdbc.BaseJdbc;
import cn.kgc.tangcco.util.reflect.BaseReflect;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/20 下午7:29
 */
public class PersonServiceImpl implements PersonService {
    private static PersonDao pd = new PersonDaoImpl();

    /**
     * 查询Person信息
     *
     * @param map
     * @return Map信息
     */
    @Override
    public Map<String, Object> findPersonByMobileOrUsername(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        //初始化默认返回值。作为查询结果的判断，50001代表没有查询到结果
        map.put("code", 50001);
        //空字符串代表有没有查询到信息
        map.put("msg", "");
        Person person = (Person) map.get("person");
        StringBuffer sql = new StringBuffer();
        sql.append(" select u_phone,u_uuid from `user` where 1 ");
        //编写动态sql
        if (!StringUtils.isEmpty(person.getUPhone())) {
            sql.append(" and u_phone = ? ");
            System.out.println("进入手机号");
        } else if (!StringUtils.isEmpty(person.getUUuid())) {
            System.out.println("进入用户名");
            sql.append(" and u_uuid = ? ");
        } else {
            //如果查询的条件有空值直接返回，不再进数据库查询
            System.out.println("返回了" + JSON.toJSONString(person));
            return map;
        }
        //封装查询参数
        map.put("sql", sql);
        //调用持久层方法，获取查询结果封装到Person对象里面。
        person = pd.selectPersonByMobileOrUsername(map);
        System.out.println("查到的结果》》》" + JSON.toJSONString(person));
        //关闭数据库连接
        BaseJdbc.close();
        if (person != null) {
            PersonVo personVo = new PersonVo();
            personVo = (PersonVo) BaseReflect.fatherToChildWithFiled(person, personVo);
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("person", personVo);
        }
        return map;
    }

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
    @Override
    public Map<String, Object> register(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        //初始化状态码：50001，“no”都表示注册失败
        map.put("code", 50001);
        map.put("msg", "no");
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO `user` ");
        sql.append(" ( u_uuid,u_phone,u_password ) ");
        sql.append(" VALUES ");
        sql.append(" (?,?,?) ");
        map.put("sql", sql);
        pd.register(map);
        return map;
    }

    /**
     * 查询person信息
     *
     * @param map 查询参数
     * @return Person信息
     */
    @Override
    public Map<String, Object> findPersonByPerson(Map<String, Object> map) throws NoSuchMethodException, InstantiationException, SQLException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        //初始化默认返回值。作为查询结果的判断，50001代表没有查询到结果
        map.put("code", 50001);
        //空字符串代表没有查询到信息
        map.put("msg", "");
        Person person = (Person) map.get("person");
        //判空，有一个空就不进数据库查
        if (StringUtils.isEmpty(person.getUPhone()) || StringUtils.isEmpty(person.getUUuid()) || StringUtils.isEmpty(person.getUPassword())) {
            return map;
        }
        StringBuffer sql = new StringBuffer();
        sql.append(" select u_uuid,u_mail,u_image,u_phone,u_power,u_fortune,u_nickname from `user` where 1 ");
        //编写动态sql
        sql.append("  and ( u_phone = ? or u_uuid = ? ) ");
        sql.append(" AND `u_password` = ? ");
        //封装查询参数
        map.put("sql", sql);
        //调用持久层方法，获取查询结果封装到Person对象里面。
        person = pd.selectPersonByPerson(map);
        //关闭数据库连接
        BaseJdbc.close();
        map.remove("sql");
        if (person != null) {
            PersonVo personVo = new PersonVo();
            personVo = (PersonVo) BaseReflect.fatherToChildWithFiled(person, personVo);
            //修改查询参数
            map.put("code", 0);
            map.put("msg", "success");
            map.put("person", personVo);
        }
        return map;
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
        //初始化状态码：50001，“no”都表示注册失败
        map.put("code", 50001);
        map.put("msg", "no");
        StringBuilder sql = new StringBuilder();
        sql.append(" UPDATE `user` ");
        sql.append(" SET u_password = ? ");
        sql.append(" WHERE 1 ");
        sql.append(" AND u_phone = ? ");
        map.put("sql", sql);
        pd.updatePasswordByPhone(map);
        return map;
    }


}

