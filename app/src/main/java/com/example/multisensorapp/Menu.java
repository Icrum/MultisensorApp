package com.example.multisensorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(getResources().getText(R.string.Menu));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        VolleyHelper.getInstance(this).getAllData(this, VolleyHelper.BASE_API + "/api/auth/readings", new VolleyHelper.OnResponseListener() {
            @Override
            public void onSuccess(JSONObject jsonObject) {
                Log.d(MainActivity.TAG, jsonObject.toString());
            }
        });
    }
}
