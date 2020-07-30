package cn.kgc.tangcco.model;

/**
 * @author 李昊哲
 * @version 1.0
 */
public class PageParam {
    /**
     * 页码号
     */
    private Integer index = 0;
    /**
     * 页码号
     */
    private Integer pageNum;
    /**
     * 每页记录数
     */
    private Integer pageSize;
    /**
     * 排序字段
     */
    private String orderBy;
    /**
     * Ascending descending
     */
    private Enum<OrderBy> ascOrDesc;

    public PageParam() {
        this(1, 8);
    }

    public PageParam(Integer pageNum, Integer pageSize) {
        super();
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.index = (pageNum - 1) * pageSize;
    }

    public PageParam(Integer pageNum, Integer pageSize, String orderBy) {
        this(pageNum, pageSize, orderBy, OrderBy.ASC);
    }

    public PageParam(Integer pageNum, Integer pageSize, String orderBy, Enum<OrderBy> ascOrDesc) {
        this(pageNum, pageSize);
        this.orderBy = orderBy + " " + ascOrDesc;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;

    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public Enum<OrderBy> getAscOrDesc() {
        return ascOrDesc;
    }

    public void setAscOrDesc(Enum<OrderBy> ascOrDesc) {
        this.ascOrDesc = ascOrDesc;
    }

    public Integer getIndex() {
        return (pageNum - 1) * pageSize;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
