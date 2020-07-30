package cn.kgc.tangcco.kjde1021.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/15  0:21
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Category {
    /**
     * 分类名scid
     */
    private String name;
    /**
     * 分类编号
     */
    private Integer scid;
    /**
     * 当前类商品数量
     */
    private Long count;
    /**
     * 当前类商品集合
     */
    List<Commodity> commodityList;

    /**
     * 商品id作为参数的构造
     *
     * @param scid
     */
    public Category(Integer scid) {
        this.scid = scid;
    }
}
