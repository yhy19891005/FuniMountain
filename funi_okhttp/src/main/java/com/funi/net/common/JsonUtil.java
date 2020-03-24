package com.funi.net.common;

import com.funi.net.model.BaseModel;
import com.funi.utils.TextUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class JsonUtil {
    static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static Gson getGson() {
        return gson;
    }

    /**
     * 把json转换为列表
     *
     * @param json
     * @param className
     * @return
     */
    public static ArrayList<BaseModel> getModelList(String json, Class<? extends BaseModel> className) {
        if (TextUtil.stringIsNull(json)) {
            return null;
        }

        try {
            ArrayList<?> list = JsonUtil.getGson().fromJson(json,
                    new TypeToken<List<?>>() {
                    }.getType()
            );
            String jsonStr = null;
            ArrayList<BaseModel> baseDomains = new ArrayList<BaseModel>();
            BaseModel baseDomain = null;
            for (int i = 0; i < list.size(); i++) {
                jsonStr = JsonUtil.toJson(list.get(i));
                baseDomain = JsonUtil.getGson().fromJson(jsonStr, className);
                baseDomains.add(baseDomain);
            }
            return baseDomains;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 把json转为实体
     *
     * @param json
     * @param className
     * @return
     */
    public static BaseModel getModel(String json, Class<? extends BaseModel> className) {
        if (TextUtil.stringIsNull(json)) {
            return null;
        }

        try {
            BaseModel baseDomain = JsonUtil.getGson().fromJson(json, className);
            return baseDomain;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
