package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link code_upload_basic#newInstance} factory method to
 * create an instance of this fragment.
 */
public class code_upload_basic extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private static String codedata;

    public code_upload_basic() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment code_upload_basic.
     */
    // TODO: Rename and change types and number of parameters
    public static code_upload_basic newInstance(String param1, String param2) {
        code_upload_basic fragment = new code_upload_basic();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

            View mainview=getView();
            TextView code_upload_basic_title=mainview.findViewById(R.id.code_upload_basic_title);
            TextView code_upload_packname=mainview.findViewById(R.id.code_upload_packname);
            TextView code_upload_basic_size=mainview.findViewById(R.id.code_upload_basic_size);
            TextView upload_code_basic_version=mainview.findViewById(R.id.upload_code_basic_version);
            TextView upload_code_basic_yu=mainview.findViewById(R.id.upload_code_basic_yu);
            //源码信息读取
            String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/upload/AndroidManifest.xml"; // 要读取的文本文件路径

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
            code_upload_basic_size.setText(formattedNumber+"MB");

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
                                            code_upload_basic_title.setText(title);
                                        } else if ("icon".equals(tagName)) {
                                            String icon = parser.nextText();
                                            System.out.println("Icon: " + icon);
                                        } else if ("packageName".equals(tagName)) {
                                            String packageName = parser.nextText();
                                            code_upload_packname.setText(packageName);
                                        } else if ("versionName".equals(tagName)) {
                                            String versionName = parser.nextText();
                                            upload_code_basic_version.setText(versionName);
                                        } else if ("versionint".equals(tagName)) {
                                            int versionInt = Integer.parseInt(parser.nextText());
                                            System.out.println("Version Int: " + versionInt);
                                        } else if ("sdk".equals(tagName)) {
                                            int sdk = Integer.parseInt(parser.nextText());
                                            System.out.println("SDK: " + sdk);
                                        } else if ("yuv".equals(tagName)) {
                                            int yuv = Integer.parseInt(parser.nextText());
                                            upload_code_basic_yu.setText("v"+yuv);
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
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_code_upload_basic, container, false);
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
}