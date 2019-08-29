package com.sense.naoto.sense.save_fashion;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.SelectItemsActivityHelper;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.user_page.GridItemAdapter;
import com.sense.naoto.sense.widgets.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.List;

public class SelectItemsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<FashionItem> itemList = new ArrayList<>();

    private List<FashionItem> selectedItems = new ArrayList<>();

    private List<Boolean> isItemSelected = new ArrayList<>();

    private Activity activity = this;

    private int flag = 0;
    //０がキャンセルの場合
    //１が完了をした時

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_items);

        setLists();
        setViews();
    }

    private void setLists() {
        itemList = SavedDataHelper.getMyItemsOrderedByNew(this);

        for (int i = 0; i < itemList.size(); i++) {
            isItemSelected.add(false);
        }
    }

    private void setViews() {
        Button btnComplete = findViewById(R.id.btn_complete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSelectedItems();
                if (selectedItems.size() == 0){
                    //つまり何も選択されていない状態
                    activity.finish();
                }else {
                    flag = 1;
                    activity.finish();
                }
            }
        });

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ExpandableHeightGridView gridView = findViewById(R.id.grid_view);

        GridItemAdapter adapter = new GridItemAdapter(getLayoutInflater(), R.layout.grid_items, itemList, this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    private void setSelectedItems(){
        for (int i = 0;i<isItemSelected.size();i++){
            if (isItemSelected.get(i)){
                selectedItems.add(itemList.get(i));
            }
        }
    }


    @Override
    public void finish() {
        super.finish();
        switch (flag){
            case 0:
                SelectItemsActivityHelper.launchSetUpFashionActivityForCancel(activity);
                break;
            case 1:
                SelectItemsActivityHelper.launchSetUpFashionActivityForPickItems(activity,selectedItems);
        }
    }

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
}
