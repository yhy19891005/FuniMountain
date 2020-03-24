package com.house.infomation.model;

import com.funi.net.model.BaseModel;
import com.google.gson.annotations.Expose;

/**
 * HouseInformation
 *
 * @Description:房产资讯
 * @Author: pengqiang.zou
 * @CreateDate: 2019-04-04 10:50
 */
public class HouseInfo extends BaseModel {
    @Expose
    private String channelName;
    @Expose
    private String releaseTime;
    @Expose
    private String title;
    @Expose
    private String abstractContent;
    @Expose
    private String id;
    //测试环境(马鞍山)
    //String test_web_main_url = "http://125.71.215.213:12715/masWap/html";
    //测试环境(东营)
    //String test_web_main_url = "http://128.195.26.102:80/masWap/html";
    String test_web_main_url = "http://devzhfc.qiye.lht1.ccb.com/masWap/html";
    //正式环境(马鞍山)
    //String real_web_main_url = "http://www.masfdc.com.cn/masWap/html";
    //正式环境(东营)
    String real_web_main_url = "http://dy.zhfc.ccbhome.cn/html";
    String WEB_IP_HOST_MAIN = real_web_main_url;

    private String infoDetailUrl = WEB_IP_HOST_MAIN + "/news_detail.html?id=";


    public String getUrl() {
        return infoDetailUrl + getId();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getAbstractContent() {
        return abstractContent;
    }

    public void setAbstractContent(String abstractContent) {
        this.abstractContent = abstractContent;
    }
}
