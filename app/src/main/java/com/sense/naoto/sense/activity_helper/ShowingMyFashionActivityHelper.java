package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;

import com.sense.naoto.sense.MainActivity;
import com.sense.naoto.sense.constatnt.ActivityConstants;

public class ShowingMyFashionActivityHelper {

    public static int getFashionNumberArg(Activity activity){
        Intent intent = activity.getIntent();
        int i = intent.getIntExtra(ActivityConstants.FASHION_NUMBER_ARG,0);
        return i;
    }

    public static void launchMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra("request",ActivityConstants.SHOWING_MY_FASHION_ACTIVITY_CODE);
        activity.startActivity(intent);
    }

}
