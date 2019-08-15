package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;

import com.sense.naoto.sense.constatnt.ActivityConstants;

public class ShowingMyFashionActivityHelper {

    public static int getFashionNumberArg(Activity activity){
        Intent intent = activity.getIntent();
        return intent.getIntExtra(ActivityConstants.FASHION_NUMBER_ARG,0);
    }

    public static void launchMainActivity(Activity activity){
        Intent intent = new Intent();
        activity.startActivityForResult(intent, ActivityConstants.SHOWING_MY_FASHION_ACTIVITY_CODE);
    }
}
