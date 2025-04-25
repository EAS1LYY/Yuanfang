package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kongzue.dialogx.DialogX;
import com.kongzue.dialogx.dialogs.MessageDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {

    List<Fragment> fragmentList = new ArrayList<>();

    @SuppressLint({"NonConstantResourceId", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //初始化dialogXd
        DialogX.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil status=new StatusBarUtil();
        status.setStatusColor();

        //主页内容填充
        ViewPager mainviewPager = findViewById(R.id.viewpager);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        initData();
        MainActivityAdapter adapter = new MainActivityAdapter(this.getSupportFragmentManager(), fragmentList);
        mainviewPager.setAdapter(adapter);
        mainviewPager.setOffscreenPageLimit(5);
        //mainviewPager.setOffscreenPageLimit(5);
        mainviewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.setSelectedItemId(R.id.nav_main);
                        break;
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.nav_code);
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.nav_post);
                        break;
                    case 3:
                        bottomNavigationView.setSelectedItemId(R.id.nav_comment);
                        break;
                    case 4:
                        bottomNavigationView.setSelectedItemId(R.id.nav_user);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
/*
            //禁止主页窗体滑动
            viewPager.setOnTouchListener(new View.OnTouchListener() {
                private float downY;
                private float downX;

                @SuppressLint("ClickableViewAccessibility")
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                             downX = event.getX();
                             downY = event.getY();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            float moveX = event.getX() - downX;
                            float moveY = event.getY() - downY;
                            if(Math.abs(moveX) > Math.abs(moveY)){
                                return true;
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                        case MotionEvent.ACTION_CANCEL:
                            break;
                    }
                    return false;
                }
            });

*/
//        图标选择监听
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_main) {
                mainviewPager.setCurrentItem(0);
            } else if (item.getItemId() == R.id.nav_code) {
                mainviewPager.setCurrentItem(1);
            }  else if (item.getItemId() == R.id.nav_post) {
                mainviewPager.setCurrentItem(2);
            }else if (item.getItemId() == R.id.nav_comment) {
                mainviewPager.setCurrentItem(3);
            } else if (item.getItemId() == R.id.nav_user) {
                mainviewPager.setCurrentItem(4);
            }
            return true;
        });
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));

        //网络判断
        if (!isConnect(this)) {
            setNetworkMethod(this);
        } else {


            //登录判定
            String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/account.json";
            File afile = new File(filePath);
            PackageInfo packageInfo = null;
            try {
                packageInfo = getPackageManager().getPackageInfo("com.gujiunet.code", 0);
            } catch (PackageManager.NameNotFoundException e) {
                throw new RuntimeException(e);
            }
            String versionName = packageInfo.versionName;
            //查看版本号
            // 判断文件是否存在，如果不存在则创建文件
            if (!afile.exists()) {
                finish();
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, login.class);
                startActivity(intent);
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    int page=1;
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
                    //builder.toString() 返回表示此序列中数据的字符串
                    //System.out.println(builder);
                    //使用Json解析
                    JSONObject jsonObject = new JSONObject(builder.toString());
                    //System.out.println(jsonObject);
                    Log.i("文件json", jsonObject.toString());
                    String account = jsonObject.get("account").toString();
                    String password = jsonObject.get("password").toString();
                    String url = "http://software.toolr.cn/api.php?type=login&ID=" + account + "&password=" + password;
                    //String post="type=adminlogin&ID="+account+"&password="+text;
                    //String zhmm = "<account>" + account + "<account><password>" + text + "<password>";

                    new Thread(() -> {
                        Http http = new Http();
                        Log.i("链接", url);
                        http.setUrl(url).setRequestType(Http.REQUEST_TYPE_GET);
                        Http.Request result = http.request();
                        //Log.i("内容",result.getContent());
                        Log.i("内容1", String.valueOf(result.getContent()));
                        //1810820293
                        if (result.getContent().equals("登录成功")) {
                            String userurl = "http://software.toolr.cn/api.php?type=logininfo&ID=" + account;
                            Http userhttp = new Http();
                            //Log.i("链接",url);
                            userhttp.setUrl(userurl).setRequestType(Http.REQUEST_TYPE_GET);
                            Http.Request user_return_data = userhttp.request();
                            Http bannerhttp=new Http();
                            bannerhttp.setUrl("http://software.toolr.cn/banner/banner.json").setRequestType(Http.REQUEST_TYPE_GET);
                            Http.Request banner_return=bannerhttp.request();
                            Http random_http = new Http();
                            random_http.setUrl("http://software.toolr.cn/api.php?type=randomcode").setRequestType(Http.REQUEST_TYPE_GET);
                            Http.Request random_code_urlreturn = random_http.request();
                            Http code_http = new Http();
                            code_http.setUrl("http://software.toolr.cn/api.php?type=getcodejson&page="+page);
                            Http.Request codereturn = code_http.request();
                            Http text_http=new Http();
                            text_http.setUrl("http://software.toolr.cn/api.php?type=articlelist&page=1").setRequestType(Http.REQUEST_TYPE_GET);
                            Http.Request text=text_http.request();



                            Log.i("数据", random_code_urlreturn.content);
                            try {
                                JSONObject bannerjson=new JSONObject(banner_return.content);
                                String banner1=bannerjson.get("banner1").toString();
                                String banner2=bannerjson.get("banner2").toString();
                                String banner3=bannerjson.get("banner3").toString();
                                String banner4=bannerjson.get("banner4").toString();
                                String banner5=bannerjson.get("banner5").toString();
                                String banner1url=bannerjson.get("banner1url").toString();
                                String banner2url=bannerjson.get("banner2url").toString();
                                String banner3url=bannerjson.get("banner3url").toString();
                                String banner4url=bannerjson.get("banner4url").toString();
                                String banner5url=bannerjson.get("banner5url").toString();


                                JSONObject userdatajson = new JSONObject(user_return_data.content);
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
                                String user_level = userdatajson.get("Level").toString();
                                String code_number = userdatajson.get("源码总数").toString();
                                String comment_number = userdatajson.get("评论总数").toString();
                                String post_number = userdatajson.get("发帖总数").toString();
                                String randomdata=random_code_urlreturn.content;
                                user.updateUserVarInfo(user_id, user_name, user_qq, user_qm, user_regip, user_lastlogin, user_coin, user_vip, user_regtime, user_tx, user_yhqx, user_buycode,user_level,code_number,comment_number,post_number);
                                main.updateVarInfo(user_tx,banner1,banner2,banner3,banner4,banner5,banner1url,banner2url,banner3url,banner4url,banner5url,randomdata);
                                code.updateVarInfo(codereturn.content);
                                main.updateUserVarInfo(user_id, user_name, user_qq, user_qm, user_regip, user_lastlogin, user_coin, user_vip, user_regtime, user_tx, user_yhqx, user_buycode);
                                comment.updatetextVarInfo(text.content);
                                main.updatetextVarInfo(text.content);
                                //user fragment_user = new user();
                                // 添加Fragment
                                //fragmentTransaction.replace(R.id.viewpager, fragment_user).commit();

                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }


                            runOnUiThread(() -> {

                                //登录成功事件
                                //Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
                            });


                        } else {
                            runOnUiThread(() -> {
                                Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                                finish();
                                Intent intent = new Intent();
                                intent.setClass(MainActivity.this, login.class);
                                startActivity(intent);
                            });
                        }
                    }).start();
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            //登录判定事件结束
            //检测更新事件开始
            new Thread(() -> {
                Http notice_http=new Http();
                notice_http.setUrl("http://software.toolr.cn/notice.json").setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request notcie_return=notice_http.request();
                JSONObject noticejsonObject = null;
                try {
                    noticejsonObject = new JSONObject(notcie_return.content);
                    String title = noticejsonObject.get("TITLE").toString();
                    String text = noticejsonObject.get("TEXT").toString();
                    String time = noticejsonObject.get("TIME").toString();
                    runOnUiThread(() -> {
                        MessageDialog.build()
                                .setTitle(title)
                                .setMessage(text+"\n\n"+time)
                                .setOkButton("确定")
                                .show();
                    });
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }).start();

            new Thread(() -> {
                String updateurl = "http://software.toolr.cn/version.json";
                Http http = new Http();
                //Log.i("链接", updateurl);
                http.setUrl(updateurl).setRequestType(Http.REQUEST_TYPE_GET);
                Http.Request result = http.request();
                //Log.i("内容",result.getContent());
                Log.i("内容1", String.valueOf(result.getContent()));
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result.getContent());
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                //System.out.println(jsonObject);
                try {
                    String ModifyContent = jsonObject.get("ModifyContent").toString();
                    String DownloadUrl = jsonObject.get("DownloadUrl").toString();
                    String VersionName = jsonObject.get("VersionName").toString();
                    String ApkSize = jsonObject.get("ApkSize").toString();
                    if (!Objects.equals(versionName, VersionName)) {
                        runOnUiThread(() -> {
                            Log.i("版本状态", "旧版本");
                            AlertDialog.Builder update_dialog = new AlertDialog.Builder(this);
                            update_dialog.setTitle("发现新版本v" + VersionName);
                            update_dialog.setMessage("更新内容:\n" + ModifyContent + "\n软件大小:" + ApkSize).setCancelable(false);
                            update_dialog.setPositiveButton("更新", (dialog, which) -> {
                                Log.i("事件", "点击了更新");
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(DownloadUrl));
                                if (intent.resolveActivity(getPackageManager()) != null) {
                                    finish();
                                    startActivity(intent);
                                } else {
                                    finish();
                                    Toast.makeText(this, "没有可用的浏览器程序", Toast.LENGTH_SHORT).show();
                                }
                            });


                            update_dialog.setNegativeButton("取消", (dialog, which) -> {
                                finish();
                                dialog.cancel();
                                Log.i("事件", "点击了取消");
                            });

                            AlertDialog utw = update_dialog.create();
                            utw.show();
                        });
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }).start();

            //检测更新事件结束
        }
    }


    //界面切换点击事件结束


    private void initData() {
        main mainfragment = main.newInstance("首页", "");
        fragmentList.add(mainfragment);
        code codefragment = code.newInstance("源码", "");
        fragmentList.add(codefragment);
        fragment_post fragmentpost = fragment_post.newInstance("操作", "");
        fragmentList.add(fragmentpost);
        comment commentFragment = comment.newInstance("社区", "");
        fragmentList.add(commentFragment);
        user mineFragment = user.newInstance("我的", "");
        fragmentList.add(mineFragment);
    }

    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isConnect(Context context) {
        boolean _isConnect = false;
        ConnectivityManager conManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = conManager.getActiveNetworkInfo();
        if (network != null) {
            _isConnect = conManager.getActiveNetworkInfo().isAvailable();
        }
        return _isConnect;
    }


    /**
     * 打开无网络界面
     *
     * @param context
     */
    public void setNetworkMethod(final Context context) {
        finish();
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, nonetwork.class);
        startActivity(intent);
    }
}