package com.espressif.iot.esptouch.kingja;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Description:TODO
 * Create Time:2018/2/3 11:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class XSTetView extends AppCompatTextView {
    public XSTetView(Context context) {
        this(context,null);
    }

    public XSTetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public XSTetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/fz.TTF");
            setTypeface(tf);
        }
    }
}
