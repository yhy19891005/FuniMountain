package yin.deng.dyutils.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.greenrobot.eventbus.EventBus;

import yin.deng.dyutils.base.BaseConfig;


/**
 * Created by Administrator on 2017/2/20.
 */

public class NetStateReceiver extends BroadcastReceiver implements BaseConfig {
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if(activeInfo!=null){
            //准备登陆
            if(activeInfo.getTypeName().equalsIgnoreCase("WIFI")){
                EventBus.getDefault().post(new NetInfo(wifi));
            }else{
                EventBus.getDefault().post(new NetInfo(moblie));
            }
        }else{
            EventBus.getDefault().post(new NetInfo(noNet));
        }
    }
}
