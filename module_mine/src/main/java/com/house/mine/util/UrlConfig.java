package com.house.mine.util;

public interface UrlConfig {

    //申报类型
    String ORGTYPE = "org_type";
    //开发商信息申报
    String ORGTYPE_DEVELOPER = "DEVELOPER";
    //经纪机构信息申报
    String ORGTYPE_BROKER = "BROKER";
    //获取证件类型
    String GET_EMP_CERTIFICATE_TYPE = " http://qn.zhfc.ccbhome.cn/pract/public/getEmpCertificateType";
    //获取区域
    String GET_LOGIC_REGION = "http://qn.zhfc.ccbhome.cn/pract/public/getLogicRegion";
    //获取企业类型
    String GET_ORG_TYPE = "http://qn.zhfc.ccbhome.cn/pract/public/getOrgType";
    //获取公司类型
    String GET_ORG_NATURE = "http://qn.zhfc.ccbhome.cn/pract/public/getOrgNature";
    //获取货币类型
    String GET_CAPITAL_UNIT = "http://qn.zhfc.ccbhome.cn/pract/public/getCapitalUnit";
    //获取省
    String GET_PROVINCE = "http://qn.zhfc.ccbhome.cn/pract/public/getProvince";
    //获取市
    String GET_CITY = "http://qn.zhfc.ccbhome.cn/pract/public/getCity";
    //获取区
    String GET_REGION = "http://qn.zhfc.ccbhome.cn/pract/public/getRegion";
    //获取资质等级
    String GET_ORG_QUALIFICATION_GRADE_BY_TYPE = "http://qn.zhfc.ccbhome.cn/pract/public/getOrgQualificationGradeByType";
    //保存委托人信息
    String FORMANDATOR_SAVE = "http://qn.zhfc.ccbhome.cn/pract/forMandator/save";
    //保存公司基本信息
    String FORORGBASE_SAVE = "http://qn.zhfc.ccbhome.cn/pract/forOrgBase/save";
    //获取母公司信息
    String GET_UP_COMP = "http://qn.zhfc.ccbhome.cn/pract/public/getUpComp";
    //上传图片
    String UF_UPLOAD = "http://qn.zhfc.ccbhome.cn/pract/uf/upload";
    //保存图片
    String FORORGFILE_CREATEFILE = "http://qn.zhfc.ccbhome.cn/pract/forOrgFile/createFile";
    //删除图片
    String FORORGFILE_REMOVE = "http://qn.zhfc.ccbhome.cn/pract/forOrgFile/remove";
    //获取要件
    String FORORGFILE_GETFILES = "http://qn.zhfc.ccbhome.cn/pract/forOrgFile/getFiles";
    //检查营业执照号
    String FORORGBASE_CHECKWHILECREATE = "http://qn.zhfc.ccbhome.cn/pract/forOrgBase/checkWhileCreate";
    //查看大图
    String FORORGFILE_GETFILEIMG = "http://qn.zhfc.ccbhome.cn/pract/forOrgFile/getFileImg";

}
