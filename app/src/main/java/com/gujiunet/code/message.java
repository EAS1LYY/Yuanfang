package com.gujiunet.code;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class message extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //弹窗初始化
        setContentView(R.layout.message);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        new Thread(() -> {
            try {
                InputStream open = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    open = Files.newInputStream(Paths.get("/storage/emulated/0/Android/data/com.gujiunet.code/account.json"));
                }
                InputStreamReader isr = new InputStreamReader(open, StandardCharsets.UTF_8);
                //包装字符流,将字符流放入缓存里
                BufferedReader br = new BufferedReader(isr);
                String line;
                //StringBuilder和StringBuffer功能类似,存储字符串
                StringBuilder builder = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    //append 被选元素的结尾(仍然在内部)插入指定内容,缓存的内容依次存放到builder中
                    builder.append(line);
                }
                br.close();
                isr.close();
                //builder.toString() 返回表示此序列中数据的字符串
                //System.out.println(builder);
                //使用Json解析
                JSONObject jsonObject = new JSONObject(builder.toString());
                //System.out.println(jsonObject);
                Log.i("文件json", jsonObject.toString());
                String account = jsonObject.get("account").toString();
                Http message_http=new Http();
                message_http.setUrl("http://software.toolr.cn/api.php?type=informinfo&ID="+account).setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request message_return=message_http.request();
                JSONObject processjsonObject = new JSONObject(message_return.content);
                //String process_list = processjsonObject.get("process_list").toString();
                JSONArray text_lists = processjsonObject.getJSONArray("list");
                ArrayList<message_model> messageModelArrayList = new ArrayList<>();
                new Thread(() -> {
                    try {
                        for (int i = 0; i < text_lists.length(); i++) {
                            //获取json对象
                            JSONObject jsonObject1 = text_lists.getJSONObject(i);
                                messageModelArrayList.add(new message_model(jsonObject1.get("标题").toString(),jsonObject1.get("内容").toString(),jsonObject1.get("ID").toString()));
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                message.this.runOnUiThread(() -> {
                    RecyclerView textrecyclerView = findViewById(R.id.message_RecyclerView);
                    messagerecyclerviewadapter textrecyclerViewAdapter = new messagerecyclerviewadapter(message.this, messageModelArrayList);
                    textrecyclerView.setAdapter(textrecyclerViewAdapter);
                    textrecyclerView.setLayoutManager(new LinearLayoutManager(message.this));
                    textrecyclerView.addOnItemTouchListener(new recyclerItemClickListener(message.this, new recyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
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

            } catch (JSONException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();









    }
}
