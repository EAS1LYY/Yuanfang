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

public class random_code_RecyclerAdapter extends RecyclerView.Adapter<random_code_RecyclerAdapter.MyViewHoloder> {

    Context context;
    ArrayList<random_code_model> process_code_list;
    public random_code_RecyclerAdapter(Context context, ArrayList<random_code_model> process_code_list){
        this.context=context;
        this.process_code_list=process_code_list;
    }

    @NonNull
    @Override
    public MyViewHoloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.list_random_code,parent,false);
        return new MyViewHoloder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHoloder holder, int position) {
        holder.codetitle.setText(process_code_list.get(position).getCode_title());
        //holder.codetext.setText(process_code_list.get(position).getCode_text());
        holder.codeid.setText(process_code_list.get(position).getCode_id());
        Glide.with(getApplicationContext()).load(process_code_list.get(position).getCode_imageurl())
                .into(holder.imageid);

    }

    @Override
    public int getItemCount() {
        return process_code_list.size();
    }


    public static class MyViewHoloder extends RecyclerView.ViewHolder{
        TextView codetitle;
        TextView codetext;
        TextView codeid;
        ImageView imageid;
        public MyViewHoloder(@NonNull View itemView) {
            super(itemView);
            codetitle=itemView.findViewById(R.id.code_name);
            //codetext=itemView.findViewById(R.id.user_id);
            codeid=itemView.findViewById(R.id.code_id);
            imageid=itemView.findViewById(R.id.imageView11);
        }
    }
}
