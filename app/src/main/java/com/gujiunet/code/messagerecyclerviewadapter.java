package com.gujiunet.code;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class messagerecyclerviewadapter extends RecyclerView.Adapter<messagerecyclerviewadapter.MyViewHoloder> {

    Context context;
    ArrayList<message_model> messageModels;
    public messagerecyclerviewadapter(Context context, ArrayList<message_model> messageModels){
        this.context=context;
        this.messageModels=messageModels;
    }

    @NonNull
    @Override
    public MyViewHoloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.list_message,parent,false);
        return new MyViewHoloder(view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(@NonNull MyViewHoloder holder, int position) {
        holder.text_message_text.setText(messageModels.get(position).getInform_text());
        holder.text_message_id.setText(messageModels.get(position).getInform_id());
        holder.text_message_name.setText(messageModels.get(position).getInform_name());
    }

    @Override
    public int getItemCount() {
        return messageModels.size();
    }


    public static class MyViewHoloder extends RecyclerView.ViewHolder{
        TextView text_message_name;
        TextView text_message_text;
        TextView text_message_id;
        public MyViewHoloder(@NonNull View itemView) {
            super(itemView);
            text_message_text=itemView.findViewById(R.id.message_text);
            text_message_id=itemView.findViewById(R.id.message_id);
            text_message_name=itemView.findViewById(R.id.message_name);
        }
    }
}
