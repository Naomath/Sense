package com.sense.naoto.sense.FashionSwipe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sense.naoto.sense.R;

public class FashionSwipeFragment extends Fragment {

    //Views
    private View mView;

    public FashionSwipeFragment() {
        // Required empty public constructor
    }

    public static FashionSwipeFragment newInstance() {
        FashionSwipeFragment fragment = new FashionSwipeFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_fashion_swipe, container, false);

        setViews();
        return mView;
    }

    private void setViews(){

        ViewPager viewPager = mView.findViewById(R.id.viewPager);
        FragmentPagerAdapter pagerAdapter = new FashionFragmentPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

    }

}
