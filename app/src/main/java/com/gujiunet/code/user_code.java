package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kongzue.dialogx.dialogs.PopNotification;
import com.kongzue.dialogx.dialogs.WaitDialog;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link user_code#newInstance} factory method to
 * create an instance of this fragment.
 */
public class user_code extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int currentPage = 1;

    // TODO: Rename and change types of parameters

    private static String codedataa;

    public user_code() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment user_code.
     */
    // TODO: Rename and change types and number of parameters
    public static user_code newInstance(String param1, String param2) {
        user_code fragment = new user_code();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            Thread.sleep(500);
            initcode();
            WaitDialog.dismiss();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_code, container, false);
    }

    @SuppressLint({"NotifyDataSetChanged", "ResourceAsColor"})
    private void initcode(){
        WaitDialog.show("正在加载");
        ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载更多";
        ClassicsFooter.REFRESH_FOOTER_RELEASE = "释放立即加载";
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在刷新...";
        ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载...";
        ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成";
        ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败";
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "没有更多数据了";
        //InputStreamReader 将字节输入流转换为字符流
        try {
            if (codedataa.equals("")) {
            } else {
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
                JSONObject processjsonObject = new JSONObject(codedataa);
                //String process_list = processjsonObject.get("process_list").toString();
                JSONArray process_list = processjsonObject.getJSONArray("list");
                ArrayList<process_code_model> user_code_list = new ArrayList<>();
                for (int i = 0; i < process_list.length(); i++) {
                    //获取json对象
                    JSONObject jsonObject1 = process_list.getJSONObject(i);
                    user_code_list.add(new process_code_model(jsonObject1.get("名称").toString(), jsonObject1.get("介绍").toString(), jsonObject1.get("ID").toString(), jsonObject1.get("图片1").toString()));
                }
                View code_view=getView();

                RefreshLayout refreshLayout = code_view.findViewById(R.id.refreshLayout);
                refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
                refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
                //refreshLayout.setEnableRefresh(false);
                refreshLayout.setEnableAutoLoadMore(false);
                refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
                refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setColorSchemeColors(R.color.Blue));
                RecyclerView recyclerView = code_view.findViewById(R.id.recyclerview_user_code);
                RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), user_code_list);
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


                refreshLayout.setOnRefreshListener(refreshlayout -> new Thread(() -> {
                    try {

                        if (codedataa.equals("")) {
                        } else {
                            Http code_http = new Http();
                            code_http.setUrl("http://software.toolr.cn/api.php?type=getusercodejson&ID="+create+"&page=1");
                            Http.Request codereturn = code_http.request();
                            JSONObject processjsonObject1 = new JSONObject(codereturn.content);
                            //String process_list = processjsonObject.get("process_list").toString();
                            JSONArray process_list1 = processjsonObject1.getJSONArray("list");
                            ArrayList<process_code_model> process_code_list1 = new ArrayList<>();
                            for (int i = 0; i < process_list1.length(); i++) {
                                //获取json对象
                                JSONObject jsonObject1 = process_list1.getJSONObject(i);
                                process_code_list1.add(new process_code_model(jsonObject1.get("名称").toString(), jsonObject1.get("介绍").toString(), jsonObject1.get("ID").toString(), jsonObject1.get("图片1").toString()));
                            }
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                }).start());


                refreshLayout.setOnLoadMoreListener(refreshlayout -> {
                    Log.i("page", String.valueOf(currentPage));
                    currentPage++;
                    new Thread(() -> {
                        try {
                            Http morecode = new Http();
                            morecode.setUrl("http://software.toolr.cn/api.php?type=getusercodejson&ID="+create+"&page="+currentPage).setRequestType(Http.REQUEST_TYPE_GET);
                            Http.Request morecode_return = morecode.request();
                            if (Objects.equals(morecode_return.getContent(), "")) {
                                boolean isLoadingMore = false;
                                PopNotification.show("已经到底了!");
                                refreshLayout.setNoMoreData(true);
                                refreshLayout.finishRefresh();
                            }else{
                                JSONObject morecodejson = new JSONObject(morecode_return.content);
                                JSONArray more_list = morecodejson.getJSONArray("list");

                                for (int i = 0; i < more_list.length(); i++) {
                                    //获取json对象
                                    JSONObject jsonObject2 = more_list.getJSONObject(i);
                                    Log.i("源码名称",jsonObject2.get("名称").toString());
                                    user_code_list.add(new process_code_model(jsonObject2.get("名称").toString(), jsonObject2.get("介绍").toString(), jsonObject2.get("ID").toString(), jsonObject2.get("图片1").toString()));
                                }
                                refreshLayout.finishRefresh();
                            }

                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                });
                //recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

                // 首先获取RecyclerView对象及其布局管理器


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
                            intent.setClass(requireActivity(), code_info.class);
                            startActivity(intent);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onLongClick(View view, int posotion) {

                    }
                }));
            }
        } catch (JSONException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void updateVarInfo(String code_data){
        codedataa = code_data;
    }


}