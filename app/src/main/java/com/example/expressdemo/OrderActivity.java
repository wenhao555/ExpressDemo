package com.example.expressdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OrderActivity extends AppCompatActivity {
    private String order;
    private Logistics mLogistics;
    private RecyclerView recyvle;
    private BaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        recyvle = findViewById(R.id.recyvle);

        new Thread() {
            @Override
            public void run() {
                super.run();
                LogisticsUtils api = new LogisticsUtils("", getIntent().getStringExtra("type"), getIntent().getStringExtra("order"));
                String resultJson = null;
                try {
                    resultJson = api.getOrderTracesByJson();
                    mLogistics = JSONObject.parseObject(resultJson, Logistics.class);//使用的的fastJson 解析

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    mHandler.sendEmptyMessage(1);//在主线程更新ui
                }
            }
        }.start();
        recyvle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        initData();
        recyvle.setAdapter(adapter);
    }

    private List<Logistics.TracesBean> tracesBeans = new ArrayList<>();
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (mLogistics != null) {
                //设置数据
                JsonObject jsonObject = new JsonParser().parse(new Gson().toJson(mLogistics)).getAsJsonObject();
                tracesBeans = new Gson().fromJson(jsonObject.get("Traces").getAsJsonArray(), new TypeToken<List<Logistics.TracesBean>>() {
                }.getType());
                adapter.notifyDataSetChanged();
            }
            return true;
        }
    });

    private void initData() {
        adapter = new BaseRecyclerAdapter() {
            @Override
            protected void onBindView(@NonNull BaseViewHolder holder, @NonNull final int position) {
                Logistics.TracesBean tracesBean = tracesBeans.get(position);
                TextView item_1 = holder.getView(R.id.item_1);
                TextView item_2 = holder.getView(R.id.item_2);
                item_1.setText(tracesBean.getAcceptStation());
                item_2.setText(tracesBean.getAcceptTime());
            }

            @Override
            protected int getLayoutResId(int position) {
                return R.layout.iten_adapter;
            }

            @Override
            public int getItemCount() {
                return tracesBeans.size();
            }
        };
    }
}