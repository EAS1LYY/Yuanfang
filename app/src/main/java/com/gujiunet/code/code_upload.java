package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class code_upload extends AppCompatActivity {
    List<Fragment> fragmentList = new ArrayList<>();
    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.code_upload);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ViewPager mainviewPager = findViewById(R.id.upload_code);
        TabLayout bottomNavigationView = findViewById(R.id.upload_tablayout);
        code_upload_basic code_upload_basic = com.gujiunet.code.code_upload_basic.newInstance("基本信息", "");
        fragmentList.add(code_upload_basic);
        code_upload_detail commfragment = code_upload_detail.newInstance("详细信息", "");
        fragmentList.add(commfragment);
        MainActivityAdapter adapter = new MainActivityAdapter(this.getSupportFragmentManager(), fragmentList);
        mainviewPager.setOffscreenPageLimit(2);
        mainviewPager.setAdapter(adapter);
        bottomNavigationView.setupWithViewPager(mainviewPager);
        bottomNavigationView.getTabAt(0).setText("基本信息");
        bottomNavigationView.getTabAt(1).setText("详细信息");
        new Thread(() -> {
            TextView code_upload_name=findViewById(R.id.code_upload_name);
            TextView code_upload_pack=findViewById(R.id.code_upload_pack);
            TextView code_upload_version=findViewById(R.id.code_upload_version);
            TextView code_upload_size=findViewById(R.id.code_upload_size);
            ImageView leave_code_upload=findViewById(R.id.leave_code_upload);
            leave_code_upload.setOnClickListener(v -> {
                finish();
            });
            //加载源码图片
            File imgfile = new File("/storage/emulated/0/Android/data/com.gujiunet.code/upload/icon.png");
            ImageView uplod_img=findViewById(R.id.code_upload_img);
            code_upload.this.runOnUiThread(() -> {
                Glide.with(this).load(imgfile).into(uplod_img);
            });
            //源码信息读取
            File folder = new File("/storage/emulated/0/Android/data/com.gujiunet.code/upload/");
            long length = 0;
            try {
                File[] files = folder.listFiles();
                for (File file : files) {
                    if (file.isFile()) {
                        length += file.length();
                    } else if (file.isDirectory()) {
                        length += getFolderSize(file);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            DecimalFormat decimalFormat = new DecimalFormat("#0.00"); // 设置格式为保留两位小数
            String formattedNumber = decimalFormat.format(length / (1024 * 1024)); // 对数字进行格式化
            code_upload_size.setText(formattedNumber+"MB");
            String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/upload/AndroidManifest.xml"; // 要读取的文本文件路径
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(Files.newInputStream(Paths.get(filePath))))){
                    String line;

                    while ((line = reader.readLine()) != null){
                        System.out.println(line);
                        try {
                            // 创建XmlPullParserFactory实例
                            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                            // 创建XmlPullParser实例
                            XmlPullParser parser = factory.newPullParser();
                            // 传入XML字符串
                            parser.setInput(new StringReader(line));
                            // 解析XML
                            int eventType = parser.getEventType();
                            while (eventType != XmlPullParser.END_DOCUMENT) {
                                String tagName = parser.getName();
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
                                        if ("title".equals(tagName)) {
                                            String title = parser.nextText();
                                            code_upload_name.setText(title);
                                        } else if ("icon".equals(tagName)) {
                                            String icon = parser.nextText();
                                            System.out.println("Icon: " + icon);
                                        } else if ("packageName".equals(tagName)) {
                                            String packageName = parser.nextText();
                                            code_upload_pack.setText(packageName);
                                        } else if ("versionName".equals(tagName)) {
                                            String versionName = parser.nextText();
                                            code_upload_version.setText(versionName);
                                        } else if ("versionint".equals(tagName)) {
                                            int versionInt = Integer.parseInt(parser.nextText());
                                            System.out.println("Version Int: " + versionInt);
                                        } else if ("sdk".equals(tagName)) {
                                            int sdk = Integer.parseInt(parser.nextText());
                                            System.out.println("SDK: " + sdk);
                                        } else if ("yuv".equals(tagName)) {
                                            int yuv = Integer.parseInt(parser.nextText());
                                            System.out.println("YUV: " + yuv);
                                        } else if ("Permissions".equals(tagName)) {
                                            String permissions = parser.nextText();
                                            System.out.println("Permissions: " + permissions);
                                        } else if ("createTime".equals(tagName)) {
                                            String createTime = parser.nextText();
                                            System.out.println("Create Time: " + createTime);
                                        } else if ("upTime".equals(tagName)) {
                                            String upTime = parser.nextText();
                                            System.out.println("Update Time: " + upTime);
                                        } else if ("remark".equals(tagName)) {
                                            String remark = parser.nextText();
                                            System.out.println("Remark: " + remark);
                                        }
                                        break;
                                    case XmlPullParser.END_TAG:
                                        break;
                                }
                                eventType = parser.next();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private long getFolderSize(File folder) throws IOException {
        long length = 0;
        File[] files = folder.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                length += file.length();
            } else if (file.isDirectory()) {
                length += getFolderSize(file);
            }
        }
        return length;
    }

    public void receiveDataFromFragment(String data) {
    }
}
