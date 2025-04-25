package com.gujiunet.code;

import static com.kongzue.dialogx.impl.ActivityLifecycleImpl.getApplicationContext;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class textrecyclerviewadapter extends RecyclerView.Adapter<textrecyclerviewadapter.MyViewHoloder> {

    Context context;
    ArrayList<text_model> text_list;
    public textrecyclerviewadapter(Context context, ArrayList<text_model> text_models){
        this.context=context;
        this.text_list=text_models;
    }

    @NonNull
    @Override
    public MyViewHoloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.list_text,parent,false);
        return new MyViewHoloder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoloder holder, int position) {
        Glide.with(getApplicationContext()).load(text_list.get(position).getText_author_imgurl())
                .into(holder.text_user_img);
        holder.text_text.setText(text_list.get(position).getText_text());
        holder.text_time.setText(text_list.get(position).getText_time());
        holder.text_title.setText(text_list.get(position).getText_title());
        holder.text_views.setText(text_list.get(position).getText_views());
        holder.text_user_name.setText(text_list.get(position).getText_author_name());
        holder.text_id.setText(text_list.get(position).getText_id());

    }

    @Override
    public int getItemCount() {
        return text_list.size();
    }


    public static class MyViewHoloder extends RecyclerView.ViewHolder{
        ImageView text_user_img;
        TextView text_user_name;
        TextView text_time;
        TextView text_title;
        TextView text_text;
        TextView text_views;
        TextView text_id;
        public MyViewHoloder(@NonNull View itemView) {
            super(itemView);
            text_time=itemView.findViewById(R.id.text_time);
            text_views=itemView.findViewById(R.id.text_views);
            text_title=itemView.findViewById(R.id.text_title);
            text_user_img=itemView.findViewById(R.id.text_user_img);
            text_user_name=itemView.findViewById(R.id.text_user_name);
            text_text=itemView.findViewById(R.id.text_text);
            text_id=itemView.findViewById(R.id.text_id);
        }
    }
}
