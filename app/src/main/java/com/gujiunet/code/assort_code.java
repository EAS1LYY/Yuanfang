package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kongzue.dialogx.dialogs.PopNotification;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class assort_code extends AppCompatActivity {
    private int currentPage = 1;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.assort_code);
        StatusBarUtil status = new StatusBarUtil();
        status.setStatusColor();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        new Thread(() -> {
            View leave=findViewById(R.id.leave_assort_code_button);
            leave.setOnClickListener(v -> finish());
            ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载更多";
            ClassicsFooter.REFRESH_FOOTER_RELEASE = "释放立即加载";
            ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在刷新...";
            ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载...";
            ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成";
            ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败";
            ClassicsFooter.REFRESH_FOOTER_NOTHING = "没有更多数据了";
            //InputStreamReader 将字节输入流转换为字符流
            try {
                InputStream open = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    open = Files.newInputStream(Paths.get("/storage/emulated/0/Android/data/com.gujiunet.code/assort_code.json"));
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
                String assort = jsonObject.get("CODEID").toString();
                Http assort_code_http=new Http();
                assort_code_http.setUrl("http://software.toolr.cn/api.php?type=codelistassortjson&page=1&assort="+assort).setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request codedata=assort_code_http.request();
                if (codedata.content.equals("")) {
                } else {
                    JSONObject processjsonObject = new JSONObject(codedata.content);
                    //String process_list = processjsonObject.get("process_list").toString();
                    JSONArray process_list = processjsonObject.getJSONArray("list");
                    ArrayList<process_code_model> process_code_list = new ArrayList<>();
                    for (int i = 0; i < process_list.length(); i++) {
                        //获取json对象
                        JSONObject jsonObject1 = process_list.getJSONObject(i);
                        process_code_list.add(new process_code_model(jsonObject1.get("名称").toString(), jsonObject1.get("介绍").toString(), jsonObject1.get("ID").toString(), jsonObject1.get("图片1").toString()));
                    }
                    assort_code.this.runOnUiThread(() -> {
                        RefreshLayout refreshLayout = findViewById(R.id.refreshLayout);
                        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
                        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
                        //refreshLayout.setEnableRefresh(false);
                        refreshLayout.setEnableAutoLoadMore(false);
                        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
                        refreshLayout.setRefreshHeader(new MaterialHeader(this).setColorSchemeColors(R.color.Blue));
                        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                            @Override
                            public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                                new Thread(() -> {
                                    try {
                                        // 清空原始的Array数组
                                        // Arrays.fill(new JSONArray[]{process_list}, null);

                                        // 重置ArrayList的大小为0，相当于清空了ArrayList
                                        //process_code_list.clear();
                                        if (codedata.equals("")) {
                                        } else {
                                            Http code_http = new Http();
                                            code_http.setUrl("http://software.toolr.cn/api.php?type=codelistassortjson&page=1&assort="+assort);
                                            Http.Request codereturn = code_http.request();
                                            JSONObject processjsonObject = new JSONObject(codereturn.content);
                                            //String process_list = processjsonObject.get("process_list").toString();
                                            JSONArray process_list = processjsonObject.getJSONArray("list");
                                            ArrayList<process_code_model> process_code_list = new ArrayList<>();
                                            for (int i = 0; i < process_list.length(); i++) {
                                                //获取json对象
                                                JSONObject jsonObject1 = process_list.getJSONObject(i);
                                                process_code_list.add(new process_code_model(jsonObject1.get("名称").toString(), jsonObject1.get("介绍").toString(), jsonObject1.get("ID").toString(), jsonObject1.get("图片1").toString()));
                                            }
                                        }
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }

                                    refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                                }).start();
                            }
                        });
                        refreshLayout.setOnLoadMoreListener(refreshlayout -> {
                            currentPage++;

                            new Thread(() -> {

                                try {
                                    Http morecode = new Http();
                                    morecode.setUrl("http://software.toolr.cn/api.php?type=codelistassortjson&page="+currentPage+"&assort="+assort).setRequestType(Http.REQUEST_TYPE_GET);
                                    Http.Request morecode_return = morecode.request();
                                    if (Objects.equals(morecode_return.content, "")) {
                                        boolean isLoadingMore = false;
                                        PopNotification.show("已经到底了!");
                                        refreshLayout.setNoMoreData(true);
                                        refreshLayout.finishRefresh();
                                    }else{
                                        JSONObject morecodejson = new JSONObject(morecode_return.content);
                                        JSONArray more_list = morecodejson.getJSONArray("list");

                                        for (int i = 0; i < more_list.length(); i++) {
                                            //获取json对象
                                            JSONObject jsonObject2 = more_list.getJSONObject(i);
                                            process_code_list.add(new process_code_model(jsonObject2.get("名称").toString(), jsonObject2.get("介绍").toString(), jsonObject2.get("ID").toString(), jsonObject2.get("图片1").toString()));
                                        }
                                        refreshLayout.finishRefresh();
                                    }

                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }).start();




                            refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                        });


                        RecyclerView recyclerView = findViewById(R.id.assort_code_recyclerview);
                        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(this, process_code_list);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(this));



                        //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                        // 首先获取RecyclerView对象及其布局管理器


                        recyclerView.addOnItemTouchListener(new recyclerItemClickListener(this, new recyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                TextView codeid = view.findViewById(R.id.code_id);
                                String ID = codeid.getText().toString();
                                //Toast.makeText(process.this, ID, Toast.LENGTH_SHORT).show();
                                String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/code.json";
                                File file = new File(filePath);
                                JSONObject jsonObj = new JSONObject();
                                try {
                                    jsonObj.put("codeid", ID);
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }
                                try {
                                    // 判断文件是否存在，如果不存在则创建文件
                                    if (!file.exists()) {
                                        assort_code.this.getExternalFilesDir(null);
                                        file.createNewFile();
                                    }

                                    FileWriter writer = new FileWriter(filePath);
                                    writer.write(jsonObj.toString());
                                    writer.close();

                                    Intent intent = new Intent();
                                    intent.setClass(assort_code.this, code_info.class);
                                    startActivity(intent);


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onLongClick(View view, int posotion) {

                            }
                        }));
                    });

                }
            } catch (JSONException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
