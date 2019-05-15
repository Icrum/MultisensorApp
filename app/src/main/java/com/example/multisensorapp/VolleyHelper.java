package com.example.multisensorapp;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class VolleyHelper {

    public static String BASE_API = "https://192.168.41.68:4000";

    public interface OnResponseListener {
        void onSuccess(JSONObject jsonObject);
    }

    static JSONObject getJsonError (String error){
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("status",error);
        } catch (JSONException e1) {
            Log.d(MainActivity.TAG, e1.toString());
        }

        return jsonObject;
    }

    public static void getAllData(Context context, String url, final OnResponseListener listener){
        RequestQueue queue= Volley.newRequestQueue(context);
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject= new JSONObject(response);
                    listener.onSuccess(jsonObject);
                } catch (JSONException e) {
                    Log.d(MainActivity.TAG, e.toString());
                    listener.onSuccess(getJsonError("error"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                JSONObject jsonObject = new JSONObject();
                listener.onSuccess(getJsonError("error"));
            }
        });

        queue.add(request);
    }

    public static void userLogin (final Context context, final String username, String password, final OnResponseListener listener){

        RequestQueue queue = Volley.newRequestQueue(context);

        JSONObject obj = null;

        try {
            obj = new JSONObject();
            obj.put("email", username);
            obj.put("password", password);

        } catch (JSONException e) {
            Log.d(MainActivity.TAG, e.toString());
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_API + "/api/users/sign_in", obj, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onSuccess(getJsonError("error"));
            }

        });

        //jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT,
        //        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
        //        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(jsonObjectRequest);
    }

}