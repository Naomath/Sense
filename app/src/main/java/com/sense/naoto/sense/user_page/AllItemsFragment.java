package com.sense.naoto.sense.user_page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.MainActivityHelper;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.widgets.GridItemAdapter;

import java.util.ArrayList;
import java.util.List;


public class AllItemsFragment extends Fragment implements AdapterView.OnItemClickListener {


    //Views
    private View mView;
    private LayoutInflater mInflater;
    private GridView mGridView;
    private List<ImageView> imvTypeSelected = new ArrayList<>();
    private GridItemAdapter mGridItemAdapter;
    private TextView txvNoItem;


    //変数
    private List<FashionItem> itemList;
    private int ITEM_LIST_SIZE = 0;
    private int mode = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_all_items, container, false);
        mInflater = inflater;

        setViews();

        mode = 0;

        return mView;

    }

    private void setViews() {
        itemList = SavedDataHelper.getMyItemsOrderedByNew(getContext());
        ITEM_LIST_SIZE = itemList.size();

        txvNoItem = mView.findViewById(R.id.txv_no_items);

        HorizontalScrollView typeFrame = mView.findViewById(R.id.horizontalScroll);

        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_all_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_tops_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_bottom_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_outer_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_dress_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_bag_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_shoes_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_accessory_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_other_selected));

        List<ImageButton> btns = new ArrayList<>();
        btns.add((ImageButton) mView.findViewById(R.id.btn_all));
        btns.add((ImageButton) mView.findViewById(R.id.btn_tops));
        btns.add((ImageButton) mView.findViewById(R.id.btn_bottom));
        btns.add((ImageButton) mView.findViewById(R.id.btn_outer));
        btns.add((ImageButton) mView.findViewById(R.id.btn_dress));
        btns.add((ImageButton) mView.findViewById(R.id.btn_bag));
        btns.add((ImageButton) mView.findViewById(R.id.btn_shoes));
        btns.add((ImageButton) mView.findViewById(R.id.btn_accessory));
        btns.add((ImageButton) mView.findViewById(R.id.btn_other));

        for (int i = 0; i < btns.size(); i++) {
            final int mode = i;

            btns.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectType(mode);
                }
            });
        }


        if (ITEM_LIST_SIZE > 0) {
            mGridView = mView.findViewById(R.id.grid_view);
            mGridItemAdapter = new GridItemAdapter(mInflater, R.layout.fashion_item_grid,
                    itemList, getActivity());
            mGridView.setAdapter(mGridItemAdapter);
            mGridView.setOnItemClickListener(this);

            txvNoItem.setVisibility(View.GONE);
        } else {
            txvNoItem.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.GONE);
            typeFrame.setVisibility(View.GONE);

        }

    }

    private void selectType(int i) {

        for (ImageView item : imvTypeSelected) {
            item.setVisibility(View.GONE);
        }

        imvTypeSelected.get(i).setVisibility(View.VISIBLE);

        if (i != mode) {
            //違うものが押された時
            mode = i;

            i = i - 1;
            //itemadapterではtopsが0扱いなので
            if (i == -1) {
                txvNoItem.setVisibility(View.GONE);

                mGridItemAdapter.changeToAll();
            } else {
                int number = mGridItemAdapter.changeToSpecific(i);
                //このナンバーが何この要素が入っているか
                if (number ==0){
                    //何も要素が入っていない時
                    txvNoItem.setVisibility(View.VISIBLE);
                }else {
                    txvNoItem.setVisibility(View.GONE);
                }
            }
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //gridViewの要素がクリックされた時
        String prefKey = itemList.get(position).getPrefKey();
        MainActivityHelper.launchItemTagFashionsActivity(getActivity(), prefKey, 1);
    }
}
