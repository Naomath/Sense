package com.sense.naoto.sense.fashion_swipe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sense.naoto.sense.classes.Fashion;

import java.util.List;

public class FashionFragmentPagerAdapter extends FragmentPagerAdapter {

    /*
    これはFashionFragmentをスワイプする時に必要なPagerAdapter
     */

    List<Fashion> fashionList;


    public static int NUM_ITEMS;

    public FashionFragmentPagerAdapter(FragmentManager fm, List<Fashion> list) {
        super(fm);
        fashionList = list;
        NUM_ITEMS = list.size();
    }

    @Override
    public Fragment getItem(int i) {

        Fashion fashion = fashionList.get(i);

        Fragment fm = FashionFragment.newInstance(fashion);

        return fm;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
