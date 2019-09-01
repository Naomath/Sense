package com.sense.naoto.sense.save_fashion;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.SaveFashionActivityHelper;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.CalendarHelper;
import com.sense.naoto.sense.processings.SavedDataHelper;

import java.util.ArrayList;
import java.util.List;

public class SaveFashionAndItemActivity extends AppCompatActivity implements ChooseTypeFragment.ChooseTypeFragmentListener
        , SelectPhotoFragment.OnSelectPhotoFragmentListener, SelectItemsFragment.OnSelectItemsListener
        , NameItemFragment.OnNameItemFragmentListener, CheckFashionFragment.OnCheckFashionFragmentListener
        , CheckItemFragment.OnCheckItemFragmentListener, OnBackFragmentListener {

    //定数
    public static final int TYPE_FASHION = 0;
    public static final int TYPE_ITEM = 1;

    //変数
    private boolean isSuccess = false;
    private int type;
    private Bitmap pickedImage;
    private Uri pickedImageUri;
    private List<FashionItem> selectedItems = new ArrayList<>();
    private String itemName;
    private int itemType;

    //Fragment関連
    private int count = 0;

    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_fashion_and_item);

        //fragmentの順じょ
        //ChooseTypeFragment -- SelectPhotoFragment --SelectItemsFragment -- CheckFashionFragment
        //                                          |
        //                                          --NameItemFragment    -- CheckItemFragment
        setFragmentRelated();

        ChooseTypeFragment chooseTypeFragment = new ChooseTypeFragment();
        chooseTypeFragment.setListeners(this, this);

        setFragment(chooseTypeFragment);
    }

    private void setFragmentRelated() {
        fragmentManager = getSupportFragmentManager();
    }

    private void setFragment(Fragment fm) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(
                R.anim.slide_in_right, R.anim.slide_out_left,// 追加した時のアニメーション
                R.anim.slide_in_left, R.anim.slide_out_right // 戻る(popBackStack)した時のアニメーション
        );

        transaction.replace(R.id.frame, fm);

        if (count != 0) transaction.addToBackStack(null);

        transaction.commit();
        count++;
    }

    private Fashion generateFashion(){
        List<String> itemPrefKeys = new ArrayList<>();
        String strDate= CalendarHelper.getNowDate();
        String prefKey = Fashion.newPreferenceKey();

        Fashion fashion;

        if (selectedItems.size()>0){
            for (FashionItem item:selectedItems){
                itemPrefKeys.add(item.getPrefKey());
            }

            fashion = new Fashion(strDate, pickedImageUri.toString(), prefKey, itemPrefKeys);
        }else {

            fashion = new Fashion(strDate, pickedImageUri.toString(), prefKey);
        }

        return fashion;
    }

    private FashionItem generateFashionItem(){
        String strDate= CalendarHelper.getNowDate();
        String prefKey = Fashion.newPreferenceKey();

        return new FashionItem(pickedImageUri.toString(), itemName, strDate, prefKey,itemType);
    }

    @Override
    public void finish() {
        if (isSuccess) {
            SaveFashionActivityHelper.launchMainActivityForSaveSucucess(this);
        } else {
            SaveFashionActivityHelper.launchMainActivity(this);
        }
        super.finish();
    }

    @Override
    public void onDecidedType(int i) {
        type = i;

        SelectPhotoFragment fragment = new SelectPhotoFragment();
        fragment.setListeners(this, this);
        fragment.setType(type);
        setFragment(fragment);
    }


    @Override
    public void onSelectPhoto(Bitmap bitmap, Uri uri) {
        pickedImage = bitmap;
        pickedImageUri = uri;

        switch (type) {
            case TYPE_FASHION:
                SelectItemsFragment itemsFragment = new SelectItemsFragment();
                itemsFragment.setListeners(this, this);
                setFragment(itemsFragment);

                break;

            case TYPE_ITEM:
                NameItemFragment nameItemFragment = new NameItemFragment();
                nameItemFragment.setListeners(this, this);
                nameItemFragment.setImage(pickedImage);
                setFragment(nameItemFragment);

                break;
        }
    }

    @Override
    public void onSelectItems(List<FashionItem> items) {

        selectedItems.clear();
        for (FashionItem item:items){
            selectedItems.add(item);
        }

        CheckFashionFragment fragment = new CheckFashionFragment();
        fragment.setListeners(this, this);
        fragment.setBitmap(pickedImage);
        if (selectedItems.size()>0){
            fragment.setFashionItems(selectedItems);
        }

        setFragment(fragment);
    }

    @Override
    public void onNameItem(String name, int itemType) {
        itemName = name;
        this.itemType = itemType;

        CheckItemFragment fragment = new CheckItemFragment();
        fragment.setListeners(this, this);
        fragment.setImage(pickedImage);
        fragment.setName(itemName);
        fragment.setItemType(itemType);
        setFragment(fragment);
    }


    @Override
    public void onCheckFashion() {
        SavedDataHelper.saveNewFashion(this, generateFashion());
        SaveFashionActivityHelper.launchMainActivity(this);
    }


    @Override
    public void onCheckItem() {
        SavedDataHelper.saveNewItem(this, generateFashionItem(), pickedImage);
        SaveFashionActivityHelper.launchMainActivity(this);

    }

    @Override
    public void onBack() {
        if (count == 1) {
            SaveFashionActivityHelper.launchMainActivity(this);
        } else {
            fragmentManager.popBackStack();
            count --;

        }
    }

}

