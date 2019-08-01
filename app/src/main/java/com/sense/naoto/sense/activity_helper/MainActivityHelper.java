package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;

import com.sense.naoto.sense.post_fashion.PostFashionActivity;

public class MainActivityHelper {

    public static void launchPostFashionActivity(Activity activity){
        Intent intent  = new Intent(activity, PostFashionActivity.class);
        activity.startActivity(intent);


    }

}
