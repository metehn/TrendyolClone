package com.metehanersoy.trendyolclone.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.metehanersoy.trendyolclone.Adapter.BasketRecyclerViewAdapter;
import com.metehanersoy.trendyolclone.Class.MyProduct;
import com.metehanersoy.trendyolclone.R;
import java.util.ArrayList;


public class BasketFragment extends Fragment {

    static final String LOG_TAG = "LogMetehan";
    RecyclerView rv_FragmentBasket;



    BasketRecyclerViewAdapter adapter;
    ArrayList<MyProduct> list = new ArrayList<>();

    Context mContext;
    Activity mActivity;

    AppCompatButton acb_FragmentBasket;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getContext();
        mActivity = getActivity();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        acb_FragmentBasket = view.findViewById(R.id.acb_FragmentBasket);

        rv_FragmentBasket = view.findViewById(R.id.rv_FragmentBasket);

        rv_FragmentBasket.setHasFixedSize(true);
        rv_FragmentBasket.setLayoutManager(new LinearLayoutManager(mContext));

        adapter = new BasketRecyclerViewAdapter(mContext, list);
        rv_FragmentBasket.setAdapter(adapter);

        Toast.makeText(mContext, "OncreateView", Toast.LENGTH_SHORT).show();

        acb_FragmentBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "!", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}