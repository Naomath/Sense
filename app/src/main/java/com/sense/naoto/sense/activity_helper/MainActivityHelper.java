package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;

import com.sense.naoto.sense.constatnt.ActivityConstants;
import com.sense.naoto.sense.post_fashion.PostFashionActivity;
import com.sense.naoto.sense.showing_my_fashion.ShowingMyFashionActivity;

public class MainActivityHelper {

    public static void launchPostFashionActivity(Activity activity){
        Intent intent  = new Intent(activity, PostFashionActivity.class);
        activity.startActivity(intent);

    }

    public static void launchShowingMyFashionActivity(Activity activity, int position){
        Intent intent = new Intent(activity, ShowingMyFashionActivity.class);
        intent.putExtra(ActivityConstants.FASHION_NUMBER_ARG,position);
        activity.startActivity(intent);
    }

}
