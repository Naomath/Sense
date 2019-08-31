package com.sense.naoto.sense.save_fashion;

import android.content.Context;
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
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.processings.ButtonHelper;
import com.sense.naoto.sense.processings.ImageHelper;

import java.io.IOException;
import java.io.InputStream;

import lombok.Setter;

import static android.app.Activity.RESULT_OK;

public class SelectPhotoFragment extends Fragment {

    //定数
    public static int PICK_IMAGE_REQUEST = 1;

    //View
    private View mView;
    private ImageView mImageView;
    private ImageButton mBtnComplete;

    //Listener
    private OnSelectPhotoFragmentListener selectListener;
    private OnBackFragmentListener backListener;

    //変数
    private Bitmap pickedImage;
    private Uri pickedImageUri;
    private boolean hasBitmap = false;
    @Setter
    private int type = 0;

    public SelectPhotoFragment() {
        // Required empty public constructor
    }

    public static SelectPhotoFragment newInstance() {
        SelectPhotoFragment fragment = new SelectPhotoFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            try {
                Uri uriOfContent = data.getData();
                pickedImageUri = uriOfContent;

                InputStream in = getActivity().getContentResolver().openInputStream(uriOfContent);
                ExifInterface exifInterface = new ExifInterface(in);

                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                Bitmap bmRotated = ImageHelper.rotateBitmap(bitmap, orientation);

                int size = 0;

                switch (type) {
                    case SaveFashionAndItemActivity.TYPE_FASHION:
                        size = 1000;
                        break;
                    case SaveFashionAndItemActivity.TYPE_ITEM:
                        size = 300;
                        break;
                }

                Bitmap resizedBitmap = ImageHelper.resizeBitmap(size, bmRotated);

                pickedImage = resizedBitmap;

                mImageView.setImageBitmap(resizedBitmap);

                ButtonHelper.enableCheckButton(mBtnComplete, getContext());

                hasBitmap = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_select_photo, container, false);
        setViews();

        return mView;
    }

    public void setListeners(OnSelectPhotoFragmentListener selectListener,
                             OnBackFragmentListener backListener) {
        this.selectListener = selectListener;
        this.backListener = backListener;
    }

    private void setViews() {
        Button btnSelectPhoto = mView.findViewById(R.id.btn_select_photo);
        btnSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchGallery();
            }
        });


        mBtnComplete = mView.findViewById(R.id.btn_complete);
        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.onSelectPhoto(pickedImage, pickedImageUri);
            }
        });

        ImageButton btnBack = mView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backListener.onBack();
            }
        });

        mImageView = mView.findViewById(R.id.imageView);

        if (hasBitmap) {
            mImageView.setImageBitmap(pickedImage);
            ButtonHelper.enableCheckButton(mBtnComplete, getContext());
        } else {
            ButtonHelper.unEnableCheckButton(mBtnComplete, getContext());
        }
    }

    private void launchGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    public interface OnSelectPhotoFragmentListener {
        void onSelectPhoto(Bitmap image, Uri uri);
    }


}
