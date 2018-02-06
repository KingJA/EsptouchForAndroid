package com.espressif.iot.esptouch.kingja;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Description:TODO
 * Create Time:2018/2/3 11:06
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class BottomLineTetView extends AppCompatTextView {
    public BottomLineTetView(Context context) {
        this(context, null);
    }

    public BottomLineTetView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomLineTetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/fz.TTF");
        setTypeface(tf);
        getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
    }
}
