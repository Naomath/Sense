package com.sense.naoto.sense.item_tag_fashions;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.ItemTagFashionActivityHelper;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.fashion_swipe.FashionSwipeFragment;
import com.sense.naoto.sense.processings.SavedDataHelper;

public class ItemTagFashionsActivity extends AppCompatActivity implements OnItemTagFashionListener {


    //変数
    private FashionItem mItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_tag_fashions);

        String prefKey = ItemTagFashionActivityHelper.getSentItemPrefKey(this);
        mItem = SavedDataHelper.getItemByPrefKey(this, prefKey);

        setFragment(getAllFashionTaggedItemFragment());
    }


    private Fragment getAllFashionTaggedItemFragment() {
        AllFashionTaggedItemFragment fragment = new AllFashionTaggedItemFragment();
        fragment.setMItem(mItem);
        fragment.setListener(this);
        return fragment;
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();
    }

    @Override
    public void onBackFragment() {
        setFragment(getAllFashionTaggedItemFragment());
    }

    @Override
    public void onBackActivity() {
        ItemTagFashionActivityHelper.launchMainActivity(this);
    }

    @Override
    public void onSelectFashion(int position) {
        FashionSwipeFragment fragment = FashionSwipeFragment.newInstance(FashionSwipeFragment.REQUEST_TAGGED_ITEM);
        fragment.setFashionPrefKeys(mItem.getUsedFashionPrefKeys());
        fragment.setStartingNumber(position);
        fragment.setItemTagFashionListener(this);
        setFragment(fragment);
    }
}
