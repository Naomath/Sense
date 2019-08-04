package com.sense.naoto.sense.post_fashion;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.constatnt.FragmentConstants;
import com.sense.naoto.sense.interfaces.SetUpFashionFmListener;

public class SetUpFashionFragment extends Fragment {

    private View mView;

    private SetUpFashionFmListener mListener;

    private Bitmap mBitmap;


    public SetUpFashionFragment() {}

    public static SetUpFashionFragment newInstance(String param1, String param2) {
        SetUpFashionFragment fragment = new SetUpFashionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBitmap = (Bitmap) getArguments().get(FragmentConstants.BITMAP_ARG_CODE);
        }
        if (getActivity() instanceof SetUpFashionFmListener) mListener = (SetUpFashionFmListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_set_up_fashion, container, false);
        setViews();
        return mView;
    }

    private void setViews(){
        ImageButton btnBack = mView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener!=null) mListener.onFinishSetUpFashion();
            }
        });

        ImageView imageView = mView.findViewById(R.id.image_fashion);
        imageView.setImageBitmap(mBitmap);
    }


}
