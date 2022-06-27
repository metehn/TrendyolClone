package com.metehanersoy.trendyolclone.Fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metehanersoy.trendyolclone.Adapter.BasketRecyclerViewAdapter;
import com.metehanersoy.trendyolclone.Class.MyProduct;
import com.metehanersoy.trendyolclone.R;
import java.util.ArrayList;


public class BasketFragment extends Fragment {

    static final String LOG_TAG = "LogMetehan";
    static final String PRODUCTS = "Products";
    static final String BEST_SELLER = "BestSeller";

    DatabaseReference mDatabase;

    RecyclerView rv_FragmentBasket;
    AppCompatButton acb_FragmentBasket;
    NestedScrollView nestedScrollView_BasketFragment;

    BasketRecyclerViewAdapter adapter;
    ArrayList<MyProduct> list = new ArrayList<>();


    Context mContext;
    Activity mActivity;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
        mActivity = getActivity();

        mDatabase = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        nestedScrollView_BasketFragment = view.findViewById(R.id.nestedScrollView_BasketFragment);
        nestedScrollView_BasketFragment.setNestedScrollingEnabled(true);

        acb_FragmentBasket = view.findViewById(R.id.acb_FragmentBasket);

        rv_FragmentBasket = view.findViewById(R.id.rv_FragmentBasket);

        rv_FragmentBasket.setHasFixedSize(true);
        rv_FragmentBasket.setLayoutManager(new LinearLayoutManager(mContext));

        adapter = new BasketRecyclerViewAdapter(mContext, list);
        rv_FragmentBasket.setAdapter(adapter);


        //Recyclerview divider
        rv_FragmentBasket.addItemDecoration(new DividerItemDecoration(rv_FragmentBasket.getContext(), DividerItemDecoration.VERTICAL));

        //rv_FragmentBasket.setNestedScrollingEnabled(false);
        //rv_FragmentBasket.setHasFixedSize(false);

            mDatabase.child(PRODUCTS).child(BEST_SELLER).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {

                    Log.e(LOG_TAG, task.getResult().getValue().getClass().toString());

                    for (DataSnapshot ds : task.getResult().getChildren()){

                        Log.e(LOG_TAG, ds.toString());
                        MyProduct myProduct = ds.getValue(MyProduct.class);

                        list.add(myProduct);

                        adapter.notifyDataSetChanged();
                    }


                }
            });


        Toast.makeText(mContext, "OncreateView", Toast.LENGTH_SHORT).show();

        acb_FragmentBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "!", Toast.LENGTH_SHORT).show();

                mDatabase.child(PRODUCTS).child(BEST_SELLER).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        Log.e(LOG_TAG, task.getResult().getValue().getClass().toString());

                        for (DataSnapshot ds : task.getResult().getChildren()){

                            Log.e(LOG_TAG, ds.toString());
                            MyProduct myProduct = ds.getValue(MyProduct.class);

                            list.add(myProduct);
                            adapter.notifyDataSetChanged();
                        }


                    }
                });
            }
        });


        return view;
    }
}