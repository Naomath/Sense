package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;

import com.sense.naoto.sense.constatnt.ActivityConstants;
import com.sense.naoto.sense.login.LoginActivity;
import com.sense.naoto.sense.save_fashion.SaveFashionActivity;
import com.sense.naoto.sense.showing_my_fashion.ShowingMyFashionActivity;

public class MainActivityHelper {

    public static void launchPostFashionActivity(Activity activity){
        Intent intent  = new Intent(activity, SaveFashionActivity.class);
        activity.startActivity(intent);

    }

    public static void launchShowingMyFashionActivity(Activity activity, int position){
        Intent intent = new Intent(activity, ShowingMyFashionActivity.class);
        intent.putExtra(ActivityConstants.FASHION_NUMBER_ARG,position);
        activity.startActivity(intent);
    }

    public static void launchLoginActivity(Activity activity){
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

}
