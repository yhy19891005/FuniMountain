package com.house.enquiry.modle;

import java.util.List;

import yin.deng.dyutils.http.BaseHttpInfo;

public class MostNewSearchInfo extends BaseHttpInfo {
    @Override
    public String toString() {
        return "MostNewSearchInfo{" +
                "result=" + result +
                '}';
    }

    /**
     * result : {"list":[{"address":"高新区中和镇梓州大道233号","coverUrl":"http://125.71.215.213:12715/masWeb/common/img/house-test.png","currentSellHouseCount":100,"currentSellNumber":"预1021号","id":"1","openTime":"2016-09-11","orgName":"中德地产","projectName":"中德英伦联邦1","regionCode":"510100","regionName":"高新区","types":["住宅"]}]}
     */

    private ResultBean result;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }


    public static class ResultBean extends BaseHttpInfo{
        @Override
        public String toString() {
            return "ResultBean{" +
                    "list=" + list +
                    '}';
        }

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean extends BaseHttpInfo{
            /**
             * address : 高新区中和镇梓州大道233号
             * coverUrl : http://125.71.215.213:12715/masWeb/common/img/house-test.png
             * currentSellHouseCount : 100
             * currentSellNumber : 预1021号
             * id : 1
             * openTime : 2016-09-11
             * orgName : 中德地产
             * projectName : 中德英伦联邦1
             * regionCode : 510100
             * regionName : 高新区
             * types : ["住宅"]
             */

            private String address;
            private String coverUrl;
            private int currentSellHouseCount;
            private String currentSellNumber;
            private String id;
            private String openTime;
            private String orgName;
            private String projectName;
            private String regionCode;
            private String regionName;
            private List<String> types;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCoverUrl() {
                return coverUrl;
            }

            public void setCoverUrl(String coverUrl) {
                this.coverUrl = coverUrl;
            }

            public int getCurrentSellHouseCount() {
                return currentSellHouseCount;
            }

            public void setCurrentSellHouseCount(int currentSellHouseCount) {
                this.currentSellHouseCount = currentSellHouseCount;
            }

            public String getCurrentSellNumber() {
                return currentSellNumber;
            }

            public void setCurrentSellNumber(String currentSellNumber) {
                this.currentSellNumber = currentSellNumber;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOpenTime() {
                return openTime;
            }

            public void setOpenTime(String openTime) {
                this.openTime = openTime;
            }

            public String getOrgName() {
                return orgName;
            }

            public void setOrgName(String orgName) {
                this.orgName = orgName;
            }

            public String getProjectName() {
                return projectName;
            }

            public void setProjectName(String projectName) {
                this.projectName = projectName;
            }

            public String getRegionCode() {
                return regionCode;
            }

            public void setRegionCode(String regionCode) {
                this.regionCode = regionCode;
            }

            public String getRegionName() {
                return regionName;
            }

            public void setRegionName(String regionName) {
                this.regionName = regionName;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }

            @Override
            public String toString() {
                return "ListBean{" +
                        "address='" + address + '\'' +
                        ", coverUrl='" + coverUrl + '\'' +
                        ", currentSellHouseCount=" + currentSellHouseCount +
                        ", currentSellNumber='" + currentSellNumber + '\'' +
                        ", id='" + id + '\'' +
                        ", openTime='" + openTime + '\'' +
                        ", orgName='" + orgName + '\'' +
                        ", projectName='" + projectName + '\'' +
                        ", regionCode='" + regionCode + '\'' +
                        ", regionName='" + regionName + '\'' +
                        ", types=" + types +
                        '}';
            }
        }
    }
}
