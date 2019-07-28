package com.sense.naoto.sense;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sense.naoto.sense.FashionSwipe.FashionFragmentPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager viewPager = findViewById(R.id.viewPager);
        FragmentPagerAdapter pagerAdapter = new FashionFragmentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
    }
}
