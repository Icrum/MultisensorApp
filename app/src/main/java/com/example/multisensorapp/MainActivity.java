package com.example.multisensorapp;

import android.content.Intent;
import android.location.SettingInjectorService;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static String TAG = "multisensorapp";

    Button buttonLogin;
    EditText editTextEmail;
    EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle(getResources().getText(R.string.Multisensor));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 15)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        editTextEmail.setText("asd@asd.com");
        editTextPassword.setText("qwerty");
    }

    public void login() {
        Log.d(MainActivity.TAG, "LOGIN");
        VolleyHelper.getInstance(this).userLogin(
                this,
                editTextEmail.getText().toString(),
                editTextPassword.getText().toString(),
                new VolleyHelper.OnResponseListener() {
                    @Override
                    public void onSuccess(JSONObject jsonObject) {
                        // Testar os vários tipos de resposta
                        Log.d(MainActivity.TAG, "chegou aqui.."+jsonObject.toString());
                        if (jsonObject.has("status")){
                            try {
                                if (jsonObject.getString("status").contains("error")){
                                    Toast.makeText(MainActivity.this,"No internet connection",Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Log.d(MainActivity.TAG, e.toString());
                                Toast.makeText(MainActivity.this,"No internet connection",Toast.LENGTH_LONG).show();
                            }
                        }else if (jsonObject.has("data")){
                            // Guardar dados do utilizador
                            try {
                                JSONObject data = (JSONObject) jsonObject.get("data");
                                JSONObject user = (JSONObject) data.get("user");
                                int id = user.getInt("id");
                                String email = user.getString("email");
                                Singleton.getInstance().setEmail(email);
                                Singleton.getInstance().setId(id);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            Intent intent = new Intent(MainActivity.this, Menu.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this,"No user name or password",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
}