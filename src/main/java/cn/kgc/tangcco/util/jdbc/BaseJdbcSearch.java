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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author 李昊哲
 * @version 1.21
 * @date 2020/5/16 上午7:25
 */
public class BaseJdbcSearch {
    /**
     * 本地线程 作用与当前Connection绑定
     */
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
    private  static QueryRunner queryRunner = new QueryRunner();
    // 获取数据源
    private static DataSource ds;
    // 从类路径中加载文件
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
            // 如果获取到了Connection 则使用驱动管理器获取Connection
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
        System.out.println(sql);
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
        ;
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
        return newQueryIntance().query(getConnection(),sql, new BeanListHandler<>(clazz), params);
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
        sql += " LIMIT " +  page.getIndex() + " , " + page.getPageSize();
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
    public static <K, V, T> Map<String ,Object> queryPages(String sql, Class<T> clazz, PageParam page, Object... params) throws SQLException {
        Map<String ,Object> map = new HashMap<>();
        Number count = 0;
        map.put("count",count);
        map.put("data",new ArrayList<>());
        List list = queryList(sql,clazz,params);
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
     * @return 返回查询结果封装的javaBead对象
     * @throws SQLException SQLException
     */
    public static <T> List<T> queryCovertList(String sql, Class<T> clazz, PageParam page, Object... params) throws SQLException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchFieldException {
        sql += " LIMIT " +  page.getIndex() + " , " + page.getPageSize();
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
    public static <K, V, T> Map<String ,Object> queryConvertPages(String sql, Class<T> clazz, PageParam page, Object... params) throws SQLException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        Map<String ,Object> map = new HashMap<>();
        Number count = 0;
        map.put("count",count);
        map.put("data",new ArrayList<>());
        List list = queryCovertList(sql,clazz,params);
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
        System.out.println(sql);
        return newQueryIntance().query(getConnection(), sql, new MapHandler(), params);
    }

    /**
     * @param sql    sql
     * @param params 查收参数
     * @return 查询结果
     * @throws SQLException SQLException
     */
    public static List<Map<String, Object>> queryMapList(String sql, Object... params) throws SQLException {
        System.out.println(sql);
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
        if (Pattern.compile("^\\S+_+\\S+$").matcher(coluname).find()) {
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
     * 获取PreparedStatement
     * @param sql       sql
     * @return          PreparedStatement
     * @throws SQLException SQLException
     */
    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return getConnection().prepareStatement(sql);
    }
    /**
     * 获取PreparedStatement
     * @param sql       sql
     * @param params    参数
     * @return          PreparedStatement
     * @throws SQLException SQLException
     */
    public static PreparedStatement getPreparedStatement(String sql, Object... params) throws SQLException {
        PreparedStatement pst = getPreparedStatement(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
        }
        return pst;
    }
    /**
     * 获取PreparedStatement
     * @param conn      Connection
     * @param sql       sql
     * @param params    参数
     * @return          PreparedStatement
     * @throws SQLException SQLException
     */
    public static PreparedStatement getPreparedStatement(Connection conn,String sql, Object... params) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(sql);
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
        }
        return pst;
    }

    /**
     *
     * @param pst   PreparedStatement
     * @return      数据操作状态
     * @throws SQLException SQLException
     */
    public static int executeUpdate(PreparedStatement pst) throws SQLException {
        return executeUpdate(pst,null);
    }

    /**
     *
     * @param pst       PreparedStatement
     * @param params    参数
     * @return          数据操作状态
     * @throws SQLException SQLException
     */
    public static int executeUpdate(PreparedStatement pst, Object... params) throws SQLException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                pst.setObject(i + 1, params[i]);
            }
        }
        return pst.executeUpdate();
    }
}
