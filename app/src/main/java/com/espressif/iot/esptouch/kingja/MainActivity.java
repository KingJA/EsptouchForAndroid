package com.espressif.iot.esptouch.kingja;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

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
        TextView tv_send_password = findViewById(R.id.tv_send_password);
        TextView tv_tag_datas = findViewById(R.id.tv_tag_datas);
        TextView tv_tags = findViewById(R.id.tv_tags);
        tv_send_password.setOnClickListener(new OnSoundClickListener() {
            @Override
            public void onSoundClick(View view) {
                startActivity(new Intent(MainActivity.this,EsptouchDemoActivity.class));
            }
        });
        tv_tag_datas.setOnClickListener(new OnSoundClickListener() {
            @Override
            public void onSoundClick(View view) {
                startActivity(new Intent(MainActivity.this,TagDataListActivity.class));
            }
        });
        tv_tags.setOnClickListener(new OnSoundClickListener() {
            @Override
            public void onSoundClick(View view) {
                startActivity(new Intent(MainActivity.this,TagListActivity.class));
            }
        });
    }
}
