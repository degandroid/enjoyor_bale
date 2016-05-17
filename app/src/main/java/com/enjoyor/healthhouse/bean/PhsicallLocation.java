package com.enjoyor.healthhouse.bean;

import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/13.
 */
@DatabaseTable(tableName = "physicall_location")
public class PhsicallLocation {


    /**
     * currentPage : 0
     * needCount : 10
     * orderType : DESC
     * pageCount : 6
     * pageMethod : 2
     * pageNum : 1
     * paging : true
     * sortParam :
     * total : 227
     */

    private PageEntity page;
    /**
     * addressName :
     * compName : 银江股份
     * companyId : 2
     * distance : null
     * macAddress : MADD51
     * machineAddress :
     * machineCode : CODE51
     * machineId : 77
     * machineLat :
     * machineLong :
     */

    private List<MachineModelsEntity> machineModels;

    public PageEntity getPage() {
        return page;
    }

    public void setPage(PageEntity page) {
        this.page = page;
    }

    public List<MachineModelsEntity> getMachineModels() {
        return machineModels;
    }

    public void setMachineModels(List<MachineModelsEntity> machineModels) {
        this.machineModels = machineModels;
    }

    public static class PageEntity {
        private int currentPage;
        private int needCount;
        private String orderType;
        private int pageCount;
        private int pageMethod;
        private int pageNum;
        private boolean paging;
        private String sortParam;
        private int total;

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getNeedCount() {
            return needCount;
        }

        public void setNeedCount(int needCount) {
            this.needCount = needCount;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getPageMethod() {
            return pageMethod;
        }

        public void setPageMethod(int pageMethod) {
            this.pageMethod = pageMethod;
        }

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public boolean isPaging() {
            return paging;
        }

        public void setPaging(boolean paging) {
            this.paging = paging;
        }

        public String getSortParam() {
            return sortParam;
        }

        public void setSortParam(String sortParam) {
            this.sortParam = sortParam;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }

    public static class MachineModelsEntity {
        private String addressName;
        private String compName;
        private int companyId;
        private Object distance;
        private String macAddress;
        private String machineAddress;
        private String machineCode;
        private int machineId;
        private String machineLat;
        private String machineLong;

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public String getCompName() {
            return compName;
        }

        public void setCompName(String compName) {
            this.compName = compName;
        }

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public Object getDistance() {
            return distance;
        }

        public void setDistance(Object distance) {
            this.distance = distance;
        }

        public String getMacAddress() {
            return macAddress;
        }

        public void setMacAddress(String macAddress) {
            this.macAddress = macAddress;
        }

        public String getMachineAddress() {
            return machineAddress;
        }

        public void setMachineAddress(String machineAddress) {
            this.machineAddress = machineAddress;
        }

        public String getMachineCode() {
            return machineCode;
        }

        public void setMachineCode(String machineCode) {
            this.machineCode = machineCode;
        }

        public int getMachineId() {
            return machineId;
        }

        public void setMachineId(int machineId) {
            this.machineId = machineId;
        }

        public String getMachineLat() {
            return machineLat;
        }

        public void setMachineLat(String machineLat) {
            this.machineLat = machineLat;
        }

        public String getMachineLong() {
            return machineLong;
        }

        public void setMachineLong(String machineLong) {
            this.machineLong = machineLong;
        }
    }
}



//package com.enjoyor.healthhouse.bean;
//
//import com.j256.ormlite.field.DatabaseField;
//import com.j256.ormlite.table.DatabaseTable;
//
//import java.util.List;
//
///**
// * Created by YuanYuan on 2016/5/13.
// */
//@DatabaseTable(tableName = "physicall_location")
//public class PhsicallLocation {
//    @DatabaseField(id = true)
//    int id;
//    private List<PhyicallLocation_info> machineModels;
//    private Page page;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public List<PhyicallLocation_info> getPhyicallLocation_infoList() {
//        return machineModels;
//    }
//
//    public void setPhyicallLocation_infoList(List<PhyicallLocation_info> machineModels) {
//        this.machineModels = machineModels;
//    }
//
//    public Page getPage() {
//        return page;
//    }
//
//    public void setPage(Page page) {
//        this.page = page;
//    }
//}
