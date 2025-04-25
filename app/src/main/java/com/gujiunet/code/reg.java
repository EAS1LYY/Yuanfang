package com.gujiunet.code;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.dialogs.PopNotification;
import com.kongzue.dialogx.dialogs.WaitDialog;

import java.util.Objects;

public class reg extends AppCompatActivity {
    private MessageDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //弹窗初始化
        StatusBarUtil status=new StatusBarUtil();
        status.setStatusColor();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        setContentView(R.layout.register);
        Button login_btn=findViewById(R.id.login_button);
        login_btn.setOnClickListener(v -> finish());
        Button reg_btn=findViewById(R.id.reg);
        reg_btn.setOnClickListener(v -> {
            new Thread(() -> {
                WaitDialog.show("正在注册...");
                EditText mail_text=findViewById(R.id.mail);
                EditText account_text=findViewById(R.id.reg_user);
                EditText password_text=findViewById(R.id.reg_password);
                String mail=mail_text.getText().toString();
                String account=account_text.getText().toString();
                String password=password_text.getText().toString();
                Http register_http=new Http();
                register_http.setUrl("http://software.toolr.cn/api.php?type=reg&ID="+account+"&Name="+account+"&password="+password+"&mail="+mail).setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request reg_return=register_http.request();
                if(Objects.equals(reg_return.content, "注册成功")){
                    runOnUiThread(() -> {
                        WaitDialog.dismiss();
                        Toast.makeText(this, "注册成功！", Toast.LENGTH_SHORT).show();
                    });

                    finish();
                }else{
                    runOnUiThread(() -> {
                        WaitDialog.dismiss();
                        PopNotification.show(reg_return.content);
                    });

                }
            }).start();
        });
    }
}
