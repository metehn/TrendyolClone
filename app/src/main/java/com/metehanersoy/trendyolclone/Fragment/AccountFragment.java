package com.metehanersoy.trendyolclone.Fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.metehanersoy.trendyolclone.Activity.MainActivity;
import com.metehanersoy.trendyolclone.R;

public class AccountFragment extends Fragment {

    Dialog dialog;
    AppCompatButton acb_FragmentAccount_SignIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("DENEME", "Created " + MainActivity.ACCOUNT_FRAGMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        View viewDialog = inflater.inflate(R.layout.dialog_signin_signup, container, false);
        //dialog = new Dialog( getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog = new Dialog( getContext(), R.style.MyDialogStatusBar);
        dialog.setContentView(viewDialog);
        Window window = dialog.getWindow();
        window.getAttributes().windowAnimations = R.style.DialogAnimation;


        acb_FragmentAccount_SignIn = view.findViewById(R.id.acb_FragmentAccount_SignIn);
        acb_FragmentAccount_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

            }
        });

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                Toast.makeText(getActivity(), "Back pressed", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()


        return view;
    }
}