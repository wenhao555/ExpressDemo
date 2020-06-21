package com.example.expressdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText input_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        input_num = findViewById(R.id.input_num);
    }

    public void commit(View view) {
        String str = input_num.getText().toString();
        if (!str.equals("")) {
            startActivity(new Intent(this, OrderActivity.class).putExtra("order", str)
                    .putExtra("type", getIntent().getStringExtra("type")));
        } else {
            Toast.makeText(this, "请输入快递单号", Toast.LENGTH_SHORT).show();
        }
    }
}