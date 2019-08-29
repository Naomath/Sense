package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.constatnt.ActivityConstants;
import com.sense.naoto.sense.save_fashion.SaveFashionActivity;
import com.sense.naoto.sense.save_fashion.SetUpFashionFragment;

import java.util.List;

public class SelectItemsActivityHelper {

    public static void launchSetUpFashionActivityForCancel(Activity activity){
        Intent intent = new Intent(activity, SaveFashionActivity.class);
        activity.startActivityForResult(intent,ActivityConstants.CANCEL_PICK_ITEMS);
    }

    public static void launchSetUpFashionActivityForPickItems(Activity activity, List<FashionItem> items){
        Intent intent = new Intent(activity, SaveFashionActivity.class);
        intent.putExtra("items", new Gson().toJson(items));
        activity.startActivityForResult(intent, ActivityConstants.DONE_PICK_ITEM);
    }

}
