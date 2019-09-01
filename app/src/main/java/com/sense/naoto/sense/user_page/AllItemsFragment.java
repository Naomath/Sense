package com.sense.naoto.sense.user_page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.MainActivityHelper;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.widgets.GridItemAdapter;

import java.util.List;


public class AllItemsFragment extends Fragment implements AdapterView.OnItemClickListener {


    //Views
    private View mView;
    private LayoutInflater mInflater;


    //変数
    private List<FashionItem> itemList;
    private int ITEM_LIST_SIZE = 0;

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

        if(ITEM_LIST_SIZE > 0){
            GridView gridView = mView.findViewById(R.id.grid_view);
            GridItemAdapter adapter = new GridItemAdapter(mInflater, R.layout.fashion_item_grid, itemList, getActivity());
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(this);

            TextView txvNoItem = mView.findViewById(R.id.txv_no_items);
            txvNoItem.setVisibility(View.GONE);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //gridViewの要素がクリックされた時
        String prefKey = itemList.get(position).getPrefKey();
        MainActivityHelper.launchItemTagFashionsActivity(getActivity(), prefKey, 1);
    }
}
