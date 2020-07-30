package cn.kgc.tangcco.kjde1021.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/13  15:23
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CommoditySort {
    /**
     * 编号
     */
    private Integer sortId;
    /**
     * 类别名称
     */
    private String sortName;
    /**
     * 类别编号
     */
    private Integer sortCid;

    public CommoditySort(Integer sortCid) {
        this.sortCid = sortCid;
    }

    public CommoditySort(String sortName) {
        this.sortName = sortName;
    }
}
