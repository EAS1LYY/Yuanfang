package com.gujiunet.code;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kongzue.dialogx.dialogs.BottomDialog;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.BaseBannerInfo;
import com.stx.xhb.androidx.transformers.Transformer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link main#newInstance} factory method to
 * create an instance of this fragment.
 */
public class main extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static String text_data;
    private static String user_tx_data;
    private static String banner1;
    private static String banner2;
    private static String banner3;
    private static String banner4;
    private static String banner5;
    private static String banner1url;
    private static String banner2url;
    private static String banner3url;
    private static String banner4url;
    private static String banner5url;
    private static String random_data_returna;

    private static String user_id_data;
    private static String user_name_data;
    private static String user_qq_data;
    private static String user_qm_data;
    private static  String user_regip_data;
    private static String user_lastlogin_data;
    private static String user_coin_data;
    private static String user_vip_data;
    private static  String user_regtime_data;
    private static String user_yhqx_data;
    private static String user_buycode_data;

    RecyclerView random_code_recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public main() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment main.
     */
    // TODO: Rename and change types and number of parameters
    public static main newInstance(String param1, String param2) {
        main fragment = new main();
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

    public void updateInfo() throws JSONException {
        if(random_data_returna != null) {

            Log.i("随机源码返回",random_data_returna);
            JSONObject processjsonObject = new JSONObject(random_data_returna);
            //String process_list = processjsonObject.get("process_list").toString();
            JSONArray process_list = processjsonObject.getJSONArray("list");
            ArrayList<random_code_model> process_code_list = new ArrayList<>();
            for (int i = 0; i < process_list.length(); i++) {
                //获取json对象
                JSONObject jsonObject1 = process_list.getJSONObject(i);
                process_code_list.add(new random_code_model(jsonObject1.get("名称").toString(), jsonObject1.get("ID").toString(), jsonObject1.get("图标").toString()));
            }
            View main_view_random=getView();
            ImageView userimg=main_view_random.findViewById(R.id.user_image);
            userimg.setOnClickListener(v -> {
                BottomDialog.show("用户信息", "ID:"+user_id_data+"\n邮箱:"+user_qq_data+"\n注册IP:"+user_regip_data+"\n最后登录:"+user_lastlogin_data+"\n金币:"+user_coin_data+"\n注册时间:"+user_regtime_data+"\n用户权限:"+user_yhqx_data+"\n会员到期时间:"+user_vip_data+"\n\n\n");
            });
            RecyclerView recyclerView = main_view_random.findViewById(R.id.recyclerview_random_code);
            random_code_RecyclerAdapter random_code_RecyclerAdapter_class = new random_code_RecyclerAdapter(requireActivity(), process_code_list);
            recyclerView.setAdapter(random_code_RecyclerAdapter_class);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addOnItemTouchListener(new recyclerItemClickListener(getActivity(), new recyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    TextView codeid = view.findViewById(R.id.code_id);
                    String ID = codeid.getText().toString();
                    //Toast.makeText(process.this, ID, Toast.LENGTH_SHORT).show();
                    String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/code.json";
                    File file = new File(filePath);
                    JSONObject jsonObj = new JSONObject();
                    try {
                        jsonObj.put("codeid", ID);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        // 判断文件是否存在，如果不存在则创建文件
                        if (!file.exists()) {
                            requireActivity().getExternalFilesDir(null);
                            file.createNewFile();
                        }

                        FileWriter writer = new FileWriter(filePath);
                        writer.write(jsonObj.toString());
                        writer.close();

                        Intent intent = new Intent();
                        intent.setClass(getActivity(), code_info.class);
                        startActivity(intent);


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onLongClick(View view, int posotion) {

                }
            }));
        }  else {
            Log.i("1","没有数据");
        }




    }

        @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            try {
                Thread.sleep(1000);
                updateInfo();
                initBanner();

            } catch (InterruptedException | JSONException e) {
                throw new RuntimeException(e);
            }
inittext();

        }

        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_main, container, false);
        }


    // 初始化 轮播图
    private void initBanner() {
        //获取控件
        View mainview=getView();
        XBanner mXBanner =mainview.findViewById(R.id.xbanner);
        List<BaseBannerInfo> imgesUrl = new ArrayList<>();


        imgesUrl.add(new BaseBannerInfo() {
            @Override
            public Object getXBannerUrl() {
                return banner1;
            }

            @Override
            public String getXBannerTitle() {
                return "11";
            }
        });
        imgesUrl.add(new BaseBannerInfo() {
            @Override
            public Object getXBannerUrl() {
                return banner2;
            }

            @Override
            public String getXBannerTitle() {
                return "11";
            }
        });
        imgesUrl.add(new BaseBannerInfo() {
            @Override
            public Object getXBannerUrl() {
                return banner3;
            }

            @Override
            public String getXBannerTitle() {
                return "11";
            }
        });
        imgesUrl.add(new BaseBannerInfo() {
            @Override
            public Object getXBannerUrl() {
                return banner4;
            }

            @Override
            public String getXBannerTitle() {
                return "11";
            }
        });
        imgesUrl.add(new BaseBannerInfo() {
            @Override
            public Object getXBannerUrl() {
                return banner5;
            }

            @Override
            public String getXBannerTitle() {
                return "11";
            }
        });
        // 为XBanner绑定数据
        mXBanner.setBannerData(imgesUrl);
//        // XBanner适配数据
        mXBanner.loadImage((banner, model, view, position) -> Glide.with(getActivity()).load(imgesUrl.get(position).getXBannerUrl()).into((ImageView) view));
        // 设置XBanner的页面切换特效，选择一个即可，总的大概就这么多效果啦，欢迎使用
        mXBanner.setPageTransformer(Transformer.Default);//横向移动
        ImageView user_img=mainview.findViewById(R.id.user_image);
        Glide.with(requireContext()).load(user_tx_data).into(user_img);

        mXBanner.setOnItemClickListener((banner, model, view, position) -> {
            if (position==0){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(banner1url));
                startActivity(intent);
            } else if (position==1){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(banner2url));
                startActivity(intent);
            }if (position==2){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(banner3url));
                startActivity(intent);
            }if (position==3){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(banner4url));
                startActivity(intent);
            }if (position==4){
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(banner5url));
                startActivity(intent);
            }
        });
    }

    private void inittext(){
            if(Objects.equals(text_data, "")){

            }else{

                try {
                        JSONObject processjsonObject = new JSONObject(text_data);
                        //String process_list = processjsonObject.get("process_list").toString();
                        JSONArray text_lists = processjsonObject.getJSONArray("list");
                        ArrayList<text_model> text_list = new ArrayList<>();
                        new Thread(() -> {
                            try {
                                for (int i = 0; i < text_lists.length(); i++) {
                                    //获取json对象
                                    JSONObject jsonObject1 = text_lists.getJSONObject(i);
                                    String userurl = "http://software.toolr.cn/api.php?type=logininfo&ID=" + jsonObject1.get("作者").toString();
                                    Http userhttp = new Http();
                                    //Log.i("链接",url);
                                    userhttp.setUrl(userurl).setRequestType(Http.REQUEST_TYPE_GET);
                                    Http.Request user_return_data = userhttp.request();
                                    JSONObject userdatajson = new JSONObject(user_return_data.content);
                                    String user_tx = userdatajson.get("头像").toString();
                                    String user_name = userdatajson.get("Name").toString();
                                    if (user_tx.equals("0")){
                                        String user_txx="http://software.toolr.cn/code/tp/user.png";
                                        text_list.add(new text_model(jsonObject1.get("ID").toString(), jsonObject1.get("标题").toString(), jsonObject1.get("内容").toString(), jsonObject1.get("作者").toString(),jsonObject1.get("发布时间").toString(),jsonObject1.get("浏览").toString(),user_name,user_txx));
                                    }
                                    else{
                                        text_list.add(new text_model(jsonObject1.get("ID").toString(), jsonObject1.get("标题").toString(), jsonObject1.get("内容").toString(), jsonObject1.get("作者").toString(),jsonObject1.get("发布时间").toString(),jsonObject1.get("浏览").toString(),user_name,user_tx));

                                    }
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }).start();

                        View text_view=getView();
                    CardView serch_button=text_view.findViewById(R.id.search_button);
                    serch_button.setOnClickListener(v -> {
                        Intent intent = new Intent();
                        intent.setClass(requireActivity(), serch.class);
                        startActivity(intent);
                    });
                        RecyclerView textrecyclerView = text_view.findViewById(R.id.text_Recycler);
                        textrecyclerviewadapter textrecyclerViewAdapter = new textrecyclerviewadapter(getActivity(), text_list);
                    textrecyclerView.setAdapter(textrecyclerViewAdapter);
                    textrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    textrecyclerView.addOnItemTouchListener(new recyclerItemClickListener(getActivity(), new recyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            TextView textid = view.findViewById(R.id.text_id);
                            String ID = textid.getText().toString();
                            //Toast.makeText(process.this, ID, Toast.LENGTH_SHORT).show();
                            String filePath = "/storage/emulated/0/Android/data/com.gujiunet.code/text.json";
                            File file = new File(filePath);
                            JSONObject jsonObj = new JSONObject();
                            try {
                                jsonObj.put("textid", ID);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                // 判断文件是否存在，如果不存在则创建文件
                                if (!file.exists()) {
                                    requireActivity().getExternalFilesDir(null);
                                    file.createNewFile();
                                }

                                FileWriter writer = new FileWriter(filePath);
                                writer.write(jsonObj.toString());
                                writer.close();

                                Intent intent = new Intent();
                                intent.setClass(requireActivity(), text_info.class);
                                startActivity(intent);


                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onLongClick(View view, int posotion) {

                        }
                    }));

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
    }

        public static void updateVarInfo (String image, String banner_1, String banner_2, String
        banner_3, String banner_4, String banner_5, String banner_1_url
                , String banner_2_url
                , String banner_3_url
                , String banner_4_url
                , String banner_5_url
        ,String random_code_return){
            user_tx_data = image;
            banner1 = banner_1;
            banner2 = banner_2;
            banner3 = banner_3;
            banner4 = banner_4;
            banner5 = banner_5;
            banner1url = banner_1_url;
            banner2url = banner_2_url;
            banner3url = banner_3_url;
            banner4url = banner_4_url;
            banner5url = banner_5_url;
            random_data_returna= random_code_return;
        }

    public static void updateUserVarInfo(String user_id,String user_name,String user_qq,String user_qm,String user_regip,String user_lastlogin,String user_coin,String user_vip,String user_regtime,String user_tx,String user_yhqx,String user_buycode) {
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
    }
    public static void updatetextVarInfo(String text){
        text_data = text;
    }


}
