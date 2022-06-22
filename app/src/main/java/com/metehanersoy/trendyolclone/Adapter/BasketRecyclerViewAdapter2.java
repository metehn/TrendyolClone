package com.metehanersoy.trendyolclone.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.metehanersoy.trendyolclone.Class.MyProduct;

import java.util.ArrayList;

public class BasketRecyclerViewAdapter2 extends RecyclerView.Adapter<BasketRecyclerViewAdapter2.MyViewHolder> {

    Context mContext;
    ArrayList<MyProduct> list;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
