package com.sense.naoto.sense.fashion_swipe;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.processings.ImageHelper;
import com.sense.naoto.sense.showing_my_fashion.ShowingMyFashionActivity;

import java.util.ArrayList;
import java.util.List;

public class FashionSwipeFragment extends Fragment {

    private final static String REQUEST_CODE = "REQUEST";

    public final static int REQUEST_FOLLOWING = 0;

    public final static int REQUEST_MINE = 1;


    //Views
    private View mView;

    private int request;

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

        int currentNumber = 0;

        ViewPager viewPager = mView.findViewById(R.id.viewPager);

        List<Fashion> fashionList = new ArrayList<>();

        String makerName = "ナオト";
        Bitmap bmpIcon = ImageHelper.fromResourceIdToBitmap(getActivity(),R.drawable.default_icon);
        String iconCode = ImageHelper.fromBitmapToBase64(bmpIcon);

        switch (request) {
            case REQUEST_FOLLOWING:
                //TODO 検索したデータから取得する　

                fashionList.add(new Fashion(R.drawable.fashion1, "量産型コーデ1", "これさえきれば大丈夫!!",makerName, iconCode));
                fashionList.add(new Fashion(R.drawable.fashion2, "量産型コーデ2", "お祭りにも使えるかも!?",makerName, iconCode));
                fashionList.add(new Fashion(R.drawable.fashion3, "量産型コーデ3", "海もこれでOK!!",makerName, iconCode));

                break;
            case REQUEST_MINE:
                //TODO 保存したデータから取得する　
                String title = "a";
                String description = "b";

                for (int i = 1; i <= 10; i++) {
                    fashionList.add(new Fashion(R.drawable.fashion1, title, description,makerName, iconCode));
                    fashionList.add(new Fashion(R.drawable.fashion2, title, description,makerName, iconCode));
                }
                ShowingMyFashionActivity activity = (ShowingMyFashionActivity) getActivity();
                currentNumber = activity.startingNumber;
                break;

        }

        FragmentPagerAdapter pagerAdapter = new FashionFragmentPagerAdapter(getChildFragmentManager(), fashionList);
        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(currentNumber);

    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
