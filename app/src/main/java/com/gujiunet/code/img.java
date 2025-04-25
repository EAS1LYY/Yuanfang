package com.gujiunet.code;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class img extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.img);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.Blue));
        try {
            //获取本地的Json文件
            InputStream open = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                open = Files.newInputStream(Paths.get("/storage/emulated/0/Android/data/com.gujiunet.code/img.json"));
            }
            InputStreamReader isr = new InputStreamReader(open, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }
            br.close();
            isr.close();
            JSONObject jsonObject = new JSONObject(builder.toString());
            //System.out.println(jsonObject);
            String img = jsonObject.get("img").toString();
            ImageView image=findViewById(R.id.image);
            Glide.with(getApplicationContext()).load(img).override(1920,1080).into(image);
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}