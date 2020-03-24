package com.funi.utils.storage;

import android.util.LruCache;

/**
 * 将数据保存到运行内存中
 * 那种临时存贮的可以保存到这个里面
 * 如果需要长时间保存的请保存到SharedPreferences中
 * 或者数据库中
 */
public class MemoryUtils {

    private static LruCache<String, Object> mCache = new LruCache<>(10 * 1024 * 1024);
    private volatile static MemoryUtils mInstance;

    private MemoryUtils() {

    }

    public static MemoryUtils getInstance() {
        if (mInstance == null) {
            synchronized (MemoryUtils.class) {
                if (mInstance == null) {
                    mInstance = new MemoryUtils();
                }
            }
        }
        return mInstance;
    }

    public MemoryUtils save(String key, String value) {
        mCache.put(key, value);
        return this;
    }

    public MemoryUtils save(String key, double value) {
        mCache.put(key, value);
        return this;
    }

    public MemoryUtils save(String key, int value) {
        mCache.put(key, value);
        return this;
    }

    public MemoryUtils save(String key, long value) {
        mCache.put(key, value);
        return this;
    }


    public MemoryUtils save(String key, boolean value) {
        mCache.put(key, value);
        return this;
    }

    public MemoryUtils save(String key, float value) {
        mCache.put(key, value);
        return this;
    }

    public MemoryUtils save(String key, Object value) {
        mCache.put(key, value);
        return this;
    }

    public String getString(String key) {
        return (String) mCache.get(key);
    }

    public double getDouble(String key, double defaultValue) {
        return (double) mCache.get(key);
    }

    public int getInt(String key, int defaultValue) {
        return (int) mCache.get(key);
    }

    public long getLong(String key, long defaultValue) {
        return (long) mCache.get(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return (boolean) mCache.get(key);
    }

    public float getFloat(String key, float defaultValue) {
        return (float) mCache.get(key);
    }

    public Object getObject(String key) {
        return mCache.get(key);
    }
}
