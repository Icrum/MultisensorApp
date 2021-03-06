package com.example.multisensorapp;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.Map;

public class VolleyHelper {

    private static VolleyHelper instance = null;


    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";

    RequestQueue queue;
    protected VolleyHelper(Context context){
        queue= Volley.newRequestQueue(context);
    }

    public static VolleyHelper getInstance(Context context){
        if (instance == null){
            instance = new VolleyHelper(context);
        }
        return instance;
    }


    private static final int TIMEOUT = 5000;
    public static String BASE_API = "http://192.168.41.86:4000";

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

    public  void getAllData(Context context, final String url, final OnResponseListener listener){

        StringRequest request=new MyStringRequest(url, new Response.Listener<String>() {
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
                Log.d(MainActivity.TAG, "Get Data:" + error.toString());
                listener.onSuccess(getJsonError(error.toString()));
            }
        });

        queue.add(request);
    }

    public  void userLogin (final Context context, final String username, String password, final OnResponseListener listener){

        JSONObject obj = null;

        try {
            obj = new JSONObject();
            obj.put("email", username);
            obj.put("password", password);

        } catch (JSONException e) {
            Log.d(MainActivity.TAG, e.toString());
        }

        Log.d(MainActivity.TAG, "json login"+obj.toString());
        String url = BASE_API + "/api/users/sign_in/";
        Log.d(MainActivity.TAG, url);

        //JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {

       JsonObjectRequest jsonObjectRequest = new MyJsonObjectRequest(Request.Method.POST, url, obj, new Response.Listener<JSONObject>() {


            @Override
            public void onResponse(JSONObject response) {

                Log.d("MultiSensorApp", Singleton.getInstance().getToken());
                listener.onSuccess(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(MainActivity.TAG, "Login:" +error.toString());
                listener.onSuccess(getJsonError(error.toString()));
            }

        });
/*
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*/
        queue.add(jsonObjectRequest);
    }






}