package com.sense.naoto.sense.FashionSwipe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FashionFragmentPagerAdapter extends FragmentPagerAdapter {

    /*
    これはFashionFragmentをスワイプする時に必要なPagerAdapter
     */


    public static int NUM_ITEMS = 3;

    public FashionFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {

        Fragment fm;

        switch (i) {
            case 0:
                fm = FashionFragment.newInstance("Fashion1");
                break;
            case 1:
                fm = FashionFragment.newInstance("Fashion2");
                break;
            case 2:
                fm = FashionFragment.newInstance("Fashion3");
                break;
            default:
                fm = null;
                break;
        }

        return fm;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
