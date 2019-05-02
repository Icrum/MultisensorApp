package com.example.multisensorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(getResources().getText(R.string.Menu));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
}
