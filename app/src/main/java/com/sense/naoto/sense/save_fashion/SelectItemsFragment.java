package com.sense.naoto.sense.save_fashion;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;

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


    //変数
    private int flag = 0;
    //０がキャンセルの場合
    //１が完了をした時

    public SelectItemsFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_select_items, container, false);
        setViews();
//        setLists();
//        setViews();

        return mView;
    }

    public void setListeners(OnSelectItemsListener selectItemsListener, OnBackFragmentListener backFragmentListener){
        this.selectItemsListener = selectItemsListener;
        this.backFragmentListener = backFragmentListener;
    }

    private void setViews(){
        Button btnComplete = mView.findViewById(R.id.btn_complete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItemsListener.onSelectItems();
            }
        });

        Button btnBack = mView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFragmentListener.onBack();
            }
        });
    }

//
//    private void setLists() {
//        itemList = SavedDataHelper.getMyItemsOrderedByNew(getContext());
//
//        for (int i = 0; i < itemList.size(); i++) {
//            isItemSelected.add(false);
//        }
//    }
//
//    private void setViews() {
//        Button btnComplete = mView.findViewById(R.id.btn_complete);
//        btnComplete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setSelectedItems();
//                if (selectedItems.size() == 0){
//                    //つまり何も選択されていない状態
//                    //todo
//                }else {
//                    flag = 1;
//                    //todo
//                }
//            }
//        });
//
//        ImageButton btnBack = mView.findViewById(R.id.btn_back);
//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //todo
//            }
//        });
//
//        ExpandableHeightGridView gridView = mView.findViewById(R.id.grid_view);
//
//        GridItemAdapter adapter = new GridItemAdapter(getLayoutInflater(), R.layout.grid_items, itemList, getActivity());
//        gridView.setAdapter(adapter);
//        gridView.setOnItemClickListener(this);
//
//    }
//
//    private void setSelectedItems(){
//        for (int i = 0;i<isItemSelected.size();i++){
//            if (isItemSelected.get(i)){
//                selectedItems.add(itemList.get(i));
//            }
//        }
//    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ImageView imvSelected = view.findViewById(R.id.imgSelected);

        boolean wasSelected = isItemSelected.get(position);

        if (wasSelected){
            isItemSelected.set(position, false);
            imvSelected.setVisibility(View.INVISIBLE);

        }else {
            isItemSelected.set(position, true);
            imvSelected.setVisibility(View.VISIBLE);

        }
    }

    public interface OnSelectItemsListener{
        void onSelectItems();
    }
}
