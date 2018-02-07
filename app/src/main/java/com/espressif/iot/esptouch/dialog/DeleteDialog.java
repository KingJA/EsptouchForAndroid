package com.espressif.iot.esptouch.dialog;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.espressif.iot.esptouch.kingja.SoundPlayer;
import com.espressif.iot_esptouch_demo.R;

/**
 * Description:TODO
 * Create Time:2018/2/3 14:17
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class DeleteDialog extends BaseDialog {

    private final int tagId;
    private final int position;
    private TextView tv_cancel;
    private TextView tv_delete;

    public DeleteDialog(Context context, int tagId, int position) {
        super(context);
        this.tagId = tagId;
        this.position = position;
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_delete;
    }

    @Override
    public void initView() {
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_delete = findViewById(R.id.tv_delete);

    }

    @Override
    public void initData() {
        tv_cancel.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
    }

    @Override
    public void settData() {

    }

    @Override
    public void childClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                break;
            case R.id.tv_delete:
                if (onTagDeleteListener != null) {
                    SoundPlayer.getInstance().playVoice(R.raw.close);
                    onTagDeleteListener.onTagDelete(tagId,position);
                }
                break;
            default:
                break;
        }
        dismiss();
    }

    public interface OnTagDeleteListener {
        void onTagDelete(int tagId, int position);
    }

    private OnTagDeleteListener onTagDeleteListener;

    public void setOnTagDeleteListener(OnTagDeleteListener onTagDeleteListener) {
        this.onTagDeleteListener = onTagDeleteListener;
    }
}
