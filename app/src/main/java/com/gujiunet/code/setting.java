package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.kongzue.dialogx.dialogs.CustomDialog;
import com.kongzue.dialogx.dialogs.PopNotification;
import com.kongzue.dialogx.interfaces.OnBindView;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;

public class setting extends AppCompatActivity {


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        StatusBarUtil status=new StatusBarUtil();
        status.setStatusColor();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
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
            String url = "http://software.toolr.cn/api.php?type=login&ID=" + account + "&password=" + password;
            //String post="type=adminlogin&ID="+account+"&password="+text;
            //String zhmm = "<account>" + account + "<account><password>" + text + "<password>";

            new Thread(() -> {
                Http http = new Http();
                Log.i("链接", url);
                http.setUrl(url).setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request result = http.request();
                //Log.i("内容",result.getContent());
                Log.i("内容1", String.valueOf(result.getContent()));
                //1810820293
                if (result.getContent().equals("登录成功")) {
                    String userurl = "http://software.toolr.cn/api.php?type=logininfo&ID=" + account;
                    Http userhttp = new Http();
                    //Log.i("链接",url);
                    userhttp.setUrl(userurl).setRequestType(Http.REQUEST_TYPE_GET);
                    Http.Request user_return_data = userhttp.request();
                    Log.i("数据", user_return_data.content);
                    try {
                        JSONObject userdatajson = new JSONObject(user_return_data.content);
                        String user_id = userdatajson.get("ID").toString();
                        String user_name = userdatajson.get("Name").toString();
                        String user_qq = userdatajson.get("QQ").toString();
                        String user_qm = userdatajson.get("签名").toString();
                        String user_regip = userdatajson.get("注册IP").toString();
                        String user_lastlogin = userdatajson.get("最后登录").toString();
                        String user_coin = userdatajson.get("金币").toString();
                        String user_vip = userdatajson.get("会员").toString();
                        String user_regtime = userdatajson.get("注册时间").toString();
                        String user_tx = userdatajson.get("头像").toString();
                        String user_yhqx = userdatajson.get("用户权限").toString();
                        String user_buycode = userdatajson.get("购买源码").toString();
                        //File folder = new File("/storage/emulated/0/Android/data/com.gujiunet.code"); // 将"路径/到/目标文件夹"替换为要计算大小的文件夹的真实路径

                        View change_sign_id=findViewById(R.id.sign_change);
                        change_sign_id.setOnClickListener(v -> {
                            CustomDialog.show(new OnBindView<CustomDialog>(R.layout.editdialog) {
                                @Override
                                public void onBind(CustomDialog dialog, View v) {
                                    CardView edit_change=v.findViewById(R.id.edit_change);
                                    edit_change.setOnClickListener(v1 -> {
                                        TextView edittext=v.findViewById(R.id.edittext);
                                        if (edittext.getText()==""){
                                            PopNotification.show("请写入信息");
                                        }else{
                                            new Thread(() -> {
                                                Http change_sign_http=new Http();
                                                change_sign_http.setUrl("http://software.toolr.cn/api.php?type=change&ID="+account+"&password="+password+"&Name="+user_name+"&QQ="+user_qq+"&qm="+edittext.getText()+"&tx="+user_tx).setRequestType(Http.REQUEST_TYPE_GET);
                                                Http.Request change_sign_return=change_sign_http.request();
                                                Log.i("修改回调",change_sign_return.content);
                                                if (Objects.equals(change_sign_return.content, "修改成功")){
                                                    PopNotification.show("修改成功");
                                                    TextView nametag = findViewById(R.id.setting_tag);
                                                    setting.this.runOnUiThread(() -> nametag.setText(edittext.getText()));
                                                    dialog.dismiss();
                                                }else{
                                                    PopNotification.show(change_sign_return.content);
                                                }
                                            }).start();
                                        }
                                    });
                                }
                            }).setMaskColor(R.color.mask);
                        });
                        View change_name_id=findViewById(R.id.change_name);
                        change_name_id.setOnClickListener(v -> {
                            CustomDialog.show(new OnBindView<CustomDialog>(R.layout.editdialog) {
                                @Override
                                public void onBind(CustomDialog dialog, View v) {
                                    CardView edit_change=v.findViewById(R.id.edit_change);
                                    edit_change.setOnClickListener(v1 -> {
                                        TextView edittext=v.findViewById(R.id.edittext);
                                        if (edittext.getText()==""){
                                            PopNotification.show("请写入信息");
                                        }else{
                                            new Thread(() -> {
                                                Http change_sign_http=new Http();
                                                change_sign_http.setUrl("http://software.toolr.cn/api.php?type=change&ID="+account+"&password="+password+"&Name="+edittext.getText()+"&QQ="+user_qq+"&qm="+user_qm+"&tx="+user_tx).setRequestType(Http.REQUEST_TYPE_GET);
                                                Http.Request change_sign_return=change_sign_http.request();
                                                Log.i("修改回调",change_sign_return.content);
                                                if (Objects.equals(change_sign_return.content, "修改成功")){
                                                    PopNotification.show("修改成功");
                                                    TextView settingname_id = findViewById(R.id.setting_name);
                                                    setting.this.runOnUiThread(() -> settingname_id.setText(edittext.getText()));
                                                    dialog.dismiss();
                                                }else{
                                                    PopNotification.show(change_sign_return.content);
                                                }
                                            }).start();
                                        }
                                    });
                                }
                            }).setMaskColor(R.color.mask);
                        });




                        File cacheDir=getCacheDir();
                        long sizeInBytes = getFolderSize(cacheDir);
                        Log.i("文件夹大小", String.valueOf(sizeInBytes));
                        double sizeInMB = (double)sizeInBytes / (1024 * 1024);
                        BigDecimal bg = new BigDecimal(sizeInMB);
                        double f1 = bg.setScale(4, RoundingMode.HALF_UP).doubleValue();
                        View deletecache=findViewById(R.id.cache_button);
                        /*
                        deletecache.setOnClickListener(v -> {
                            Context context = getApplicationContext();
                            File[] files = cacheDir.listFiles();
                            for (File file : files) {
                                boolean isDeleted =file.delete();
                                if (isDeleted) {
                                    long refreshsizeInBytes = getFolderSize(cacheDir);
                                    //Log.i("文件夹大小", String.valueOf(refreshsizeInBytes));
                                    double refreshsizeInMB = (double)refreshsizeInBytes / (1024 * 1024);
                                    BigDecimal abg = new BigDecimal(refreshsizeInMB);
                                    double af1 = abg.setScale(2, RoundingMode.HALF_UP).doubleValue();
                                    runOnUiThread(() -> {
                                        PopNotification.show("清除成功！");
                                        TextView setting_size_id=findViewById(R.id.setting_size);
                                        setting_size_id.setText(af1+"MB");
                                    });
                                } else {
                                    PopNotification.show("清除失败！");
                                }
                            }
                        });*/
                        runOnUiThread(() -> {
                            TextView setting_size_id=findViewById(R.id.setting_size);
                            ImageView head_id = findViewById(R.id.setting_imageview);
                            TextView settingname_id = findViewById(R.id.setting_name);
                            TextView nametag = findViewById(R.id.setting_tag);
                            settingname_id.setText(user_name);
                            nametag.setText(user_qm);
                            setting_size_id.setText(f1+"MB");

                            Glide.with(getApplicationContext()).load(user_tx).into(head_id);
                        });

                        View setting_button = findViewById(R.id.leave_setting_button);
                        setting_button.setOnClickListener(v -> finish());
                        View head_icon_id=findViewById(R.id.head_icon_button);
                        head_icon_id.setOnClickListener(v -> {

                            PictureSelector.create(this)
                                    .openSystemGallery(SelectMimeType.ofImage())
                                    .forSystemResult(new OnResultCallbackListener<LocalMedia>() {
                                        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                                        @Override
                                        public void onResult(ArrayList<LocalMedia> result) {

                                            for (LocalMedia lm: result) {
                                                Log.i("选择图片", String.valueOf(result.get(lm.position).getRealPath()));
                                                String userpic="http://software.toolr.cn/tx/"+account+".png";
                                                String uploadurl="http://software.toolr.cn/upload.php?type=login&ID="+account+"&password="+password+"&title="+account;
                                                String updateinfo="http://software.toolr.cn/api.php?type=change&ID="+account+"&password="+password+"&Name="+user_name+"&qm="+user_qm+"&tx="+userpic+"&QQ="+user_qq;
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
                                                        Log.i("上传链接",uploadurl);
                                                        Log.i("内容", String.valueOf(upload_return.getContent()));
                                                        Log.i("文件:::", String.valueOf(result.get(lm.position).getPath()));
                                                        if (String.valueOf(upload_return.getContent()).equals("登录成功头像上传成功!")){
                                                            Http updateinfo_http=new Http();
                                                            updateinfo_http.setUrl(updateinfo).setRequestType(Http.REQUEST_TYPE_GET);
                                                            Http.Request update_info_return=updateinfo_http.request();
                                                            if(String.valueOf(update_info_return.getContent()).equals("修改成功")){
                                                                runOnUiThread(() -> {
                                                                    PopNotification.show("头像上传成功！");
                                                                    ImageView head_id = findViewById(R.id.setting_imageview);
                                                                    Glide.with(getApplicationContext()).load(userpic).into(head_id);
                                                                });
                                                            }
                                                        }
                                                        else {
                                                            runOnUiThread(() -> PopNotification.show("头像上传失败！"));
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
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    View setting_about_id=findViewById(R.id.setting_about_button);
                    setting_about_id.setOnClickListener(v -> {
                        startActivity(new Intent().setClass(this, about.class));
                    });
                    View leave_login_id=findViewById(R.id.leave_login);
                    View setting_share_id=findViewById(R.id.setting_share);
                    setting_share_id.setOnClickListener(v -> {
                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "分享资源，交流讨论，http://software.toolr.cn/index/");
                        sendIntent.putExtra(Intent.EXTRA_TITLE, "CODE");
                        sendIntent.setType("text/plain");
                        Intent shareIntent = Intent.createChooser(sendIntent, null);
                        startActivity(shareIntent);
                    });
                    leave_login_id.setOnClickListener(v -> {
                        String accountfilePath = "/storage/emulated/0/Android/data/com.gujiunet.code/account.json"; // 这里的"包名"应为你自己项目的包名，而"filename.txt"则为要删除的文件名

// 创建File对象并判断文件是否存在
                        File file = new File(accountfilePath);
                        if (file.exists()) {
                            if (file.delete()) {
                                runOnUiThread(() -> {
                                    Intent intent = new Intent(this, login.class);
                                    startActivity(intent);
                                    Toast.makeText(this, "退出成功", Toast.LENGTH_SHORT).show();
                                });
                            } else {
                                System.err.println("无法删除文件");
                            }
                        } else {
                            System.err.println("文件不存在");
                        }
                    });

                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent();
                        intent.setClass(this, login.class);
                        startActivity(intent);
                    });
                }
            }).start();
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }


    }

        private static long getFolderSize(File file) {
            if (!file.exists()) return 0L;

            long totalSize = 0;

            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    totalSize += getFolderSize(child);
                }
            } else {
                totalSize += file.length();
            }

            return totalSize;
        }


}

