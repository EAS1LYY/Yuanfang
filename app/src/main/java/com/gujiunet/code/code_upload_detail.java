package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kongzue.dialogx.dialogs.PopNotification;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.luck.picture.lib.basic.PictureSelector;
import com.luck.picture.lib.config.SelectMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.interfaces.OnResultCallbackListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link code_upload_detail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class code_upload_detail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public code_upload_detail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment code_upload_detail.
     */
    // TODO: Rename and change types and number of parameters
    public static code_upload_detail newInstance(String param1, String param2) {
        code_upload_detail fragment = new code_upload_detail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }


    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View detailmain = getView();
        ImageView screen1=detailmain.findViewById(R.id.upload_code_screen1);
        ImageView screen2=detailmain.findViewById(R.id.upload_code_screen2);
        ImageView screen3=detailmain.findViewById(R.id.upload_code_screen3);
        TextView code_upload_detail_name=detailmain.findViewById(R.id.code_upload_detail_name);
        TextView img1=detailmain.findViewById(R.id.img1);
        TextView img2=detailmain.findViewById(R.id.img2);
        TextView img3=detailmain.findViewById(R.id.img3);
        screen1.setOnClickListener(v -> {
            PictureSelector.create(this)
                    .openSystemGallery(SelectMimeType.ofImage())
                    .forSystemResult(new OnResultCallbackListener<LocalMedia>() {
                        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                        @Override
                        public void onResult(ArrayList<LocalMedia> result) {
                            for (LocalMedia lm: result) {
                                Log.i("选择图片", String.valueOf(result.get(lm.position).getRealPath()));
                                String img1file=String.valueOf(result.get(lm.position).getRealPath());
                                Glide.with(requireActivity()).load(img1file).into(screen1);
                                img1.setText(img1file);
                            }
                        }
                        @Override
                        public void onCancel() {
                        }
                    });
        });
        screen2.setOnClickListener(v -> {
            PictureSelector.create(this)
                    .openSystemGallery(SelectMimeType.ofImage())
                    .forSystemResult(new OnResultCallbackListener<LocalMedia>() {
                        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                        @Override
                        public void onResult(ArrayList<LocalMedia> result) {
                            for (LocalMedia lm: result) {
                                Log.i("选择图片", String.valueOf(result.get(lm.position).getRealPath()));
                                String img2file=String.valueOf(result.get(lm.position).getRealPath());
                                Glide.with(requireActivity()).load(img2file).into(screen2);
                                img2.setText(img2file);
                            }
                        }
                        @Override
                        public void onCancel() {
                        }
                    });
        });
        screen3.setOnClickListener(v -> {
            PictureSelector.create(this)
                    .openSystemGallery(SelectMimeType.ofImage())
                    .forSystemResult(new OnResultCallbackListener<LocalMedia>() {
                        @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                        @Override
                        public void onResult(ArrayList<LocalMedia> result) {
                            for (LocalMedia lm: result) {
                                Log.i("选择图片", String.valueOf(result.get(lm.position).getRealPath()));
                                String img3file=String.valueOf(result.get(lm.position).getRealPath());
                                Glide.with(requireActivity()).load(img3file).into(screen3);
                                img3.setText(img3file);
                            }
                        }
                        @Override
                        public void onCancel() {
                        }
                    });
        });

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
                                        code_upload_detail_name.setText(title);
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
        TextView assort=detailmain.findViewById(R.id.assort);
        RadioGroup radio=detailmain.findViewById(R.id.radiogroup);
        radio.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId==R.id.radioButton){
                assort.setText("1");
            }
            if(checkedId==R.id.radioButton2){
                assort.setText("2");
            }
            if(checkedId==R.id.radioButton3){
                assort.setText("3");
            }
            if(checkedId==R.id.radioButton4){
                assort.setText("4");
            }
        });


        TextView code_upload_detail_show=detailmain.findViewById(R.id.code_upload_detail_show);
        TextView code_upload_detail_coin=detailmain.findViewById(R.id.code_upload_detail_coin);
        FloatingActionButton upload_button=detailmain.findViewById(R.id.upload_button2);
        upload_button.setOnClickListener(v -> {
            new Thread(() -> {
                try {
                    String filePatha = "/storage/emulated/0/Android/data/com.gujiunet.code/account.json";
                    //获取本地的Json文件
                    InputStream open = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        open = Files.newInputStream(Paths.get(filePatha));
                    }
                    InputStreamReader isr = new InputStreamReader(open, StandardCharsets.UTF_8);
                    //包装字符流,将字符流放入缓存里
                    BufferedReader br = new BufferedReader(isr);
                    String line;
                    //StringBuilder和StringBuffer功能类似,存储字符串
                    StringBuilder builder = new StringBuilder();
                    while ((line = br.readLine()) != null) {
                        builder.append(line);
                    }
                    br.close();
                    isr.close();
                    JSONObject jsonObject = new JSONObject(builder.toString());
                    String account = jsonObject.get("account").toString();
                    String password = jsonObject.get("password").toString();

                    String filePathaa = "/storage/emulated/0/Android/data/com.gujiunet.code/file.json";
                    //获取本地的Json文件
                    InputStream opena = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        opena = Files.newInputStream(Paths.get(filePathaa));
                    }
                    InputStreamReader isra = new InputStreamReader(opena, StandardCharsets.UTF_8);
                    //包装字符流,将字符流放入缓存里
                    BufferedReader bra = new BufferedReader(isra);
                    String linea;
                    //StringBuilder和StringBuffer功能类似,存储字符串
                    StringBuilder buildera = new StringBuilder();
                    while ((linea = bra.readLine()) != null) {
                        buildera.append(linea);
                    }
                    bra.close();
                    isra.close();
                    JSONObject jsonObjectfile = new JSONObject(buildera.toString());
                    String codefile = jsonObjectfile.get("file").toString();
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
                    String filesize=formattedNumber+"MB";




                    if (img1.getText().toString().equals("")) {
                        getActivity().runOnUiThread(() -> PopNotification.show("图片1未上传"));
                    } else if (img2.getText().toString().equals("")) {
                        getActivity().runOnUiThread(() -> PopNotification.show("图片2未上传"));
                    } else if (img3.getText().toString().equals("")) {
                        getActivity().runOnUiThread(() -> PopNotification.show("图片3未上传"));
                    } else if (code_upload_detail_name.getText().toString().equals("")) {
                        getActivity().runOnUiThread(() -> PopNotification.show("源码名称未填写"));
                    } else if (code_upload_detail_show.getText().toString().equals("")) {
                        getActivity().runOnUiThread(() -> PopNotification.show("源码介绍未填写"));
                    } else if(code_upload_detail_coin.getText().toString().equals("")){
                        getActivity().runOnUiThread(() -> PopNotification.show("源码价格未填写"));
                    } else{
                        WaitDialog.show("正在上传...");
                        String uploadurl = "http://software.toolr.cn/code/upload_code.php?files=tp/&title=" + code_upload_detail_name.getText().toString() + "1.png";
                        Http uploadpic = new Http();
                        uploadpic.setUrl(uploadurl).setRequestType(Http.REQUEST_TYPE_UPDATE);
                        uploadpic.setFilePath(img1.getText().toString());
                        Http.Request upload_return = uploadpic.request();
                        String uploadurl2 = "http://software.toolr.cn/code/upload_code.php?files=tp/&title=" + code_upload_detail_name.getText().toString() + "2.png";
                        Http uploadpic2 = new Http();
                        uploadpic2.setUrl(uploadurl2).setRequestType(Http.REQUEST_TYPE_UPDATE);
                        uploadpic2.setFilePath(img2.getText().toString());
                        Http.Request upload_return2 = uploadpic2.request();
                        String uploadurl3 = "http://software.toolr.cn/code/upload_code.php?files=tp/&title=" + code_upload_detail_name.getText().toString() + "3.png";
                        Http uploadpic3 = new Http();
                        uploadpic3.setUrl(uploadurl3).setRequestType(Http.REQUEST_TYPE_UPDATE);
                        uploadpic3.setFilePath(img3.getText().toString());
                        Http.Request upload_return3 = uploadpic3.request();

                        String uploadurl4 = "http://software.toolr.cn/code/upload_code.php?files=tp/&title=" + code_upload_detail_name.getText().toString() + "4.png";
                        Http uploadpic4 = new Http();
                        uploadpic4.setUrl(uploadurl4).setRequestType(Http.REQUEST_TYPE_UPDATE);
                        uploadpic4.setFilePath("/storage/emulated/0/Android/data/com.gujiunet.code/upload/icon.png");
                        Http.Request upload_return4 = uploadpic4.request();

                        String uploadurlcode = "http://software.toolr.cn/code/upload_code.php?files=code/&title=" + code_upload_detail_name.getText().toString() + ".iApp";
                        Http uploadcode = new Http();
                        uploadcode.setUrl(uploadurlcode).setRequestType(Http.REQUEST_TYPE_UPDATE);
                        uploadcode.setFilePath(codefile);
                        Http.Request uploadcode_return = uploadcode.request();

                        String uploadcodehttpurl = "http://software.toolr.cn/api.php?type=codeupload&ID="+account+"&password="+password+"&name="+code_upload_detail_name.getText().toString()+"&jieshao="+code_upload_detail_show.getText().toString()+"&coin="+code_upload_detail_coin.getText().toString()+"&p1=http://software.toolr.cn/code/tp/"+code_upload_detail_name.getText().toString() + "1.png&p2=http://software.toolr.cn/code/tp/"+code_upload_detail_name.getText().toString() + "2.png&p3=http://software.toolr.cn/code/tp/"+code_upload_detail_name.getText().toString() + "3.png&icon=http://software.toolr.cn/code/tp/"+code_upload_detail_name.getText().toString() + "4.png&url=http://software.toolr.cn/code/code/"+code_upload_detail_name.getText().toString() + ".iApp&assort="+assort.getText().toString()+"&dx="+ filesize;
                        Http uploadcodehttp = new Http();
                        uploadcodehttp.setUrl(uploadcodehttpurl).setRequestType(Http.REQUEST_TYPE_GET);
                        Http.Request code_return = uploadcodehttp.request();
                        if (String.valueOf(upload_return4.getContent()).equals("上传成功!")) {
                            getActivity().runOnUiThread(() -> PopNotification.show("源码图标上传成功"));
                        }
                        if (String.valueOf(upload_return.getContent()).equals("上传成功!")) {
                            getActivity().runOnUiThread(() -> Glide.with(requireActivity()).load(R.drawable.success_icon).into(screen1));
                        }
                        if (String.valueOf(upload_return2.getContent()).equals("上传成功!")) {
                            getActivity().runOnUiThread(() -> Glide.with(requireActivity()).load(R.drawable.success_icon).into(screen2));
                        }
                        if (String.valueOf(upload_return3.getContent()).equals("上传成功!")) {
                            getActivity().runOnUiThread(() -> Glide.with(requireActivity()).load(R.drawable.success_icon).into(screen3));
                        }
                        if (String.valueOf(uploadcode_return.getContent()).equals("上传成功!")) {
                            getActivity().runOnUiThread(() -> PopNotification.show("源码源文件上传成功"));
                        }
                        if(Objects.equals(code_return.content, "上传成功，等待审核(审核结果将由私信通知)")){
                            getActivity().runOnUiThread(() -> PopNotification.show("上传成功，等待审核(审核结果将由私信通知)"));
                            WaitDialog.dismiss();
                            String folderPath = "/storage/emulated/0/Android/data/com.gujiunet.code/upload/"; // 根据自己的情况修改路径
                            File codefolder = new File(folderPath);
                            if (codefolder.exists()) {
                                deleteDirectory(codefolder);
                            }
                        }
                    }
                } catch (JSONException | IOException e) {
                    throw new RuntimeException(e);
                }

            }).start();
        });
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_code_upload_detail, container, false);
    }

    private static boolean deleteDirectory(File file) {
        if (file != null && file.isDirectory()) {
            String[] children = file.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDirectory(new File(file, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return file.delete();
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