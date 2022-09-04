package com.metehanersoy.trendyolclone.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.metehanersoy.trendyolclone.Activity.MainActivity;
import com.metehanersoy.trendyolclone.Class.Basket;
import com.metehanersoy.trendyolclone.Class.BasketParentItem;
import com.metehanersoy.trendyolclone.Class.MyProduct;
import com.metehanersoy.trendyolclone.Fragment.BasketFragment;
import com.metehanersoy.trendyolclone.R;

import java.text.DecimalFormat;
import java.util.ArrayList;

import lombok.AllArgsConstructor;

public class BasketRecyclerViewAdapterParent extends RecyclerView.Adapter<BasketRecyclerViewAdapterParent.MyViewHolder> {

    Activity mActivity;
    ArrayList<BasketParentItem> list;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    BasketRecyclerViewAdapterChild childItemAdapter;

    private static final DecimalFormat df = new DecimalFormat("0.0");

    public BasketRecyclerViewAdapterParent(Activity mActivity, ArrayList<BasketParentItem> list) {
        this.mActivity = mActivity;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_basket_parent_row, parent, false);

        return new BasketRecyclerViewAdapterParent.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // Create an instance of the ParentItem
        // class for the given position
        BasketParentItem basketParentItem = list.get(position);

        holder.tv_Basket_Parent.setText(basketParentItem.getChildItemList().get(0).getSellerName());
        holder.tv_Basket_Parent_Rate.setText( df.format( basketParentItem.getChildItemList().get(0).getSellerRate() ) + "" );

        int pL = holder.tv_Basket_Parent_Rate.getPaddingLeft();
        int pT = holder.tv_Basket_Parent_Rate.getPaddingTop();
        int pR = holder.tv_Basket_Parent_Rate.getPaddingRight();
        int pB = holder.tv_Basket_Parent_Rate.getPaddingBottom();



        if( basketParentItem.getChildItemList().get(0).getSellerRate() >=8.5 ){

            //holder.tv_Basket_Parent_Rate.setBackground(ContextCompat.getDrawable(mActivity, R.drawable.custom_background_1));
            holder.tv_Basket_Parent_Rate.setBackgroundResource(R.drawable.custom_background_9);
        }else if(basketParentItem.getChildItemList().get(0).getSellerRate() >=5){

            holder.tv_Basket_Parent_Rate.setBackgroundResource(R.drawable.custom_background_10);
        }else{

            holder.tv_Basket_Parent_Rate.setBackgroundResource(R.drawable.custom_background_11);
        }

        holder.tv_Basket_Parent_Rate.setPadding(pL, pT, pR, pB);


        // For the created instance,
        // get the title and set it
        // as the text for the TextView

        // Create a layout manager
        // to assign a layout
        // to the RecyclerView.
        // Here we have assigned the layout
        // as LinearLayout with vertical orientation
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.rv_child_Basket_Parent.getContext(), LinearLayoutManager.VERTICAL, false);

        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager.setInitialPrefetchItemCount(basketParentItem.getChildItemList().size());

        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
        childItemAdapter = new BasketRecyclerViewAdapterChild(mActivity, basketParentItem.getChildItemList());

        holder.rv_child_Basket_Parent.setLayoutManager(layoutManager);
        holder.rv_child_Basket_Parent.setAdapter(childItemAdapter);
        holder.rv_child_Basket_Parent.setRecycledViewPool(viewPool);

       // holder.rv_child_Basket_Parent.addItemDecoration(new DividerItemDecoration(mActivity.getApplicationContext(), DividerItemDecoration.VERTICAL));

        if (list.get(position).isChecked()) {
            holder.cb_Basket_CardView.setChecked(true);
        } else {
            holder.cb_Basket_CardView.setChecked(false);
        }


        holder.cb_Basket_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                BasketParentItem b = list.get(position);
                ArrayList<MyProduct> tempList = b.getChildItemList();

                if (b.isChecked()) {
                    holder.cb_Basket_CardView.setChecked(false);
                    list.get(position).setChecked(false);
                    //check all childs and notifydatachanges for both parent adapter and child adapter
                    for (MyProduct p : tempList) {
                        p.setChecked(false);
                    }


                } else {
                    holder.cb_Basket_CardView.setChecked(true);
                    list.get(position).setChecked(true);
                    //uncheck all childs and notifydatachanges for both parent adapter and child adapter
                    for (MyProduct p : tempList) {
                        p.setChecked(true);
                    }

                }
                ((BasketFragment)((MainActivity)mActivity).findFragment(MainActivity.BASKET_FRAGMENT)).updateTotalPrice();
                childItemAdapter.notifyDataSetChanged();
                notifyDataSetChanged();


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_Basket_Parent, tv_Basket_Parent_Rate;
        RecyclerView rv_child_Basket_Parent;
        CheckBox cb_Basket_CardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_Basket_Parent = itemView.findViewById(R.id.tv_Basket_Parent);
            tv_Basket_Parent_Rate = itemView.findViewById(R.id.tv_Basket_Parent_Rate);
            rv_child_Basket_Parent = itemView.findViewById(R.id.rv_child_Basket_Parent);
            cb_Basket_CardView = itemView.findViewById(R.id.cb_Basket_CardView);

        }
    }

}
