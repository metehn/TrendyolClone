package com.metehanersoy.trendyolclone.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.metehanersoy.trendyolclone.R;

public class AccountFragmentAuth extends Fragment {

    FirebaseAuth mAuth;
    Activity mActivity;

    TextView tv_AccountFragmentAuth_mail;

    LinearLayout ll_Main_AccountFragmentAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account_auth, container, false);

        ll_Main_AccountFragmentAuth = view.findViewById(R.id.ll_Main_AccountFragmentAuth);
        tv_AccountFragmentAuth_mail = view.findViewById(R.id.tv_AccountFragmentAuth_mail);
        tv_AccountFragmentAuth_mail.setText(mAuth.getCurrentUser().getEmail());










        return view;
    }

}