package com.house.enquiry.modle;

import java.util.ArrayList;
import java.util.List;

import yin.deng.dyutils.http.BaseHttpInfo;

public class OccMainPartHouseInfoList extends BaseHttpInfo {

    /**
     * result : {"list":[{"address":"","contractInformation":"0555-2326096","id":"a7c85068-bcaa-47fa-a14d-17f65d27609f","netSignStatus":"异常","organizationName":"马鞍山开发企业测试数据","organizationType":"有限责任公司"},{"address":"","contractInformation":"3456666","id":"e761bca7-4d07-484a-8aeb-08f4c4bf0a4e","netSignStatus":"异常","organizationName":"开发企业测试请问","organizationType":"有限责任公司"},{"address":"","contractInformation":"","id":"ee4e7903-9c78-4acb-8077-b669c1064a0e","netSignStatus":"异常","organizationName":"开发企业审核测试","organizationType":"有限责任公司"},{"address":"","contractInformation":"0555-8888888","id":"4c1dfc1a-6416-44f9-a0ba-88c490a64703","netSignStatus":"异常","organizationName":"开发企业----张玺测试数据","organizationType":"有限责任公司"},{"address":"","contractInformation":"4234234","id":"85ebf465-e87a-4167-acea-ea2b46c4462d","netSignStatus":"异常","organizationName":"新工作流开发企业","organizationType":"有限责任公司"}],"total":5}
     * success : true
     */

    private ResultBean result;
    private boolean success;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public static class ResultBean extends BaseHttpInfo{
        /**
         * list : [{"address":"","contractInformation":"0555-2326096","id":"a7c85068-bcaa-47fa-a14d-17f65d27609f","netSignStatus":"异常","organizationName":"马鞍山开发企业测试数据","organizationType":"有限责任公司"},{"address":"","contractInformation":"3456666","id":"e761bca7-4d07-484a-8aeb-08f4c4bf0a4e","netSignStatus":"异常","organizationName":"开发企业测试请问","organizationType":"有限责任公司"},{"address":"","contractInformation":"","id":"ee4e7903-9c78-4acb-8077-b669c1064a0e","netSignStatus":"异常","organizationName":"开发企业审核测试","organizationType":"有限责任公司"},{"address":"","contractInformation":"0555-8888888","id":"4c1dfc1a-6416-44f9-a0ba-88c490a64703","netSignStatus":"异常","organizationName":"开发企业----张玺测试数据","organizationType":"有限责任公司"},{"address":"","contractInformation":"4234234","id":"85ebf465-e87a-4167-acea-ea2b46c4462d","netSignStatus":"异常","organizationName":"新工作流开发企业","organizationType":"有限责任公司"}]
         * total : 5
         */

        private int total;
        private List<ListBean> list=new ArrayList<>();

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean extends BaseHttpInfo{
            /**
             * address :
             * contractInformation : 0555-2326096
             * id : a7c85068-bcaa-47fa-a14d-17f65d27609f
             * netSignStatus : 异常
             * organizationName : 马鞍山开发企业测试数据
             * organizationType : 有限责任公司
             */

            private String address;
            private String contractInformation;
            private String id;
            private String netSignStatus;
            private String organizationName;
            private String organizationType;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getContractInformation() {
                return contractInformation;
            }

            public void setContractInformation(String contractInformation) {
                this.contractInformation = contractInformation;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getNetSignStatus() {
                return netSignStatus;
            }

            public void setNetSignStatus(String netSignStatus) {
                this.netSignStatus = netSignStatus;
            }

            public String getOrganizationName() {
                return organizationName;
            }

            public void setOrganizationName(String organizationName) {
                this.organizationName = organizationName;
            }

            public String getOrganizationType() {
                return organizationType;
            }

            public void setOrganizationType(String organizationType) {
                this.organizationType = organizationType;
            }
        }
    }
}
