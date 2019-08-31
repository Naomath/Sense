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

    //定数
    public static final int REQUEST_MINE = 0;
    public static final int REQUEST_FASHION_TAGGED_ITEM = 1;


    //変数
    private int mRequest;
    private List<Fashion> fashionList;


    public static int NUM_ITEMS;

    public FashionFragmentPagerAdapter(FragmentManager fm, List<Fashion> list, int request) {
        super(fm);
        fashionList = list;
        NUM_ITEMS = list.size();
        mRequest = request;
    }

    @Override
    public Fragment getItem(int i) {

        Fashion fashion = fashionList.get(i);

        Fragment fm = FashionFragment.newInstance(fashion, mRequest);

        return fm;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
