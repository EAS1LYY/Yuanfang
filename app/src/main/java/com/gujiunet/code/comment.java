package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
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
 * Use the {@link comment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class comment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int currentPage = 1;

    private static String text_data;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public comment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment comment.
     */
    // TODO: Rename and change types and number of parameters
    public static comment newInstance(String param1, String param2) {
        comment fragment = new comment();
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
        inittext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }
    @SuppressLint("ResourceAsColor")
    private void inittext(){
        ClassicsFooter.REFRESH_FOOTER_PULLING = "上拉加载更多";
        ClassicsFooter.REFRESH_FOOTER_RELEASE = "释放立即加载";
        ClassicsFooter.REFRESH_FOOTER_REFRESHING = "正在刷新...";
        ClassicsFooter.REFRESH_FOOTER_LOADING = "正在加载...";
        ClassicsFooter.REFRESH_FOOTER_FINISH = "加载完成";
        ClassicsFooter.REFRESH_FOOTER_FAILED = "加载失败";
        ClassicsFooter.REFRESH_FOOTER_NOTHING = "没有更多数据了";
            if(Objects.equals(text_data, "")){

            }else{

                try {
                    JSONObject processjsonObject = new JSONObject(text_data);
                    //String process_list = processjsonObject.get("process_list").toString();
                    JSONArray text_lists = processjsonObject.getJSONArray("list");
                    ArrayList<text_model> text_list = new ArrayList<>();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
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

            }
    }).start();
                    View text_view=getView();
                    RefreshLayout textrefreshLayout = text_view.findViewById(R.id.textrefreshLayout);
                    textrefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
                    textrefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
                    //refreshLayout.setEnableRefresh(false);
                    textrefreshLayout.setEnableAutoLoadMore(false);
                    textrefreshLayout.setEnableLoadMoreWhenContentNotFull(false);
                    textrefreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setColorSchemeColors(R.color.Blue));
                    textrefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                        @Override
                        public void onRefresh(@NonNull RefreshLayout refreshlayout) {
                            refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                        }
                    });
                    textrefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                        @Override
                        public void onLoadMore(@NonNull RefreshLayout refreshlayout) {
                            currentPage++;

                            new Thread(() -> {

                                try {
                                    Http more_text_http=new Http();
                                    more_text_http.setUrl("http://software.toolr.cn/api.php?type=articlelist&page="+currentPage).setRequestType(Http.REQUEST_TYPE_GET);
                                    Http.Request more_text=more_text_http.request();
                                    if (Objects.equals(more_text.content, "")) {
                                        PopNotification.show("已经到底了!");
                                        textrefreshLayout.setNoMoreData(true);
                                        textrefreshLayout.finishRefresh();
                                    }else{
                                        JSONObject processjsonObject = new JSONObject(more_text.content);
                                        //String process_list = processjsonObject.get("process_list").toString();
                                        JSONArray more_text_lists = processjsonObject.getJSONArray("list");
                                        for (int i = 0; i < more_text_lists.length(); i++) {
                                            //获取json对象
                                            JSONObject jsonObject1 = more_text_lists.getJSONObject(i);
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
                                            }                                        }
                                        textrefreshLayout.finishRefresh();
                                    }

                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }).start();




                            refreshlayout.finishLoadMore(2000);//传入false表示加载失败
                        }

                    });
                    RecyclerView textrecyclerView = text_view.findViewById(R.id.recyclerview_text);
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

    public static void updatetextVarInfo(String text){
        text_data = text;
    }

}