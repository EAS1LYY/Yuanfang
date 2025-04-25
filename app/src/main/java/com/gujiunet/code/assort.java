package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class assort extends AppCompatActivity {


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assort);
        StatusBarUtil status = new StatusBarUtil();
        status.setStatusColor();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        new Thread(() -> {
            try {
                Http message_http=new Http();
                message_http.setUrl("http://software.toolr.cn/api.php?type=assortlist").setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request message_return=message_http.request();
                JSONObject processjsonObject = new JSONObject(message_return.content);
                //String process_list = processjsonObject.get("process_list").toString();
                JSONArray text_lists = processjsonObject.getJSONArray("list");
                ArrayList<assort_model> assortModels = new ArrayList<>();
                new Thread(() -> {
                    try {
                        for (int i = 0; i < text_lists.length(); i++) {
                            //获取json对象
                            JSONObject jsonObject1 = text_lists.getJSONObject(i);
                            assortModels.add(new assort_model(jsonObject1.get("名称").toString(),jsonObject1.get("ID").toString(),jsonObject1.get("介绍").toString()));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                assort.this.runOnUiThread(() -> {
                    RecyclerView textrecyclerView = findViewById(R.id.assort_RecyclerView);
                    assort_RecyclerViewAdapter textrecyclerViewAdapter = new assort_RecyclerViewAdapter(assort.this, assortModels);
                    textrecyclerView.setAdapter(textrecyclerViewAdapter);
                    GridLayoutManager layoutManager = new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL,false);
                    textrecyclerView.setLayoutManager(layoutManager);
                    textrecyclerView.addOnItemTouchListener(new recyclerItemClickListener(assort.this, new recyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            CardView assort_button=view.findViewById(R.id.assort_button);
                            assort_button.setOnClickListener(v -> {
                                TextView textid = view.findViewById(R.id.assort_id);
                                String ID = textid.getText().toString();
                                //Toast.makeText(process.this, ID, Toast.LENGTH_SHORT).show();
                                String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/assort_code.json";
                                File file = new File(filePath);
                                JSONObject jsonObj = new JSONObject();
                                try {
                                    jsonObj.put("CODEID", ID);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    // 判断文件是否存在，如果不存在则创建文件
                                    if (!file.exists()) {
                                        assort.this.getExternalFilesDir(null);
                                        file.createNewFile();
                                    }

                                    FileWriter writer = new FileWriter(filePath);
                                    writer.write(jsonObj.toString());
                                    writer.close();
                                    Intent intent = new Intent();
                                    intent.setClass(assort.this, assort_code.class);
                                    startActivity(intent);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                        @Override
                        public void onLongClick(View view, int posotion) {
                        }
                    }));
                });
                View leave=findViewById(R.id.leave_message_button);
                leave.setOnClickListener(v -> {
                    finish();
                });

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
