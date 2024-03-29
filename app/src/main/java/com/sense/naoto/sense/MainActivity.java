package com.sense.naoto.sense;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.sense.naoto.sense.activity_helper.MainActivityHelper;
import com.sense.naoto.sense.constatnt.ActivityConstants;
import com.sense.naoto.sense.fashion_swipe.FashionSwipeFragment;
import com.sense.naoto.sense.view_helper.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    private final Activity mActivity = this;


    //Views
    private BottomNavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setViews();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // 注意：superメソッドは呼ぶようにする
        // Activity側のonActivityResultで呼ばないとFragmentのonActivityResultが呼ばれない
        super.onActivityResult(requestCode,resultCode,data);

        switch(requestCode){
            case(ActivityConstants.POST_FASHION_ADTIVITY_CODE):

                //homeにfocusがくるように
                mNavigation.setSelectedItemId(R.id.home);

                break;
            default:
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

                mNavigation.getMenu().getItem(0).setIcon(R.drawable.outline_home_black_36);
                mNavigation.getMenu().getItem(1).setIcon(R.drawable.outline_add_circle_outline_black_36);
                mNavigation.getMenu().getItem(2).setIcon(R.drawable.outline_person_black_36);

                Fragment fm = null;
                boolean isFragment = false;

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        menuItem.setIcon(R.drawable.round_home_black_36);
                        fm = new FashionSwipeFragment();
                        isFragment = true;
                        break;

                    case R.id.post:
                        menuItem.setIcon(R.drawable.outline_add_circle_black_36);
                        MainActivityHelper.launchPostFashionActivity(mActivity);
                        isFragment = false;
                        break;

                    case R.id.user:
                        menuItem.setIcon(R.drawable.baseline_person_black_36);
                        fm = new FashionSwipeFragment();
                        isFragment = true;
                        //TODO:正しいfragmentを作る　
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
        Fragment fm = new FashionSwipeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fm).commit();
    }

}







