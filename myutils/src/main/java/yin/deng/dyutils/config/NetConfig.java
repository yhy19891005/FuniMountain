package yin.deng.dyutils.config;

public interface NetConfig {
    //测试环境地址(马鞍山)
    //String testBaseUrl="http://125.71.215.213:12715/masWeb";
    //测试环境地址(东营)
    //String testBaseUrl="http://128.195.26.102:80/masWeb";
    String testBaseUrl                    = "http://devzhfc.qiye.lht1.ccb.com/masWeb";
    //正式环境地址(马鞍山)
    //String realBaseUrl                    = "http://www.masfdc.com.cn/masWeb";
    //正式环境地址(东营)
    String realBaseUrl                    = "http://dy.zhfc.ccbhome.cn/masWeb";
    String BASE_URl                       = realBaseUrl;
    //新房楼盘列表查询
    String MOST_NEW_HOUSE_LIST_SEARCH_URL = BASE_URl + "/service/project/list";
    //新房楼盘列表查询
    String BUILDING_MARKET_LIST_URL       = BASE_URl + "/service/quotation";
    //从业主体列表
    String OCC_MAIN_PART_LIST_URL         = BASE_URl + "/service/organization/list";
    //查询合同是否存在(商品房)
    String IS_HT_EXIST_XF                 = BASE_URl + "/service/xf/summary";
    //查询合同是否存在（存量房）
    String IS_HT_EXIST_ESF                = BASE_URl + "/service/esf/summary";
}
