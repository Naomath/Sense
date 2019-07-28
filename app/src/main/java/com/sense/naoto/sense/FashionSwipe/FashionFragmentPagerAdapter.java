package com.sense.naoto.sense.FashionSwipe;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;

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

        Fashion fashion;

        switch (i) {
            case 0:
                fashion = new Fashion(R.drawable.fashion1,"量産型コーデ1","これさえきれば大丈夫!!");
                break;

            case 1:
                fashion = new Fashion(R.drawable.fashion2,"量産型コーデ2","お祭りにも使えるかも!?");
                break;

            case 2:
                fashion = new Fashion(R.drawable.fashion3,"量産型コーデ3","海もこれでOK!!");
                break;

            default:
                fashion = null;
                break;
        }

        Fragment fm = FashionFragment.newInstance(fashion);

        return fm;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }
}
