package cn.kgc.tangcco.dao.impl;

import cn.kgc.tangcco.kjde1021.pojo.*;
import cn.kgc.tangcco.util.jdbc.BaseJdbc;
import org.apache.commons.dbutils.QueryRunner;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CartsDaoImpl {

    //使用User的id查询用户的购物车信息
    public List<Carts> getCartsByUserId(String uuid) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        String sql = "SELECT * FROM mall.`carts` WHERE 1 AND s_uuid=?";
        Object[] params = {uuid};
        List<Carts> cartsList = new ArrayList<Carts>();
        cartsList = BaseJdbc.queryCovertList(sql, Carts.class, params);
        List<Commodity> commodityList = new ArrayList<Commodity>();
        if (cartsList != null) {
            for (Carts carts : cartsList) {
                sql = "SELECT * FROM mall.`commodity` WHERE 1 AND c_id=?";
                params = new Object[]{carts.getSCid().intValue()};
                Commodity commodity = BaseJdbc.queryCovertEntity(sql, Commodity.class, params);
                carts.setCommodity(commodity);
            }
            return cartsList;
        } else return new ArrayList<>();
    }

    //添加商品到购物车
    public Map<String, Object> addCarts(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        QueryRunner queryRunner = null;
        try {
            // 声明参数数组
            Object[][] params = new Object[1][];
            Carts carts = (Carts) map.get("carts");
            params[0] = new Object[]{
                    carts.getSUuid(),
                    carts.getSCid(),
                    carts.getSNum()
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

    //更新购物车中商品数目
    public Map<String, Object> updataCartsSnum(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        QueryRunner queryRunner = null;
        try {
            // 声明参数数组
            Object[][] params = new Object[1][];
            Carts carts = (Carts) map.get("carts");
            int newNum = carts.getSNum() + 1;
            params[0] = new Object[]{
                    newNum,
                    carts.getSId()
            };
            String sql = map.get("sql").toString();
            queryRunner = BaseJdbc.newUpdateIntance();
            System.out.println(sql);

            queryRunner.batch(BaseJdbc.getConnection(), sql, params);
            BaseJdbc.commitAndClose();
            //新增数据成功
            map.put("code", 0);
            map.put("msg", "success");
            System.out.println("修改购物车中商品数量OK");
        } catch (SQLException throwables) {
            try {
                BaseJdbc.rollbackAndClose();
                System.out.println("事务回滚，修改失败！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return map;
    }

    //删除购物车中的商品by
    public Map<String, Object> deleteCarts(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        QueryRunner queryRunner = null;
        try {
            // 声明参数数组
            Object[][] params = new Object[1][];
            Carts carts = (Carts) map.get("carts");

            params[0] = new Object[]{

                    carts.getSId()
            };
            String sql = map.get("sql").toString();
            queryRunner = BaseJdbc.newUpdateIntance();
            System.out.println(sql);

            queryRunner.batch(BaseJdbc.getConnection(), sql, params);
            BaseJdbc.commitAndClose();
            //新增数据成功
            map.put("code", 0);
            map.put("msg", "success");
            System.out.println("删除购物车中商品OK");
        } catch (SQLException throwables) {
            try {
                BaseJdbc.rollbackAndClose();
                System.out.println("事务回滚，删除失败！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return map;
    }

    public Map<String, Object> updateFortune(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        QueryRunner queryRunner = null;
        try {
            // 声明参数数组
            Object[][] params = new Object[1][];
            Person person = (Person) map.get("person");

            params[0] = new Object[]{
                    person.getUFortune(),
                    person.getUId()
            };
            String sql = map.get("sql").toString();
            queryRunner = BaseJdbc.newUpdateIntance();
            System.out.println(sql);

            queryRunner.batch(BaseJdbc.getConnection(), sql, params);
            BaseJdbc.commitAndClose();
            //新增数据成功
            map.put("code", 0);
            map.put("msg", "success");
            System.out.println("修改用户余额OK");
        } catch (SQLException throwables) {
            try {
                BaseJdbc.rollbackAndClose();
                System.out.println("事务回滚，修改用户余额失败！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return map;
    }

    public Map<String, Object> addOrder(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        QueryRunner queryRunner = null;
        try {
            // 声明参数数组
            Object[][] params = new Object[1][];
            Orders order = (Orders) map.get("order");

            params[0] = new Object[]{
                    new Date(),
                    order.getOUuid(),
                    order.getOState(),
                    order.getOReceiving(),
                    order.getOMobile(),
                    order.getOName()
            };
            String sql = map.get("sql").toString();
            queryRunner = BaseJdbc.newUpdateIntance();
            System.out.println(sql);

            queryRunner.batch(BaseJdbc.getConnection(), sql, params);
            BaseJdbc.commitAndClose();
            //新增数据成功
            map.put("code", 0);
            map.put("msg", "success");
            System.out.println("新增订单OK");
        } catch (SQLException throwables) {
            try {
                BaseJdbc.rollbackAndClose();
                System.out.println("事务回滚，新增订单失败！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return map;
    }

    public Map<String, Object> addOrderItems(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        QueryRunner queryRunner = null;
        try {
            // 声明参数数组
            Object[][] params = new Object[1][];
            OrderItems orderItems = (OrderItems) map.get("orderItems");

            params[0] = new Object[]{
                    orderItems.getItemCid(),
                    orderItems.getItemMoney(),
                    orderItems.getItemNum()
            };
            String sql = map.get("sql").toString();
            queryRunner = BaseJdbc.newUpdateIntance();
            System.out.println(sql);

            queryRunner.batch(BaseJdbc.getConnection(), sql, params);
            BaseJdbc.commitAndClose();
            //新增数据成功
            map.put("code", 0);
            map.put("msg", "success");
            System.out.println("新增订单明细OK");
        } catch (SQLException throwables) {
            try {
                BaseJdbc.rollbackAndClose();
                System.out.println("事务回滚，新增订单明细失败！");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwables.printStackTrace();
        }
        return map;
    }

    public Map<String, Object> checkstockByCid(Map<String, Object> map) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        Commodity commodity = (Commodity) map.get("commodity");
        Object sql = map.get("sql");
        Integer cId = commodity.getCId();
        Object[] params = {cId};
        commodity = BaseJdbc.queryCovertEntity(sql.toString(), Commodity.class, params);
        if (commodity != null) {
            map.put("code", "0");
            map.put("commodity", commodity);
        }
        return map;
    }

}


