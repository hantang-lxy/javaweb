package cn.kgc.tangcco.util.jdbc;

import cn.kgc.tangcco.model.PageParam;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import static java.util.regex.Pattern.compile;

/**
 * @author 李雪阳
 * @version 1.3
 * @date 2020/5/16 上午7:25
 */
public class BaseJdbc {
    /**
     * 本地线程 作用与当前Connection绑定
     */
    private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    private static final QueryRunner queryRunner = new QueryRunner();
    /**
     * 获取数据源
     */
    private static DataSource ds;
    /**
     * 从类路径中加载文件
     */
    private static Properties properties = null;

    static {
        try {
            properties = PropertiesLoaderUtils.loadAllProperties("druid.properties");
            ds = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取数据源
     *
     * @return 数据源
     */
    public static DataSource getDataSource() throws Exception {
        return ds;
    }

    /**
     * 获取数据库连接
     *
     * @return Connection
     * @throws SQLException SQLException
     */
    public static Connection getConnection() throws SQLException {
        // 获取当前线程中的Connection
        Connection conn = threadLocal.get();
        if (conn != null) {
            // 如果获取到了Connection 返回获取到的Connection
            return conn;
        } else {
            // 如果没有获取到了Connection 则使用驱动管理器获取Connection
            conn = ds.getConnection();
            // 将使用驱动管理器获取Connection与当前线程绑定
            threadLocal.set(conn);
            // 返回Connection
            return conn;
        }
    }

    /**
     * 开启事务
     *
     * @throws SQLException SQLException
     */
    public static void startTransaction() throws SQLException {
        // 关闭当前连接的自动提交功能
        getConnection().setAutoCommit(false);
    }

    /**
     * QueryRunner对象 手动管理事务事务
     *
     * @return QueryRunner对象
     * @throws SQLException SQLException
     */
    public static QueryRunner newUpdateIntance() throws SQLException {
        startTransaction();
        return queryRunner;
    }

    /**
     * QueryRunner对象 自动管理事务
     *
     * @return QueryRunner对象
     * @throws SQLException SQLException
     */
    public static QueryRunner newQueryIntance() throws SQLException {
        return queryRunner;
    }

    /**
     * 查询一条记录
     *
     * @param sql    sql
     * @param clazz  javaBean的Class对象
     * @param params 参数数组
     * @param <T>    返回值数据类型
     * @return 返回查询结果封装的javaBead对象
     * @throws SQLException SQLException
     */
    public static <T> T queryEntity(String sql, Class<T> clazz, Object... params) throws SQLException {
//        System.out.println(sql);
        return newQueryIntance().query(getConnection(), sql, new BeanHandler<>(clazz), params);
    }

    /**
     * 查询一条记录
     *
     * @param sql    sql
     * @param clazz  javaBean的Class对象
     * @param params 参数数组
     * @param <T>    返回值数据类型
     * @return 返回查询结果封装的javaBead对象
     * @throws SQLException SQLException
     */
    public static <T> T queryCovertEntity(String sql, Class<T> clazz, Object... params) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        Map<String, Object> map = queryMap(sql, params);
        if (map == null || map.isEmpty()) {
            return null;
        }
        T instance = clazz.getConstructor().newInstance();
        Set<String> set = map.keySet();
        Iterator<String> it = set.iterator();
        String key = null;
        while (it.hasNext()) {
            key = it.next();
            Field declaredField = clazz.getDeclaredField(nameInDb2nameInJava(key));
            // 设置属性可访问权限
            declaredField.setAccessible(true);
            declaredField.set(instance, map.get(key));
        }
        return instance;
    }

    /**
     * 查询多条记录
     *
     * @param sql    sql
     * @param clazz  javaBean的Class对象
     * @param params 参数数组
     * @param <T>    返回值数据类型
     * @return 返回查询结果封装的javaBead对象的集合
     * @throws SQLException SQLException
     */
    public static <T> List<T> queryList(String sql, Class<T> clazz, Object... params) throws SQLException {
        System.out.println(sql);
        return newQueryIntance().query(getConnection(), sql, new BeanListHandler<>(clazz), params);
    }

    /**
     * 查询多条记录
     *
     * @param <T>    返回值数据类型
     * @param sql    sql
     * @param clazz  javaBean的Class对象
     * @param page   分页参数
     * @param params 参数数组
     * @return 返回查询结果封装的javaBead对象的集合
     * @throws SQLException SQLException
     */
    public static <T> List<T> queryList(String sql, Class<T> clazz, PageParam page, Object... params) throws SQLException {
        sql += " LIMIT " + page.getIndex() + " , " + page.getPageSize();
        return queryList(sql, clazz, params);
    }

    /**
     * 分页查收
     *
     * @param sql    sql
     * @param clazz  javaBean的Class对象
     * @param page   分页参数
     * @param params 参数数组
     * @param <T>    返回值数据类型
     * @return 返回查询结果封装的javaBead对象的集合
     * @throws SQLException SQLException
     */
    public static <K, V, T> Map<String, Object> queryPages(String sql, Class<T> clazz, PageParam page, Object... params) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        Number count = 0;
        map.put("count", count);
        map.put("data", new ArrayList<>());
        List list = queryList(sql, clazz, params);
        if (list != null && list.size() > 0) {
            // Number count = queryEntity("SELECT FOUND_ROWS()", Number.class, null);
            // sql += " LIMIT " +  page.getIndex() + " , " + page.getPageSize();
            count = list.size();
            list = list.subList(page.getIndex(), page.getIndex() + page.getPageSize());
            map.put("count", count);
            map.put("data", list);
        }
        return map;
    }

