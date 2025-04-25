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

    public class assort_RecyclerViewAdapter extends RecyclerView.Adapter<com.gujiunet.code.assort_RecyclerViewAdapter.MyViewHoloder> {

        Context context;
        ArrayList<assort_model> assortModels;
        public assort_RecyclerViewAdapter(Context context, ArrayList<assort_model> assortModels){
            this.context=context;
            this.assortModels=assortModels;
        }

        @NonNull
        @Override
        public com.gujiunet.code.assort_RecyclerViewAdapter.MyViewHoloder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(this.context).inflate(R.layout.list_assort,parent,false);
            return new com.gujiunet.code.assort_RecyclerViewAdapter.MyViewHoloder(view);
        }

        @SuppressLint("SetJavaScriptEnabled")
        @Override
        public void onBindViewHolder(@NonNull com.gujiunet.code.assort_RecyclerViewAdapter.MyViewHoloder holder, int position) {
            holder.assort_name_id.setText(assortModels.get(position).getAssort_name());
            holder.assort_id_id.setText(assortModels.get(position).getAssort_ID());
            holder.assort_text_id.setText(assortModels.get(position).getAssort_text());
        }

        @Override
        public int getItemCount() {
            return assortModels.size();
        }


        public static class MyViewHoloder extends RecyclerView.ViewHolder{
            TextView assort_name_id;
            TextView assort_id_id;
            TextView assort_text_id;
            public MyViewHoloder(@NonNull View itemView) {
                super(itemView);
                assort_name_id=itemView.findViewById(R.id.assort_name);
                assort_id_id=itemView.findViewById(R.id.assort_id);
                assort_text_id=itemView.findViewById(R.id.assort_text);
            }
        }
    }
