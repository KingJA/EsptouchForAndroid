package com.espressif.iot.esptouch.kingja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.espressif.iot.esptouch.demo_activity.EsptouchDemoActivity;
import com.espressif.iot_esptouch_demo.R;

/**
 * Description:TODO
 * Create Time:2018/2/3 9:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSendPassword(View view) {
        startActivity(new Intent(this,EsptouchDemoActivity.class));
    }

    public void openTagDataActivity(View view) {
        startActivity(new Intent(this,TagDataListActivity.class));
    }

    public void openTagActivity(View view) {
        startActivity(new Intent(this,TagListActivity.class));
    }
}
