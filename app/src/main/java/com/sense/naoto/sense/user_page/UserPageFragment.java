package com.sense.naoto.sense.user_page;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.widgets.UserPagePager;


public class UserPageFragment extends Fragment{

    private View mView;

    public UserPageFragment() {
        // Required empty public constructor
    }


    public static UserPageFragment newInstance() {
        UserPageFragment fragment = new UserPageFragment();
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

        mView = inflater.inflate(R.layout.fragment_user_page, container, false);
        setViews();
        return mView;
    }

    private void setViews() {
        ViewPager viewPager = mView.findViewById(R.id.viewPager);
        TabLayout tabLayout = mView.findViewById(R.id.tabLayout);

        UserPagePager pagePager = new UserPagePager(getChildFragmentManager());
        viewPager.setAdapter(pagePager);
        tabLayout.setupWithViewPager(viewPager);

    }

}
