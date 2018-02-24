package com.espressif.iot.esptouch.kingja;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.espressif.iot.esptouch.dialog.LoadDialog;
import com.espressif.iot.esptouch.entiy.TagData;
import com.espressif.iot.esptouch.loadsir.EmptyCallback;
import com.espressif.iot.esptouch.loadsir.ErrorCallback;
import com.espressif.iot.esptouch.loadsir.LoadingCallback;
import com.espressif.iot_esptouch_demo.R;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.volleysir.GsonRequest;
import com.kingja.volleysir.VolleySir;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/2/3 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TagDataListActivity extends Activity implements Callback.OnReloadListener {
    public static final String TAG = "TagDataListActivity";
    private ListView lv_tag_datas;
    private TagDatasAdapter tagDatasAdapter;
    private List<TagData.DataBean> tagDatas = new ArrayList<>();
    private LoadDialog loadDialog;
    private LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_datas);
        loadService = LoadSir.getDefault().register(this, this);
        VolleySir.getDefault().init(this);
        initView();
        initNet();
    }

    private void initView() {
        lv_tag_datas = findViewById(R.id.lv_tag_datas);
        tagDatasAdapter = new TagDatasAdapter(this, tagDatas);
        lv_tag_datas.setAdapter(tagDatasAdapter);
        loadDialog = new LoadDialog(this);
    }

    private void initNet() {
        loadService.showCallback(LoadingCallback.class);
        GsonRequest request = new GsonRequest.Builder<TagData>()
                .setResponseType(TagData.class)
                .setResponseListener(new Response.Listener<TagData>() {
                    @Override
                    public void onResponse(TagData response) {
                        loadDialog.dismiss();
                        List<TagData.DataBean> tagDatas = response.getData();
                        if (tagDatas != null) {
                            if (tagDatas != null) {
                                if (tagDatas.size() > 0) {
                                    tagDatasAdapter.setData(tagDatas);
                                    loadService.showSuccess();
                                } else {
                                    loadService.showCallback(EmptyCallback.class);
                                    SoundPlayer.getInstance().playVoice(R.raw.error01);
                                }
                            }
                        }
                    }
                })
                .setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadService.showCallback(ErrorCallback.class);
                        SoundPlayer.getInstance().playVoice(R.raw.error01);
                    }
                })
                .setUrl(Constants.TAG_DATAS)
                .setMethod(Request.Method.GET)
                .build();

        VolleySir.getDefault().addRequest(request, this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleySir.getDefault().cancle(this);
    }

    @Override
    public void onReload(View v) {
        initNet();
    }
}
