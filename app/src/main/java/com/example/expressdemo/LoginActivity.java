package com.example.expressdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


public class LoginActivity extends AppCompatActivity {

    private EditText account, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        account = findViewById(R.id.account);
        pwd = findViewById(R.id.pwd);

    }

    public void login(View view) {
        //比对缓存账号是否正确
        if (account.getText().toString().equals(PrefUtils.getString(this, "account", ""))
                && pwd.getText().toString().equals(PrefUtils.getString(this, "pwd", ""))) {
            startActivity(new Intent(LoginActivity.this, MainActivity2.class));
            finish();
        }
    }

    public void regist(View view) {
        startActivity(new Intent(LoginActivity.this, RegistActivity.class));
    }
}
