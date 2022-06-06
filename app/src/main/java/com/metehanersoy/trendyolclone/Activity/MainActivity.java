package com.metehanersoy.trendyolclone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.metehanersoy.trendyolclone.Fragment.AccountFragment;
import com.metehanersoy.trendyolclone.Fragment.BasketFragment;
import com.metehanersoy.trendyolclone.Fragment.FavoritesFragment;
import com.metehanersoy.trendyolclone.Fragment.HomePageFragment;
import com.metehanersoy.trendyolclone.Fragment.TrendyolGoFragment;
import com.metehanersoy.trendyolclone.R;

public class MainActivity extends AppCompatActivity {

    public static final String ACCOUNT_FRAGMENT = "AccountFragment";
    public static final String BASKET_FRAGMENT = "BasketFragment";
    public static final String FAVORITES_FRAGMENT = "FavoritesFragment";
    public static final String TRENDYOL_GO_FRAGMENT = "TrendyolGoFragment";
    public static final String HOME_PAGE_FRAGMENT = "HomePageFragment";

    BottomNavigationView bottomNavigationView;
    FrameLayout fl_main;
    FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize ui objects
        initializeUiObjects();

        //Initialize fragment manager
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.ll_1, new HomePageFragment(), HOME_PAGE_FRAGMENT).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.navigation_home:
                        addFragment(HOME_PAGE_FRAGMENT);
                        return true;

                    case R.id.navigation_go:
                        addFragment(TRENDYOL_GO_FRAGMENT);
                        return true;

                    case R.id.navigation_favorite:
                        addFragment(FAVORITES_FRAGMENT);
                        return true;

                    case R.id.navigation_basket:
                        addFragment(BASKET_FRAGMENT);
                        return true;

                    case R.id.navigation_account:
                        addFragment(ACCOUNT_FRAGMENT);
                        return true;

                }
                return false;

            }
        });

    }//end of onCreate

    //Initialize ui objects
    public void initializeUiObjects() {
        bottomNavigationView = findViewById(R.id.ll_2);
        fl_main = findViewById(R.id.ll_1);
    }


    public void addFragment(String tag) {

        Fragment fragment = fragmentManager.findFragmentByTag(tag);


        //That means there is no fragment created yet, so create and add one
        if (fragment == null) {

            switch (tag) {
                case ACCOUNT_FRAGMENT:
                    fragmentManager.beginTransaction().add(R.id.ll_1, new AccountFragment(), ACCOUNT_FRAGMENT).commit();
                    return;

                case BASKET_FRAGMENT:
                    fragmentManager.beginTransaction().add(R.id.ll_1, new BasketFragment(), BASKET_FRAGMENT).commit();
                    return;

                case FAVORITES_FRAGMENT:
                    fragmentManager.beginTransaction().add(R.id.ll_1, new FavoritesFragment(), FAVORITES_FRAGMENT).commit();
                    return;

                case TRENDYOL_GO_FRAGMENT:
                    fragmentManager.beginTransaction().add(R.id.ll_1, new TrendyolGoFragment(), TRENDYOL_GO_FRAGMENT).commit();
                    return;

                case HOME_PAGE_FRAGMENT:
                    fragmentManager.beginTransaction().add(R.id.ll_1, new HomePageFragment(), HOME_PAGE_FRAGMENT).commit();
                    return;
            }
        }
        //Detach all
        detachAllRemaining(fragment);

        //Attach fragment
        if(!fragment.isVisible()){
            fragmentManager.beginTransaction().attach(fragment).commit();
        }

    }

    public void detachAllRemaining(Fragment fragment) {
        Fragment accountF = fragmentManager.findFragmentByTag(ACCOUNT_FRAGMENT);
        Fragment basketF = fragmentManager.findFragmentByTag(BASKET_FRAGMENT);
        Fragment favoritesF = fragmentManager.findFragmentByTag(FAVORITES_FRAGMENT);
        Fragment homeF = fragmentManager.findFragmentByTag(HOME_PAGE_FRAGMENT);
        Fragment goF = fragmentManager.findFragmentByTag(TRENDYOL_GO_FRAGMENT);

        if(accountF != null && accountF != fragment) {
            fragmentManager.beginTransaction().detach(accountF).commit();
        }
        if(basketF != null && basketF != fragment) {
            fragmentManager.beginTransaction().detach(basketF).commit();
        }
        if(favoritesF != null && favoritesF != fragment) {
            fragmentManager.beginTransaction().detach(favoritesF).commit();
        }
        if(homeF != null && homeF != fragment) {
            fragmentManager.beginTransaction().detach(homeF).commit();
        }
        if(goF != null && goF != fragment) {
            fragmentManager.beginTransaction().detach(goF).commit();
        }



    }

}