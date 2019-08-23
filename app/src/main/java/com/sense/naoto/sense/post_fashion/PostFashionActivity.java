package com.sense.naoto.sense.post_fashion;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.PostFashionActivityHelper;
import com.sense.naoto.sense.constatnt.FragmentConstants;
import com.sense.naoto.sense.interfaces.SetUpFashionFmListener;
import com.sense.naoto.sense.interfaces.TakePictureFmListener;

public class PostFashionActivity extends AppCompatActivity implements TakePictureFmListener, SetUpFashionFmListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_fashion);

        setViews();
    }

    private void setViews(){
        Fragment fm = new TakePictureFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fm).commit();
    }

    @Override
    public void finish(){
        super.finish();
        PostFashionActivityHelper.launchMainActivity(PostFashionActivity.this);
    }


    @Override
    public void onMoveToSetUpFashion(Bitmap bitmap, String pathName) {
        Fragment fm = new SetUpFashionFragment();

        Bundle b = new Bundle();
        b.putParcelable(FragmentConstants.BITMAP_ARG_CODE, bitmap);
        b.putString(FragmentConstants.FILE_PATH_CODE, pathName);
        fm.setArguments(b);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fm).commit();
    }

    @Override
    public void onFinishTakePicture() {
        finish();
    }

    @Override
    public void onFinishSetUpFashion() {
        Fragment fm = new TakePictureFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fm).commit();
    }
}
