package com.metehanersoy.trendyolclone.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metehanersoy.trendyolclone.Activity.MainActivity;
import com.metehanersoy.trendyolclone.Class.Basket;
import com.metehanersoy.trendyolclone.Class.BasketParentItem;
import com.metehanersoy.trendyolclone.Class.MyProduct;
import com.metehanersoy.trendyolclone.Fragment.BasketFragment;
import com.metehanersoy.trendyolclone.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BasketRecyclerViewAdapterChild extends RecyclerView.Adapter<BasketRecyclerViewAdapterChild.MyViewHolder> {

    @NonNull
    Activity mActivity;
    @NonNull
    ArrayList<MyProduct> list;

    private static final DecimalFormat df = new DecimalFormat("0.00");
    DatabaseReference mDatabase;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_basket_child_row, parent, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        return new BasketRecyclerViewAdapterChild.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        MyProduct product = list.get(position);

        if( product.isChecked() ){
            holder.cb_Basket_CardView_Child.setChecked(true);
        }else{
            holder.cb_Basket_CardView_Child.setChecked(false);
        }

        try {
            Picasso.get().load(product.getImageURL()).into(holder.iv_Basket_CardView_Child);
            String nameAndDescription = "<b><font color='black'>" + list.get(position).getName() + "</font></b> " + list.get(position).getDescription();
            holder.tv_productName_Basket_CardView_Child.setText(Html.fromHtml(nameAndDescription));

            holder.tv_Price_Basket_CardView_Child.setText(product.getPrice() + " TL");
            if (product.getFastDelivery()) {
                Spannable str = new SpannableString(mActivity.getString(R.string.fast_delivery));
                str.setSpan(new ForegroundColorSpan(mActivity.getResources().getColor(R.color.green_weird)), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.tv_FastDelivery_Basket_CardView_Child.setText(TextUtils.concat(str, ": ", Html.fromHtml("<font color='black'>1 gün içinde kargoda</font>")));
            }

        } catch (Error e) {

            Toast.makeText(mActivity, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        holder.iv_deleteProduct_Basket_CardView_Child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity, position + "", Toast.LENGTH_SHORT).show();

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        removeItem(list.get(position));


                    }
                });

            }
        });

        holder.iv_Increase_CardView_Child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.pb_CardView_Child.setVisibility(View.VISIBLE);
                holder.tv_ProductAmount_Basket_CardView_Child.setVisibility(View.INVISIBLE);

                mDatabase.child("Seller").child(list.get(position).getSellerId()).child("products").child(list.get(position).getId()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {

                        int stock = Long.valueOf((Long) dataSnapshot.child("stock").getValue()).intValue();

                        if (list.get(position).getAmount() < stock) {

                            list.get(position).setAmount(list.get(position).getAmount() + 1);

                            if (list.get(position).getAmount() == stock) {
                                holder.iv_Increase_CardView_Child.setClickable(false);
                                holder.iv_Increase_CardView_Child.setImageResource(R.drawable.ic_increase_disabled);
                            }

                            holder.tv_ProductAmount_Basket_CardView_Child.setText(list.get(position).getAmount() + "");
                            holder.tv_Price_Basket_CardView_Child.setText(df.format(list.get(position).getAmount() * list.get(position).getPrice()) + " TL");
                        } else {
                            holder.iv_Increase_CardView_Child.setClickable(false);
                            holder.iv_Increase_CardView_Child.setImageResource(R.drawable.ic_increase_disabled);
                        }

                        holder.iv_Decrease_CardView_Child.setClickable(true);
                        holder.iv_Decrease_CardView_Child.setImageResource(R.drawable.ic_decrease);

                        holder.pb_CardView_Child.setVisibility(View.INVISIBLE);
                        holder.tv_ProductAmount_Basket_CardView_Child.setVisibility(View.VISIBLE);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        holder.tv_ProductAmount_Basket_CardView_Child.setText(list.get(position).getAmount() + "");
                        holder.tv_Price_Basket_CardView_Child.setText(df.format(list.get(position).getAmount() * list.get(position).getPrice()) + " TL");

                        holder.pb_CardView_Child.setVisibility(View.INVISIBLE);
                        holder.tv_ProductAmount_Basket_CardView_Child.setVisibility(View.VISIBLE);
                    }
                });
            }
        });

        holder.iv_Decrease_CardView_Child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.iv_Increase_CardView_Child.setClickable(true);
                holder.iv_Increase_CardView_Child.setImageResource(R.drawable.ic_increase);

                if (list.get(position).getAmount() > 1) {
                    list.get(position).setAmount(list.get(position).getAmount() - 1);

                    if (list.get(position).getAmount() == 1) {
                        holder.iv_Decrease_CardView_Child.setClickable(false);
                        holder.iv_Decrease_CardView_Child.setImageResource(R.drawable.ic_decrease_disabled);
                    }
                    holder.tv_ProductAmount_Basket_CardView_Child.setText(list.get(position).getAmount() + "");
                    holder.tv_Price_Basket_CardView_Child.setText(df.format(list.get(position).getAmount() * list.get(position).getPrice()) + " TL");

                } else {
                    holder.iv_Decrease_CardView_Child.setClickable(false);
                    holder.iv_Decrease_CardView_Child.setImageResource(R.drawable.ic_decrease_disabled);
                }
            }
        });

        holder.cb_Basket_CardView_Child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(list.get(position).isChecked()){

                    holder.cb_Basket_CardView_Child.setChecked(false);
                    list.get(position).setChecked(false);

                }else{
                    holder.cb_Basket_CardView_Child.setChecked(true);
                    list.get(position).setChecked(true);
                }


                boolean allCheck = false;

                for (BasketParentItem b : Basket.basketList) {
                    ArrayList<MyProduct> tempList = b.getChildItemList();

                    if (tempList.contains(list.get(position)) ) {

                        for (MyProduct mP : tempList) {

                            if(mP.isChecked()){
                                allCheck = true;
                            }
                        }

                        if(!allCheck){
                            //set parent uncheck
                            b.setChecked(false);
                        }

                    }

                }
                notifyDataSetChanged();
                notifyDataChangeSetChildAndParent();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        CheckBox cb_Basket_CardView_Child;
        ImageView iv_Basket_CardView_Child, iv_Decrease_CardView_Child, iv_Increase_CardView_Child, iv_deleteProduct_Basket_CardView_Child;
        TextView tv_productName_Basket_CardView_Child, tv_FastDelivery_Basket_CardView_Child,
                tv_ProductAmount_Basket_CardView_Child, tv_Price_Basket_CardView_Child;
        ProgressBar pb_CardView_Child;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cb_Basket_CardView_Child = itemView.findViewById(R.id.cb_Basket_CardView_Child);
            iv_Basket_CardView_Child = itemView.findViewById(R.id.iv_Basket_CardView_Child);
            iv_Decrease_CardView_Child = itemView.findViewById(R.id.iv_Decrease_CardView_Child);
            iv_Increase_CardView_Child = itemView.findViewById(R.id.iv_Increase_CardView_Child);
            iv_deleteProduct_Basket_CardView_Child = itemView.findViewById(R.id.iv_deleteProduct_Basket_CardView_Child);
            tv_productName_Basket_CardView_Child = itemView.findViewById(R.id.tv_productName_Basket_CardView_Child);
            tv_FastDelivery_Basket_CardView_Child = itemView.findViewById(R.id.tv_FastDelivery_Basket_CardView_Child);
            tv_ProductAmount_Basket_CardView_Child = itemView.findViewById(R.id.tv_ProductAmount_Basket_CardView_Child);
            tv_Price_Basket_CardView_Child = itemView.findViewById(R.id.tv_Price_Basket_CardView_Child);
            pb_CardView_Child = itemView.findViewById(R.id.pb_CardView_Child);

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void removeItem(MyProduct myProduct) {

        for (BasketParentItem b : Basket.basketList) {

            boolean temp = b.getChildItemList().remove(myProduct);
            if (b.getChildItemList().isEmpty()) {
                //remove basketparent item from basket parent
                Basket.basketList.remove(b);
            }
            Basket.bestSeller.add(myProduct);
            notifyDataChangeSetChildAndParent();
            //notifyDataSetChanged(); basketFragmenttan basketNotifyDataSetChanged() çağırdığımız için tekrardan çağırmamıza gerek yok.
            if (temp) {
                return;
            }
        }

    }

    public void checkUncheck(MyProduct myProduct) {

        boolean allCheck = false;

        for (BasketParentItem b : Basket.basketList) {
            ArrayList<MyProduct> tempList = b.getChildItemList();

            if (tempList.contains(myProduct)) {

                for (MyProduct mP : tempList) {

                    if(mP.isChecked()){
                        allCheck = true;
                    }
                }

                if(!allCheck){
                    //set parent uncheck
                    b.setChecked(false);
                }

            }

        }

        notifyDataChangeSetChildAndParent();
    }

   private void notifyDataChangeSetChildAndParent(){
       BasketFragment basketFragment = (BasketFragment) ((MainActivity) mActivity).findFragment(MainActivity.BASKET_FRAGMENT);
       basketFragment.basketNotifyDataSetChanged();
       basketFragment.bestSellerNotifyDataSetChanged();
    }


}
