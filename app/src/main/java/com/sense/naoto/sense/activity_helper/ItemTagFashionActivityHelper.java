package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;

import com.sense.naoto.sense.MainActivity;

public class ItemTagFashionActivityHelper {

    public static String getSentItemPrefKey(Activity activity){
        Intent intent = activity.getIntent();
        return intent.getStringExtra("prefKey");
    }

    public static void launchMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }
}
