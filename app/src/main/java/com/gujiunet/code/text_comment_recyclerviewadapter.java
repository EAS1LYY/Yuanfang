package com.gujiunet.code;

import static com.kongzue.dialogx.impl.ActivityLifecycleImpl.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

public class text_comment_recyclerviewadapter extends RecyclerView.Adapter<text_comment_recyclerviewadapter.MyViewHoloder> {

    Context context;
    ArrayList<text_comment_model> text_comment_list;
    public text_comment_recyclerviewadapter(Context context, ArrayList<text_comment_model> text_comment_models){
        this.context=context;
        this.text_comment_list=text_comment_models;
    }

    @NonNull
    @Override
    public MyViewHoloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.list_text_comment,parent,false);
        return new MyViewHoloder(view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(@NonNull MyViewHoloder holder, int position) {
        Glide.with(getApplicationContext()).load(text_comment_list.get(position).getText_commentimgurl())
                .into(holder.text_user_img);
        if (Objects.equals(text_comment_list.get(position).getText_commentqx(), "普通用户")){
            holder.text_qx.setVisibility(View.GONE);
        }
        holder.text_qx.setText(text_comment_list.get(position).getText_commentqx());
        holder.text_time.setText(text_comment_list.get(position).getText_commenttime());
        holder.text_user_name.setText(text_comment_list.get(position).getText_commentauthorname());
        WebSettings webSettings = holder.text_text.getSettings();
        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        holder.text_text.loadUrl(text_comment_list.get(position).getText_commenttext());
    }

    @Override
    public int getItemCount() {
        return text_comment_list.size();
    }


    public static class MyViewHoloder extends RecyclerView.ViewHolder{
        ImageView text_user_img;
        TextView text_user_name;
        TextView text_time;
        WebView text_text;
        TextView text_id;
        TextView text_qx;
        public MyViewHoloder(@NonNull View itemView) {
            super(itemView);
            text_time=itemView.findViewById(R.id.text_comment_time);
            text_qx=itemView.findViewById(R.id.text_comment_qx);
            text_user_img=itemView.findViewById(R.id.text_comment_user_img);
            text_user_name=itemView.findViewById(R.id.text_comment_name);
            text_text=itemView.findViewById(R.id.text_comment_text);
            text_id=itemView.findViewById(R.id.text_id);
        }
    }
}
