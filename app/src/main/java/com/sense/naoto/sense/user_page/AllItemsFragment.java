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


    //変数
    private List<FashionItem> itemList;
    private int ITEM_LIST_SIZE = 0;

    //定数
    private final int NUMBER_ALL = 0;
    private final int NUMBER_TOP = 1;
    private final int NUMBER_BOTTOM = 2;
    private final int NUMBER_ACCESSORY = 3;
    private final int NUMBER_OTHERS = 4;

    public static AllItemsFragment newInstance(String param1, String param2) {
        AllItemsFragment fragment = new AllItemsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

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

        return mView;

    }

    private void setViews() {
        itemList = SavedDataHelper.getMyItemsOrderedByNew(getContext());
        ITEM_LIST_SIZE = itemList.size();

        TextView txvNoItem = mView.findViewById(R.id.txv_no_items);

        HorizontalScrollView typeFrame = mView.findViewById(R.id.horizontalScroll);

        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_all_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_tops_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_bottom_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_accessory_selected));
        imvTypeSelected.add((ImageView) mView.findViewById(R.id.imv_other_selected));

        List<ImageButton> btns = new ArrayList<>();
        btns.add((ImageButton) mView.findViewById(R.id.btn_all));
        btns.add((ImageButton) mView.findViewById(R.id.btn_tops));
        btns.add((ImageButton) mView.findViewById(R.id.btn_bottom));
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
            GridItemAdapter adapter = new GridItemAdapter(mInflater, R.layout.fashion_item_grid, itemList, getActivity());
            mGridView.setAdapter(adapter);
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

        //todo:ここから個別のgridviewの処理
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //gridViewの要素がクリックされた時
        String prefKey = itemList.get(position).getPrefKey();
        MainActivityHelper.launchItemTagFashionsActivity(getActivity(), prefKey, 1);
    }
}
