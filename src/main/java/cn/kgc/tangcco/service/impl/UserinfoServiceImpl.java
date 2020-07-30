package cn.kgc.tangcco.service.impl;

import cn.kgc.tangcco.dao.impl.UserinfoDaoImpl;
import cn.kgc.tangcco.kjde1021.pojo.Person;
import cn.kgc.tangcco.service.UserinfoService;

import java.util.Map;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/17 下午2:36
 */
public class UserinfoServiceImpl implements UserinfoService {
    private static UserinfoDaoImpl userinfoDao = new UserinfoDaoImpl();
    /**
     * 通过uuid查询Person
     * @param map
     * @return map
     * @throws Exception
     */
    @Override
    public Map<String, Object> findUserInfo(Map<String, Object> map) throws Exception {
        Person person = null;
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM user WHERE 1 ");
            sql.append(" AND u_uuid= ? ");
            Object[] params = new Object[]{map.get("uuid").toString()};
            map.put("sql", sql);
            map.put("params",params);
            person = userinfoDao.findPersonByUuid(map);
            map.put("data", person);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 执行修改密码
     * @param map
     * @return map
     * @throws Exception
     */
    @Override
    public Map<String, Object> setPassword(Map<String, Object> map) throws Exception {
        Person person = new Person();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT * FROM user WHERE 1 ");
        sql.append(" AND u_uuid= ? AND u_password= ? ");
        Object[] params = new Object[]{map.get("uuid"), map.get("pwd0")};
        map.put("sql", sql);
        map.put("params", params);
        try{
            //以下两行代码仅用来报错,若报错，则说明用户密码输入错误，程序继续运行，返回相应的信息
            person = userinfoDao.findPersonByUuid(map);
            Integer Uid = person.getUId();
        } catch(Exception e){
            e.printStackTrace();
            map.put("result","fail");
            return map;
        }
        sql = new StringBuilder();
        sql.append("UPDATE user SET u_password = ? WHERE 1 ");
        sql.append(" AND u_uuid= ? ");
        params = new Object[]{map.get("pwd1"), map.get("uuid")};
        map.put("sql",sql);
        map.put("params",params);
        try{
            userinfoDao.setInfo(map);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("result","databaseError");
        }
        map.put("result","success");
        return map;
    }

    /**
     * 修改基本信息
     * @param map
     * @return map
     * @throws Exception
     */
    @Override
    public Map<String, Object> setInfo(Map<String, Object> map) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE user SET u_image = ? ,u_nickname= ? ,u_phone = ?,u_mail = ? WHERE 1 ");
        sql.append(" AND u_uuid= ? ");
        Object[] params = new Object[]{null,map.get("nickname").toString(),map.get("mobile").toString(),map.get("email").toString(),map.get("uuid").toString()};
        map.put("sql",sql);
        map.put("params",params);
        map.put("result","success");
        try{
            userinfoDao.setInfo(map);
        }catch (Exception e){
            e.printStackTrace();
            map.put("result","fail");
        }
        return map;
    }
}
