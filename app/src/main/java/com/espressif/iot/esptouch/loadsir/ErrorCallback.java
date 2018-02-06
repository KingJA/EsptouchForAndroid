package com.espressif.iot.esptouch.loadsir;


import com.espressif.iot_esptouch_demo.R;
import com.kingja.loadsir.callback.Callback;


/**
 * Description:TODO
 * Create Time:2017/9/4 10:20
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class ErrorCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_error;
    }
}
