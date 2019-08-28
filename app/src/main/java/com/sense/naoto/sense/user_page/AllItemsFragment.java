package com.sense.naoto.sense.user_page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.widgets.ExpandableHeightGridView;

import java.util.List;


public class AllItemsFragment extends Fragment implements AdapterView.OnItemClickListener{


    private View mView;

    private LayoutInflater mInflater;

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
        ExpandableHeightGridView gridView = mView.findViewById(R.id.grid_view);

        List<FashionItem> itemList = SavedDataHelper.getMyItemsOrderedByNew(getContext());
        ITEM_LIST_SIZE = itemList.size();

        GridItemAdapter adapter = new GridItemAdapter(mInflater, R.layout.grid_items, itemList, getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //gridViewの要素がクリックされた時
        //todo:
    }
}
