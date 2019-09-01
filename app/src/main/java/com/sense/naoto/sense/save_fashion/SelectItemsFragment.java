package com.sense.naoto.sense.save_fashion;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.widgets.GridItemAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectItemsFragment extends Fragment implements AdapterView.OnItemClickListener {


    //View
    private View mView;

    //Listeners
    OnSelectItemsListener selectItemsListener;
    OnBackFragmentListener backFragmentListener;

    //List
    private List<FashionItem> itemList = new ArrayList<>();
    private List<FashionItem> selectedItems = new ArrayList<>();
    private List<Boolean> isItemSelected = new ArrayList<>();
    private boolean hasSelectedItems = false;
    private int numberOfSelected = 0;


    //変数
    private int flag = 0;
    //０がキャンセルの場合
    //１が完了をした時

    public SelectItemsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_select_items, container, false);
        setLists();
        setViews();

        return mView;
    }

    public void setListeners(OnSelectItemsListener selectItemsListener, OnBackFragmentListener backFragmentListener) {
        this.selectItemsListener = selectItemsListener;
        this.backFragmentListener = backFragmentListener;
    }

    private void setViews() {
        ImageButton btnComplete = mView.findViewById(R.id.btn_complete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeSelect();
            }
        });

        ImageButton btnBack = mView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFragmentListener.onBack();
            }
        });

        if (itemList.size()>0) {
            GridView gridView = mView.findViewById(R.id.grid_view);

            GridItemAdapter adapter = new GridItemAdapter(getLayoutInflater(), R.layout.fashion_item_grid, itemList, getActivity());
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(this);

            TextView txvNoItem = mView.findViewById(R.id.txv_no_items);
            txvNoItem.setVisibility(View.GONE);
        }
    }

    private void completeSelect(){
        setSelectedItems();
        selectItemsListener.onSelectItems(selectedItems);
    }


    private void setLists() {
        itemList.clear();
        isItemSelected.clear();
        selectedItems.clear();
        itemList = SavedDataHelper.getMyItemsOrderedByNew(getContext());

        for (int i = 0; i < itemList.size(); i++) {
            isItemSelected.add(false);
        }
    }

    private void setSelectedItems() {
        for (int i = 0; i < isItemSelected.size(); i++) {
            if (isItemSelected.get(i)) {
                selectedItems.add(itemList.get(i));
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imvSelected = view.findViewById(R.id.imgSelected);

        boolean wasSelected = isItemSelected.get(position);

        if (wasSelected) {
            isItemSelected.set(position, false);
            imvSelected.setVisibility(View.INVISIBLE);
            numberOfSelected --;

            if (numberOfSelected == 0){
                hasSelectedItems = false;
            }
        } else {
            isItemSelected.set(position, true);
            imvSelected.setVisibility(View.VISIBLE);

            hasSelectedItems = true;
            numberOfSelected++;
        }
    }

    public interface OnSelectItemsListener {
        void onSelectItems(List<FashionItem> selectedItems);
    }
}
