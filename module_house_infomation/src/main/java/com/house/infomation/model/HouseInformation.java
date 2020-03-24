package com.house.infomation.model;

import com.funi.net.model.BaseModel;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

/**
 * HouseInfomation
 *
 * @Description:
 * @Author: pengqiang.zou
 * @CreateDate: 2019-04-12 17:17
 */
public class HouseInformation extends BaseModel {
    @Expose
    private ArrayList<HouseInfo> list;

    public ArrayList<HouseInfo> getList() {
        return list;
    }

    public void setList(ArrayList<HouseInfo> list) {
        this.list = list;
    }
}
