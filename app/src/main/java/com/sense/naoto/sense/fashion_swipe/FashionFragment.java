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
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;
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
        mView = inflater.inflate(R.layout.fragment_fashion, container, false);

        setViews();

        return mView;
    }

    public void setViews() {
        ImageView fashionImage = mView.findViewById(R.id.fashionImageView);
       // setFashionImage(fashionImage);
        Picasso.with(getContext())
                .load(Uri.parse(mFashion.getLocalDeviceUri()))
                .placeholder(R.drawable.fashion1)
                .error(R.drawable.fashion2)
                .into(fashionImage);

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

        TextView txvTitle = mView.findViewById(R.id.layout_description).findViewById(R.id.title_textView);
        txvTitle.setText(mFashion.getTitle());

        TextView txvDescription = mView.findViewById(R.id.layout_description).findViewById(R.id.ordinary_description_textView);
        txvDescription.setText(mFashion.getDescription());

        TextView txvMakerName = mView.findViewById(R.id.layout_description).findViewById(R.id.maker_textView);
        txvMakerName.setText(mFashion.getMakerName());

        ImageView imvIcon = mView.findViewById(R.id.layout_description).findViewById(R.id.user_image);
       /* Bitmap bmpIcon = ImageHelper.fromBase64ToBitmap(mFashion.getMakerImageCode());
        imvIcon.setImageBitmap(bmpIcon);
        imvIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo:他人のを見る時用
            }
        });

        */
        //todo:userをidから取得してiconの画像を写す

        //todo:hashtagの部分の処理をかく　
    }

    private void setFashionImage(ImageView imageView) {
        Uri uri = Uri.parse(mFashion.getLocalDeviceUri());

        try {
            InputStream in = getActivity().getContentResolver().openInputStream(uri);
            ExifInterface exifInterface = new ExifInterface(in);

            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
            Bitmap bmRotated = ImageHelper.rotateBitmap(bitmap, orientation);
            imageView.setImageBitmap(bmRotated);
        } catch (IOException e) {
            //todo:ここのエラー処理
        }

    }


}
