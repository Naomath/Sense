package com.sense.naoto.sense.fashion_swipe;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.processings.ImageHelper;
import com.sense.naoto.sense.processings.SavedDataHelper;

import java.util.List;

public class FashionSwipeFragment extends Fragment{

    private final static String REQUEST_CODE = "REQUEST";

    public final static int REQUEST_MINE = 0;


    //Views
    private View mView;

    private int request;

    private ViewPager mViewPager;


    public FashionSwipeFragment() {
        //empty
    }

    public static FashionSwipeFragment newInstance(int request) {
        FashionSwipeFragment fragment = new FashionSwipeFragment();
        Bundle args = new Bundle();
        args.putInt(REQUEST_CODE, request);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.request = getArguments().getInt(REQUEST_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_fashion_swipe, container, false);
        setViews();
        return mView;
    }

    private void setViews() {

        List<Fashion> fashionList = SavedDataHelper.getMyFashionsOrderedByNew(getContext());

        mViewPager = mView.findViewById(R.id.viewPager);

        String makerName = "ナオト";
        Bitmap bmpIcon = ImageHelper.fromResourceIdToBitmap(getActivity(), R.drawable.default_icon);
        String iconCode = ImageHelper.fromBitmapToBase64(bmpIcon);
        FragmentPagerAdapter pagerAdapter;

        switch (request) {

            case REQUEST_MINE:

                fashionList = SavedDataHelper.getMyFashionsOrderedByNew(getContext());

                pagerAdapter = new FashionFragmentPagerAdapter(getChildFragmentManager(), fashionList);
                mViewPager.setAdapter(pagerAdapter);

                break;

        }

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
