package com.sense.naoto.sense.fashion_swipe;

import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.processings.GetImageFromDeviceTask;
import com.sense.naoto.sense.processings.ImageHelper;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.io.IOException;
import java.io.InputStream;

public class FashionFragment extends Fragment {
    /*
    このFragmentはファッションの画像とその他の情報を表示するFragment
     */

    //定数
    private static final String ARGS_KEY = "fashion";

    //変数
    private Fashion mFashion;

    private GetImageFromDeviceTask imagetask;

    //Views
    private View mView;

    private ImageView mFashionImage;

    private ProgressBar mProgressBar;


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
        mView = inflater.inflate(R.layout.fragment_fashion, container, false);

        setViews();

        return mView;
    }

    @Override
    public void onDestroy() {
        imagetask.setListener(null);
        super.onDestroy();
    }

    public void setViews() {
        mFashionImage = mView.findViewById(R.id.fashionImageView);
        imagetask = new GetImageFromDeviceTask();
        imagetask.setListener(createListener());
        imagetask.setActivity(getActivity());

        GetImageFromDeviceTask.Param param = new GetImageFromDeviceTask.Param(2000, Uri.parse(mFashion.getLocalDeviceUri()));

        imagetask.execute(param);


        SparkButton favButton = mView.findViewById(R.id.fav_button);
        favButton.setEventListener(new SparkEventListener() {
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
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
            }
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

        TextView txvDate = mView.findViewById(R.id.layout_description).findViewById(R.id.txv_date);
        txvDate.setText(mFashion.getStrDate());

        mProgressBar = mView.findViewById(R.id.progressBar);
    }


    private GetImageFromDeviceTask.GetImageFromDeviceListener createListener() {
        return new GetImageFromDeviceTask.GetImageFromDeviceListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                mProgressBar.setVisibility(View.GONE);
                mFashionImage.setImageBitmap(bitmap);
            }
        };
    }


}
