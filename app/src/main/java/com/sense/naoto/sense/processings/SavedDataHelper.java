package com.sense.naoto.sense.processings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.classes.FashionItem;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SavedDataHelper {


    public static final String FASHION_LIST_PREF_KEY = "FASHIONLISTPREFKEY";

    public static final String ITEM_LIST_PREF_KEY = "ITEMLISTPREFKEY";

    private static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences("BasicData", Context.MODE_PRIVATE);
    }

    public static void saveNewFashion(Context context, Fashion fashion) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        String prefKey = fashion.getPrefKey();

        Gson gson = new Gson();
        preferences.edit().putString(prefKey, gson.toJson(fashion)).commit();

        addNewFashionToList(prefKey, context);

    }

    public static void saveNewItem(Context context, FashionItem item, Bitmap bitmap) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);

        String imageCode = ImageHelper.fromBitmapToBase64(bitmap);
        String preKey = item.getPrefKey();

        item.setImageCode(imageCode);

        Gson gson = new Gson();
        preferences.edit().putString(preKey, gson.toJson(item)).commit();

        addNewItemToList(preKey, context);
    }

    public static List<Fashion> getMyFashionsOrderedByNew(Context context) {
        List<String> fashionKeyList = getFashionPrefKeyList(context);

        Collections.reverse(fashionKeyList);
        //これにより新しい順にFashionのPrefKeyが並ぶ

        List<Fashion> fashionList = new ArrayList<>();

        for (String key : fashionKeyList) {
            Fashion item = getFashionByPrefKey(context, key);
            fashionList.add(item);
        }

        return fashionList;
    }

    public static List<FashionItem> getMyItemsOrderedByNew(Context context){
        List<String> itemKeyList = getItemPrefKeyList(context);

        Collections.reverse(itemKeyList);

        List<FashionItem> itemList = new ArrayList<>();

        for (String key: itemKeyList){
            FashionItem item = getItemByPrefKey(context, key);
            itemList.add(item);
        }

        return itemList;
    }

    public static Fashion getFashionByPrefKey(Context context, String prefKey) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        Gson gson = new Gson();

        Fashion fashion = gson.fromJson(preferences.getString(prefKey, ""), Fashion.class);
        return fashion;
    }

    public static FashionItem getItemByPrefKey(Context context, String prefKey) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        Gson gson = new Gson();

        FashionItem item = gson.fromJson(preferences.getString(prefKey, ""), FashionItem.class);
        return item;
    }

    public static Fashion getFashionByNumber(Context context, int i) {
        //ここで受け取るnumber、なんばん目に作られたか
        //最初が0

        List<String> prefKeys = getFashionPrefKeyList(context);
        Fashion fashion = getFashionByPrefKey(context, prefKeys.get(i));

        return fashion;
    }

    public static FashionItem getItemByNumber(Context context, int i) {
        //ここで受け取るnumber、なんばん目に作られたか
        //最初が0
        List<String> prefKeys = getItemPrefKeyList(context);
        FashionItem item = getItemByPrefKey(context, prefKeys.get(i));

        return item;
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


    private static void addNewItemToList(String prefKey, Context context) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        List<String> list = getItemPrefKeyList(context);

        list.add(prefKey);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            jsonArray.put(list.get(i));
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ITEM_LIST_PREF_KEY, jsonArray.toString());
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


    private static List<String> getItemPrefKeyList(Context context) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);

        List<String> list = new ArrayList<>();

        String strJson = preferences.getString(ITEM_LIST_PREF_KEY, "");
        if (!strJson.equals("")) {
            try {
                JSONArray jsonArray = new JSONArray(strJson);
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(jsonArray.getString(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public static void saveImageCode(String str, Context context){
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("image",str);
        editor.apply();
    }

    public static String getImageCode(Context context){
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        String str = preferences.getString("image","");
        return str;
    }

}
