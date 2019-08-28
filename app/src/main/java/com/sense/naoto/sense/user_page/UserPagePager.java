package com.sense.naoto.sense.user_page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

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
                return "ファッション";
            case 1:
                return "アイテム";
            default:
                return null;
        }
    }

}
