package com.kingja.volleysir;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Description:TODO
 * Create Time:2017/7/31 16:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class VolleySir {

    private RequestQueue mQueue;
    private static VolleySir mInstance;

    private VolleySir() {

    }

    public static VolleySir getDefault() {
        if (mInstance == null) {
            synchronized (VolleySir.class) {
                if (mInstance == null) {
                    mInstance = new VolleySir();
                }
            }
        }
        return mInstance;
    }

    public void init(Context context) {
        Context applicationContext = context.getApplicationContext();
        mQueue = Volley.newRequestQueue(applicationContext);
    }

    public RequestQueue getRequestQueue() {
        return mQueue;
    }

    public void addRequest(Request request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        mQueue.add(request);
    }

    public void addRequest(Request request) {
        addRequest(request, null);
    }

    public void cancle(Object tag) {
        mQueue.cancelAll(tag);
    }

}
