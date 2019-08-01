package com.sense.naoto.sense;

import android.support.annotation.NonNull;
import android.support.design.internal.BaselineLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.sense.naoto.sense.FashionSwipe.FashionSwipeFragment;
import com.sense.naoto.sense.ViewHelpers.BottomNavigationViewHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setViews();
    }

    private void setViews() {
        setDefaultFragment();
        setBottomNavigation();
    }

    private void setBottomNavigation() {

        final BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setItemIconTintList(null);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                navigation.getMenu().getItem(0).setIcon(R.drawable.outline_home_black_36);
                navigation.getMenu().getItem(1).setIcon(R.drawable.outline_add_circle_outline_black_36);
                navigation.getMenu().getItem(2).setIcon(R.drawable.outline_person_black_36);

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
                        //TODO:正しいfragmentを作る　
                        break;
                    case R.id.user:
                        menuItem.setIcon(R.drawable.baseline_person_black_36);
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
        navigation.setSelectedItemId(R.id.home);

        BottomNavigationViewHelper.adjustNavigation(navigation);
    }

    private void setDefaultFragment() {
        Fragment fm = new FashionSwipeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fm).commit();
    }
}
