package com.metehanersoy.trendyolclone.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.metehanersoy.trendyolclone.Activity.MainActivity;
import com.metehanersoy.trendyolclone.Class.Basket;
import com.metehanersoy.trendyolclone.Class.BasketParentItem;
import com.metehanersoy.trendyolclone.Class.MyProduct;
import com.metehanersoy.trendyolclone.Fragment.BasketFragment;
import com.metehanersoy.trendyolclone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


//Best seller adapter view
public class BestSellerRecyclerViewAdapter extends RecyclerView.Adapter<BestSellerRecyclerViewAdapter.MyViewHolder> {

    private Activity mActivity;
    private ArrayList<MyProduct> list;

    public BestSellerRecyclerViewAdapter(Activity mActivity, ArrayList<MyProduct> list) {
        this.mActivity = mActivity;
        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_best_seller_row, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MyProduct myProduct = list.get(position);

        try {
            Picasso.get().load(myProduct.getImageURL()).into(holder.iv_custom_row);

            String nameAndDescription = "<b><font color='black'>" + list.get(position).getName() + "</font></b> " + list.get(position).getDescription();
            holder.tv_productName_custom_row.setText(Html.fromHtml(nameAndDescription));

            if (myProduct.getFastDelivery()) {
                holder.tv_fastDelivery_custom_row.setVisibility(View.VISIBLE);
            } else {
                holder.tv_fastDelivery_custom_row.setVisibility(View.INVISIBLE);
            }

            holder.tv_productPrice_custom_row.setText(list.get(position).getPrice() + " TL");

        } catch (Error e) {
            Toast.makeText(mActivity.getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        holder.acb_custom_row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Basket.basketList.add(myProduct);
                addToBasket(myProduct);
                Basket.bestSeller.remove(position);

                BasketFragment fragment = (BasketFragment) ((MainActivity) mActivity).findFragment(MainActivity.BASKET_FRAGMENT);

                fragment.bestSellerNotifyDataSetChanged();
                fragment.basketNotifyDataSetChanged();

                fragment = null;
            }
        });

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
        }
    }

    public void addToBasket(MyProduct myProduct) {

        if (Basket.basketList.isEmpty()) {
            BasketParentItem basketParentItem = new BasketParentItem();
            basketParentItem.addToList(myProduct);
            Basket.basketList.add(basketParentItem);
        } else {
            Toast.makeText(mActivity, "else", Toast.LENGTH_SHORT).show();
            for (BasketParentItem parentItem : Basket.basketList) {
                if (parentItem.getChildItemList().get(0).getSellerId().equals(myProduct.getSellerId())) {
                    parentItem.getChildItemList().add(myProduct);
                    return;
                }
            }
            BasketParentItem newParentItem = new BasketParentItem();
            newParentItem.addToList(myProduct);
            Basket.basketList.add(newParentItem);

        }

    }
}

