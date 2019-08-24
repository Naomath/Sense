package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;

import com.sense.naoto.sense.MainActivity;
import com.sense.naoto.sense.constatnt.ActivityConstants;

public class PostFashionActivityHelper {

    public static void launchMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivityForResult(intent,ActivityConstants.POST_FASHION_ACTIVITY_CODE);
    }

    public static void launchMainActivityForUploadSuccess(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivityForResult(intent, ActivityConstants.UPLOAD_SUCCESS);
    }



}
