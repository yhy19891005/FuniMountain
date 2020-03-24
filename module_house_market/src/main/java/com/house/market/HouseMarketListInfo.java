package com.house.market;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import yin.deng.dyutils.http.BaseHttpInfo;

public class HouseMarketListInfo extends BaseHttpInfo {
    /**
     * message : ""
     * result : {"esfCountRegion":[{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340503","regionName":"花山区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340502","regionName":"金家庄区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340506","regionName":"博望区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340521","regionName":"当涂县","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340504","regionName":"雨山区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340523","regionName":"和县","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340522","regionName":"含山县","residenceArea":0,"residenceUnit":0,"unit":0}],"esfCountYear":[{"area":350.46,"businessArea":0,"businessUnit":0,"regionCode":"340503","regionName":"花山区","residenceArea":350.46,"residenceUnit":3,"unit":3},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340502","regionName":"金家庄区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340506","regionName":"博望区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340521","regionName":"当涂县","residenceArea":0,"residenceUnit":0,"unit":0},{"area":490.75,"businessArea":39.82,"businessUnit":1,"regionCode":"340504","regionName":"雨山区","residenceArea":450.93,"residenceUnit":6,"unit":7},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340523","regionName":"和县","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340522","regionName":"含山县","residenceArea":0,"residenceUnit":0,"unit":0}],"xfCountRegion":[{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340503","regionName":"花山区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340502","regionName":"金家庄区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340506","regionName":"博望区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340521","regionName":"当涂县","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340504","regionName":"雨山区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340523","regionName":"和县","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340522","regionName":"含山县","residenceArea":0,"residenceUnit":0,"unit":0}],"xfCountYear":[{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340503","regionName":"花山区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340502","regionName":"金家庄区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340506","regionName":"博望区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340521","regionName":"当涂县","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340504","regionName":"雨山区","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340523","regionName":"和县","residenceArea":0,"residenceUnit":0,"unit":0},{"area":0,"businessArea":0,"businessUnit":0,"regionCode":"340522","regionName":"含山县","residenceArea":0,"residenceUnit":0,"unit":0}]}
     * success : true
     */


    private String message;

    private ResultBean result;

