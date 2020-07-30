package cn.kgc.tangcco.service.impl;

import cn.kgc.tangcco.dao.impl.CartsDaoImpl;
import cn.kgc.tangcco.kjde1021.pojo.Carts;
import cn.kgc.tangcco.kjde1021.pojo.OrderItems;
import cn.kgc.tangcco.kjde1021.pojo.Orders;
import cn.kgc.tangcco.kjde1021.pojo.Person;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class CartsService {
    private static CartsDaoImpl cartsDao = new CartsDaoImpl();

    //获取用户的购物车
    public List<Carts> getCartsByUuid(String uuid) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        return cartsDao.getCartsByUserId(uuid);
    }

    //添加商品到购物车
    public int addCarts(String uuid,int cid) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        //查询该用户的购物车
        List<Carts> cartsByUserId = cartsDao.getCartsByUserId(uuid);
        Carts carts = new Carts();

        HashMap<String, Object> HashMap = new HashMap<>();
        for (Carts carts1 : cartsByUserId
                ) {
            //购物车中已经有此商品，修改其数量+1
            if (carts1.getSCid()==cid){
                HashMap.put("sql","UPDATE `mall`.`carts` SET `s_num` = ? WHERE `s_id` = ?");
                HashMap.put("carts",carts1);
                return (int)cartsDao.updataCartsSnum(HashMap).get("code");
            }
        }
        carts.setSNum(1);
        carts.setSUuid(uuid);
        carts.setSCid(cid);
        HashMap.put("sql","INSERT INTO `mall`.`carts` (`s_id`, `s_uuid`, `s_cid`, `s_num`) VALUES (NULL, ?, ?, ?)");
        HashMap.put("carts",carts);
        return (int)cartsDao.addCarts(HashMap).get("code");
    }
    //删除用户购物车中的商品
    public int deleteCarts(int cartsId) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        Carts carts = new Carts(cartsId);
        HashMap<String, Object> HashMap = new HashMap<>();
        HashMap.put("sql","DELETE FROM `mall`.`carts` WHERE `s_id` = ?");
        HashMap.put("carts",carts);
        return (int) cartsDao.deleteCarts(HashMap).get("code");
    }
    //修改用户的余额
    public int updateFortune(Person person) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        HashMap<String, Object> HashMap = new HashMap<>();
        HashMap.put("sql","UPDATE `mall`.`user` SET `u_fortune` = ? WHERE `u_id` =?");
        HashMap.put("person",person);
        return (int) cartsDao.updateFortune(HashMap).get("code");
    }
    public int addOrders(Orders order) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        HashMap<String, Object> HashMap = new HashMap<>();
        HashMap.put("sql","INSERT INTO `mall`.`orders` (`o_num`, `o_date`, `o_uuid`, `o_state`, `o_receiving`, `o_mobile`, `o_name`) VALUES (NULL, ?, ?, ?, ?, ?, ?)");
        HashMap.put("order",order);
        return (int) cartsDao.addOrder(HashMap).get("code");
    }
    public int addOrderItems(OrderItems orderItems) throws NoSuchMethodException, IllegalAccessException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {
        HashMap<String, Object> HashMap = new HashMap<>();
        HashMap.put("sql","INSERT INTO `mall`.`orderitems` (`item_id`, `item_oid`, `item_cid`, `item_money`, `item_num`) VALUES (NULL, (SELECT MAX(o_num) FROM `mall`.`orders`), ?, ?, ?)");
        HashMap.put("orderItems",orderItems);
        return (int) cartsDao.addOrderItems(HashMap).get("code");
    }
}
