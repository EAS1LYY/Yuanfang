package com.gujiunet.code;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.kongzue.dialogx.dialogs.PopNotification;
import com.kongzue.dialogx.dialogs.WaitDialog;

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
import java.util.Objects;

public class code_info extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_info);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        new Thread(() -> {

            //新建一个File，传入文件夹目录
            File code = new File("/storage/emulated/0/Android/data/com.gujiunet.code/code");
//判断文件夹是否存在，如果不存在就创建，否则不创建
            if (!code.exists()) {
                //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
                code.mkdirs();
            }


            WaitDialog.show("正在加载...");

            try {
                //获取本地的Json文件
                InputStream open = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    open = Files.newInputStream(Paths.get("/storage/emulated/0/Android/data/com.gujiunet.code/code.json"));
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
                String codeid = jsonObject.get("codeid").toString();
                Http http=new Http();
                http.setUrl("http://software.toolr.cn/api.php?type=codeinfo&codeid="+codeid);
                Http.Request urlreturn = http.request();
                JSONObject count = new JSONObject(urlreturn.content);
                String code_name = count.get("名称").toString();
                String code_text = count.get("介绍").toString();
                String code_coin = count.get("价格").toString();
                String code_img_1 = count.get("图片1").toString();
                String code_img_2 = count.get("图片2").toString();
                String code_img_3 = count.get("图片3").toString();
                String code_create = count.get("作者").toString();
                //String code_download_url = count.get("下载地址").toString();
                String code_icon = count.get("图标").toString();
                String code_size = count.get("大小").toString();
                String code_download=count.get("下载量").toString();
                Http create_http=new Http();
                create_http.setUrl("http://software.toolr.cn/api.php?type=logininfo&ID="+code_create).setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request create_return=create_http.request();
                JSONObject create_info=new JSONObject(create_return.content);
                String create_img=create_info.get("头像").toString();
                String create_name=create_info.get("Name").toString();
                View leave=findViewById(R.id.leave_about_button);
                leave.setOnClickListener(v -> finish());
                //获取本地的Json文件
                InputStream aopen = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    aopen = Files.newInputStream(Paths.get("/storage/emulated/0/Android/data/com.gujiunet.code/account.json"));
                }
                InputStreamReader aisr = new InputStreamReader(aopen, StandardCharsets.UTF_8);
                //包装字符流,将字符流放入缓存里
                BufferedReader abr = new BufferedReader(aisr);
                String aline;
                //StringBuilder和StringBuffer功能类似,存储字符串
                StringBuilder abuilder = new StringBuilder();
                while ((aline = abr.readLine()) != null) {
                    //append 被选元素的结尾(仍然在内部)插入指定内容,缓存的内容依次存放到builder中
                    abuilder.append(aline);
                }
                br.close();
                aisr.close();
                //builder.toString() 返回表示此序列中数据的字符串
                //System.out.println(builder);
                //使用Json解析
                JSONObject ajsonObject = new JSONObject(abuilder.toString());
                //System.out.println(jsonObject);
                String account = ajsonObject.get("account").toString();
                String password = ajsonObject.get("password").toString();
                Http user_info_http=new Http();
                user_info_http.setUrl("http://software.toolr.cn/api.php?type=logininfo&ID="+account+"&password="+password).setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request user_info=user_info_http.request();
                JSONObject userdatajson = new JSONObject(user_info.content);
                String user_buycode = userdatajson.get("购买源码").toString();
                runOnUiThread(() -> {
                    TextView info_download=findViewById(R.id.info_code_download);
                    TextView create_name_id=findViewById(R.id.create_name);
                    ImageView create_img_id=findViewById(R.id.create_img);
                    TextView code_name_id=findViewById(R.id.info_code_title);
                    TextView code_text_id=findViewById(R.id.info_code_text);
                    TextView code_size_id=findViewById(R.id.info_code_size);
                    //TextView code_create_id=findViewById(R.id.code_info_create);
                    TextView code_coin_id=findViewById(R.id.info_code_coin);
                    ImageView info_img_icon=findViewById(R.id.info_code_icon);
                    ImageView info_img_1=findViewById(R.id.info_code_image_1);
                    ImageView info_img_2=findViewById(R.id.info_code_image_2);
                    ImageView info_img_3=findViewById(R.id.info_code_image_3);
                    Button buy_button=findViewById(R.id.buy);
                    Button download_button=findViewById(R.id.download);
                    Button import_button=findViewById(R.id.importcode);
                    if (user_buycode.contains(codeid)){
                        buy_button.setVisibility(View.GONE);
                        download_button.setVisibility(View.VISIBLE);
                    }
                    File codefile=new File("/storage/emulated/0/Android/data/com.gujiunet.code/code/"+code_name+".iApp");
                    if (codefile.exists()) {
                        download_button.setVisibility(View.GONE);
                        import_button.setVisibility(View.VISIBLE);
                    }


                    import_button.setOnClickListener(v -> {
                        Uri contentUri = FileProvider.getUriForFile(this, this.getPackageName() + ".fileprovider", codefile);
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                                | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
                        this.startActivity(Intent.createChooser(shareIntent, null));
                    });

                    info_download.setText(code_download);
                    info_img_1.setOnClickListener(v -> {
                        JSONObject jsonObj = new JSONObject();
                        try {
                            jsonObj.put("img", code_img_1);
                            String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/img.json";
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
                                intent.setClass(code_info.this, img.class);
                                startActivity(intent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    info_img_2.setOnClickListener(v -> {
                        JSONObject jsonObj = new JSONObject();
                        try {
                            jsonObj.put("img", code_img_2);
                            String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/img.json";
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
                                intent.setClass(code_info.this, img.class);
                                startActivity(intent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    info_img_3.setOnClickListener(v -> {
                        JSONObject jsonObj = new JSONObject();
                        try {
                            jsonObj.put("img", code_img_3);
                            String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/img.json";
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
                                intent.setClass(code_info.this, img.class);
                                startActivity(intent);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }



                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    View create_id=findViewById(R.id.create_button);
                    View jubao_id=findViewById(R.id.jubao);
                    jubao_id.setOnClickListener(v -> {
                        joinQQGroup("9zhvp729KmL0p20Bg3-Hfd8wQCETevIx");
                    });
                    create_id.setOnClickListener(v -> {
                        JSONObject jsonObj = new JSONObject();
                        try {
                            jsonObj.put("createid", code_create);
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
                            intent.setClass(code_info.this, user_info.class);
                            startActivity(intent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Http createhttp=new Http();
                        createhttp.setUrl("http://software.toolr.cn/api.php?type=getusercodejson&ID="+code_create+"&page=1").setRequestType(Http.REQUEST_TYPE_GET);
                        Http.Request codereturn=createhttp.request();
                        user_code.updateVarInfo(codereturn.content);
                    });
                    buy_button.setOnClickListener(v -> {
                        AlertDialog.Builder update_dialog = new AlertDialog.Builder(this);
                        update_dialog.setTitle("提示");
                        update_dialog.setMessage("确定用"+code_coin+"金币购买["+code_name+"]?").setCancelable(false);
                        update_dialog.setPositiveButton("确定", (dialog, which) -> {
                            new Thread(() -> {
                                Http buy_code_http=new Http();
                                buy_code_http.setUrl("http://software.toolr.cn/api.php?type=buycode&ID="+account+"&password="+password+"&codeid="+codeid).setRequestType(Http.REQUEST_TYPE_GET);
                                Http.Request return_buy_code=buy_code_http.request();
                                Log.i("购买回调",return_buy_code.content);
                                if (Objects.equals(return_buy_code.getContent(), "购买成功")){
                                    runOnUiThread(() -> {
                                        PopNotification.show("购买成功!");
                                        buy_button.setVisibility(View.GONE);
                                        download_button.setVisibility(View.VISIBLE);
                                    });
                                }else{
                                    runOnUiThread(() -> {
                                        PopNotification.show(return_buy_code.getContent());
                                    });
                                }
                            }).start();
                        });


                        update_dialog.setNegativeButton("取消", (dialog, which) -> {
                            dialog.cancel();
                            Log.i("事件", "点击了取消");
                        });
                        AlertDialog utw = update_dialog.create();
                        utw.show();
                    });
                    download_button.setOnClickListener(v -> {
                        WaitDialog.show("正在下载...");
                        AlertDialog.Builder update_dialog = new AlertDialog.Builder(this);
                        update_dialog.setTitle("提示");
                        update_dialog.setMessage("确定下载["+code_name+"]?").setCancelable(false);
                        update_dialog.setPositiveButton("确定", (dialog, which) -> {
                            new Thread(() -> {
                                Http downloadurlhttp=new Http();
                                downloadurlhttp.setUrl("http://software.toolr.cn/api.php?type=downloadcode&ID="+account+"&password="+password+"&codeid="+codeid).setRequestType(Http.REQUEST_TYPE_GET);
                                Http.Request downloadurl=downloadurlhttp.request();
                                Http downloadhttp = new Http();
                                downloadhttp.setUrl(downloadurl.content).setFilePath("/storage/emulated/0/Android/data/com.gujiunet.code/code/"+code_name+".iApp").setRequestType(Http.REQUEST_TYPE_DOWNLOAD)
                                        .setRequest(new Http.RequestEvent() {
                                            @Override
                                            public void requestComplete(int code, String content, String cookie) {
                                                //请求结束 code=200即请求完成
                                                if (code==200){
                                                    runOnUiThread(() -> {
                                                        PopNotification.show("下载完成");
                                                        download_button.setVisibility(View.GONE);
                                                        import_button.setVisibility(View.VISIBLE);
                                                        WaitDialog.dismiss();
                                                    });
                                                }
                                                Log.i("下载状态回调", String.valueOf(code));
                                            }

                                            @Override
                                            public void downloadProgress(long length, double percentage) {
                                                //下载进度 length=下载的字节大小,percentage=下载进度百分比
                                            }

                                            @Override
                                            public void updateProgress(long length, double percentage) {
                                                //上传进度 length=上传的字节大小,percentage=上次进度百分比
                                                //进度只支持单个文件目前不支持多文件上传
                                            }
                                        }).request();
                                Log.i("下载回调",downloadurl.content);
                            }).start();
                        });


                        update_dialog.setNegativeButton("取消", (dialog, which) -> {
                            WaitDialog.dismiss();
                            dialog.cancel();
                            Log.i("事件", "点击了取消");
                        });
                        AlertDialog utw = update_dialog.create();
                        utw.show();
                    });



                    create_name_id.setText(create_name);
                    code_coin_id.setText(code_coin);
                    //code_create_id.setText(code_create);
                    code_name_id.setText(code_name);
                    code_size_id.setText(code_size);
                    code_text_id.setText(code_text);
                    Glide.with(getApplicationContext()).load(code_icon).into(info_img_icon);
                    Glide.with(getApplicationContext()).load(code_img_1).override(1280,720).into(info_img_1);
                    Glide.with(getApplicationContext()).load(create_img).override(1280,720).into(create_img_id);
                    Glide.with(getApplicationContext()).load(code_img_2).override(1280,720).into(info_img_2);
                    Glide.with(getApplicationContext()).load(code_img_3).override(1280,720).into(info_img_3);


                });
            } catch (JSONException | IOException e) {
                throw new RuntimeException(e);
            }
            WaitDialog.dismiss();
        }).start();


    }
    public static String getMimeType(File file) {
        String extension = getFileExtension(file.getName());
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            return mime.getMimeTypeFromExtension(extension);
        }
        return null;
    }

    public static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0) {
            return null;
        }
        return fileName.substring(dotIndex + 1);
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
