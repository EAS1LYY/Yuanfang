package com.gujiunet.code;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.kongzue.dialogx.dialogs.MessageDialog;

public class serch extends AppCompatActivity {
    private MessageDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //弹窗初始化
        StatusBarUtil status = new StatusBarUtil();
        status.setStatusColor();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        setContentView(R.layout.serch);
        View leave=findViewById(R.id.leave_serch_button);
        leave.setOnClickListener(v -> finish());
        new Thread(() -> {

        }).start();
    }
}
