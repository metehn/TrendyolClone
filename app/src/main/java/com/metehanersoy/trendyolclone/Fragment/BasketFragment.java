package com.metehanersoy.trendyolclone.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metehanersoy.trendyolclone.Activity.MainActivity;
import com.metehanersoy.trendyolclone.Adapter.BasketRecyclerViewAdapterParent;
import com.metehanersoy.trendyolclone.Adapter.BestSellerRecyclerViewAdapter;
import com.metehanersoy.trendyolclone.Class.Basket;
import com.metehanersoy.trendyolclone.Class.BasketParentItem;
import com.metehanersoy.trendyolclone.Class.MyProduct;
import com.metehanersoy.trendyolclone.R;

import java.text.DecimalFormat;


public class BasketFragment extends Fragment {

    private static final DecimalFormat df = new DecimalFormat("0.00");

    DatabaseReference mDatabase;
    FirebaseAuth mAuth;

    RecyclerView rv_bestSeller_FragmentBasket, rv_parent_FragmentBasket;
    AppCompatButton acb_FragmentBasket, acb_pay_FragmentBasket;
    NestedScrollView nestedScrollView_BasketFragment;
    LinearLayout ll_BasketFragment, ll_totalPrice_BasketFragment;
    BestSellerRecyclerViewAdapter adapter;
    BasketRecyclerViewAdapterParent parentAdapter;
    Activity mActivity;
    TextView tv_totalPrice_BasketFragment;

    Dialog paymentDialog,dialog;
    ProgressBar pb_Dialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        mAuth = FirebaseAuth.getInstance();
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
                                        myProduct.setSellerName((String) dataSnapshot.child("name").getValue());
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
        ll_totalPrice_BasketFragment = view.findViewById(R.id.ll_totalPrice_BasketFragment);
        tv_totalPrice_BasketFragment = view.findViewById(R.id.tv_totalPrice_BasketFragment);
        acb_pay_FragmentBasket = view.findViewById(R.id.acb_pay_FragmentBasket);

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

        acb_pay_FragmentBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                if(mAuth.getCurrentUser() != null){
                    paymentDialog.show();
                }else{
                    dialog.show();
                }
*/
                ((MainActivity)mActivity).showSignInFragment();


            }
        });

        return view;
    }

    public void bestSellerNotifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    public void basketNotifyDataSetChanged() {
        if (!Basket.basketList.isEmpty()) {
            ll_BasketFragment.setVisibility(View.GONE);
            rv_parent_FragmentBasket.setVisibility(View.VISIBLE);
        } else {
            ll_BasketFragment.setVisibility(View.VISIBLE);
            rv_parent_FragmentBasket.setVisibility(View.GONE);
        }
        if (parentAdapter != null) {
            parentAdapter.notifyDataSetChanged();
        }
    }

    public void updateTotalPrice() {
        if (Basket.basketList.isEmpty()) {

            ll_totalPrice_BasketFragment.setVisibility(View.INVISIBLE);

        } else {
            ll_totalPrice_BasketFragment.setVisibility(View.VISIBLE);
        }
        double totalPrice = 0.00;

        for (BasketParentItem b : Basket.basketList) {

            for (MyProduct p : b.getChildItemList()) {

                if (p.isChecked()) {
                    totalPrice += (p.getPrice() * p.getAmount());
                }

            }

        }

        tv_totalPrice_BasketFragment.setText(df.format(totalPrice)+ " TL"); //Decimal objesiyle stringe çevir

    }

}