    /**
     * 查询多条记录
     *
     * @param sql    sql
     * @param clazz  javaBean的Class对象
     * @param params 参数数组
     * @param <T>    返回值数据类型
     * @return 返回查询结果封装的javaBead对象
     * @throws SQLException SQLException
     */
    public static <T> List<T> queryCovertList(String sql, Class<T> clazz, Object... params) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        List<T> list = new ArrayList<T>();
        List<Map<String, Object>> mapList = queryMapList(sql, params);
        if (mapList == null || mapList.isEmpty()) {
            return null;
        }
        ListIterator<Map<String, Object>> mapListIterator = mapList.listIterator();
        Map<String, Object> map = null;
        T instance = null;
        while (mapListIterator.hasNext()) {
            map = mapListIterator.next();
            instance = clazz.getConstructor().newInstance();
            Set<String> set = map.keySet();
            Iterator<String> it = set.iterator();
            String key = null;
            while (it.hasNext()) {
                key = it.next();
                Field declaredField = clazz.getDeclaredField(nameInDb2nameInJava(key));
                // 设置属性可访问权限
                declaredField.setAccessible(true);
                declaredField.set(instance, map.get(key));
            }
            list.add(instance);
        }

        return list;
    }

    /**
     * 查询多条记录
     *
     * @param sql    sql
     * @param clazz  javaBean的Class对象
     * @param page   分页参数
     * @param params 参数数组
     * @param <T>    返回值数据类型
     * @return 返回查询结果封装的javaBead对象，未查询到内容返回null
     * @throws SQLException SQLException
     */
    public static <T> List<T> queryCovertList(String sql, Class<T> clazz, PageParam page, Object... params) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        sql += " LIMIT " + page.getIndex() + " , " + page.getPageSize();
        List<T> list = new ArrayList<T>();
        List<Map<String, Object>> mapList = queryMapList(sql, params);
        if (mapList == null || mapList.isEmpty()) {
            return null;
        }
        ListIterator<Map<String, Object>> mapListIterator = mapList.listIterator();
        Map<String, Object> map = null;
        T instance = null;
        while (mapListIterator.hasNext()) {
            map = mapListIterator.next();
            instance = clazz.getConstructor().newInstance();
            Set<String> set = map.keySet();
            Iterator<String> it = set.iterator();
            String key = null;
            while (it.hasNext()) {
                key = it.next();
                Field declaredField = clazz.getDeclaredField(nameInDb2nameInJava(key));
                // 设置属性可访问权限
                declaredField.setAccessible(true);
                declaredField.set(instance, map.get(key));
            }
            list.add(instance);
        }

        return list;
    }

    /**
     * 分页查收
     *
     * @param sql    sql
     * @param clazz  javaBean的Class对象
     * @param page   分页参数
     * @param params 参数数组
     * @param <T>    返回值数据类型
     * @return 返回查询结果封装的javaBead对象的集合
     * @throws SQLException SQLException
     */
    public static <K, V, T> Map<String, Object> queryConvertPages(String sql, Class<T> clazz, PageParam page, Object... params) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Map<String, Object> map = new HashMap<>();
        Number count = 0;
        map.put("count", count);
        map.put("data", new ArrayList<>());
        List list = queryCovertList(sql, clazz, params);
        if (list != null && list.size() > 0) {
            // Number count = queryEntity("SELECT FOUND_ROWS()", Number.class, null);
            // sql += " LIMIT " +  page.getIndex() + " , " + page.getPageSize();
            count = list.size();
            list = list.subList(page.getIndex(), page.getIndex() + page.getPageSize());
            map.put("count", count);
            map.put("data", list);
        }
        return map;
    }

    /**
     * @param sql    sql
     * @param params 查收参数
     * @return 查询结果
     * @throws SQLException SQLException
     */
    public static int queryCount(String sql, Object... params) throws SQLException {
        System.out.println(sql);
        return newQueryIntance().query(getConnection(), sql, new ScalarHandler<Number>(), params).intValue();
    }

    /**
     * @param sql    sql
     * @param params 查收参数
     * @return 查询结果
     * @throws SQLException SQLException
     */
    public static Map<String, Object> queryMap(String sql, Object... params) throws SQLException {
//        System.out.println(sql);
        return newQueryIntance().query(getConnection(), sql, new MapHandler(), params);
    }

    /**
     * @param sql    sql
     * @param params 查收参数
     * @return 查询结果
     * @throws SQLException SQLException
     */
    public static List<Map<String, Object>> queryMapList(String sql, Object... params) throws SQLException {
//        System.out.println(sql);
        return newQueryIntance().query(getConnection(), sql, new MapListHandler(), params);
    }


    /**
     * 数据库里下划线命名规则转化为java里面驼峰式命名
     *
     * @param filedName 字段名称
     * @return
     */
    public static String nameInDb2nameInJava(String filedName) {
        String coluname = filedName.toLowerCase();
        //正则
        if (compile("^\\S+_+\\S+$").matcher(coluname).find()) {
            char[] ca = coluname.toCharArray();
            for (int j = 1; j < ca.length - 1; j++) {
                if (ca[j] == '_') {
                    ca[j] = '\0';
                    ca[j + 1] = Character.toUpperCase(ca[j + 1]);
                }
            }
            coluname = new String(ca);
        }
        return coluname.replaceAll("\0", "");
    }

    /**
     * 事务提交 关闭连接
     *
     * @throws SQLException SQLException
     */
    public static void commitAndClose() throws SQLException {
        DbUtils.commitAndClose(getConnection());
        threadLocal.remove();
    }

    /**
     * 关闭连接
     *
     * @throws SQLException SQLException
     */
    public static void close() throws SQLException {
        DbUtils.close(getConnection());
        threadLocal.remove();
    }

    /**
     * 事务回滚 关闭连接
     *
     * @throws SQLException SQLException
     */
    public static void rollbackAndClose() throws SQLException {
        DbUtils.rollbackAndClose(getConnection());
        threadLocal.remove();
    }

    /**
     * 插入一条记录
     *
     * @param sql    sql
     * @param params 参数数组
     * @return 返回插入结果 true 成功 false 失败
     * @throws SQLException SQLException
     */
    public static Map<String, Object> addedEntity(String sql, Object... params) throws SQLException {
        int up = newQueryIntance().update(getConnection(), sql, params);
        Map<String, Object> map = new HashMap<>();
        if (up > 0) {
            map.put("isOk", true);
        } else {
            map.put("isOk", false);
        }
        return map;
    }

    /**
     * 删除一条记录
     */
    public static Map<String, Boolean> deleteEntity(String sql, Object... params) throws SQLException {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        int isDelete = newQueryIntance().update(getConnection(), sql, params);
        map.put("isDelete", isDelete > 0);
        return map;
    }

    /**
     * 将多条记录封装到List集合中
     *
     * @param sql    sql
     * @param params 查收参数
     * @return 查询结果
     * @throws SQLException SQLException
     */
    public static int[] deleteMapList(String sql, Object... params) throws SQLException {
        int[] update = new int[params.length];
        if (params.length == 1) {
            update[0] = newQueryIntance().update(getConnection(), sql, params);
        } else {
            for (int i = 0; i < params.length; i++) {
                update[i] = newQueryIntance().update(getConnection(), sql, params[i]);
            }
        }

        return update;
    }

    /**
     * 删除多条记录
     *
     * @param sql    sql
     * @param params 参数数组
     * @param <T>    返回值数据类型
     * @return 返回查询结果封装的javaBead对象，未查询到内容返回null
     * @throws SQLException SQLException
     */
    public static <T> List<Boolean> deleteCovertList(String sql, Object... params) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        List<Boolean> list = new ArrayList<Boolean>();
        int[] deleteMapList = deleteMapList(sql, params);
        for (int i = 0; i < deleteMapList.length; i++) {
            list.add(deleteMapList[i] > 0);
        }
        return list;
    }

    /**
     * 修改商品
     *
     * @param sql    SQL语句
     * @param params SQL参数
     * @param <T>    T
     * @return 返回map类型数据
     * @throws SQLException SQLException
     */
    public static <T> boolean updateEntity1(String sql, Object... params) throws SQLException {
        Boolean b = null;
        int update = updateMessage(sql, params);
        if (update > 0) {
            b = true;
        } else {
            b = false;
        }
        return b;
    }

    /**
     * 修改商品
     *
     * @param sql    SQL语句
     * @param params SQL参数
     * @param <T>    T
     * @return 返回map类型数据
     * @throws SQLException SQLException
     */
    public static <T> int updateEntity(String sql, Object... params) throws SQLException {
        int a = updateMessage(sql, params);
        return a;
    }


    /**
     * 修改一条记录
     */

    /**
     * 修改单件商品
     *
     * @param sql    SQL语句
     * @param params SQL参数
     * @param <T>    T
     * @return 数值大于0代表修改成功，否则失败
     * @throws SQLException SQLException
     */
    public static <T> int updateMessage(String sql, Object... params) throws SQLException {
        int update = newQueryIntance().update(getConnection(), sql, params);
        return update;
    }

    /**
     * 修改商品
     */
    public static List<Object> updatecNameOrcId(String sql, Object... params) throws SQLException {
        List<Object> list = new ArrayList<>();
        int update = updateMessage(sql, params);
        list.add(update > 0);
        return list;
    }

    /**
     * 删除多条记录
     *
     * @param sql sql
     * @return 返回查询结果封装的javaBead对象，未查询到内容返回null
     * @throws SQLException SQLException
     */
    public static Boolean updateBatchList(String sql, Object... params) throws SQLException {
        int updateList = updateMapList(sql, params);
        if (updateList > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param sql    sql
     * @param params 查收参数
     * @return 查询结果
     * @throws SQLException SQLException
     */
    private static int updateMapList(String sql, Object... params) throws SQLException {
        return newQueryIntance().update(getConnection(), sql, params);
    }

    public static <T> List<Object[]> selectAll(String sql) throws SQLException {
        return newQueryIntance().query(getConnection(), sql, new ArrayListHandler());
    }


}