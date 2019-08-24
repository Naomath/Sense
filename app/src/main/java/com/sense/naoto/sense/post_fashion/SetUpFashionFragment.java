package com.sense.naoto.sense.post_fashion;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.PostFashionActivityHelper;
import com.sense.naoto.sense.interfaces.SetUpFashionFmListener;
import com.sense.naoto.sense.processings.FireBaseHelper;
import com.sense.naoto.sense.processings.ImageHelper;
import com.squareup.picasso.Picasso;



import java.io.IOException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class SetUpFashionFragment extends Fragment implements FireBaseHelper.OnFirebaseCompleteListener{

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

                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                Bitmap bmRotated = ImageHelper.rotateBitmap(bitmap, orientation);
                mImageView.setImageBitmap(bmRotated);

            }catch (IOException e){

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
                if (mListener != null) mListener.onFinishSetUpFashion();
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
        FireBaseHelper.OnFirebaseCompleteListener listener = (FireBaseHelper.OnFirebaseCompleteListener) this;

        FireBaseHelper.uploadFashion(title, description, mUri, getActivity(), listener);
    }

    @Override
    public void onFirebaseComplete() {
        PostFashionActivityHelper.launchMainActivityForUploadSuccess(getActivity());
    }

    @Override
    public void onFirebaseFailed(){
        mCircleProgress.setVisibility(View.GONE);

        mBtnPost.setVisibility(View.VISIBLE);
    }

/*
    private void setImage(){

        try {

            WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);

            Display disp = wm.getDefaultDisplay();
            float viewWidth = disp.getWidth();

            ImageView imageView = mView.findViewById(R.id.image_fashion);
            ExifInterface exifInterface = new ExifInterface(mPathName);
            // 向きを取得
            int orientation = Integer.parseInt(exifInterface.getAttribute(ExifInterface.TAG_ORIENTATION));


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
            /*

            lp.width = (int) (lp.width * 0.5);
            lp.height = (int) (lp.height * 0.5);

            Matrix bitmapMatrix= new Matrix();
            bitmapMatrix.postScale(0.5f, 0.5f);
            Bitmap resizeBitmap = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), bitmapMatrix, true);

            imageView.setLayoutParams(lp);
            imageView.setImageMatrix(mat);
            imageView.setScaleType(ImageView.ScaleType.MATRIX);
            imageView.setImageBitmap(mBitmap);
            imageView.invalidate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */


}
