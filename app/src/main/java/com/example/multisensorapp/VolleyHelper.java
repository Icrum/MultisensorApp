package com.example.multisensorapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VolleyHelper {

    public interface OnGetAllDataListener {
        void onSuccess(JSONObject jsonObject);
    }

    public static void getAllData(Context context, String url, final OnGetAllDataListener listener){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    listener.onSuccess(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("status","error");
                        listener.onSuccess(jsonObject);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("status","error");
                    listener.onSuccess(jsonObject);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        });

        queue.add(request);
    }

}
