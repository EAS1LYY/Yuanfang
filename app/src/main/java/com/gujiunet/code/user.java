package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.kongzue.dialogx.dialogs.PopNotification;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String user_id_data;
    private static String user_name_data;
    private static String user_qq_data;
    private static String user_qm_data;
    private static  String user_regip_data;
    private static String user_lastlogin_data;
    private static String user_coin_data;
    private static String user_vip_data;
    private static  String user_regtime_data;
    private static String user_tx_data;
    private static String user_yhqx_data;
    private static String user_buycode_data;
    private static String user_level;
    private static String code_number;
    private static String comment_number;
    private static String article_number;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean scrollable = true;

    public user() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user.
     */
    // TODO: Rename and change types and number of parameters
    public static user newInstance(String param1, String param2) {
        user fragment = new user();
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



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateInfo();

    }


    @SuppressLint("SimpleDateFormat")
    public void updateInfo() {
        View userview = getView();
        TextView user_id_id=userview.findViewById(R.id.user_info_id);
        TextView user_qq_id=userview.findViewById(R.id.user_info_qq);
        TextView user_regip_id=userview.findViewById(R.id.user_info_regipid);
        TextView user_lastlogin_id=userview.findViewById(R.id.user_info_lastloginid);
        TextView user_coin_id=userview.findViewById(R.id.user_info_coin);
        TextView user_regtime_id=userview.findViewById(R.id.user_info_regtimeid);
        TextView user_yhqx_id=userview.findViewById(R.id.user_info_userqxid);
        //TextView user_buycode_id=userview.findViewById(R.id.user_info_buycodeid);
        TextView user_vip_id=userview.findViewById(R.id.user_info_vipid);
        TextView user_name_id=userview.findViewById(R.id.user_name);
        TextView user_text_id=userview.findViewById(R.id.user_id);
        TextView user_level_id=userview.findViewById(R.id.text_user_qx);
        ImageView user_img=userview.findViewById(R.id.imageView);
        TextView user_code_number=userview.findViewById(R.id.user_code_number);
        TextView user_comment_number=userview.findViewById(R.id.user_comment_number);
        TextView user_post_number=userview.findViewById(R.id.user_post_number);
        View progress=userview.findViewById(R.id.user_loading_background);
        user_code_number.setText(code_number);
        user_comment_number.setText(comment_number);
        user_post_number.setText(article_number);
        user_level_id.setText(user_level);
        String id = String.valueOf(user_id_data);
        user_coin_id.setText(user_coin_data);
        user_id_id.setText(id);
        user_qq_id.setText(user_qq_data);
        user_regip_id.setText(user_regip_data);
        user_lastlogin_id.setText(user_lastlogin_data);
        user_regtime_id.setText(user_regtime_data);
        user_yhqx_id.setText(user_yhqx_data);
        //user_buycode_id.setText(user_buycode_data);
        user_vip_id.setText(user_vip_data);
        user_name_id.setText(user_name_data);
        user_text_id.setText(user_qm_data);
        Glide.with(requireContext()).load(user_tx_data).into(user_img);
        progress.setVisibility(View.GONE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainview= inflater.inflate(R.layout.fragment_user, container, false);
        CardView buttonuserinfo=mainview.findViewById(R.id.userinfo_button);
        View aboutbutton=mainview.findViewById(R.id.about_button);
        aboutbutton.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(requireActivity(), about.class);
            startActivity(intent);
        });
        buttonuserinfo.setOnClickListener(v -> {

            JSONObject jsonObj = new JSONObject();
            try {
                jsonObj.put("createid", user_id_data);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/create.json";
            File file = new File(filePath);

            try {
                // 判断文件是否存在，如果不存在则创建文件
                if (!file.exists()) {
                    getActivity().getExternalFilesDir(null);
                    file.createNewFile();

                }

                FileWriter writer = new FileWriter(filePath);
                writer.write(jsonObj.toString());
                writer.close();
                Http createhttp=new Http();
                createhttp.setUrl("http://software.toolr.cn/api.php?type=getusercodejson&ID="+user_id_data+"&page=1").setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request codereturn=createhttp.request();
                user_code.updateVarInfo(codereturn.content);
                Intent intent = new Intent();
                intent.setClass(requireActivity(), user_info.class);
                startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        View sign_button_id=mainview.findViewById(R.id.sign_button);
        sign_button_id.setOnClickListener(v -> {
            new Thread(() -> {
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
                    JSONObject jsonObject = new JSONObject(builder.toString());
                    String account = jsonObject.get("account").toString();
                    String password = jsonObject.get("password").toString();
                    String signurl="http://software.toolr.cn/api.php?type=daysignin&ID="+account+"&password="+password;
                    Http sign=new Http();
                    sign.setUrl(signurl).setRequestType(Http.REQUEST_TYPE_GET);
                    Http.Request result = sign.request();
                    if (result.getContent().equals("签到成功，经验+10")){
                        Looper.prepare();
                        PopNotification.show("签到成功，经验+10!");
                        Looper.loop();
                    }else{
                        Looper.prepare();
                        PopNotification.show("今天已经签到过了！");
                        Looper.loop();
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).start();


        });



        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) View setting=mainview.findViewById(R.id.user_setting);
        setting.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(requireActivity(), setting.class);
            startActivity(intent);
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) View user_vip=mainview.findViewById(R.id.user_vip);
        user_vip.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://2.jukami.cn/Yuanfang"));
            startActivity(intent);
        });
        MaterialToolbar materialToolbar=mainview.findViewById(R.id.materialToolbar);
        materialToolbar.inflateMenu(R.menu.menu_user);
        materialToolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.setting_button) {
                Intent intent = new Intent();
                intent.setClass(requireActivity(), setting.class);
                startActivity(intent);
            } else if (item.getItemId() == R.id.message_button) {
                //Toast.makeText(requireActivity(), "待开放", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(requireActivity(), message.class);
                startActivity(intent);
            }
            return false;
        });
        return mainview;
    }

    public static void updateUserVarInfo(String user_id,String user_name,String user_qq,String user_qm,String user_regip,String user_lastlogin,String user_coin,String user_vip,String user_regtime,String user_tx,String user_yhqx,String user_buycode,String level,String codenumber,String commentnumber,String articlenumber) {
        user_name_data = user_name;
       user_id_data=user_id;
        user_qq_data = user_qq;
        user_qm_data = user_qm;
        user_regip_data = user_regip;
        user_lastlogin_data = user_lastlogin;
        user_coin_data = user_coin;
        user_vip_data = user_vip;
        user_regtime_data = user_regtime;
        user_tx_data = user_tx;
        user_yhqx_data = user_yhqx;
        user_buycode_data = user_buycode;
        user_level=level;
        code_number=codenumber;
        comment_number=commentnumber;
        article_number=articlenumber;
    }

}