package com.espressif.iot.esptouch.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.espressif.iot.esptouch.entiy.Tag;
import com.espressif.iot_esptouch_demo.R;

/**
 * Description:TODO
 * Create Time:2018/2/3 14:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class EditDialog extends BaseDialog {
    private static final int DISABLE = 0;
    private static final int ABLE = 1;

    private Tag.DataBean tag;
    private final int position;
    private TextView tv_disable;
    private TextView tv_able;
    private OnEditTagStatusListener onEditTagStatusListener;

    public EditDialog(Context context, Tag.DataBean tag, int position) {
        super(context);
        this.tag = tag;
        this.position = position;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_edit;
    }

    @Override
    public void initView() {
        tv_disable = findViewById(R.id.tv_cancel);
        tv_able = findViewById(R.id.tv_delete);

    }

    @Override
    public void initData() {
        tv_disable.setOnClickListener(this);
        tv_able.setOnClickListener(this);
    }

    @Override
    public void settData() {
        setButtomStatus(tag.getStatus());
    }

    private void setButtomStatus(int status) {
        switch (status) {
            case DISABLE:
                tv_disable.setClickable(false);
                tv_able.setClickable(true);
                tv_disable.setTextColor(context.getResources().getColor(R.color.c_green_light));
                tv_able.setTextColor(context.getResources().getColor(R.color.c_font));
                break;
            case ABLE:
                tv_disable.setClickable(true);
                tv_able.setClickable(false);
                tv_disable.setTextColor(context.getResources().getColor(R.color.c_font));
                tv_able.setTextColor(context.getResources().getColor(R.color.c_green_light));
                break;
            default:
                break;
        }
    }

    @Override
    public void childClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                onEditTagStatusListener.onEditTagStatus(tag.getTagId(),DISABLE,position);
                break;
            case R.id.tv_delete:
                onEditTagStatusListener.onEditTagStatus(tag.getTagId(),ABLE,position);
                break;
            default:
                break;
        }
        dismiss();
    }

    public interface OnEditTagStatusListener {
        void onEditTagStatus(int tagId,int status,int position);
    }

    public void setOnEditTagStatusListener(OnEditTagStatusListener onEditTagStatusListener) {
        this.onEditTagStatusListener = onEditTagStatusListener;
    }
}