    private boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public static class ResultBean extends BaseHttpInfo {
        private List<EsfCountMonthBean> esfCountMonth = new ArrayList<>();

        private List<EsfCountRegionBean> esfCountRegion = new ArrayList<>();

        private List<EsfCountYearBean> esfCountYear = new ArrayList<>();

        private List<XfCountRegionBean> xfCountRegion = new ArrayList<>();

        private List<XfCountMonthBean> xfCountMonth = new ArrayList<>();

        private List<XfCountYearBean> xfCountYear = new ArrayList<>();

        public List<EsfCountMonthBean> getEsfCountMonth() {
            return esfCountMonth;
        }

        public void setEsfCountMonth(List<EsfCountMonthBean> esfCountMonth) {
            this.esfCountMonth = esfCountMonth;
        }

        public List<XfCountMonthBean> getXfCountMonth() {
            return xfCountMonth;
        }

        public void setXfCountMonth(List<XfCountMonthBean> xfCountMonth) {
            this.xfCountMonth = xfCountMonth;
        }

        public List<EsfCountRegionBean> getEsfCountRegion() {
            return esfCountRegion;
        }

        public void setEsfCountRegion(List<EsfCountRegionBean> esfCountRegion) {
            this.esfCountRegion = esfCountRegion;
        }

        public List<EsfCountYearBean> getEsfCountYear() {
            return esfCountYear;
        }

        public void setEsfCountYear(List<EsfCountYearBean> esfCountYear) {
            this.esfCountYear = esfCountYear;
        }

        public List<XfCountRegionBean> getXfCountRegion() {
            return xfCountRegion;
        }

        public void setXfCountRegion(List<XfCountRegionBean> xfCountRegion) {
            this.xfCountRegion = xfCountRegion;
        }

        public List<XfCountYearBean> getXfCountYear() {
            return xfCountYear;
        }

        public void setXfCountYear(List<XfCountYearBean> xfCountYear) {
            this.xfCountYear = xfCountYear;
        }

        public static class EsfCountRegionBean extends BaseHttpInfo {
            /**
             * area : 0
             * businessArea : 0
             * businessUnit : 0
             * regionCode : 340503
             * regionName : 花山区
             * residenceArea : 0
             * residenceUnit : 0
             * unit : 0
             */

            private double area;

            private double businessArea;
            private int businessUnit;
            private String regionCode;
            private String regionName;
            private double residenceArea;
            private int residenceUnit;
            private int unit;

            private double otherArea;
            private int otherUnit;

            public double getOtherArea() {
                return otherArea;
            }

            public void setOtherArea(double otherArea) {
                this.otherArea = otherArea;
            }

            public int getOtherUnit() {
                return otherUnit;
            }

            public void setOtherUnit(int otherUnit) {
                this.otherUnit = otherUnit;
            }

            public double getArea() {
                return area;
            }

            public void setArea(double area) {
                this.area = area;
            }

            public double getBusinessArea() {
                return businessArea;
            }

            public void setBusinessArea(double businessArea) {
                this.businessArea = businessArea;
            }

            public int getBusinessUnit() {
                return businessUnit;
            }

            public void setBusinessUnit(int businessUnit) {
                this.businessUnit = businessUnit;
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

            public double getResidenceArea() {
                return residenceArea;
            }

            public void setResidenceArea(double residenceArea) {
                this.residenceArea = residenceArea;
            }

            public int getResidenceUnit() {
                return residenceUnit;
            }

            public void setResidenceUnit(int residenceUnit) {
                this.residenceUnit = residenceUnit;
            }

            public int getUnit() {
                return unit;
            }

            public void setUnit(int unit) {
                this.unit = unit;
            }
        }

        public static class EsfCountYearBean extends BaseHttpInfo {
            /**
             * area : 350.46
             * businessArea : 0
             * businessUnit : 0
             * regionCode : 340503
             * regionName : 花山区
             * residenceArea : 350.46
             * residenceUnit : 3
             * unit : 3
             */

            private double area;

            private double businessArea;

            private int businessUnit;

            private String regionCode;

            private String regionName;

            private double residenceArea;

            private int residenceUnit;

            private int unit;

            private double otherArea;
            private int otherUnit;

            public double getOtherArea() {
                return otherArea;
            }

            public void setOtherArea(double otherArea) {
                this.otherArea = otherArea;
            }

            public int getOtherUnit() {
                return otherUnit;
            }

            public void setOtherUnit(int otherUnit) {
                this.otherUnit = otherUnit;
            }

            public double getArea() {
                return area;
            }

            public void setArea(double area) {
                this.area = area;
            }

            public double getBusinessArea() {
                return businessArea;
            }

            public void setBusinessArea(double businessArea) {
                this.businessArea = businessArea;
            }

            public int getBusinessUnit() {
                return businessUnit;
            }

            public void setBusinessUnit(int businessUnit) {
                this.businessUnit = businessUnit;
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

            public double getResidenceArea() {
                return residenceArea;
            }

            public void setResidenceArea(double residenceArea) {
                this.residenceArea = residenceArea;
            }

            public int getResidenceUnit() {
                return residenceUnit;
            }

            public void setResidenceUnit(int residenceUnit) {
                this.residenceUnit = residenceUnit;
            }

            public int getUnit() {
                return unit;
            }

            public void setUnit(int unit) {
                this.unit = unit;
            }
        }

        public static class XfCountRegionBean extends BaseHttpInfo {
            /**
             * area : 0
             * businessArea : 0
             * businessUnit : 0
             * regionCode : 340503
             * regionName : 花山区
             * residenceArea : 0
             * residenceUnit : 0
             * unit : 0
             */

            private double area;

            private double businessArea;

            private int businessUnit;

            private String regionCode;

            private String regionName;

            private double residenceArea;

            private int residenceUnit;

            private int unit;

            private double otherArea;
            private int otherUnit;

            public double getOtherArea() {
                return otherArea;
            }

            public void setOtherArea(double otherArea) {
                this.otherArea = otherArea;
            }

            public int getOtherUnit() {
                return otherUnit;
            }

            public void setOtherUnit(int otherUnit) {
                this.otherUnit = otherUnit;
            }

            public double getArea() {
                return area;
            }

            public void setArea(double area) {
                this.area = area;
            }

            public double getBusinessArea() {
                return businessArea;
            }

            public void setBusinessArea(double businessArea) {
                this.businessArea = businessArea;
            }

            public int getBusinessUnit() {
                return businessUnit;
            }

            public void setBusinessUnit(int businessUnit) {
                this.businessUnit = businessUnit;
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

            public double getResidenceArea() {
                return residenceArea;
            }

            public void setResidenceArea(double residenceArea) {
                this.residenceArea = residenceArea;
            }

            public int getResidenceUnit() {
                return residenceUnit;
            }

            public void setResidenceUnit(int residenceUnit) {
                this.residenceUnit = residenceUnit;
            }

            public int getUnit() {
                return unit;
            }

            public void setUnit(int unit) {
                this.unit = unit;
            }
        }

        public static class XfCountYearBean extends BaseHttpInfo {
            /**
             * area : 0
             * businessArea : 0
             * businessUnit : 0
             * regionCode : 340503
             * regionName : 花山区
             * residenceArea : 0
             * residenceUnit : 0
             * unit : 0
             */

            private double area;

            private double businessArea;

            private int businessUnit;

            private String regionCode;

            private String regionName;

            private double residenceArea;

            private int residenceUnit;

            private int unit;

            private double otherArea;
            private int otherUnit;

            public double getOtherArea() {
                return otherArea;
            }

            public void setOtherArea(double otherArea) {
                this.otherArea = otherArea;
            }

            public int getOtherUnit() {
                return otherUnit;
            }

            public void setOtherUnit(int otherUnit) {
                this.otherUnit = otherUnit;
            }

            public double getArea() {
                return area;
            }

            public void setArea(double area) {
                this.area = area;
            }

            public double getBusinessArea() {
                return businessArea;
            }

            public void setBusinessArea(double businessArea) {
                this.businessArea = businessArea;
            }

            public int getBusinessUnit() {
                return businessUnit;
            }

            public void setBusinessUnit(int businessUnit) {
                this.businessUnit = businessUnit;
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

            public double getResidenceArea() {
                return residenceArea;
            }

            public void setResidenceArea(double residenceArea) {
                this.residenceArea = residenceArea;
            }

            public int getResidenceUnit() {
                return residenceUnit;
            }

            public void setResidenceUnit(int residenceUnit) {
                this.residenceUnit = residenceUnit;
            }

            public int getUnit() {
                return unit;
            }

            public void setUnit(int unit) {
                this.unit = unit;
            }
        }

        public static class EsfCountMonthBean implements Serializable{
            /**
             * area : 0
             * businessArea : 0
             * businessUnit : 0
             * otherArea : 0
             * otherUnit : 0
             * regionCode : 340503
             * regionName : 花山区
             * residenceArea : 0
             * residenceUnit : 0
             * unit : 0
             */

            private double area;
            private double businessArea;
            private int businessUnit;
            private double otherArea;
            private int otherUnit;
            private String regionCode;
            private String regionName;
            private double residenceArea;
            private int residenceUnit;
            private int unit;

            public double getArea() {
                return area;
            }

            public void setArea(double area) {
                this.area = area;
            }

            public double getBusinessArea() {
                return businessArea;
            }

            public void setBusinessArea(double businessArea) {
                this.businessArea = businessArea;
            }

            public int getBusinessUnit() {
                return businessUnit;
            }

            public void setBusinessUnit(int businessUnit) {
                this.businessUnit = businessUnit;
            }

            public double getOtherArea() {
                return otherArea;
            }

            public void setOtherArea(double otherArea) {
                this.otherArea = otherArea;
            }

            public int getOtherUnit() {
                return otherUnit;
            }

            public void setOtherUnit(int otherUnit) {
                this.otherUnit = otherUnit;
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

            public double getResidenceArea() {
                return residenceArea;
            }

            public void setResidenceArea(double residenceArea) {
                this.residenceArea = residenceArea;
            }

            public int getResidenceUnit() {
                return residenceUnit;
            }

            public void setResidenceUnit(int residenceUnit) {
                this.residenceUnit = residenceUnit;
            }

            public int getUnit() {
                return unit;
            }

            public void setUnit(int unit) {
                this.unit = unit;
            }
        }

        public static class XfCountMonthBean implements Serializable {
            /**
             * area : 0
             * businessArea : 0
             * businessUnit : 0
             * otherArea : 0
             * otherUnit : 0
             * regionCode : 340503
             * regionName : 花山区
             * residenceArea : 0
             * residenceUnit : 0
             * unit : 0
             */

            private double area;
            private double businessArea;
            private int businessUnit;
            private double otherArea;
            private int otherUnit;
            private String regionCode;
            private String regionName;
            private double residenceArea;
            private int residenceUnit;
            private int unit;

            public double getArea() {
                return area;
            }

            public void setArea(double area) {
                this.area = area;
            }

            public double getBusinessArea() {
                return businessArea;
            }

            public void setBusinessArea(double businessArea) {
                this.businessArea = businessArea;
            }

            public int getBusinessUnit() {
                return businessUnit;
            }

            public void setBusinessUnit(int businessUnit) {
                this.businessUnit = businessUnit;
            }

            public double getOtherArea() {
                return otherArea;
            }

            public void setOtherArea(double otherArea) {
                this.otherArea = otherArea;
            }

            public int getOtherUnit() {
                return otherUnit;
            }

            public void setOtherUnit(int otherUnit) {
                this.otherUnit = otherUnit;
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

            public double getResidenceArea() {
                return residenceArea;
            }

            public void setResidenceArea(double residenceArea) {
                this.residenceArea = residenceArea;
            }

            public int getResidenceUnit() {
                return residenceUnit;
            }

            public void setResidenceUnit(int residenceUnit) {
                this.residenceUnit = residenceUnit;
            }

            public int getUnit() {
                return unit;
            }

            public void setUnit(int unit) {
                this.unit = unit;
            }
        }

    }
}
