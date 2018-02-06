package com.espressif.iot.esptouch.kingja;

import android.widget.Toast;


/**
 * Description：TODO
 * Create Time：2016/8/4 17:10
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class ToastUtil {
   private static Toast mToast;

    public static void showText(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(App.getContext(), "", Toast.LENGTH_LONG);
        }
        mToast.setText(msg);
        mToast.show();
    }
}
