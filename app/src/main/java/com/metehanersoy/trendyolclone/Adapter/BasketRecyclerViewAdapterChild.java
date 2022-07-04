package com.metehanersoy.trendyolclone.Adapter;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.metehanersoy.trendyolclone.Class.MyProduct;
import com.metehanersoy.trendyolclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BasketRecyclerViewAdapterChild extends RecyclerView.Adapter<BasketRecyclerViewAdapterChild.MyViewHolder> {

    Context mContext;
    ArrayList<MyProduct> list;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_basket_child_row, parent, false);

        return new BasketRecyclerViewAdapterChild.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        MyProduct product = list.get(position);

        try {
            Picasso.get().load(product.getImageURL()).into(holder.iv_Basket_CardView_Child);
            String nameAndDescription = "<b><font color='black'>" + list.get(position).getName() + "</font></b> " + list.get(position).getDescription();
            holder.tv_productName_Basket_CardView_Child.setText(Html.fromHtml(nameAndDescription));
            holder.tv_Price_Basket_CardView_Child.setText(product.getPrice() + " TL");
            if (product.getFastDelivery()) {
                Spannable str = new SpannableString(mContext.getString(R.string.fast_delivery));
                str.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.green_weird)), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tv_FastDelivery_Basket_CardView_Child.setText(TextUtils.concat(str, ": ", Html.fromHtml("<font color='black'>1 gün içinde kargoda</font>")));
            }

        } catch (Error e) {

            Toast.makeText(mContext, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb_Basket_CardView_Child;
        ImageView iv_Basket_CardView_Child, iv_Decrease_CardView_Child, iv_Increase_CardView_Child;
        TextView tv_productName_Basket_CardView_Child, tv_FastDelivery_Basket_CardView_Child,
                tv_ProductAmount_Basket_CardView_Child, tv_Price_Basket_CardView_Child;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cb_Basket_CardView_Child = itemView.findViewById(R.id.cb_Basket_CardView_Child);
            iv_Basket_CardView_Child = itemView.findViewById(R.id.iv_Basket_CardView_Child);
            iv_Decrease_CardView_Child = itemView.findViewById(R.id.iv_Decrease_CardView_Child);
            iv_Increase_CardView_Child = itemView.findViewById(R.id.iv_Increase_CardView_Child);
            tv_productName_Basket_CardView_Child = itemView.findViewById(R.id.tv_productName_Basket_CardView_Child);
            tv_FastDelivery_Basket_CardView_Child = itemView.findViewById(R.id.tv_FastDelivery_Basket_CardView_Child);
            tv_ProductAmount_Basket_CardView_Child = itemView.findViewById(R.id.tv_ProductAmount_Basket_CardView_Child);
            tv_Price_Basket_CardView_Child = itemView.findViewById(R.id.tv_Price_Basket_CardView_Child);

        }
    }


}
