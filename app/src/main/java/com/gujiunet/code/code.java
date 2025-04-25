package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kongzue.dialogx.dialogs.PopNotification;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link code#newInstance} factory method to
 * create an instance of this fragment.
 */
public class code extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private int currentPage = 1;



    // TODO: Rename and change types of parameters

    private static String codedata;
    private String mParam1;
    private String mParam2;

    public code() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment code.
     */
    // TODO: Rename and change types and number of parameters
    public static code newInstance(String param1, String param2) {
        code fragment = new code();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initcode();

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_code, container, false);
    }
    @SuppressLint({"NotifyDataSetChanged", "ResourceAsColor"})
    private void initcode(){
        ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载更多";
        ClassicsFooter.REFRESH_FOOTER_RELEASE = "释放立即加载";
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在刷新...";
        ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载...";
        ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成";
        ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败";
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "没有更多数据了";
            //InputStreamReader 将字节输入流转换为字符流
            try {
                if (codedata.equals("")) {
                } else {
                    JSONObject processjsonObject = new JSONObject(codedata);
                    //String process_list = processjsonObject.get("process_list").toString();
                    JSONArray process_list = processjsonObject.getJSONArray("list");
                    ArrayList<process_code_model> process_code_list = new ArrayList<>();
                    for (int i = 0; i < process_list.length(); i++) {
                        //获取json对象
                        JSONObject jsonObject1 = process_list.getJSONObject(i);
                        process_code_list.add(new process_code_model(jsonObject1.get("名称").toString(), jsonObject1.get("介绍").toString(), jsonObject1.get("ID").toString(), jsonObject1.get("图片1").toString()));
                    }
                    View code_view=getView();

                    RefreshLayout refreshLayout = code_view.findViewById(R.id.refreshLayout);
                    refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
                    refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
                    //refreshLayout.setEnableRefresh(false);
                    refreshLayout.setEnableAutoLoadMore(false);
                    refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
                    refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setColorSchemeColors(R.color.Blue));
                    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
                        @Override
                        public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                            new Thread(() -> {
                                try {
                                    // 清空原始的Array数组
                                   // Arrays.fill(new JSONArray[]{process_list}, null);

                                    // 重置ArrayList的大小为0，相当于清空了ArrayList
                                    //process_code_list.clear();
                                    if (codedata.equals("")) {
                                    } else {
                                        Http code_http = new Http();
                                        code_http.setUrl("http://software.toolr.cn/api.php?type=getcodejson&page=1");
                                        Http.Request codereturn = code_http.request();
                                        JSONObject processjsonObject = new JSONObject(codereturn.content);
                                        //String process_list = processjsonObject.get("process_list").toString();
                                        JSONArray process_list = processjsonObject.getJSONArray("list");
                                        ArrayList<process_code_model> process_code_list = new ArrayList<>();
                                        for (int i = 0; i < process_list.length(); i++) {
                                            //获取json对象
                                            JSONObject jsonObject1 = process_list.getJSONObject(i);
                                            process_code_list.add(new process_code_model(jsonObject1.get("名称").toString(), jsonObject1.get("介绍").toString(), jsonObject1.get("ID").toString(), jsonObject1.get("图片1").toString()));
                                        }
                                    }
                                } catch (JSONException e) {
                                    throw new RuntimeException(e);
                                }

                                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                            }).start();
                        }
                    });
                    refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                        @Override
                        public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                            currentPage++;

                            new Thread(() -> {

                                try {
                                    Http morecode = new Http();
                                    morecode.setUrl("http://software.toolr.cn/api.php?type=getcodejson&page=" + currentPage).setRequestType(Http.REQUEST_TYPE_GET);
                                    Http.Request morecode_return = morecode.request();
                                    if (Objects.equals(morecode_return.content, "")) {
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
                                            process_code_list.add(new process_code_model(jsonObject2.get("名称").toString(), jsonObject2.get("介绍").toString(), jsonObject2.get("ID").toString(), jsonObject2.get("图片1").toString()));
                                        }
                                        refreshLayout.finishRefresh();
                                    }

                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }).start();




                            refreshlayout.finishLoadMore(2000/*,false*/);//传入false表示加载失败
                        }
                    });


                    RecyclerView recyclerView = code_view.findViewById(R.id.recyclerview_code);
                    RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), process_code_list);
                    recyclerView.setAdapter(recyclerViewAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



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
                ImageView assort_button=getView().findViewById(R.id.sort_button);
                assort_button.setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.setClass(requireActivity(), assort.class);
                    startActivity(intent);
                });
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
    }
    public static void updateVarInfo(String code_data){
        codedata = code_data;
    }
}
