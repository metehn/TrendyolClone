package com.metehanersoy.trendyolclone.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.metehanersoy.trendyolclone.Class.MyProduct;
import com.metehanersoy.trendyolclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//Best seller adapter view
public class BasketRecyclerViewAdapter extends RecyclerView.Adapter<BasketRecyclerViewAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<MyProduct> list;

    public BasketRecyclerViewAdapter(Context mContext, ArrayList<MyProduct> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyProduct product = list.get(position);

        try {
            Picasso.get().load(product.getImageURL()).into(holder.iv_custom_row);
            holder.tv_productName_custom_row.setText(list.get(position).getDescription());

            if (product.getFastDelivery()) {
                holder.tv_fastDelivery_custom_row.setVisibility(View.VISIBLE);
            }else{
                holder.tv_fastDelivery_custom_row.setVisibility(View.INVISIBLE);
            }

            holder.tv_productPrice_custom_row.setText(list.get(position).getPrice() + " TL");

        } catch (Error e) {
            Toast.makeText(mContext, e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_custom_row;
        TextView tv_productName_custom_row, tv_fastDelivery_custom_row, tv_productPrice_custom_row;
        AppCompatButton acb_custom_row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_custom_row = itemView.findViewById(R.id.iv_custom_row);
            tv_productName_custom_row = itemView.findViewById(R.id.tv_productName_custom_row);
            tv_fastDelivery_custom_row = itemView.findViewById(R.id.tv_fastDelivery_custom_row);
            tv_productPrice_custom_row = itemView.findViewById(R.id.tv_productPrice_custom_row);
            acb_custom_row = itemView.findViewById(R.id.acb_custom_row);


            acb_custom_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
}

