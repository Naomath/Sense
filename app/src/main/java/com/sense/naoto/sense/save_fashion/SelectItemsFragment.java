package com.sense.naoto.sense.save_fashion;

import android.support.v4.app.Fragment;
import android.os.Bundle;
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
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.widgets.GridItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectItemsFragment extends Fragment implements AdapterView.OnItemClickListener, GridItemAdapter.CheckIsSelectedListener {


    //View
    private View mView;
    private List<ImageView> imvTypeSelected = new ArrayList<>();
    private TextView txvNoItem;
    private GridItemAdapter mGridItemAdapter;


    //Listeners
    OnSelectItemsListener selectItemsListener;
    OnBackFragmentListener backFragmentListener;

    //List
    private List<FashionItem> allItemList = new ArrayList<>();
    private HashMap<String, FashionItem> allItemMap = new HashMap<>();
    private List<FashionItem> selectedItems = new ArrayList<>();
    private HashMap<String, Boolean> areItemSelected = new HashMap<>();



    //変数
    private int flag = 0;
    //０がキャンセルの場合
    //１が完了をした時

    private int mode =0;
    //0がall１がtopsってやつ

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

        imvTypeSelected.get(0).setVisibility(View.VISIBLE);

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

        if (allItemList.size() > 0) {
            GridView gridView = mView.findViewById(R.id.grid_view);

            mGridItemAdapter = new GridItemAdapter(getLayoutInflater(), R.layout.fashion_item_grid,
                    allItemList, getActivity(), this);
            gridView.setAdapter(mGridItemAdapter);
            gridView.setOnItemClickListener(this);


            txvNoItem.setVisibility(View.GONE);
        } else {
            typeFrame.setVisibility(View.GONE);

            txvNoItem.setVisibility(View.VISIBLE);
        }
    }

    private void completeSelect() {
        setSelectedItems();
        selectItemsListener.onSelectItems(selectedItems);
    }


    private void setLists() {
        allItemMap.clear();
        allItemList.clear();
        selectedItems.clear();
        areItemSelected.clear();

        allItemList = SavedDataHelper.getMyItemsOrderedByNew(getContext());

        for (int i = 0; i < allItemList.size(); i++) {
            FashionItem item = allItemList.get(i);

            areItemSelected.put(item.getPrefKey(), false);
            allItemMap.put(item.getPrefKey(), item);
        }
    }

    private void setSelectedItems() {
        for (Map.Entry<String, Boolean> entry : areItemSelected.entrySet()) {
            if (entry.getValue()){
                //つまり登録されているのなら
                final String key = entry.getKey();

                selectedItems.add(allItemMap.get(key));
            }
        }
    }

    private void selectType(int i){
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
        ImageView imvSelected = view.findViewById(R.id.imgSelected);
        TextView txvPrefKey = view.findViewById(R.id.prefKey);

        String prefKey = txvPrefKey.getText().toString();

        boolean wasSelected = areItemSelected.get(prefKey);

        if (wasSelected) {
            areItemSelected.put(prefKey, false);
            imvSelected.setVisibility(View.INVISIBLE);

        } else {
            areItemSelected.put(prefKey, true);
            imvSelected.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public boolean onCheckSelected(String prefKey) {
        return areItemSelected.get(prefKey);
    }

    public interface OnSelectItemsListener {
        void onSelectItems(List<FashionItem> selectedItems);
    }
}
