package com.espressif.iot.esptouch.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.espressif.iot_esptouch_demo.R;

/**
 * Description:TODO
 * Create Time:2018/2/3 16:00
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class AddDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private EditText et_tagId;
    private OnAddTagListener onAddTagListener;

    public AddDialog(Context context) {
        super(context, R.style.InputDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add);
        initView();
    }

    private void initView() {
        TextView tv_add = findViewById(R.id.tv_add);
        et_tagId = findViewById(R.id.et_tagId);
        tv_add.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String tagId = et_tagId.getText().toString().trim();
        if (!TextUtils.isEmpty(tagId)) {
            onAddTagListener.onAddTag(tagId);
            dismiss();
        }
    }

    public interface OnAddTagListener {
        void onAddTag(String tagId);
    }

    public void setOnAddTagListener(OnAddTagListener onAddTagListener) {
        this.onAddTagListener = onAddTagListener;
    }
}
