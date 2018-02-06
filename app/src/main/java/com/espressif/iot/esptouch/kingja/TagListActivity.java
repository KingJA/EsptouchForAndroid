package com.espressif.iot.esptouch.kingja;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.espressif.iot.esptouch.dialog.AddDialog;
import com.espressif.iot.esptouch.dialog.DeleteDialog;
import com.espressif.iot.esptouch.dialog.EditDialog;
import com.espressif.iot.esptouch.dialog.LoadDialog;
import com.espressif.iot.esptouch.entiy.NoResult;
import com.espressif.iot.esptouch.entiy.Tag;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:TODO
 * Create Time:2018/2/3 10:22
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class TagListActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView
        .OnItemLongClickListener, View.OnClickListener, EditDialog.OnEditTagStatusListener, AddDialog
        .OnAddTagListener, DeleteDialog.OnTagDeleteListener, Callback.OnReloadListener {

    private static final String TAG = "TagListActivity";
    private ListView lv_tags;
    private TextView tv_exit;
    private TextView tv_add;
    private DeleteDialog deleteDialog;
    private EditDialog editDialog;
    private List<Tag.DataBean> tags = new ArrayList<>();
    private AddDialog addDialog;
    private LoadDialog loadDialog;
    private TagsAdapter tagsAdapter;
    private LoadService loadService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags);
        loadService = LoadSir.getDefault().register(this, this);
        VolleySir.getDefault().init(this);
        initView();
        initNet();
    }

    private void initNet() {
        loadService.showCallback(LoadingCallback.class);
        GsonRequest request = new GsonRequest.Builder<Tag>()
                .setResponseType(Tag.class)
                .setResponseListener(new Response.Listener<Tag>() {
                    @Override
                    public void onResponse(Tag response) {
                        List<Tag.DataBean> tags = response.getData();
                        if (tags != null) {
                            if (tags.size() > 0) {
                                tagsAdapter.setData(tags);
                                loadService.showSuccess();
                            } else {
                                loadService.showCallback(EmptyCallback.class);
                            }

                        }
                    }
                })
                .setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadService.showCallback(ErrorCallback.class);
                    }
                })
                .setUrl(Constants.TAGS)
                .setMethod(Request.Method.GET)
                .build();

        VolleySir.getDefault().addRequest(request, this);
    }

    private void initView() {
        lv_tags = findViewById(R.id.lv_tags);
        tv_exit = findViewById(R.id.tv_exit);
        tv_add = findViewById(R.id.tv_add);
        tv_exit.setOnClickListener(this);
        tv_add.setOnClickListener(this);

        tagsAdapter = new TagsAdapter(this, tags);
        lv_tags.setAdapter(tagsAdapter);
        lv_tags.setOnItemClickListener(this);
        lv_tags.setOnItemLongClickListener(this);

        loadDialog = new LoadDialog(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Tag.DataBean tag = (Tag.DataBean) adapterView.getItemAtPosition(i);
        editDialog = new EditDialog(this, tag, i);
        editDialog.setOnEditTagStatusListener(this);
        editDialog.show();
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        Tag.DataBean tag = (Tag.DataBean) adapterView.getItemAtPosition(i);
        deleteDialog = new DeleteDialog(this, tag.getTagId(), i);
        deleteDialog.show();
        deleteDialog.setOnTagDeleteListener(this);
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_exit:
                finish();
                break;
            case R.id.tv_add:
                addDialog = new AddDialog(this);
                addDialog.setOnAddTagListener(this);
                addDialog.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onEditTagStatus(int tagId, final int status, final int position) {
        loadDialog.show();
        Map<String, String> param = new HashMap<>();
        param.put("tagid", String.valueOf(tagId));
        param.put("status", String.valueOf(status));
        GsonRequest request = new GsonRequest.Builder<NoResult>()
                .setResponseType(NoResult.class)
                .setResponseListener(new Response.Listener<NoResult>() {
                    @Override
                    public void onResponse(NoResult response) {
                        if (response.getStatus() == 0) {
                            tagsAdapter.setStatus(position, status);
                            loadDialog.showTip("Success");
                        }else{
                            loadDialog.showTip(response.getMessage());
                        }
                    }
                })
                .setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadDialog.dismiss();
                    }
                })
                .setUrl(Constants.UPDATE_TAG)
                .setMethod(Request.Method.POST)
                .setParam(param)
                .build();

        VolleySir.getDefault().addRequest(request, this);
    }

    @Override
    public void onAddTag(final String tagId) {
        loadDialog.show();
        Map<String, String> param = new HashMap<>();
        param.put("tagid", tagId);
        GsonRequest request = new GsonRequest.Builder<NoResult>()
                .setResponseType(NoResult.class)
                .setResponseListener(new Response.Listener<NoResult>() {
                    @Override
                    public void onResponse(NoResult response) {
                        if (response.getStatus() == 0) {
                            tagsAdapter.addTag(Integer.valueOf(tagId));
                            loadDialog.showTip("Success");
                        }else{
                            loadDialog.showTip(response.getMessage());
                        }
                    }
                })
                .setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadDialog.showTip("Error");
                    }
                })
                .setUrl(Constants.ADD_TAG)
                .setMethod(Request.Method.POST)
                .setParam(param)
                .build();

        VolleySir.getDefault().addRequest(request, this);

    }

    @Override
    public void onTagDelete(int tagId, final int position) {
        loadDialog.show();
        Map<String, String> param = new HashMap<>();
        param.put("tagid", String.valueOf(tagId));
        GsonRequest request = new GsonRequest.Builder<NoResult>()
                .setResponseType(NoResult.class)
                .setResponseListener(new Response.Listener<NoResult>() {
                    @Override
                    public void onResponse(NoResult response) {
                        if (response.getStatus() == 0) {
                            tagsAdapter.removeItem(position);
                            loadDialog.showTip("Success");
                        }else{
                            loadDialog.showTip(response.getMessage());
                        }
                    }
                })
                .setErrorListener(new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadDialog.dismiss();
                        ToastUtil.showText(error.getMessage());
                    }
                })
                .setUrl(Constants.DELETE_TAG)
                .setMethod(Request.Method.POST)
                .setParam(param)
                .build();

        VolleySir.getDefault().addRequest(request, this);
    }

    @Override
    public void onReload(View v) {
        initNet();
    }
}
