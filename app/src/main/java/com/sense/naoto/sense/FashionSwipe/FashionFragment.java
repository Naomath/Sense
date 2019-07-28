package com.sense.naoto.sense.FashionSwipe;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;

public class FashionFragment extends Fragment {
    /*
    このFragmentはファッションの画像とその他の情報を表示するFragment
     */

    //定数
    private static final String ARGS_KEY = "fashion";

    //変数
    private Fashion mFashion;

    //Views
    private View mView;



    public FashionFragment() {
        // Required empty public constructor
    }

    public static FashionFragment newInstance(Fashion fashion) {
        FashionFragment fragment = new FashionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS_KEY, fashion);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           mFashion = getArguments().getParcelable(ARGS_KEY);
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
        ImageView fashionImage = mView.findViewById(R.id.fashionImageView);
        fashionImage.setImageResource(mFashion.getImageCode());
    }

}
