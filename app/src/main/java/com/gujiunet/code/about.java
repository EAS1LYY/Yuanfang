package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.kongzue.dialogx.dialogs.BottomDialog;
import com.kongzue.dialogx.dialogs.PopNotification;
import com.kongzue.dialogx.dialogs.WaitDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class about extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //弹窗初始化
        setContentView(R.layout.about);
        StatusBarUtil status=new StatusBarUtil();
        status.setStatusColor();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));
        View leave_button = findViewById(R.id.leave_about_button);
        leave_button.setOnClickListener(v -> finish());
        View joinqq_button = findViewById(R.id.join_qq_button);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView user=findViewById(R.id.user_ab);
        TextView privacy=findViewById(R.id.privacy);
        privacy.setOnClickListener(v -> {
            BottomDialog.show("源坊APP隐私协议",
                    "一、总则\n" +
                    "\n" +
                    "为了保护用户的个人信息和隐私，特制定本隐私协议（以下简称“本协议”）。本协议适用于源坊APP资源聚合平台（以下简称“本平台”）收集、使用、存储和保护用户个人信息的相关行为。\n" +
                    "\n" +
                    "二、个人信息收集\n" +
                    "\n" +
                    "收集范围：为了提供更好的服务和满足用户需求，本平台会收集必要的用户个人信息，包括但不限于姓名、邮箱地址、手机号、账号信息等。\n" +
                    "收集方式：用户在使用本平台的过程中，通过注册、登录、使用相关功能等方式主动提供或产生的个人信息，将被收集用于提供服务、改进产品、进行内部管理等。\n" +
                    "安全措施：我们将采取合理的技术和管理措施，确保用户个人信息的安全，防止数据泄露、丢失或被非法访问。\n" +
                    "三、个人信息使用与共享\n" +
                    "\n" +
                    "使用目的：收集的用户个人信息仅用于提供和改进服务，满足用户需求，保障用户权益，不会用于其他商业用途。\n" +
                    "共享第三方：除非得到用户的明确同意或法律要求，本平台不会将用户的个人信息共享给任何第三方，包括其他平台或广告商等。\n" +
                    "共享必要情况：在某些必要情况下，如配合法律法规调查、防止严重违法行为等，本平台可能会向相关机构或部门共享部分个人信息。\n" +
                    "四、用户权利与责任\n" +
                    "\n" +
                    "信息管理：用户有权管理自己的个人信息，包括修改、删除或导出个人信息等。用户应确保提供信息的真实性和准确性。\n" +
                    "遵守法律法规：用户应遵守国家法律法规和本协议的规定，不得滥用个人信息或侵犯他人权益。\n" +
                    "五、儿童隐私保护\n" +
                    "\n" +
                    "特别关注：对于未满18岁的儿童，我们特别重视其隐私保护。在收集、使用、存储和保护儿童个人信息时，我们将严格遵守相关法律法规和政策。\n" +
                    "监护人同意：在使用本平台时，请确保已获得儿童的法定监护人的同意。监护人有权查看和更正儿童的个人信息，以及要求删除或导出相关信息。\n" +
                    "六、隐私政策更新\n" +
                    "\n" +
                    "更新通知：本平台保留随时修改本协议的权利，修改后的协议将在发布后生效。我们将通过适当的方式通知用户关于隐私政策的更新内容。\n" +
                    "用户同意：用户在使用本平台时，应随时关注隐私政策的更新内容，并同意遵守最新的隐私协议。如用户不同意更新后的隐私协议，应停止使用本平台。\n" +
                    "七、争议解决与法律适用\n" +
                    "\n" +
                    "争议解决：对于因本协议产生的任何争议或纠纷，双方应首先友好协商解决；协商不成的，任何一方可向所在地人民法院提起诉讼。\n" +
                    "法律适用：本协议受相关法律法规的约束和解释。如有任何疑问或需要帮助，请联系我们的客服团队或咨询专业法律人士。");
        });
        user.setOnClickListener(v -> {
            BottomDialog.show("源坊用户协议",
                    "\n" +
                    "一、总则\n" +
                    "\n" +
                    "欢迎使用源坊APP资源聚合平台（以下简称“本平台”）。在使用本平台之前，请您务必仔细阅读本协议，并同意遵守以下条款。\n" +
                    "\n" +
                    "二、平台使用\n" +
                    "\n" +
                    "资源上传与分享：您有权在本平台上上传、分享和下载各类资源，包括但不限于文字、图片、音频、视频等。请确保上传的资源不侵犯任何知识产权或其他合法权益。\n" +
                    "遵守法律法规：您应遵守国家法律法规，不得上传任何违法、违规或低俗的内容。对于违规内容，我们将进行删除或采取其他必要措施。\n" +
                    "尊重他人权益：请尊重其他用户的权益，不得上传他人的原创内容，除非您已获得相关授权。\n" +
                    "三、费用相关\n" +
                    "\n" +
                    "免费使用：本平台目前为免费使用。但未来如有收费功能或服务，您应按照相关规定支付费用。\n" +
                    "广告与推广：为了维持平台的运营，我们可能会在平台上展示广告或进行推广活动。您应遵守相关规定，不得干扰正常的广告或推广活动。\n" +
                    "四、免责声明\n" +
                    "\n" +
                    "内容审核：我们会对上传的资源进行审核，但无法保证所有内容都符合法律法规和道德标准。对于违规内容，我们不承担责任。\n" +
                    "风险自负：您应自行承担使用本平台的风险和后果。对于因使用本平台产生的任何损失或损害，我们不承担责任。\n" +
                    "第三方链接与软件：本平台可能提供第三方链接或软件下载。请注意，这些链接或软件可能存在安全风险。在使用前，请自行评估风险。\n" +
                    "五、个人信息保护\n" +
                    "\n" +
                    "信息收集与使用：我们会收集必要的用户信息，如姓名、邮箱等，用于提供更好的服务和满足您的需求。请注意，您提供的个人信息将严格保密，除非得到您的明确同意或法律要求，否则不会向任何第三方透露或出售。\n" +
                    "儿童隐私保护：对于未满18岁的儿童，您应特别注意其隐私保护。在使用本平台时，请确保已获得其法定监护人的同意。\n");
        });
        joinqq_button.setOnClickListener(v -> {
            joinQQGroup("9zhvp729KmL0p20Bg3-Hfd8wQCETevIx");
        });


        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo("com.gujiunet.code", 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        String versionName = packageInfo.versionName;
        TextView version = findViewById(R.id.version_view);
        version.setText("Version" + versionName);


        View checkupdatebutton = findViewById(R.id.checkupdate_button);
        checkupdatebutton.setOnClickListener(v -> {
            WaitDialog.show("正在检测...");
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
                                dialog.cancel();
                                Log.i("事件", "点击了取消");
                            });

                            AlertDialog utw = update_dialog.create();
                            utw.show();
                        });
                    } else {
                        WaitDialog.dismiss();
                        PopNotification.show("您目前已经是最新版!");
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });
    }

    /****************
     *
     * 发起添加群流程。群号：IAPP Code交流群(200986821) 的 key 为： 9zhvp729KmL0p20Bg3-Hfd8wQCETevIx
     * 调用 joinQQGroup(9zhvp729KmL0p20Bg3-Hfd8wQCETevIx) 即可发起手Q客户端申请加群 IAPP Code交流群(200986821)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回false表示呼起失败
     ******************/
    public boolean joinQQGroup(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            PopNotification.show("未安装QQ或版本不支持");
            return false;
        }
    }
}

