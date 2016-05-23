package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/22.
 */
public class HistoryInfoModel {
    private List<HistoryInfoList> bplist;
    private List<HistoryInfoList> bmilist;
    private List<HistoryInfoList> bslist;
    private List<HistoryInfoList> bolist;
    private List<HistoryInfoList> whrlist;
    private List<HistoryInfoList> temperlist;
    private List<HistoryInfoList> ecglist;
    private Page page;

    public List<HistoryInfoList> getBmilist() {
        return bmilist;
    }

    public void setBmilist(List<HistoryInfoList> bmilist) {
        this.bmilist = bmilist;
    }

    public List<HistoryInfoList> getBslist() {
        return bslist;
    }

    public void setBslist(List<HistoryInfoList> bslist) {
        this.bslist = bslist;
    }

    public List<HistoryInfoList> getBolist() {
        return bolist;
    }

    public void setBolist(List<HistoryInfoList> bolist) {
        this.bolist = bolist;
    }

    public List<HistoryInfoList> getWhrlist() {
        return whrlist;
    }

    public void setWhrlist(List<HistoryInfoList> whrlist) {
        this.whrlist = whrlist;
    }

    public List<HistoryInfoList> getTemperlist() {
        return temperlist;
    }

    public void setTemperlist(List<HistoryInfoList> temperlist) {
        this.temperlist = temperlist;
    }

    public List<HistoryInfoList> getEcglist() {
        return ecglist;
    }

    public void setEcglist(List<HistoryInfoList> ecglist) {
        this.ecglist = ecglist;
    }

    public List<HistoryInfoList> getBplist() {
        return bplist;
    }

    public void setBplist(List<HistoryInfoList> bplist) {
        this.bplist = bplist;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
}
