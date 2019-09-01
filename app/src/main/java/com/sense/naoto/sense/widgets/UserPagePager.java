package com.sense.naoto.sense.widgets;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sense.naoto.sense.user_page.AllFashionsFragment;
import com.sense.naoto.sense.user_page.AllItemsFragment;

public class UserPagePager extends FragmentPagerAdapter {

    public UserPagePager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                AllFashionsFragment allFashionsFragment = new AllFashionsFragment();
                return allFashionsFragment;
            case 1:
                AllItemsFragment allItemsFragment = new AllItemsFragment();
                return allItemsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "COORDINATES";
            case 1:
                return "ITEMS";
            default:
                return null;
        }
    }

}
