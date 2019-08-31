package com.sense.naoto.sense.fashion_swipe;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.item_tag_fashions.OnItemTagFashionListener;
import com.sense.naoto.sense.processings.ImageHelper;
import com.sense.naoto.sense.processings.SavedDataHelper;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

public class FashionSwipeFragment extends Fragment{

    //定数
    private final static String REQUEST_CODE = "REQUEST";
    public final static int REQUEST_MINE = 0;
    public final static int REQUEST_TAGGED_ITEM = 1;


    //Views
    private View mView;
    private ViewPager mViewPager;

    //Listeners
    @Setter
    private OnItemTagFashionListener itemTagFashionListener;

    //変数
    private int mRequest;
    @Setter
    private List<String> fashionPrefKeys = new ArrayList<>();
    @Setter
    private int startingNumber;


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
            this.mRequest = getArguments().getInt(REQUEST_CODE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        switch (mRequest){
            case REQUEST_MINE:
                mView = inflater.inflate(R.layout.fragment_fashion_swipe, container, false);
                break;

            case REQUEST_TAGGED_ITEM:
                mView = inflater.inflate(R.layout.fragment_fashion_swipe_tagged_item, container, false);
                break;
        }

        setViews();
        return mView;
    }

    private void setViews() {

        List<Fashion> fashionList = new ArrayList<>();

        mViewPager = mView.findViewById(R.id.viewPager);

        FragmentPagerAdapter pagerAdapter;

        switch (mRequest) {

            case REQUEST_MINE:

                fashionList = SavedDataHelper.getMyFashionsOrderedByNew(getContext());

                pagerAdapter = new FashionFragmentPagerAdapter(getChildFragmentManager(), fashionList, REQUEST_MINE);
                mViewPager.setAdapter(pagerAdapter);

                break;

            case REQUEST_TAGGED_ITEM:
                ImageButton btnBack = mView.findViewById(R.id.btn_back);
                btnBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemTagFashionListener.onBackFragment();
                    }
                });
                fashionList = SavedDataHelper.getFashionsByPrefKeyList(getContext(), fashionPrefKeys);

                pagerAdapter = new FashionFragmentPagerAdapter(getChildFragmentManager(),fashionList, REQUEST_TAGGED_ITEM);
                mViewPager.setAdapter(pagerAdapter);

                break;

        }

    }

}
