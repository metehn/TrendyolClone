package com.metehanersoy.trendyolclone.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.metehanersoy.trendyolclone.Activity.MainActivity;
import com.metehanersoy.trendyolclone.Class.Basket;
import com.metehanersoy.trendyolclone.Class.BasketParentItem;
import com.metehanersoy.trendyolclone.Class.MyProduct;
import com.metehanersoy.trendyolclone.R;

import java.text.DecimalFormat;


public class PaymentFragment extends Fragment {

    Activity mActivity;
    TextView tv_totalPrice_PaymentFragment;
    ImageView iv_paymentClose;
    RadioButton rb_1_PaymentDialog, rb_2_PaymentDialog, rb_3_PaymentDialog, rb_4_PaymentDialog;
    CheckBox cb_dialogPayment;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public PaymentFragment(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        /*
        paymentDialog = new Dialog(getContext(), androidx.appcompat.R.style.ThemeOverlay_AppCompat_ActionBar);
        paymentDialog.setContentView(view);
        Window window2 = paymentDialog.getWindow();
        window2.getAttributes().windowAnimations = R.style.DialogAnimation;
        */
        iv_paymentClose = view.findViewById(R.id.iv_paymentClose);
        rb_1_PaymentDialog = view.findViewById(R.id.rb_1_PaymentDialog);
        rb_2_PaymentDialog = view.findViewById(R.id.rb_2_PaymentDialog);
        rb_3_PaymentDialog = view.findViewById(R.id.rb_3_PaymentDialog);
        rb_4_PaymentDialog = view.findViewById(R.id.rb_4_PaymentDialog);
        cb_dialogPayment = view.findViewById(R.id.cb_dialogPayment);
        tv_totalPrice_PaymentFragment = view.findViewById(R.id.tv_totalPrice_PaymentFragment);

        updatePrice();

        rb_1_PaymentDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rb_1_PaymentDialog.isChecked()) {
                    rb_2_PaymentDialog.setChecked(false);
                } else {
                    rb_2_PaymentDialog.setChecked(true);
                }
            }
        });
        rb_2_PaymentDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rb_2_PaymentDialog.isChecked()) {
                    rb_1_PaymentDialog.setChecked(false);
                } else {
                    rb_1_PaymentDialog.setChecked(true);
                }

            }
        });
        rb_3_PaymentDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rb_3_PaymentDialog.isChecked()) {
                    rb_4_PaymentDialog.setChecked(false);
                    cb_dialogPayment.setChecked(false);
                } else {
                    rb_4_PaymentDialog.setChecked(true);
                }
            }
        });
        rb_4_PaymentDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rb_4_PaymentDialog.isChecked()) {
                    rb_3_PaymentDialog.setChecked(false);
                } else {
                    rb_3_PaymentDialog.setChecked(true);
                }
            }
        });

        cb_dialogPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (cb_dialogPayment.isChecked()) {
                    rb_4_PaymentDialog.setChecked(true);
                    rb_3_PaymentDialog.setChecked(false);
                }
            }
        });

        iv_paymentClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //close fragment
                ((MainActivity) mActivity).dismissSignInFragment();
            }
        });


        return view;
    }

    private void updatePrice() {
        double temp = 0.0;
        if (!Basket.basketList.isEmpty()) {

            for (BasketParentItem parent : Basket.basketList) {

                for (MyProduct product : parent.getChildItemList()) {

                    temp += product.getPrice() * product.getAmount();

                }
            }
            tv_totalPrice_PaymentFragment.setText(df.format(temp) + " TL");

        } else {
            tv_totalPrice_PaymentFragment.setText(df.format(temp) + " TL");

        }

    }

}