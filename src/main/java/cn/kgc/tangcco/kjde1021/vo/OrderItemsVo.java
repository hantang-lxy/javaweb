package cn.kgc.tangcco.kjde1021.vo;

import cn.kgc.tangcco.kjde1021.pojo.OrderItems;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 彭印龙
 * @version 1.0
 * @date 2020/6/21 上午9:47
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderItemsVo extends OrderItems{
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
     * 商品总金额
     */
    private BigDecimal itemMoney;
    /**
     * 商品数量
     */
    private Integer itemNum;

    /**
     * 商品图片连接
     */
    private String cUrl;
    /**
     * 商品名字
     */
    private String cName;
    /**
     * 商品单价
     */
    private BigDecimal cPrice;
}
