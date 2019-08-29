package com.sense.naoto.sense.save_fashion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.SaveFashionActivityHelper;

public class SaveFashionAndItemActivity extends AppCompatActivity implements ChooseTypeFragment.ChooseTypeFragmentListener
        , SelectPhotoFragment.OnSelectPhotoFragmentListener, SelectItemsFragment.OnSelectItemsListener
       ,NameItemFragment.OnNameItemFragmentListener, CheckFashionFragment.OnCheckFashionFragmentListener
        ,CheckItemFragment.OnCheckItemFragmentListener, OnBackFragmentListener {

    //定数
    public static final int TYPE_FASHION = 0;
    public static final int TYPE_ITEM = 1;

    //変数
    private boolean isSuccess = false;

    private int type;

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
        chooseTypeFragment.setListener(this);

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
        setFragment(fragment);
    }


    @Override
    public void onSelectPhoto() {
        switch (type){
            case TYPE_FASHION:
                SelectItemsFragment itemsFragment = new SelectItemsFragment();
                itemsFragment.setListeners(this, this);
                setFragment(itemsFragment);

                break;

            case TYPE_ITEM:
                NameItemFragment nameItemFragment = new NameItemFragment();
                nameItemFragment.setListeners(this,this);
                setFragment(nameItemFragment);

                break;
        }
    }

    @Override
    public void onSelectItems() {
        CheckFashionFragment fragment = new CheckFashionFragment();
        fragment.setListeners(this,this);
        setFragment(fragment);
    }

    @Override
    public void onNameItem() {
        CheckItemFragment fragment = new CheckItemFragment();
        fragment.setListeners(this,this);
        setFragment(fragment);
    }


    @Override
    public void onCheckFashion() {
        SaveFashionActivityHelper.launchMainActivity(this);
    }


    @Override
    public void onCheckItem() {
        SaveFashionActivityHelper.launchMainActivity(this);
    }

    @Override
    public void onBack() {
        fragmentManager.popBackStack();
    }

}

