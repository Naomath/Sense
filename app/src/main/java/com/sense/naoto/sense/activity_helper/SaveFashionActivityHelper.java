package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;

import com.sense.naoto.sense.MainActivity;
import com.sense.naoto.sense.constatnt.ActivityConstants;

public class SaveFashionActivityHelper {

    public static void launchMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivityForResult(intent,ActivityConstants.POST_FASHION_ACTIVITY_CODE);
    }

    public static void launchMainActivityForSaveSucucess(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivityForResult(intent,ActivityConstants.SAVE_SUCCESS);
    }


}
