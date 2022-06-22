package com.metehanersoy.trendyolclone.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.metehanersoy.trendyolclone.Activity.MainActivity;
import com.metehanersoy.trendyolclone.R;

public class AccountFragment extends Fragment {

    Dialog dialog;
    AppCompatButton acb_FragmentAccount_SignIn;
    ProgressBar pb_Dialog;

    FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    Activity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("DENEME", "Created " + MainActivity.ACCOUNT_FRAGMENT);

        mActivity = getActivity();
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onResume() {
        super.onResume();
        dialog.show();

        if(mAuth.getCurrentUser() !=null){
            Toast.makeText(mActivity, mAuth.getUid()+"", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        acb_FragmentAccount_SignIn = view.findViewById(R.id.acb_FragmentAccount_SignIn);

        View viewDialog = inflater.inflate(R.layout.dialog_signin_signup, container, false);
        //dialog = new Dialog( getContext(), android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen);
        dialog = new Dialog(getContext(), R.style.MyDialogStatusBar);
        dialog.setContentView(viewDialog);
        Window window = dialog.getWindow();
        window.getAttributes().windowAnimations = R.style.DialogAnimation;

        AppCompatButton acb_Dialog_SignIn = dialog.findViewById(R.id.acb_Dialog_SignIn);
        TextInputLayout til_Dialog_Email = dialog.findViewById(R.id.til_Dialog_Email);
        TextInputLayout til_Dialog_Password = dialog.findViewById(R.id.til_Dialog_Password);
        TextView et_Dialog_ForgotPassword = dialog.findViewById(R.id.et_Dialog_ForgotPassword);
        pb_Dialog = dialog.findViewById(R.id.pb_Dialog);
        pb_Dialog.setVisibility(View.INVISIBLE);

        til_Dialog_Email.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    til_Dialog_Email.setErrorEnabled(false);
                    til_Dialog_Email.setError(null);
                } else {
                    InputMethodManager inm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

            }
        });
        til_Dialog_Password.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    til_Dialog_Password.setErrorEnabled(false);
                    til_Dialog_Password.setError(null);
                }
            }
        });


        acb_Dialog_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pb_Dialog.setVisibility(View.VISIBLE);
                acb_Dialog_SignIn.setVisibility(View.INVISIBLE);

                String email = til_Dialog_Email.getEditText().getText().toString();
                String password = til_Dialog_Password.getEditText().getText().toString();
                if (email.isEmpty()) {
                    til_Dialog_Email.setError("Lütfen geçerli bir e-posta girin");
                    til_Dialog_Email.setErrorIconDrawable(null);

                    pb_Dialog.setVisibility(View.INVISIBLE);
                    acb_Dialog_SignIn.setVisibility(View.VISIBLE);
                    return;
                }
                if (password.isEmpty()) {

                    til_Dialog_Password.setError("Şifreniz 6 - 16 karakter arasında olmalıdır.");
                    til_Dialog_Password.setErrorIconDrawable(null);

                    pb_Dialog.setVisibility(View.INVISIBLE);
                    acb_Dialog_SignIn.setVisibility(View.VISIBLE);
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            ((MainActivity) mActivity).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ((MainActivity) mActivity).addFragment(MainActivity.ACCOUNT_FRAGMENT_AUTH);
                                    ((MainActivity) mActivity).removeFragment(MainActivity.ACCOUNT_FRAGMENT);

                                    dialog.dismiss();
                                }
                            });


                        } else {
                            Snackbar.make(view, task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG).setAction("Action", null).show();

                        }
                        pb_Dialog.setVisibility(View.INVISIBLE);
                        acb_Dialog_SignIn.setVisibility(View.VISIBLE);
                    }
                });

            }

        });


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
                dialog.dismiss();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        // The callback can be enabled or disabled here or in handleOnBackPressed()


        return view;
    }
}