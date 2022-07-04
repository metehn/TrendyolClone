package com.metehanersoy.trendyolclone.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metehanersoy.trendyolclone.Activity.MainActivity;
import com.metehanersoy.trendyolclone.Adapter.BasketRecyclerViewAdapterParent;
import com.metehanersoy.trendyolclone.Adapter.BestSellerRecyclerViewAdapter;
import com.metehanersoy.trendyolclone.Class.Basket;
import com.metehanersoy.trendyolclone.Class.MyProduct;
import com.metehanersoy.trendyolclone.R;


public class BasketFragment extends Fragment {


    DatabaseReference mDatabase;

    RecyclerView rv_bestSeller_FragmentBasket, rv_parent_FragmentBasket;
    AppCompatButton acb_FragmentBasket;
    NestedScrollView nestedScrollView_BasketFragment;
    LinearLayout ll_BasketFragment;
    BestSellerRecyclerViewAdapter adapter;
    BasketRecyclerViewAdapterParent parentAdapter;
    Activity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child(MainActivity.BEST_SELLER).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                for (DataSnapshot ds : task.getResult().getChildren()) {

                    Log.e(MainActivity.LOG_TAG, ds.toString());
                    String tmp = ds.getValue(String.class);
                    Basket.bestSellerStringList.add(tmp);

                    mDatabase.child(MainActivity.PRODUCTS).child(tmp).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {

                            if (task.isSuccessful()) {

                                MyProduct myProduct = task.getResult().getValue(MyProduct.class);

                                String sellerId = myProduct.getSellerId();
                                String productId = myProduct.getId();

                                mDatabase.child("Seller").child(sellerId).child("products").child(productId).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        Log.e("Log", dataSnapshot.toString());

                                        myProduct.setPrice((Double) dataSnapshot.child("price").getValue());
                                        myProduct.setStock(Integer.parseInt(dataSnapshot.child("stock").getValue().toString()));
                                        myProduct.setFastDelivery((Boolean) dataSnapshot.child("fastDelivery").getValue());

                                        if (myProduct.getStock() >= 1) {
                                            myProduct.setAmount(1);
                                        }
                                        Basket.bestSeller.add(myProduct);
                                       // adapter.notifyDataSetChanged();// bu satırı koymazsan ürünler yüklenmez !
                                        bestSellerNotifyDataSetChanged();
                                    }
                                });

                                mDatabase.child("Seller").child(sellerId).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                    @Override
                                    public void onSuccess(DataSnapshot dataSnapshot) {
                                        myProduct.setSellerName( (String) dataSnapshot.child("name").getValue() );
                                        myProduct.setSellerRate((Double) dataSnapshot.child("rate").getValue());

                                    }
                                });

                            }
                        }
                    });


                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        nestedScrollView_BasketFragment = view.findViewById(R.id.nestedScrollView_BasketFragment);
        nestedScrollView_BasketFragment.setNestedScrollingEnabled(true);
        ll_BasketFragment = view.findViewById(R.id.ll_BasketFragment);
        acb_FragmentBasket = view.findViewById(R.id.acb_FragmentBasket);
        rv_bestSeller_FragmentBasket = view.findViewById(R.id.rv_bestSeller_FragmentBasket);
        rv_parent_FragmentBasket = view.findViewById(R.id.rv_parent_FragmentBasket);

        //Adapters
        adapter = new BestSellerRecyclerViewAdapter(mActivity, Basket.bestSeller);
        parentAdapter = new BasketRecyclerViewAdapterParent(mActivity, Basket.basketList);

        rv_parent_FragmentBasket.setAdapter(parentAdapter);
        rv_parent_FragmentBasket.setHasFixedSize(true);
        rv_parent_FragmentBasket.setLayoutManager(new LinearLayoutManager(mActivity.getApplicationContext()));

        rv_bestSeller_FragmentBasket.setAdapter(adapter);
        rv_bestSeller_FragmentBasket.setHasFixedSize(true);
        rv_bestSeller_FragmentBasket.setLayoutManager(new LinearLayoutManager(mActivity.getApplicationContext()));

        //Recyclerview divider
        rv_bestSeller_FragmentBasket.addItemDecoration(new DividerItemDecoration(rv_bestSeller_FragmentBasket.getContext(), DividerItemDecoration.VERTICAL));
        //rv_parent_FragmentBasket.setNestedScrollingEnabled(false);
        //rv_parent_FragmentBasket.setHasFixedSize(false);

        if (Basket.basketList.isEmpty()) {
            ll_BasketFragment.setVisibility(View.VISIBLE);
            rv_parent_FragmentBasket.setVisibility(View.GONE);
        } else {
            ll_BasketFragment.setVisibility(View.INVISIBLE);
            rv_parent_FragmentBasket.setVisibility(View.VISIBLE);
        }

        acb_FragmentBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mActivity.getApplicationContext(), "!", Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
    public void bestSellerNotifyDataSetChanged(){
        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
    public void basketNotifyDataSetChanged(){
        if(!Basket.basketList.isEmpty()){
            ll_BasketFragment.setVisibility(View.GONE);
            rv_parent_FragmentBasket.setVisibility(View.VISIBLE);
        }else{
            ll_BasketFragment.setVisibility(View.VISIBLE);
            rv_parent_FragmentBasket.setVisibility(View.GONE);
        }
        if(parentAdapter != null){
            parentAdapter.notifyDataSetChanged();
        }
    }


}