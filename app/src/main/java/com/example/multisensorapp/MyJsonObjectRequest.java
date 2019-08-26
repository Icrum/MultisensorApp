package com.example.multisensorapp;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Header;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MyJsonObjectRequest extends JsonObjectRequest {

    public MyJsonObjectRequest(int method, String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
    }

    public MyJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {


        Cache.Entry entry = getCacheEntry();
        Header header = entry.allResponseHeaders.get(5);
        Singleton.getInstance().setToken(header.getValue());
       //Singleton.getInstance().setHeaders(headers);

        return super.getHeaders();
    }
}
