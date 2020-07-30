package cn.kgc.tangcco.kjde1021.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 李雪阳
 * @version 1.0
 * @date 2020/6/13  15:06
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Commodity {
    /**
     * 商品编号
     */
    private Integer cId;
    /**
     * 商品名称
     */
    private String cName;
    /**
     * 商品类别编号
     */
    private Integer cSort;
    /**
     * 商品单价
     */
    private BigDecimal cPrice;
    /**
     * 商品的销量
     */
    private Integer cSales;
    /**
     * 商品的货存
     */
    private Integer cStock;
    /**
     * 图片地址
     */
    private String cUrl;
    /**
     * 产品细节1
     */
    private String cDetail1;
    /**
     * 产品细节2
     */
    private String cDetail2;
    /**
     * 产品细节3
     */
    private String cDetail3;

    public Commodity(Integer cId) {
        this.cId = cId;
    }


    public Commodity(Integer cId,Integer cStock) {
        this.cStock = cStock;
        this.cId = cId;
    }
    public Commodity(Integer cId,char index) {
        this.cId = cId;
        System.out.println(index);
    }

    public Commodity(String cName){
        this.cName=cName;
    }

    public Commodity(Integer cId, Integer cSort , Integer cStock) {
        this.cSort = cSort;
        this.cStock = cStock;
        this.cId = cId;
    }
    public Commodity(Integer cId, String cName, Integer cStock) {
        this.cId = cId;
        this.cName = cName;
        this.cStock = cStock;
    }
    public Commodity(String cName, Integer cSort, BigDecimal cPrice, Integer cSales, Integer cStock, String cUrl) {
        this.cName = cName;
        this.cPrice = cPrice;
        this.cSales = cSales;
        this.cSort = cSort;
        this.cStock = cStock;
        this.cUrl = cUrl;
    }
}
