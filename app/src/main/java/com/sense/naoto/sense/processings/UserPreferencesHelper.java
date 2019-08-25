package com.sense.naoto.sense.processings;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.classes.User;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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

    public static final String FASHION_LIST_PREF_KEY = "FASHIONLISTPREFKEY";

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences("BasicData", Context.MODE_PRIVATE);
    }

    public static void updataPreferences(User user, Context context) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Key.ACCOUNT_NAME.name(), user.getUserName());
        editor.putString(Key.ACCOUNT_ID.name(), user.getUserId());
        editor.putInt(Key.POST_NUMBER.name(), user.getPostNumber());
        editor.putInt(Key.FOLLOWING_NUMBER.name(), user.getFollowingNumber());
        editor.putInt(Key.FOLLOWER_NUMBER.name(), user.getFollowerNumber());
        editor.putString(Key.IMAGE_ICON.name(), user.getIconImage());

        editor.putBoolean(Key.IS_LOGIN.name(), true);
        editor.apply();
    }

    public static User getUserPreferences(Context context) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        User user = new User();
        user.setUserName(preferences.getString(Key.ACCOUNT_NAME.name(), null));
        user.setUserId(preferences.getString(Key.ACCOUNT_ID.name(), null));
        user.setPostNumber(preferences.getInt(Key.POST_NUMBER.name(), 0));
        user.setFollowingNumber(preferences.getInt(Key.FOLLOWING_NUMBER.name(), 0));
        user.setFollowerNumber(preferences.getInt(Key.FOLLOWER_NUMBER.name(), 0));
        user.setIconImage(preferences.getString(Key.IMAGE_ICON.name(), null));

        return user;
    }

    public static void saveNewFashion(Context context, Fashion fashion) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        String prefKey = fashion.getPrefKey();

        Gson gson = new Gson();
        preferences.edit().putString(prefKey, gson.toJson(fashion)).commit();

        addNewFashionToList(prefKey, context);

    }


    public static void updatePreFashion(Context context) {
        //todo:いつか実装する
    }

    public static Fashion getFashionByPrefKey(Context context, String prefKey) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        Gson gson = new Gson();

        Fashion fashion = gson.fromJson(preferences.getString(prefKey, ""), Fashion.class);
        return fashion;
    }


    public static List<Fashion> getMyFashionsOrderedByNew(Context context) {
        List<String> fashionKeyList = getFashionPrefKeyList(context);

        Collections.reverse(fashionKeyList);
        //これにより新しい順にFashionのPrefKeyが並ぶ

        List<Fashion> fashionList = new ArrayList<>();

        for (String key : fashionKeyList) {
            Fashion item = getFashionByPrefKey(context,key);
            fashionList.add(item);
        }

        return fashionList;
    }

    private static void addNewFashionToList(String prefKey, Context context) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        List<String> list = getFashionPrefKeyList(context);
        //ここまでで今までにあった物を入れている

        list.add(prefKey);
        JSONArray jsonAry = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            jsonAry.put(list.get(i));
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(FASHION_LIST_PREF_KEY, jsonAry.toString());
        editor.apply();
    }

    private static List<String> getFashionPrefKeyList(Context context) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);

        List<String> list = new ArrayList<String>();

        String strJson = preferences.getString(FASHION_LIST_PREF_KEY, ""); // 第２引数はkeyが存在しない時に返す初期値
        if (!strJson.equals("")) {
            try {
                JSONArray jsonAry = new JSONArray(strJson);
                for (int i = 0; i < jsonAry.length(); i++) {
                    list.add(jsonAry.getString(i));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    protected enum Key {
        ACCOUNT_NAME,
        ACCOUNT_ID,
        POST_NUMBER,
        FOLLOWING_NUMBER,
        FOLLOWER_NUMBER,
        IMAGE_ICON,
        IS_LOGIN
    }

}
