package com.example.expressdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void st(View view) {
        startActivity(new Intent(MainActivity2.this, MainActivity.class).putExtra("type", "STO"));
    }

    public void yt(View view) {
        startActivity(new Intent(MainActivity2.this, MainActivity.class).putExtra("type", "YTO"));

    }

    public void zt(View view) {
        startActivity(new Intent(MainActivity2.this, MainActivity.class).putExtra("type", "ZTO"));

    }
}