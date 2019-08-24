package com.sense.naoto.sense.processings;

import android.content.Context;
import android.content.SharedPreferences;

import com.sense.naoto.sense.classes.User;


public class UserPreferencesHelper {
    /*
    ここで保存するのは
    アカウント名
    アカウントのid
    アカウントの投稿数
    アカウントのフォロー数
    アカウントのフォロワー数
    アカウントのプロフィール画像
     */

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
      return context.getSharedPreferences("BasicData",Context.MODE_PRIVATE);
    }

    public static void updataPreferences(User user, Context context){
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Key.ACCOUNT_NAME.name(),user.getUserName());
        editor.putString(Key.ACCOUNT_ID.name(), user.getUserId());
        editor.putInt(Key.POST_NUMBER.name(), user.getPostNumber());
        editor.putInt(Key.FOLLOWING_NUMBER.name(), user.getFollowingNumber());
        editor.putInt(Key.FOLLOWER_NUMBER.name(), user.getFollowerNumber());
        editor.putString(Key.IMAGE_ICON.name(), user.getIconImage());

        editor.putBoolean(Key.IS_LOGIN.name(), true);
        editor.apply();
    }

    public static User getUserPreferences(Context context){
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        User user  = new User();
        user.setUserName(preferences.getString(Key.ACCOUNT_NAME.name(),null));
        user.setUserId(preferences.getString(Key.ACCOUNT_ID.name(),null));
        user.setPostNumber(preferences.getInt(Key.POST_NUMBER.name(), 0));
        user.setFollowingNumber(preferences.getInt(Key.FOLLOWING_NUMBER.name(), 0));
        user.setFollowerNumber(preferences.getInt(Key.FOLLOWER_NUMBER.name(), 0));
        user.setIconImage(preferences.getString(Key.IMAGE_ICON.name(),null));

        return user;
    }

    public static void logout(Context context){
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(Key.IS_LOGIN.name(), false);
        //中のデータは大した容量ではないので、削除しない
    }

    public static boolean isLogin(Context context){
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        return preferences.getBoolean(Key.IS_LOGIN.name(),false);
    }


    protected enum Key{
        ACCOUNT_NAME,
        ACCOUNT_ID,
        POST_NUMBER,
        FOLLOWING_NUMBER,
        FOLLOWER_NUMBER,
        IMAGE_ICON,
        IS_LOGIN
    }

}
