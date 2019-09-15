package com.sense.naoto.sense.processings;

import android.content.Context;
import android.content.SharedPreferences;

public class RandomItemHelper {

    private static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences("randomitem", Context.MODE_PRIVATE);
    }

    public static void alertDoneRandom(Context context, String prefKey){
        //randomなitemの抽出を行ったことを知らせる
        SharedPreferences preferences = getDefaultSharedPreferences(context);

        String stringKey = CalendarHelper.getNowDateString();
        String booleanKey = CalendarHelper.getNowDateBoolean();
        preferences.edit().putString(stringKey, prefKey).apply();
        preferences.edit().putBoolean(booleanKey, true).apply();
    }

    public static boolean isDoneRandom(Context context){
        //すでにrandomを行ったかどうか
        SharedPreferences preferences = getDefaultSharedPreferences(context);

        String booleanKey = CalendarHelper.getNowDateBoolean();
        return preferences.getBoolean(booleanKey, false);
    }

    public static String getPresentRandomItemPrefKey(Context context){
        //すでに存在しているrandomitemのprefkeyを返す
        SharedPreferences preferences = getDefaultSharedPreferences(context);

        String strDate = CalendarHelper.getNowDateString();
        return preferences.getString(strDate,null);
    }
}
