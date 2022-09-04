package com.metehanersoy.trendyolclone.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.metehanersoy.trendyolclone.Activity.MainActivity;
import com.metehanersoy.trendyolclone.R;


public class HomePageFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("DENEME", "Created " + MainActivity.HOME_PAGE_FRAGMENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }
}

/*

<LinearLayout
      android:id="@+id/linearLayout"
      android:layout_width="match_parent"
      android:layout_height="70dp"
      android:background="@color/base"
      android:gravity="center"
      android:orientation="horizontal"
      android:paddingHorizontal="10dp"
      app:layout_constraintEnd_toEndOf="parent"
      app:layout_constraintStart_toStartOf="parent"
      app:layout_constraintTop_toTopOf="parent">

      <EditText
          android:layout_width="0dp"
          android:layout_height="50dp"
          android:layout_margin="6dp"
          android:layout_weight="0.9"
          android:background="@drawable/custom_background_1"
          android:drawableStart="@drawable/ic_search"
          android:elevation="2dp"
          android:padding="6dp" />


      <ImageView
          android:layout_width="0dp"
          android:layout_height="26dp"
          android:layout_marginLeft="10dp"
          android:layout_weight="0.1"
          android:src="@drawable/ic_notifications" />

  </LinearLayout>

 */