package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.kongzue.dialogx.dialogs.BottomMenu;
import com.kongzue.dialogx.dialogs.FullScreenDialog;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.dialogs.PopNotification;
import com.kongzue.dialogx.interfaces.BaseDialog;
import com.kongzue.dialogx.interfaces.OnBindView;
import com.kongzue.dialogx.interfaces.OnIconChangeCallBack;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class post extends AppCompatActivity {
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
        setContentView(R.layout.post);
        new Thread(() -> {
        View leave_post_button_id=findViewById(R.id.leave_post_button);
        TextView post_title_id=findViewById(R.id.post_title);
        TextView post_text_id=findViewById(R.id.post_text);
        View intocode_id=findViewById(R.id.intocode);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) CardView post_art_id=findViewById(R.id.post_art);

        try {
            String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/account.json";
            //获取本地的Json文件
            InputStream open = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                open = Files.newInputStream(Paths.get(filePath));
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
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) View uploadimg_id=findViewById(R.id.upload_img);
        uploadimg_id.setOnClickListener(v -> {
            String filename=System.currentTimeMillis()+account;
            PictureSelector.create(this)
                    .openSystemGallery(SelectMimeType.ofImage())
                    .forSystemResult(new OnResultCallbackListener<LocalMedia>() {
                        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                        @Override
                        public void onResult(ArrayList<LocalMedia> result) {

                            for (LocalMedia lm: result) {
                                Log.i("选择图片", String.valueOf(result.get(lm.position).getRealPath()));
                                String uploadurl="http://software.toolr.cn/uploadimg.php?type=login&ID="+account+"&password="+password+"&title="+filename;
                                try {
                                    Uri uri = Uri.parse(result.get(lm.position).getPath());
                                    ContentResolver cp = getContentResolver();
                                    InputStream updateInputStream = cp.openInputStream(uri);
                                    //copy to
                                    File newUserPngFile = new File(getDataDir().getAbsoluteFile() +"/" + result.get(lm.position).getFileName());
                                    if(!newUserPngFile.exists()) {
                                        newUserPngFile.createNewFile();
                                    }
                                    FileOutputStream newUserPng = new FileOutputStream(newUserPngFile);
                                    Log.i("用户头像路径",newUserPngFile.toString());
                                    byte[] bytes = new byte[1024];
                                    int len;
                                    while ((len = updateInputStream.read(bytes)) != -1) {
                                        newUserPng.write(bytes, 0, len);
                                    }
                                    newUserPng.close();
                                    new Thread(() -> {
                                        Http uploadpic=new Http();
                                        uploadpic.setUrl(uploadurl).setRequestType(Http.REQUEST_TYPE_UPDATE);
                                        uploadpic.setFilePath(newUserPngFile.toString());
                                        Http.Request upload_return = uploadpic.request();
                                        if (String.valueOf(upload_return.getContent()).equals("登录成功头像上传成功!")){
                                            runOnUiThread(() -> {PopNotification.show("图片上传成功");
                                            post_text_id.setText(post_text_id.getText()+"![图片名称](http://software.toolr.cn/picture/"+filename+".png)");});
                                        }
                                        else {
                                            runOnUiThread(() -> PopNotification.show("图片上传失败！"));
                                        }
                                    }).start();
                                } catch (Exception e) {
                                    Log.i("异常",e.toString());
                                }
                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    });





        });

        intocode_id.setOnClickListener(v -> {
            BottomMenu.show(new String[]{"代码块", "引用","提示","Markdown语法详解"})
                    .setOnIconChangeCallBack(new OnIconChangeCallBack(true) {
                        @Override
                        public int getIcon(BaseDialog dialog, int index, String menuText) {
                            return 0;
                        }
                    })
                    .setOnMenuItemClickListener((dialog, text, index) -> {
                        if (text=="代码块"){
                            post_text_id.setText(post_text_id.getText()+"```\n\\在这里写入代码\n```");
                        } else if (text=="引用") {
                            post_text_id.setText(post_text_id.getText()+">引用");
                        } else if (text=="提示") {
                            MessageDialog.show("关于Markdown的使用规范", "使用每个语法后一定要回车才能起作用,例如\n```\n//我是代码块\n```\n以上是一个完整的代码块\n>我是引用\n等等", "确定");
                        }else if (text=="Markdown语法详解"){
                            FullScreenDialog.show(new OnBindView<FullScreenDialog>(R.layout.web) {
                                @Override
                                public void onBind(FullScreenDialog dialog, View v) {
                                    WebView web=v.findViewById(R.id.web);
                                    WebSettings webSettings = web.getSettings();
                                    // 设置与Js交互的权限
                                    webSettings.setJavaScriptEnabled(true);
                                    // 设置允许JS弹窗
                                    webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
                                    web.loadUrl("https://www.runoob.com/markdown/md-title.html");
                                }
                            });
                        }
                        return false;
                    });
        });
        leave_post_button_id.setOnClickListener(v -> finish());
        post_art_id.setOnClickListener(v -> {
            if (post_title_id.getText()==""){
                PopNotification.show("标题为空");
            } else if (post_text_id.getText()=="") {
                PopNotification.show("内容为空");
            }else{
                new Thread(() -> {

                    Http post_art_http = new Http();
                    post_art_http.setUrl("http://software.toolr.cn/api.php?type=article&ID=" + account + "&password=" + password + "&title=" + post_title_id.getText() + "&assort=3&file=0").setRequestType(Http.REQUEST_TYPE_POST);
                    String post_text= String.valueOf(post_text_id.getText());
                    String text=post_text.replace("&", "%26");
                    post_art_http.setData("text=" + text);
                    Http.Request post_art_return = post_art_http.request();
                    Log.i("发布文章回调", post_art_return.content);
                    Log.i("发布文章链接", "http://software.toolr.cn/api.php?type=article&ID=" + account + "&password=" + password + "&title=" + post_title_id.getText() + "&assort=3&file=0");
                    if (Objects.equals(post_art_return.content, "发布成功")) {
                        runOnUiThread(() -> Toast.makeText(post.this, "发布成功", Toast.LENGTH_SHORT).show());
                        finish();
                    } else {
                        runOnUiThread(() -> {
                            PopNotification.show(post_art_return.content);
                        });
                    }
                }).start();
            }

        });
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
        }).start();
    }
}
