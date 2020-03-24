package yin.deng.dyutils.web;

public interface WebUrlConfig {
    //测试环境(马鞍山)
    //String test_web_main_url="http://125.71.215.213:12715/masWap/html";
    //测试环境(东营)
    //String test_web_main_url="http://128.195.26.102:80/masWap/html";
    String test_web_main_url="http://devzhfc.qiye.lht1.ccb.com/masWap/html";
    //生产环境(马鞍山)
    //String real_web_main_url="http://www.masfdc.com.cn/masWap/html";
    //生产环境(东营)
    String real_web_main_url="http://dy.zhfc.ccbhome.cn/masWap/html";

    String WEB_IP_HOST_MAIN=real_web_main_url;
//    String WEB_IP_HOST_MAIN="http://demo.funi365.com/v3/masWap/demo";

     //masWeb
    //http://192.168.2.77:8080/hkweb/service/project/list?limit=10&page=0&keyword=%E5%8F%91%E5%B9%BF%E5%91%8A%E5%A5%BD
    //资讯详情页面
    String HOUSE_INFOMATION_DETAILS_URL=WEB_IP_HOST_MAIN+"/news_detail.html?id=";
    //存量房买卖查询结果页
    String SAVE_HOUSE_SALE_SEARCH_URL=WEB_IP_HOST_MAIN+"/clf_query_info.html";
    //商品房买卖查询结果页
    String SHOP_HOUSE_SALE_SEARCH_URL=WEB_IP_HOST_MAIN+"/spf_query_info.html";
    //新房楼盘详情页面
    String MOST_NEW_HOUSE_BUILDING_SEARCH_URL=WEB_IP_HOST_MAIN+"/lp_detail.html?projectId=";
    //预售证查询结果
    String RE_SALE_CERTIFY_SEARCH_URL=WEB_IP_HOST_MAIN+"/prev_sale_result.html";
    //从业主体详情页面
    String OCCU_MAIN_PART_DETAILS_URL=WEB_IP_HOST_MAIN+"/cyzt_query_info.html";
}
