package com.sense.naoto.sense.save_fashion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.interfaces.SetUpFashionFmListener;
import com.sense.naoto.sense.processings.CalendarHelper;
import com.sense.naoto.sense.processings.FireBaseHelper;
import com.sense.naoto.sense.processings.ImageHelper;
import com.sense.naoto.sense.processings.UserPreferencesHelper;


import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class SetUpFashionFragment extends Fragment {

    public static int PICK_IMAGE_REQUEST = 1;

    private View mView;

    private SetUpFashionFmListener mListener;

    private ImageView mImageView;

    private Uri mUri = null;

    private ProgressBar mCircleProgress;

    private Button mBtnPost;


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
        /*
        if (getArguments() != null) {
            mBitmap = (Bitmap) getArguments().get(FragmentConstants.BITMAP_ARG_CODE);
            mPathName = getArguments().getString(FragmentConstants.FILE_PATH_CODE);
        }
        */
        if (getActivity() instanceof SetUpFashionFmListener)
            mListener = (SetUpFashionFmListener) getActivity();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            try {
                mUri = data.getData();

                InputStream in = getActivity().getContentResolver().openInputStream(mUri);
                ExifInterface exifInterface = new ExifInterface(in);

                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                Bitmap bmRotated = ImageHelper.rotateBitmap(bitmap, orientation);
                mImageView.setImageBitmap(bmRotated);

            } catch (IOException e) {
                //todo:ここのエラー処理
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_set_up_fashion, container, false);
        setViews();
        return mView;
    }

    private void setViews() {
        final ImageButton btnBack = mView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) mListener.onLaunchMainActivity();
            }
        });

        //  setImage();

        mCircleProgress = mView.findViewById(R.id.circle_progress);

        //todo:テキストボックスが埋まってないと押せない処理
        mBtnPost = mView.findViewById(R.id.btn_post);
        mBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTitle = (EditText) mView.findViewById(R.id.edit_title);
                EditText editDescription = (EditText) mView.findViewById(R.id.edit_description);

                String title = editTitle.getText().toString();
                String description = editDescription.getText().toString();

                mBtnPost.setVisibility(View.GONE);
                mCircleProgress.setVisibility(View.VISIBLE);

                saveFashion(title, description);
            }
        });

        mImageView = mView.findViewById(R.id.image_fashion);
        mImageView.setImageResource(R.drawable.no_fashion_selected);

        Button btnSelect = mView.findViewById(R.id.btn_select_photo);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

    }


    private void saveFashion(String title, String description) {
        String strDate = CalendarHelper.getNowDate();

        String prefeKey = Fashion.newPreferenceKey();

        Fashion fashion = new Fashion(title, description, strDate, mUri.toString(), prefeKey);
        UserPreferencesHelper.saveNewFashion(getContext(), fashion);

        mListener.onLaunchMainActivity();

    }
}
