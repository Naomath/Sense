package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.google.gson.Gson;
import com.sense.naoto.sense.MainActivity;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.constatnt.ActivityConstants;
import com.sense.naoto.sense.save_fashion.SelectItemsActivity;

import java.util.List;

public class SaveFashionActivityHelper {

    public static void launchMainActivity(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivityForResult(intent,ActivityConstants.POST_FASHION_ACTIVITY_CODE);
    }

    public static void launchMainActivityForSaveSucucess(Activity activity){
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivityForResult(intent,ActivityConstants.SAVE_SUCCESS);
    }

    public static void launchSelectItemActivity(Activity activity){
        Intent intent = new Intent(activity, SelectItemsActivity.class);
        activity.startActivity(intent);
    }

    public static List<FashionItem> getPickedItems(Activity activity){
        Intent intent = activity.getIntent();
        String strItems = intent.getStringExtra("items");
        List<FashionItem> items = new Gson().fromJson(strItems,List.class);
        return items;
    }


}
