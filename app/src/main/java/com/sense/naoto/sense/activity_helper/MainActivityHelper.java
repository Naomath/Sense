package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;

import com.sense.naoto.sense.constatnt.ActivityConstants;
import com.sense.naoto.sense.item_tag_fashions.ItemTagFashionsActivity;
import com.sense.naoto.sense.save_fashion.SaveFashionAndItemActivity;
import com.sense.naoto.sense.showing_my_fashion.ShowingMyFashionActivity;

public class MainActivityHelper {

    public static void launchPostFashionActivity(Activity activity) {
        Intent intent = new Intent(activity, SaveFashionAndItemActivity.class);
        activity.startActivity(intent);

    }

    public static void launchShowingMyFashionActivity(Activity activity, int position) {
        Intent intent = new Intent(activity, ShowingMyFashionActivity.class);
        intent.putExtra(ActivityConstants.FASHION_NUMBER_ARG, position);
        activity.startActivity(intent);
    }

    public static void launchItemTagFashionsActivity(Activity activity, String prefKey){
        Intent intent = new Intent(activity, ItemTagFashionsActivity.class);
        intent.putExtra("prefKey", prefKey);
        activity.startActivity(intent);
    }

}
