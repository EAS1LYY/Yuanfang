package com.gujiunet.code;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.kongzue.dialogx.dialogs.BottomDialog;

public class nonetwork extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.nonetwork);
        BottomDialog.show("啊哦", "你的网络貌似出现了问题，快检查一下看看吧！\n\n\n\n\n\n\n");



        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Blue));
        Button exit=findViewById(R.id.exit);
        exit.setOnClickListener(v -> {
            finish();
        });
    }
}
