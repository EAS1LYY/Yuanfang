package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kongzue.dialogx.dialogs.BottomDialog;
import com.kongzue.dialogx.dialogs.BottomMenu;
import com.kongzue.dialogx.dialogs.PopNotification;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.interfaces.OnIconChangeCallBack;

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

public class text_info extends AppCompatActivity {
    private static final int COMPLETED = 0;

    @SuppressLint({"SetJavaScriptEnabled", "ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //弹窗初始化
        StatusBarUtil status = new StatusBarUtil();
        status.setStatusColor();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        setContentView(R.layout.text_info);
        new Thread(() -> {

            try {
                WaitDialog.show("正在努力加载数据..");
                InputStream open = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    open = Files.newInputStream(Paths.get("/storage/emulated/0/Android/data/com.gujiunet.code/text.json"));
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
                String textid = jsonObject.get("textid").toString();
                Http texthttp=new Http();
                texthttp.setUrl("http://software.toolr.cn/api.php?type=articleinfo&ID="+textid).setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request textreturn=texthttp.request();
                JSONObject textjson = new JSONObject(textreturn.content);
                String text_title = textjson.get("标题").toString();
                String text_text = textjson.get("内容").toString();
                String text_author = textjson.get("作者").toString();
                String text_time = textjson.get("发布时间").toString();
                String text_views = textjson.get("浏览").toString();
                String userurl = "http://software.toolr.cn/api.php?type=logininfo&ID=" + text_author;
                Http userhttp = new Http();
                //Log.i("链接",url);
                userhttp.setUrl(userurl).setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request user_return_data = userhttp.request();
                JSONObject userdatajson = new JSONObject(user_return_data.content);
                String user_id = userdatajson.get("ID").toString();
                String user_name = userdatajson.get("Name").toString();
                String user_qq = userdatajson.get("QQ").toString();
                String user_qm = userdatajson.get("签名").toString();
                String user_regip = userdatajson.get("注册IP").toString();
                String user_lastlogin = userdatajson.get("最后登录").toString();
                String user_coin = userdatajson.get("金币").toString();
                String user_vip = userdatajson.get("会员").toString();
                String user_tx = userdatajson.get("头像").toString();
                String user_yhqx = userdatajson.get("用户权限").toString();
                String user_buycode = userdatajson.get("购买源码").toString();
                TextView text_title_id=findViewById(R.id.text_info_title);
                TextView text_author_name=findViewById(R.id.text_info_name);
                TextView text_time_top_id=findViewById(R.id.text_info_time);
                TextView text_qx_id=findViewById(R.id.text_info_qx);
                TextView text_time_bottom_id=findViewById(R.id.text_info_bottomtime);
                ImageView text_img_id=findViewById(R.id.text_info_userimg);
                TextView text_views_id=findViewById(R.id.text_info_views);
                ImageView leave_text_id=findViewById(R.id.leave_text);
                leave_text_id.setOnClickListener(v -> finish());
                WebView text_text_id=findViewById(R.id.text_info_text);
                text_img_id.setOnClickListener(v -> {
                        JSONObject jsonObj = new JSONObject();
                        try {
                            jsonObj.put("createid", text_author);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/create.json";
                        File file = new File(filePath);

                        try {
                            // 判断文件是否存在，如果不存在则创建文件
                            if (!file.exists()) {
                                getApplication().getExternalFilesDir(null);
                                file.createNewFile();

                            }

                            FileWriter writer = new FileWriter(filePath);
                            writer.write(jsonObj.toString());
                            writer.close();
                            Intent intent = new Intent();
                            intent.setClass(text_info.this, user_info.class);
                            startActivity(intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Http createhttp=new Http();
                        createhttp.setUrl("http://software.toolr.cn/api.php?type=getusercodejson&ID="+text_author+"&page=1").setRequestType(Http.REQUEST_TYPE_GET);
                        Http.Request codereturn=createhttp.request();
                        user_code.updateVarInfo(codereturn.content);

                });
                        Http text_comment_http=new Http();
                        text_comment_http.setUrl("http://software.toolr.cn/api.php?type=commentlist&ID="+textid).setRequestType(Http.REQUEST_TYPE_GET);
                        Http.Request text_comment_return=text_comment_http.request();
                        if(Objects.equals(text_comment_return.content, "")){
                            text_info.this.runOnUiThread(() -> {
                                ImageView empty = findViewById(R.id.empty_comment);
                                empty.setVisibility(View.VISIBLE);
                            });
                        }else {
                            JSONObject processjsonObject = new JSONObject(text_comment_return.content);
                            //String process_list = processjsonObject.get("process_list").toString();
                            JSONArray text_comment_list = processjsonObject.getJSONArray("list");
                            ArrayList<text_comment_model> textCommentModels = new ArrayList<>();
                            for (int i = 0; i < text_comment_list.length(); i++) {
                                //获取json对象
                                JSONObject text_comment_listJSONObject = text_comment_list.getJSONObject(i);
                                String commenturl = "http://software.toolr.cn/commentmarkdown.php?id=" + text_comment_listJSONObject.get("ID").toString();
                                String user_url = "http://software.toolr.cn/api.php?type=logininfo&ID=" + text_comment_listJSONObject.get("评论ID").toString();
                                Http user_comment_http = new Http();
                                //Log.i("链接",url);
                                user_comment_http.setUrl(user_url).setRequestType(Http.REQUEST_TYPE_GET);
                                Http.Request user_comment_return = user_comment_http.request();
                                JSONObject user_comment_data = new JSONObject(user_comment_return.content);
                                String user_comment_tx = user_comment_data.get("头像").toString();
                                String user_comment_name = user_comment_data.get("Name").toString();
                                String user_comment_qx = user_comment_data.get("用户权限").toString();
                                if (user_comment_tx.equals("0")) {
                                    String user_txx = "http://software.toolr.cn/code/tp/user.png";
                                    textCommentModels.add(new text_comment_model(text_comment_listJSONObject.get("ID").toString(), text_comment_listJSONObject.get("评论ID").toString(), commenturl, "发布于"+text_comment_listJSONObject.get("时间").toString(), user_comment_name, user_txx, user_comment_qx));
                                }else{
                                    textCommentModels.add(new text_comment_model(text_comment_listJSONObject.get("ID").toString(), text_comment_listJSONObject.get("评论ID").toString(), commenturl, "发布于"+text_comment_listJSONObject.get("时间").toString(), user_comment_name, user_comment_tx, user_comment_qx));
                                }
                            }
                            text_info.this.runOnUiThread(() -> {
                                RecyclerView recyclerView = findViewById(R.id.text_comment_Recycler);
                                text_comment_recyclerviewadapter recyclerViewAdapter = new text_comment_recyclerviewadapter(text_info.this, textCommentModels);
                                recyclerView.setAdapter(recyclerViewAdapter);
                                recyclerView.setLayoutManager(new LinearLayoutManager(text_info.this));
                            });

                        }
                FloatingActionButton fab=findViewById(R.id.upload_button);
                fab.setOnClickListener(v -> {

                    BottomMenu.show(new String[]{"发布评论", "举报此贴"})
                            .setOnIconChangeCallBack(new OnIconChangeCallBack(true) {
                                @Override
                                public int getIcon(BaseDialog dialog, int index, String menuText) {
                                    return 0;
                                }

                            })
                            .setOnMenuItemClickListener((dialog, text, index) -> {
                                if (text=="发布评论"){

                                    BottomDialog.show("", "",
                                            new OnBindView<BottomDialog>(R.layout.post_comment) {
                                                @Override
                                                public void onBind(BottomDialog dialog, View v) {
                                                    TextView post_comment_text_id=v.findViewById(R.id.post_comment_text);
                                                    TextView send_comment_id=v.findViewById(R.id.post_comment);
                                                    CardView code_area_id=v.findViewById(R.id.codearea);
                                                    CardView img_into_id=v.findViewById(R.id.imginto);
                                                    post_comment_text_id.requestFocus();
                                                    code_area_id.setOnClickListener(v1 -> {
                                                        post_comment_text_id.setText("```\n"+"//这里写入代码"+"\n```"+post_comment_text_id.getText());
                                                    });
                                                    send_comment_id.setOnClickListener(v12 -> {
                                                        WaitDialog.show("正在评论...");
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
                                                                String password = jsonObject.get("password").toString();
                                                                Http post_comment_http=new Http();
                                                                post_comment_http.setUrl("http://software.toolr.cn/api.php?type=comment&ID="+account+"&password="+password+"&codeid="+textid+"&commentid=0&").setRequestType(Http.REQUEST_TYPE_POST);
                                                                post_comment_http.setData("message="+post_comment_text_id.getText());
                                                                Http.Request post_comment_return=post_comment_http.request();
                                                                if (Objects.equals(post_comment_return.content, "评论成功")){
                                                                    runOnUiThread(() -> PopNotification.show("评论成功"));
                                                                    try {
                                                                        Http text_comment_http=new Http();
                                                                        text_comment_http.setUrl("http://software.toolr.cn/api.php?type=commentlist&ID="+textid).setRequestType(Http.REQUEST_TYPE_GET);
                                                                        Http.Request text_comment_return=text_comment_http.request();
                                                                        Log.i("评论回调",text_comment_return.content);
                                                                        if(Objects.equals(text_comment_return.content, "")){
                                                                            text_info.this.runOnUiThread(() -> {
                                                                                ImageView empty = findViewById(R.id.empty_comment);
                                                                                empty.setVisibility(View.VISIBLE);
                                                                            });
                                                                        }else {
                                                                            JSONObject processjsonObject = new JSONObject(text_comment_return.content);
                                                                            //String process_list = processjsonObject.get("process_list").toString();
                                                                            JSONArray text_comment_list = processjsonObject.getJSONArray("list");
                                                                            ArrayList<text_comment_model> textCommentModels = new ArrayList<>();
                                                                            for (int i = 0; i < text_comment_list.length(); i++) {
                                                                                //获取json对象
                                                                                JSONObject text_comment_listJSONObject = text_comment_list.getJSONObject(i);
                                                                                String commenturl = "http://software.toolr.cn/commentmarkdown.php?id=" + text_comment_listJSONObject.get("ID").toString();
                                                                                String user_url = "http://software.toolr.cn/api.php?type=logininfo&ID=" + text_comment_listJSONObject.get("评论ID").toString();
                                                                                Http user_comment_http = new Http();
                                                                                //Log.i("链接",url);
                                                                                user_comment_http.setUrl(user_url).setRequestType(Http.REQUEST_TYPE_GET);
                                                                                Http.Request user_comment_return = user_comment_http.request();
                                                                                JSONObject user_comment_data = new JSONObject(user_comment_return.content);
                                                                                String user_comment_tx = user_comment_data.get("头像").toString();
                                                                                String user_comment_name = user_comment_data.get("Name").toString();
                                                                                String user_comment_qx = user_comment_data.get("用户权限").toString();
                                                                                if (user_comment_tx.equals("0")) {
                                                                                    String user_txx = "http://software.toolr.cn/code/tp/user.png";
                                                                                    textCommentModels.add(new text_comment_model(text_comment_listJSONObject.get("ID").toString(), text_comment_listJSONObject.get("评论ID").toString(), commenturl, "发布于" + text_comment_listJSONObject.get("时间").toString(), user_comment_name, user_txx, user_comment_qx));
                                                                                text_info.this.runOnUiThread(() -> {
                                                                                    RecyclerView recyclerView = findViewById(R.id.text_comment_Recycler);
                                                                                    text_comment_recyclerviewadapter recyclerViewAdapter = new text_comment_recyclerviewadapter(text_info.this, textCommentModels);
                                                                                    recyclerView.setAdapter(recyclerViewAdapter);
                                                                                    recyclerView.setLayoutManager(new LinearLayoutManager(text_info.this));
                                                                                });
                                                                                } else {
                                                                                    textCommentModels.add(new text_comment_model(text_comment_listJSONObject.get("ID").toString(), text_comment_listJSONObject.get("评论ID").toString(), commenturl, "发布于" + text_comment_listJSONObject.get("时间").toString(), user_comment_name, user_comment_tx, user_comment_qx));
                                                                                    text_info.this.runOnUiThread(() -> {
                                                                                        RecyclerView recyclerView = findViewById(R.id.text_comment_Recycler);
                                                                                        text_comment_recyclerviewadapter recyclerViewAdapter = new text_comment_recyclerviewadapter(text_info.this, textCommentModels);
                                                                                        recyclerView.setAdapter(recyclerViewAdapter);
                                                                                        recyclerView.setLayoutManager(new LinearLayoutManager(text_info.this));
                                                                                    });
                                                                                }
                                                                            }
                                                                            text_info.this.runOnUiThread(() -> {
                                                                                ImageView empty = findViewById(R.id.empty_comment);
                                                                                empty.setVisibility(View.GONE);
                                                                            });

                                                                        }
                                                                    } catch (Exception e) {
                                                                        throw new RuntimeException(e);
                                                                    }
                                                                    WaitDialog.dismiss();
                                                                    post_comment_text_id.setText("");
                                                                }else{
                                                                    runOnUiThread(() -> PopNotification.show(post_comment_return.content));
                                                                    WaitDialog.dismiss();
                                                                }
                                                            } catch (Exception e) {
                                                                throw new RuntimeException(e);
                                                            }
                                                        }).start();
                                                    });
                                                }
                                            });






                                } else if (text=="举报此贴") {
                                    joinQQGroup("9zhvp729KmL0p20Bg3-Hfd8wQCETevIx");
                                }
                                return false;
                            });








                });
                runOnUiThread(() -> {
                    WebSettings webSettings = text_text_id.getSettings();
                    // 设置与Js交互的权限
                    webSettings.setJavaScriptEnabled(true);
                    // 设置允许JS弹窗
                    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                    text_text_id.loadUrl("http://software.toolr.cn/markdown.php?id="+textid);
                    text_title_id.setText(text_title);
                    text_time_top_id.setText(user_qm);
                    text_time_bottom_id.setText("发布于 "+text_time);
                    text_views_id.setText(text_views);
                    text_author_name.setText(user_name);
                    text_qx_id.setText(user_yhqx);
                    CardView text_qx_card=findViewById(R.id.text_qx_card);
                    if (user_yhqx.equals("普通用户")){text_qx_card.setVisibility(View.GONE);}
                    if(user_tx.equals("0")){
                        Glide.with(text_info.this).load("http://software.toolr.cn/code/tp/user.png").into(text_img_id);
                    }else{Glide.with(text_info.this).load(user_tx).into(text_img_id);}
                    if (user_vip.equals("已过期")){}else{text_author_name.setTextColor(R.color.Red);}
                    WaitDialog.dismiss();
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }


        }).start();

    }
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            PopNotification.show("未安装QQ或版本不支持");
            return false;
        }
    }
}
