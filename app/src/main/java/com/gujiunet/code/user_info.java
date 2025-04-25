package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.kongzue.dialogx.dialogs.WaitDialog;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class user_info extends AppCompatActivity {
    private int currentPage = 1;
    List<Fragment> fragmentList = new ArrayList<>();


    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);
        StatusBarUtil status=new StatusBarUtil();
        status.setStatusColor();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        //主页内容填充
        ViewPager mainviewPager = findViewById(R.id.user_infoviewpager);
        TabLayout bottomNavigationView=findViewById(R.id.tablayout);
        user_code user_code_tab = user_code.newInstance("源码", "");
        fragmentList.add(user_code_tab);
        user_comm commfragment = user_comm.newInstance("评论", "");
        fragmentList.add(commfragment);
        user_app user_app_fragment = user_app.newInstance("应用", "");
        fragmentList.add(user_app_fragment);
        MainActivityAdapter adapter = new MainActivityAdapter(this.getSupportFragmentManager(), fragmentList);
        mainviewPager.setOffscreenPageLimit(3);
        //mainviewPager.setOffscreenPageLimit(5);
        //bottomNavigationView.addTab(bottomNavigationView.newTab().setText("源码").setIcon(com.donkingliang.imageselector.R.drawable.icon_back));
        //bottomNavigationView.addTab(bottomNavigationView.newTab().setText("评论").setIcon(com.donkingliang.imageselector.R.drawable.icon_back));
        //bottomNavigationView.addTab(bottomNavigationView.newTab().setText("应用").setIcon(com.donkingliang.imageselector.R.drawable.icon_back));
        mainviewPager.setAdapter(adapter);
        bottomNavigationView.setupWithViewPager(mainviewPager);
        bottomNavigationView.getTabAt(0).setText("源码");
        bottomNavigationView.getTabAt(1).setText("应用");
        bottomNavigationView.getTabAt(2).setText("评论");
        new Thread(() -> {
try {
    WaitDialog.show("正在加载...");
    //获取本地的Json文件
    InputStream aopen = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        aopen = Files.newInputStream(Paths.get("/storage/emulated/0/Android/data/com.gujiunet.code/create.json"));
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
    abr.close();
    aisr.close();
    //builder.toString() 返回表示此序列中数据的字符串
    //System.out.println(builder);
    //使用Json解析
    JSONObject ajsonObject = new JSONObject(abuilder.toString());
    //System.out.println(jsonObject);
    String account = ajsonObject.get("createid").toString();
    //String password = ajsonObject.get("password").toString();
    Http user_info_http=new Http();
    user_info_http.setUrl("http://software.toolr.cn/api.php?type=logininfo&ID="+account).setRequestType(Http.REQUEST_TYPE_GET);
    Http.Request user_info=user_info_http.request();
    JSONObject userdatajson = new JSONObject(user_info.content);
    String user_id = userdatajson.get("ID").toString();
    String user_name = userdatajson.get("Name").toString();
    String user_qq = userdatajson.get("QQ").toString();
    String user_qm = userdatajson.get("签名").toString();
    String user_regip = userdatajson.get("注册IP").toString();
    String user_lastlogin = userdatajson.get("最后登录").toString();
    String user_coin = userdatajson.get("金币").toString();
    String user_vip = userdatajson.get("会员").toString();
    String user_regtime = userdatajson.get("注册时间").toString();
    String user_tx = userdatajson.get("头像").toString();
    String user_yhqx = userdatajson.get("用户权限").toString();
    String user_buycode = userdatajson.get("购买源码").toString();
    String Level = userdatajson.get("Level").toString();
    ImageView user_img_id=findViewById(R.id.user_img);
    TextView name_id=findViewById(R.id.name);
    TextView level_id=findViewById(R.id.level);
    TextView regtimeid=findViewById(R.id.regtime);
    TextView qmid=findViewById(R.id.qm);
    TextView coinid=findViewById(R.id.coin);
    TextView qxid=findViewById(R.id.qx);
    InputStream open = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        open = Files.newInputStream(Paths.get("/storage/emulated/0/Android/data/com.gujiunet.code/create.json"));
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
    String create = jsonObject.get("createid").toString();
    Http createhttp=new Http();
    createhttp.setUrl("http://software.toolr.cn/api.php?type=getusercodejson&ID="+create+"&page=1").setRequestType(Http.REQUEST_TYPE_GET);
    Http.Request codereturn=createhttp.request();
    user_code.updateVarInfo(codereturn.content);


    runOnUiThread(() -> {


        Glide.with(user_info.this).load(user_tx).into(user_img_id);
    name_id.setText(user_name);
    level_id.setText(Level);
    regtimeid.setText("注册时间:"+user_regtime);
    qmid.setText(user_qm);
    coinid.setText(user_coin);
        CardView qxcard=findViewById(R.id.qx_card);
        if (user_yhqx.equals("普通用户")){qxcard.setVisibility(View.GONE);}
    qxid.setText(user_yhqx);
    WaitDialog.dismiss();
});




    ImageView leave_button=findViewById(R.id.leave);
    leave_button.setOnClickListener(v -> {
        finish();
    });
} catch (Exception e) {
    throw new RuntimeException(e);
}

        }).start();
    }
}
