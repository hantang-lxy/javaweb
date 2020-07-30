package cn.kgc.tangcco.kjde1021.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/13  15:27
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItems {
    /**
     * 条目编号
     */
    private Integer itemId;
    /**
     * 订单编号
     */
    private Integer itemOid;
    /**
     * 商品编号
     */
    private Integer itemCid;
    /**
     * 订单金额
     */
    private BigDecimal itemMoney;
    /**
     * 商品数量
     */
    private Integer itemNum;

    /**
     * 购买的商品的详细信息
     */
    private Commodity commodity;

    //用于添加
    public OrderItems(Integer itemCid, BigDecimal itemMoney, Integer itemNum) {
        this.itemCid = itemCid;
        this.itemMoney = itemMoney;
        this.itemNum = itemNum;
    }
}
