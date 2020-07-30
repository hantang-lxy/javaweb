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
 * @date 2020/6/13  15:06
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Carts {
    /**
     * 购物车编号
     */
    private Integer sId;
    /**
     * 用户名
     */
    private String sUuid;
    /**
     * 商品编号
     */
    private Integer sCid;
    /**
     * 商品数量
     */
    private Integer sNum;
    /**
     * 加入购物车商品的详细信息
     */
    private Commodity commodity;

    public Carts(Integer sid) {
        this.sId = sid;
    }

}
