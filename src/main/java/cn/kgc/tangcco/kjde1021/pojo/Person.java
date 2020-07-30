package cn.kgc.tangcco.kjde1021.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/5/19 上午12:49
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Person {
    /**
     * 用户编号
     */
    private Integer uId;
    /**
     * 唯一标识符
     */
    private String uUuid;
    /**
     * 手机号
     */
    private String uPhone;
    /**
     * 密码
     */
    private String uPassword;
    /**
     * 昵称
     */
    private String uNickname;
    /**
     * 头像
     */
    private String uImage;
    /**
     * 角色权限
     */
    private Integer uPower;
    /**
     * 用户财产
     */
    private BigDecimal uFortune;
    /**
     * 邮箱
     */
    private String uMail;
    /**
     * 消费者的消费记录
     */
    List<Orders> orders;

    public Person(String uUuid, String uPhone) {
        this.uUuid = uUuid;
        this.uPhone = uPhone;
    }

    public Person(String uUuid, String uPhone, String uPassword) {
        this.uUuid = uUuid;
        this.uPhone = uPhone;
        this.uPassword = uPassword;
    }

}
