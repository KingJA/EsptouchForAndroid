package com.espressif.iot.esptouch.demo_activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchListener;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.dialog.LoadDialog;
import com.espressif.iot.esptouch.kingja.SoundPlayer;
import com.espressif.iot.esptouch.kingja.XSTetView;
import com.espressif.iot.esptouch.task.__IEsptouchTask;
import com.espressif.iot_esptouch_demo.R;

import java.util.List;

public class EsptouchDemoActivity extends Activity implements OnClickListener {

    private static final String TAG = "EsptouchDemoActivity";
    private TextView mTvSsid;
    private EditText mEdtApPassword;
    private XSTetView tv_confirm;
    private EspWifiAdminSimple mWifiAdmin;
    private TextView tv_add_redice;
    private TextView tv_add_count;
    private TextView tv_currentCount;
    private int currentCount = 1;
    private LoadDialog loadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.esptouch_demo_activity);
        initView();
    }

    private void initView() {
        mWifiAdmin = new EspWifiAdminSimple(this);
        mTvSsid = findViewById(R.id.tv_ssid);
        mEdtApPassword = findViewById(R.id.edtApPassword);
        tv_add_redice = findViewById(R.id.tv_reduce_count);
        tv_add_count = findViewById(R.id.tv_add_count);
        tv_confirm = findViewById(R.id.tv_confirm);
        tv_currentCount = findViewById(R.id.tv_currentCount);
        tv_add_redice.setOnClickListener(this);
        tv_add_count.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // display the connected ap's ssid
        String apSsid = mWifiAdmin.getWifiConnectedSsid();
        if (apSsid != null) {
            mTvSsid.setTextColor(getResources().getColor(R.color.c_green_light));
            mTvSsid.setText(apSsid);
        } else {
            mTvSsid.setTextColor(getResources().getColor(R.color.c_warn));
            mTvSsid.setText("disconnected");
        }
        // check whether the wifi is connected
        boolean isApSsidEmpty = TextUtils.isEmpty(apSsid);
        tv_confirm.setEnabled(!isApSsidEmpty);
        tv_confirm.setTextColor(!isApSsidEmpty ? getResources().getColor(R.color.c_green_light) : getResources()
                .getColor(R.color.c_green));
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_count:
                SoundPlayer.getInstance().playVoice(R.raw.power_up);
                currentCount = Integer.valueOf(tv_currentCount.getText().toString().trim());
                tv_currentCount.setText(String.valueOf(++currentCount));
                break;
            case R.id.tv_reduce_count:
                SoundPlayer.getInstance().playVoice(R.raw.power_up);
                currentCount = Integer.valueOf(tv_currentCount.getText().toString().trim());
                if (currentCount > 1) {
                    tv_currentCount.setText(String.valueOf(--currentCount));
                }
                break;
            case R.id.tv_confirm:
                SoundPlayer.getInstance().playVoice(R.raw.send);
                String apSsid = mTvSsid.getText().toString();
                String apPassword = mEdtApPassword.getText().toString();
                String apBssid = mWifiAdmin.getWifiConnectedBssid();
                String taskResultCountStr = tv_currentCount.getText().toString().trim();
                if (__IEsptouchTask.DEBUG) {
                    Log.d(TAG, "tv_confirm is clicked, mEdtApSsid = " + apSsid
                            + ", " + " mEdtApPassword = " + apPassword + ", taskResultCountStr =" + taskResultCountStr);
                }
                new EsptouchAsyncTask3().execute(apSsid, apBssid, apPassword, taskResultCountStr);
                break;
            default:
                break;
        }
    }

    private void onEsptoucResultAddedPerform(final IEsptouchResult result) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                loadDialog.showTip("Success");
            }

        });
    }

    private IEsptouchListener myListener = new IEsptouchListener() {

        @Override
        public void onEsptouchResultAdded(final IEsptouchResult result) {
            onEsptoucResultAddedPerform(result);
        }
    };

    private class EsptouchAsyncTask3 extends AsyncTask<String, Void, List<IEsptouchResult>> {


        private IEsptouchTask mEsptouchTask;
        // without the lock, if the user tap confirm and cancel quickly enough,
        // the bug will arise. the reason is follows:
        // 0. task is starting created, but not finished
        // 1. the task is cancel for the task hasn't been created, it do nothing
        // 2. task is created
        // 3. Oops, the task should be cancelled, but it is running
        private final Object mLock = new Object();


        @Override
        protected void onPreExecute() {
            loadDialog = new LoadDialog(EsptouchDemoActivity.this);
            loadDialog.setCanceledOnTouchOutside(false);
            loadDialog.setOnCancelListener(new OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    synchronized (mLock) {
                        if (__IEsptouchTask.DEBUG) {
                            Log.i(TAG, "progress dialog is canceled");
                        }
                        if (mEsptouchTask != null) {
                            mEsptouchTask.interrupt();
                        }
                    }
                }
            });
            loadDialog.showWithSound();
        }

        @Override
        protected List<IEsptouchResult> doInBackground(String... params) {
            int taskResultCount = -1;
            synchronized (mLock) {
                // !!!NOTICE
                String apSsid = mWifiAdmin.getWifiConnectedSsidAscii(params[0]);
                String apBssid = params[1];
                String apPassword = params[2];
                String taskResultCountStr = params[3];
                taskResultCount = Integer.parseInt(taskResultCountStr);
                mEsptouchTask = new EsptouchTask(apSsid, apBssid, apPassword, EsptouchDemoActivity.this);
                mEsptouchTask.setEsptouchListener(myListener);
            }
            List<IEsptouchResult> resultList = mEsptouchTask.executeForResults(taskResultCount);
            return resultList;
        }

        @Override
        protected void onPostExecute(List<IEsptouchResult> result) {
            IEsptouchResult firstResult = result.get(0);
            // check whether the task is cancelled and no results received
            if (!firstResult.isCancelled()) {
                int count = 0;
                // max results to be displayed, if it is more than maxDisplayCount,
                // just show the count of redundant ones
                final int maxDisplayCount = 5;
                // the task received some results including cancelled while
                // executing before receiving enough results
                if (firstResult.isSuc()) {
                    loadDialog.showTip("Success");
                } else {
                    loadDialog.showTip("Fail");
                }
            }
        }
    }

}
