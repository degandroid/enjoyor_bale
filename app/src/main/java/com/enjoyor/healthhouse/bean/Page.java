package com.enjoyor.healthhouse.bean;

/**
 * Created by YuanYuan on 2016/5/13.
 */
public class Page {
    private boolean paging;//是否分页,默认不分页，如需分页传true即可
    Integer pageMethod;//1=分段式获取，2=分页式获取，默认分页式获取
    Integer pageNum;//分页式获取时必传字段，开始页码数，默认从0（第一页）开始
    Integer pageCount;//分页式获取时必传字段，每页条目数，默认10条
    Long total;//总记录条目数
    Integer currentPage;//分段式获取时必传字段，当前从第几条开始，默认从0开始，如传8则从第9条数据开始
    Integer needCount;//分段式获取时必传字段，请求获取条目数，默认获取10条

    public boolean isPaging() {
        return paging;
    }

    public void setPaging(boolean paging) {
        this.paging = paging;
    }

    public Integer getPageMethod() {
        return pageMethod;
    }

    public void setPageMethod(Integer pageMethod) {
        this.pageMethod = pageMethod;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getNeedCount() {
        return needCount;
    }

    public void setNeedCount(Integer needCount) {
        this.needCount = needCount;
    }
}
