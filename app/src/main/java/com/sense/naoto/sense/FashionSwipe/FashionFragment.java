package com.sense.naoto.sense.FashionSwipe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sense.naoto.sense.R;

public class FashionFragment extends Fragment {
    /*
    このFragmentはファッションの画像とその他の情報を表示するFragment
     */

    private static final String TITLE_KEY = "title";

    private String mTitle;

    private View mView;


    public FashionFragment() {
        // Required empty public constructor
    }

    public static FashionFragment newInstance(String title) {
        FashionFragment fragment = new FashionFragment();
        Bundle args = new Bundle();
       args.putString(TITLE_KEY, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(TITLE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_fashion, container, false);

        setViews();

        return mView;
    }

    public void setViews(){
        TextView txvTitle= mView.findViewById(R.id.textView);
        txvTitle.setText(mTitle);
    }

}
