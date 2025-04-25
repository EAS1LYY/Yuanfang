package com.gujiunet.code;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.kongzue.dialogx.dialogs.MessageDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class login extends AppCompatActivity {
    private MessageDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //弹窗初始化
        StatusBarUtil status=new StatusBarUtil();
        status.setStatusColor();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        setContentView(R.layout.app_login);
        Button button = findViewById(R.id.button);
        //状态栏颜色改变为主题蓝

        String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/account.json";
        File afile = new File(filePath);
        Button reg=findViewById(R.id.reg);
        reg.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(login.this, reg.class);
            startActivity(intent);
        });
        button.setOnClickListener(v -> {
            EditText loginText = findViewById(R.id.account);
            String account = loginText.getText().toString();
            EditText passwordText = findViewById(R.id.password);
            String text = passwordText.getText().toString();
            String url="http://software.toolr.cn/api.php?type=login&ID="+account+"&password="+text;
            //String post="type=adminlogin&ID="+account+"&password="+text;
            String zhmm="<account>"+account+"<account><password>"+text+"<password>";
            new Thread(() -> {
                Http http= new Http();
                Log.i("链接",url);
                http.setUrl(url).setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request result = http.request();
                //Log.i("内容",result.getContent());
                Log.i("内容1",String.valueOf(result.getContent()));
                //1810820293
                if(result.getContent().equals("登录成功")){
                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj.put("account", account);
                        jsonObj.put("password", text);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    //String filePath = "/storage/emulated/0/Android/data/com.gujiunet.codeback/account.json";
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
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(() -> {
                        finish();
                        Toast.makeText(login.this, "登录成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        intent.setClass(login.this, MainActivity.class);
                                        startActivity(intent);
                    });


                } else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(login.this,"账号或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }).start();
        });
    }

    @Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            MessageDialog.show("提示", "确定退出吗？").setCancelable(false).setOkButton("确定", (baseDialog, v) -> {
                finish();
                return false;
            }).setCancelButton("取消", (baseDialog, v) -> false);;
        }
        return super.onKeyDown(keyCode, event);
    }


}