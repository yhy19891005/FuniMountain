package yin.deng.dyutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;

/**
 * 保存用户信息
 */
public class SharedPreferenceUtil {
    private Context context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String Tag="SharedPreferenceUtil";

    public SharedPreferenceUtil(Context context,String nameKey) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(nameKey, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveString(String tag, String s) {
        editor.putString(tag, s);
        editor.commit();
        showSuccessLog(tag, s);
    }

    public void saveBoolean(String tag, boolean s) {
        editor.putBoolean(tag, s);
        editor.commit();
        showSuccessLog(tag, s);
    }

    public void saveInt(String tag, int s) {
        editor.putInt(tag, s);
        editor.commit();
        showSuccessLog(tag, s);
    }

    public void saveLong(String tag, long s) {
        editor.putLong(tag, s);
        editor.commit();
        showSuccessLog(tag, s);
    }




    public boolean saveObject( Object object, String key) {
// TODO Auto-generated method stub
        if (object == null) {
            editor.putString(key, null);
            return editor.commit();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
// 将对象转换成byte数组，并将其进行base64编码
        String objectStr = new String(android.util.Base64.encode(baos.toByteArray(), android.util.Base64.DEFAULT));
        try {
            baos.close();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
// 将编码后的字符串写到base64.xml文件中
        editor.putString(key, objectStr);
        return editor.commit();
    }


    public Object getObject(String key) {
        try {
            String wordBase64 = sharedPreferences.getString(key, "");
// 将base64格式字符串还原成byte数组
            if (wordBase64 == null || wordBase64.equals("")) { // 不可少，否则在下面会报java.io.StreamCorruptedException
                return null;
            }
            byte[] objBytes = Base64.decode(wordBase64.getBytes(),Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(objBytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
// 将byte数组转换成product对象
            Object obj = ois.readObject();
            bais.close();
            ois.close();
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void saveSetMap(String tag, HashSet<String> map) {
        editor.putStringSet(tag, map);
        editor.commit();
        showSuccessLog(tag, "mapSaveSuccess!");
    }

    public void clearString(String tag) {
        editor.putString(tag, null);
    }

    public void clearLong(String tag) {
        editor.putLong(tag, -1);
    }

    public void clearAll() {
        editor.clear();
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1);
    }

    public String getStr(String key) {
        return sharedPreferences.getString(key, "");
    }

    public long getLong(String key) {
        return sharedPreferences.getLong(key, -1L);
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public HashSet<String> getSetMap(String key) {
        return (HashSet<String>) sharedPreferences.getStringSet(key, null);
    }


    private void showSuccessLog(String tag, String s) {
        Log.i(Tag,"save------" + "tag:" + tag + ",msg:" + s);
    }

    private void showSuccessLog(String tag, boolean s) {
        Log.i(Tag,"save------" + "tag:" + tag + ",msg:" + s);
    }

    private void showSuccessLog(String tag, int s) {
        Log.i(Tag,"save------" + "tag:" + tag + ",msg:" + s);
    }

    private void showSuccessLog(String tag, long s) {
        Log.i(Tag,"save------" + "tag:" + tag + ",msg:" + s);
    }

}
