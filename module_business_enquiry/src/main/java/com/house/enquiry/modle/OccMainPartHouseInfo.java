package com.house.enquiry.modle;

import yin.deng.dyutils.http.BaseHttpInfo;

public class OccMainPartHouseInfo extends BaseHttpInfo {
    private String houseName="芜湖铭宇房地产经纪有限公司";
    private String housePlace="芜湖市镜湖区联盛国际商业广场2#楼1707";
    private String houseType="民营企业";
    private String houseStatus="正常";
    private String houseCount="532套";

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHousePlace() {
        return housePlace;
    }

    public void setHousePlace(String housePlace) {
        this.housePlace = housePlace;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public String getHouseStatus() {
        return houseStatus;
    }

    public void setHouseStatus(String houseStatus) {
        this.houseStatus = houseStatus;
    }

    public String getHouseCount() {
        return houseCount;
    }

    public void setHouseCount(String houseCount) {
        this.houseCount = houseCount;
    }
}
