package com.sense.naoto.sense.save_fashion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.SaveFashionActivityHelper;
import com.sense.naoto.sense.constatnt.FragmentConstants;
import com.sense.naoto.sense.interfaces.SetUpFashionFmListener;
import com.sense.naoto.sense.interfaces.TakePictureFmListener;
import com.sense.naoto.sense.processings.ButtonHelper;
import com.sense.naoto.sense.processings.ImageHelper;

import java.io.IOException;
import java.io.InputStream;

public class SaveFashionActivity extends AppCompatActivity implements TakePictureFmListener, SetUpFashionFmListener {


    private boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_fashion);

        setViews();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int i = 0;
    }

    private void setViews() {

        Fragment fm = new SetUpFashionFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fm).commit();

    }

    @Override
    public void finish() {
        if (isSuccess){
            SaveFashionActivityHelper.launchMainActivityForSaveSucucess(this);
        }else {
            SaveFashionActivityHelper.launchMainActivity(SaveFashionActivity.this);

        }
        super.finish();
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
    public void onLaunchMainActivity() {
        isSuccess = true;
        finish();
    }
}
