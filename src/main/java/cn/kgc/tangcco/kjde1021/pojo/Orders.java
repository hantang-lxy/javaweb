package cn.kgc.tangcco.kjde1021.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/13  15:27
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Orders {
    /**
     * 订单编号
     */
    private Integer oNum;
    /**
     * 下单日期
     */
    private Date oDate;
    /**
     * 订单金额
     */
    private BigDecimal oMoney;
    /**
     * 用户名
     */
    private String oUuid;
    /**
     * 订单状态1.未完成2.已完成3.未发货4.已发货
     */
    private Integer oState;
    /**
     * 收货地址
     */
    private String oReceiving;
    /**
     * 收货联系电话
     */
    private String oMobile;
    /**
     * 收货人姓名
     */
    private String oName;
    /**
     * 本次消费明细
     */
    private List<OrderItems> orderItems;

    /**
     * 本次消费的消费者信息
     */
    private Person person;

    public Orders(Integer oNum, Date oDate, String oUuid, Integer oState, String oReceiving, String oMobile, String oName) {
        this.oNum = oNum;
        this.oDate = oDate;
        this.oUuid = oUuid;
        this.oState = oState;
        this.oReceiving = oReceiving;
        this.oMobile = oMobile;
        this.oName = oName;
    }
}
