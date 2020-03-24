package com.house.enquiry.ui.be_most_new_house_building_search;

import java.util.ArrayList;
import java.util.List;

import yin.deng.dyutils.http.BaseHttpInfo;

public class SearchHistoryList extends BaseHttpInfo {
    private List<String> historys=new ArrayList<>();

    public List<String> getHistorys() {
        return historys;
    }

    public void setHistorys(List<String> historys) {
        this.historys = historys;
    }
}
