package com.espressif.iot.esptouch.kingja;

import android.view.View;

import com.espressif.iot_esptouch_demo.R;

/**
 * Description:TODO
 * Create Time:2018/2/6 20:52
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public abstract class OnSoundClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        SoundPlayer.getInstance().playVoice(R.raw.btn01);
        onSoundClick(view);
    }

    public abstract void onSoundClick(View view);
}
