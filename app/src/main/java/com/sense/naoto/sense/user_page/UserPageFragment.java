package com.sense.naoto.sense.user_page;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.MainActivityHelper;
import com.sense.naoto.sense.widgets.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.List;


public class UserPageFragment extends Fragment implements AdapterView.OnItemClickListener {

    private View mView;

    private LayoutInflater mInflater;

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
        mInflater = inflater;

        mView = inflater.inflate(R.layout.fragment_user_page, container, false);
        setViews();
        return mView;
    }

    private void setViews() {
        final Button btn_change_profile = mView.findViewById(R.id.btn_change_profile);
        btn_change_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO プロフィールを編集する　
            }
        });

        //TODO:これらの情報を取得して入れる

        final TextView txv_post_number = mView.findViewById(R.id.txv_post_number);
        final TextView txv_folloing_number = mView.findViewById(R.id.txv_following_number);
        final TextView txv_follower_number = mView.findViewById(R.id.txv_follower_number);


        ExpandableHeightGridView gridView = mView.findViewById(R.id.grid_view);

        List<Integer> imgList = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            imgList.add(R.drawable.fashion1);
            imgList.add(R.drawable.fashion2);
        }
        GridAdapter adapter = new GridAdapter(mInflater, R.layout.grid_items, imgList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MainActivityHelper.launchShowingMyFashionActivity(getActivity(),i);
    }
}
