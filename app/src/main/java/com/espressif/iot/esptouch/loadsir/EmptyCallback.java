package com.espressif.iot.esptouch.loadsir;

import com.espressif.iot_esptouch_demo.R;
import com.kingja.loadsir.callback.Callback;


/**
 * Description:TODO
 * Create Time:2017/9/4 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */

public class EmptyCallback extends Callback {

    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

}
