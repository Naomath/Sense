package com.sense.naoto.sense;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.sense.naoto.sense.activity_helper.MainActivityHelper;
import com.sense.naoto.sense.constatnt.ActivityConstants;
import com.sense.naoto.sense.fashion_swipe.FashionSwipeFragment;
import com.sense.naoto.sense.random_item.RandomItemFragment;
import com.sense.naoto.sense.user_page.UserPageFragment;
import com.sense.naoto.sense.view_helper.BottomNavigationViewHelper;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private final Activity mActivity = this;


    //Views
    private BottomNavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
        Intent intent = getIntent();
        if (intent!=null){
            doFromIntent(intent);
        }
    }

    private void doFromIntent(Intent intent){

        int request = intent.getIntExtra("request",0);
        switch (request) {

            case ActivityConstants.SHOWING_MY_FASHION_ACTIVITY_CODE:
                mNavigation.setSelectedItemId(R.id.user);
                break;

            case ActivityConstants.SAVE_SUCCESS:
                Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show();
                break;

        }

        int where = intent.getIntExtra("where",3);
        switch (where){
            case 0:
                mNavigation.setSelectedItemId(R.id.home);
                break;
            case 1:
                mNavigation.setSelectedItemId(R.id.user);
                break;
        }
    }


    private void setViews() {
        setDefaultFragment();
        setBottomNavigation();
    }

    private void setBottomNavigation() {

        mNavigation = findViewById(R.id.bottom_navigation);
        mNavigation.setItemIconTintList(null);
        mNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                mNavigation.getMenu().getItem(0).setIcon(R.drawable.outline_home_black_24);
                mNavigation.getMenu().getItem(1).setIcon(R.drawable.outline_add_circle_outline_black_24);
                mNavigation.getMenu().getItem(2).setIcon(R.drawable.outline_view_module_black_24);

                Fragment fm = null;
                boolean isFragment = false;

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        menuItem.setIcon(R.drawable.round_home_black_24);
                        fm = new RandomItemFragment();
                        isFragment = true;
                        break;

                    case R.id.post:
                        MainActivityHelper.launchPostFashionActivity(mActivity);
                        isFragment = false;
                        break;

                    case R.id.user:
                        menuItem.setIcon(R.drawable.baseline_view_module_black_24);
                        fm = UserPageFragment.newInstance();
                        isFragment = true;
                        break;

                }

                if (isFragment) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame, fm).commit();
                }
                return true;
            }
        });

        //ここでボタンを押した動作をしている
        mNavigation.setSelectedItemId(R.id.home);

        BottomNavigationViewHelper.adjustNavigation(mNavigation);
    }

    private void setDefaultFragment() {
        Fragment fm = new RandomItemFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fm).commit();
    }

}







