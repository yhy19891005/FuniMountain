package com.funi.utils.storage;

import android.content.Context;
import android.content.SharedPreferences;

import com.funi.utils.Base64Util;

/**
 * PreferencesUtils
 * 单例模式
 *
 * 存的时候 调用save() 然后调用commit()
 *
 * PreferencesUtils.getInstance().saveString("k","12345").saveBoolean("j",true).commit();
 *
 * @Description: SharedPreferences
 * @Author: pengqiang.zou
 * @CreateDate: 2018-12-17 15:02
 */
public class PreferencesUtils {
    private volatile static PreferencesUtils mInstance;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    private PreferencesUtils() {

    }

    public void init(Context context) {
        mPreferences = context.getApplicationContext().getSharedPreferences("cache", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public static PreferencesUtils getInstance() {
        if (mInstance == null) {
            synchronized (PreferencesUtils.class) {
                if (mInstance == null) {
                    mInstance = new PreferencesUtils();
                }
            }
        }
        return mInstance;
    }

    public void commit() {
        mEditor.commit();
    }


    public PreferencesUtils saveString(String key, String value) {
        mEditor.putString(key, Base64Util.encode(value));
        return this;
    }

    public PreferencesUtils saveDouble(String key, double value) {
        mEditor.putString(key, String.valueOf(value));
        return this;
    }

    public PreferencesUtils saveInt(String key, int value) {
        mEditor.putInt(key, value);
        return this;
    }

    public PreferencesUtils saveLong(String key, long value) {
        mEditor.putLong(key, value);
        return this;
    }

    public PreferencesUtils saveBoolean(String key, boolean value) {
        mEditor.putBoolean(key, value);
        return this;
    }


    public PreferencesUtils saveFloat(String key, float value) {
        mEditor.putFloat(key, value);
        return this;
    }

    public String getString(String key, String defaultValue) {
        return Base64Util.decode(mPreferences.getString(key, defaultValue));
    }


    public Double getDouble(String key, double defaultValue) {
        return Double.valueOf(mPreferences.getString(key, String.valueOf(defaultValue)));
    }

    public int getInt(String key, int defaultValue) {
        return mPreferences.getInt(key, defaultValue);
    }

    public Long getLong(String key, long defaultValue) {
        return mPreferences.getLong(key, defaultValue);
    }

    public Boolean getBoolean(String key, boolean defaultValue) {
        return mPreferences.getBoolean(key, defaultValue);
    }


    public float getFloat(String key, float defaultValue) {
        return mPreferences.getFloat(key, defaultValue);
    }
}
