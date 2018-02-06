package com.espressif.iot.esptouch.kingja;

import android.app.Application;
import android.content.Context;

import com.espressif.iot.esptouch.loadsir.EmptyCallback;
import com.espressif.iot.esptouch.loadsir.ErrorCallback;
import com.espressif.iot.esptouch.loadsir.LoadingCallback;
import com.kingja.loadsir.core.LoadSir;

/**
 * Description:TODO
 * Create Time:2018/2/5 15:42
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class App extends Application {
    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        SoundPlayer.getInstance().init(context);
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }

    public static Context getContext() {
        return context;
    }
}
