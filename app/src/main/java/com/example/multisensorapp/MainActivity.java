package com.example.multisensorapp;

import android.content.Intent;
import android.location.SettingInjectorService;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    Button buttonLogin;
    EditText editTextEmail;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(getResources().getText(R.string.Multisensor));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {
        VolleyHelper.userLogin(
                this,
                editTextEmail.getText().toString(),
                editTextPassword.getText().toString(),
                new VolleyHelper.OnResponseListener() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Testar os vários tipos de resposta
                        if (jsonObject.has("status")){
                            try {
                                if (jsonObject.getString("status").contains("error")){
                                    Toast.makeText(MainActivity.this,"No internet connection",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(MainActivity.this,"No internet connection",Toast.LENGTH_LONG).show();
                            }
                        }else if (jsonObject.has("id")){
                            // Guardar dados do utilisador
                            Intent intent = new Intent(MainActivity.this, Menu.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this,"No user name or password",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}
