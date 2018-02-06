package com.kingja.volleysir;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Description:TODO
 * Create Time:2018/2/5 13:11
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class GsonRequest<T> extends Request<T> {

    private Class<T> clazz;
    private Response.Listener<T> listener;
    private Map<String, String> params;

    public GsonRequest(Builder builder) {
        this(builder.method, builder.url, builder.errorListener);
        this.clazz = builder.clazz;
        this.listener = builder.listener;
        this.params = builder.params;
    }

    private GsonRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Log.e("parseNetworkResponse", "jsonString: "+jsonString );
            return Response.success(new Gson().fromJson(jsonString, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    public static class Builder<T> {
        private String url;
        private Class<T> clazz;
        private int method = Method.POST;
        private Response.Listener<T> listener;
        private Response.ErrorListener errorListener;
        private Map<String, String> params;

        public GsonRequest<T> build() {
            return new GsonRequest<T>(this);
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setParam(Map<String, String> params) {
            this.params = params;
            return this;
        }

        public Builder<T> setResponseType(Class<T> clazz) {
            this.clazz = clazz;
            return this;
        }

        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        public Builder<T> setResponseListener(Response.Listener<T> listener) {
            this.listener = listener;
            return this;
        }

        public Builder setErrorListener(Response.ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (params != null) {
            return params;
        } else {
            return super.getParams();
        }
    }
}