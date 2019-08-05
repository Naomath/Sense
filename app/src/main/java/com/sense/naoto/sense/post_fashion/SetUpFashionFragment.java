package com.sense.naoto.sense.post_fashion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.constatnt.FragmentConstants;
import com.sense.naoto.sense.interfaces.SetUpFashionFmListener;
import com.sense.naoto.sense.processings.ImageHelper;

import java.io.IOException;

public class SetUpFashionFragment extends Fragment {

    private View mView;

    private SetUpFashionFmListener mListener;

    private Bitmap mBitmap;

    private String mPathName;


    public SetUpFashionFragment() {
    }

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
            mPathName = getArguments().getString(FragmentConstants.FILE_PATH_CODE);
        }
        if (getActivity() instanceof SetUpFashionFmListener)
            mListener = (SetUpFashionFmListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_set_up_fashion, container, false);
        setViews();
        return mView;
    }

    private void setViews() {
        ImageButton btnBack = mView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) mListener.onFinishSetUpFashion();
            }
        });

        try {
            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

            Display disp = wm.getDefaultDisplay();
            float viewWidth = disp.getWidth();

            ImageView imageView = mView.findViewById(R.id.image_fashion);
            ExifInterface exifInterface = new ExifInterface(mPathName);
            // 向きを取得
            int orientation = Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));

            imageView.setScaleType(ImageView.ScaleType.MATRIX);
            imageView.setImageBitmap(mBitmap);

            // 画像の幅、高さを取得
            int wOrg = mBitmap.getWidth();
            int hOrg = mBitmap.getHeight();
            imageView.getLayoutParams();
            ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) imageView.getLayoutParams();

            float factor;
            Matrix mat = new Matrix();
            mat.reset();
            switch (orientation) {
                case 1://only scaling
                    factor = (float) viewWidth / (float) wOrg;
                    mat.preScale(factor, factor);
                    lp.width = (int) (wOrg * factor);
                    lp.height = (int) (hOrg * factor);
                    break;
                case 2://flip vertical
                    factor = (float) viewWidth / (float) wOrg;
                    mat.postScale(factor, -factor);
                    mat.postTranslate(0, hOrg * factor);
                    lp.width = (int) (wOrg * factor);
                    lp.height = (int) (hOrg * factor);
                    break;
                case 3://rotate 180
                    mat.postRotate(180, wOrg / 2f, hOrg / 2f);
                    factor = (float) viewWidth / (float) wOrg;
                    mat.postScale(factor, factor);
                    lp.width = (int) (wOrg * factor);
                    lp.height = (int) (hOrg * factor);
                    break;
                case 4://flip horizontal
                    factor = (float) viewWidth / (float) wOrg;
                    mat.postScale(-factor, factor);
                    mat.postTranslate(wOrg * factor, 0);
                    lp.width = (int) (wOrg * factor);
                    lp.height = (int) (hOrg * factor);
                    break;
                case 5://flip vertical rotate270
                    mat.postRotate(270, 0, 0);
                    factor = (float) viewWidth / (float) hOrg;
                    mat.postScale(factor, -factor);
                    lp.width = (int) (hOrg * factor);
                    lp.height = (int) (wOrg * factor);
                    break;
                case 6://rotate 90
                    mat.postRotate(90, 0, 0);
                    factor = (float) viewWidth / (float) hOrg;
                    mat.postScale(factor, factor);
                    mat.postTranslate(hOrg * factor, 0);
                    lp.width = (int) (hOrg * factor);
                    lp.height = (int) (wOrg * factor);
                    break;
                case 7://flip vertical, rotate 90
                    mat.postRotate(90, 0, 0);
                    factor = (float) viewWidth / (float) hOrg;
                    mat.postScale(factor, -factor);
                    mat.postTranslate(hOrg * factor, wOrg * factor);
                    lp.width = (int) (hOrg * factor);
                    lp.height = (int) (wOrg * factor);
                    break;
                case 8://rotate 270
                    mat.postRotate(270, 0, 0);
                    factor = (float) viewWidth / (float) hOrg;
                    mat.postScale(factor, factor);
                    mat.postTranslate(0, wOrg * factor);
                    lp.width = (int) (hOrg * factor);
                    lp.height = (int) (wOrg * factor);
                    break;
            }
            imageView.setLayoutParams(lp);
            imageView.setImageMatrix(mat);
            imageView.invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
