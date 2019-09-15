package com.sense.naoto.sense.processings;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.widget.ListView;

import com.google.gson.Gson;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.classes.FashionItem;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SavedDataHelper {


    public static final String FASHION_LIST_PREF_KEY = "FASHIONLISTPREFKEY";
    public static final String ITEM_LIST_PREF_KEY = "ITEMLISTPREFKEY";

    private static SharedPreferences getDefaultSharedPreferences(Context context) {
        return context.getSharedPreferences("sense_data", Context.MODE_PRIVATE);
    }

    public static void saveNewFashion(Context context, Fashion fashion, Bitmap bitmap) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        String prefKey = fashion.getPrefKey();

        Gson gson = new Gson();
        preferences.edit().putString(prefKey, gson.toJson(fashion)).apply();

        for (String itemPrefKey : fashion.getItemPrefKey()) {
            addNewFashionToItemList(prefKey, itemPrefKey, context);
        }

        addNewFashionToList(prefKey, context);

    }

    public static void saveNewItem(Context context, FashionItem item, Bitmap bitmap) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        String preKey = item.getPrefKey();

        Gson gson = new Gson();
        preferences.edit().putString(preKey, gson.toJson(item)).apply();

        addNewItemToList(preKey, context);
    }

    public static void updateFashion(Context context, Fashion fashion) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        String prefKey = fashion.getPrefKey();

        Gson gson = new Gson();
        preferences.edit().putString(prefKey, gson.toJson(fashion)).apply();
    }

    public static void updateFashionItem(Context context, FashionItem item) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        String prefKey = item.getPrefKey();

        Gson gson = new Gson();
        preferences.edit().putString(prefKey, gson.toJson(item)).apply();
    }

    public static List<Fashion> getMyFashionsOrderedByNew(Context context) {
        List<String> fashionKeyList = getFashionPrefAllKeyList(context);

        Collections.reverse(fashionKeyList);
        //これにより新しい順にFashionのPrefKeyが並ぶ

        List<Fashion> fashionList = new ArrayList<>();

        for (String key : fashionKeyList) {
            Fashion item = getFashionByPrefKey(context, key);
            fashionList.add(item);
        }

        return fashionList;
    }

    public static List<FashionItem> getMyItemsOrderedByNew(Context context) {
        List<String> itemKeyList = getItemPrefAllKeyList(context);

        Collections.reverse(itemKeyList);

        List<FashionItem> itemList = new ArrayList<>();

        for (String key : itemKeyList) {
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


    public static List<Fashion> getFashionsByPrefKeyList(Context context, List<String> prefKeys) {
        List<Fashion> fashions = new ArrayList<>();

        for (String key : prefKeys) {
            fashions.add(getFashionByPrefKey(context, key));
        }

        return fashions;
    }

    public static List<FashionItem> getItemsByPrefKeyList(Context context, List<String> prefKeys) {
        List<FashionItem> items = new ArrayList<>();

        for (String key : prefKeys) {
            items.add(getItemByPrefKey(context, key));
        }

        return items;
    }


    public static Fashion getFashionByNumber(Context context, int i) {
        //ここで受け取るnumber、なんばん目に作られたか
        //最初が0

        List<String> prefKeys = getFashionPrefAllKeyList(context);
        Fashion fashion = getFashionByPrefKey(context, prefKeys.get(i));

        return fashion;
    }

    public static FashionItem getItemByNumber(Context context, int i) {
        //ここで受け取るnumber、なんばん目に作られたか
        //最初が0
        List<String> prefKeys = getItemPrefAllKeyList(context);
        FashionItem item = getItemByPrefKey(context, prefKeys.get(i));

        return item;
    }

    public static FashionItem getRandomFashionItem(Context context) {
        List<String> itemKeys = getItemPrefAllKeyList(context);
        Collections.shuffle(itemKeys);

        String prefKey = itemKeys.get(0);
        return getItemByPrefKey(context, prefKey);
    }

    public static void changeFavState(Context context, Fashion fashion) {
        boolean wasFav = fashion.isFav();
        boolean isFav = false;
        if (wasFav) {
            isFav = false;
        } else {
            isFav = true;
        }

        fashion.setFav(isFav);
        updateFashion(context, fashion);
    }

    public static boolean isFashionItem(Context context) {
        //fashionItemが一つでも登録されているか調べる
        List<String> itemKeys = getItemPrefAllKeyList(context);

        if (itemKeys.size() > 0) {
            return true;
        } else {
            return false;
        }
    }


    private static void addNewFashionToList(String prefKey, Context context) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        List<String> list = getFashionPrefAllKeyList(context);
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

    private static void addNewFashionToItemList(String fashionPrefKey, String itemPrefKey, Context context) {
        FashionItem fashionItem = getItemByPrefKey(context, itemPrefKey);
        List<String> list = fashionItem.getUsedFashionPrefKeys();
        list.add(fashionPrefKey);
        fashionItem.setUsedFashionPrefKeys(list);
        updateFashionItem(context, fashionItem);
    }


    private static void addNewItemToList(String prefKey, Context context) {
        SharedPreferences preferences = getDefaultSharedPreferences(context);
        List<String> list = getItemPrefAllKeyList(context);

        list.add(prefKey);
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < list.size(); i++) {
            jsonArray.put(list.get(i));
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(ITEM_LIST_PREF_KEY, jsonArray.toString());
        editor.apply();
    }


    private static List<String> getFashionPrefAllKeyList(Context context) {
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


    private static List<String> getItemPrefAllKeyList(Context context) {
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

}
