package com.sense.naoto.sense.fashion_swipe;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

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

        SparkButton favButton = mView.findViewById(R.id.fav_button);
        favButton.setEventListener(new SparkEventListener(){
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (buttonState) {
                    // Button is active
                    //TODO:favのボタンの処理　
                } else {
                    // Button is inactive
                    //TODO:favのボタンの処理　
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {}

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {}
        });

        View bottomSheet = mView.findViewById(R.id.description_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        //TODO textVIewに適切なテキストを書き込む　
    }


}
