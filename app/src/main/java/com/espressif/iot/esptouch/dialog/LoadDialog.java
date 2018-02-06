package com.espressif.iot.esptouch.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.espressif.iot_esptouch_demo.R;

/**
 * Description:TODO
 * Create Time:2018/2/3 15:39
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class LoadDialog extends BaseDialog implements DialogInterface.OnDismissListener, DialogInterface
        .OnShowListener {
    private static final String[] loadChar = {"-____", "_-___", "__-__", "___-_", "____-"};
    private TextView tv_loadChar;
    private Runnable loadRunnable;
    private Handler loadHandler;

    public LoadDialog(Context context) {
        super(context);
        setCanceledOnTouchOutside(false);
        setOnShowListener(this);
    }

    @Override
    public int getContentView() {
        return R.layout.dialog_load;
    }

    @Override
    public void initView() {
        tv_loadChar = findViewById(R.id.tv_loadChar);
        loadHandler = new Handler();
    }

    @Override
    public void initData() {
        setOnDismissListener(this);
    }

    private int loadProgress;

    @Override
    public void settData() {
        loadRunnable = new Runnable() {
            @Override
            public void run() {
                loadHandler.removeCallbacks(loadRunnable);
                tv_loadChar.setText(loadChar[loadProgress % loadChar.length]);
                loadProgress += 1;
                loadHandler.postDelayed(loadRunnable, 200);
            }
        };
        startLoading();
    }

    private void startLoading() {
        loadHandler.postDelayed(loadRunnable, 200);
    }

    @Override
    public void childClick(View v) {

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        loadHandler.removeCallbacks(loadRunnable);
    }


    @Override
    public void onShow(DialogInterface dialogInterface) {
        tv_loadChar.setText(loadChar[0]);
        loadHandler.postDelayed(loadRunnable, 200);
    }

    public void showTip(String tip) {
        loadHandler.removeCallbacks(loadRunnable);
        tv_loadChar.setText(tip);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, 500);
    }
}
