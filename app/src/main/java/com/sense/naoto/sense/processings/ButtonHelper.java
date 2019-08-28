package com.sense.naoto.sense.processings;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import com.sense.naoto.sense.R;

public class ButtonHelper {

    public static void enableButton(Button button, Context context){
        button.setEnabled(true);
        button.setTextColor(ContextCompat.getColor(context, R.color.colorHashtag));
    }

    public static void unEnableButton(Button button, Context context){
        button.setEnabled(false);
        button.setTextColor(ContextCompat.getColor(context, R.color.colorUnSelected));
    }
}
