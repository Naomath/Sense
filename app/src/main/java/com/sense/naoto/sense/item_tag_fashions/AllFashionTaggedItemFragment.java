package com.sense.naoto.sense.item_tag_fashions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.widgets.GridFashionAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Setter;


public class AllFashionTaggedItemFragment extends Fragment  implements AdapterView.OnItemClickListener{

    //Views
    private View mView;
    private LayoutInflater inflater;

    //Listeners
    @Setter
    private OnItemTagFashionListener listener;

    //変数
    @Setter
    private FashionItem mItem;
    private List<Fashion> list;



    public AllFashionTaggedItemFragment() {
        // Required empty public constructor
    }

    public static AllFashionTaggedItemFragment newInstance() {
        AllFashionTaggedItemFragment fragment = new AllFashionTaggedItemFragment();
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
        this.inflater = inflater;

        mView = inflater.inflate(R.layout.fragment_all_fashion_taggeditem, container, false);

        setViews();
        return mView;

    }

    private void setViews(){
        ImageButton btnBack = mView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBackActivity();
            }
        });

        TextView txvDate = mView.findViewById(R.id.txv_date);
        txvDate.setText(mItem.getStrDate());

        TextView txvName = mView.findViewById(R.id.txv_item_name);
        txvName.setText(mItem.getName());

        List<String> fashionKeys = mItem.getUsedFashionPrefKeys();
        Collections.reverse(fashionKeys);
        list = SavedDataHelper.getFashionsByPrefKeyList(getContext(), fashionKeys);

        if (list.size()>0){
            GridView gridView = mView.findViewById(R.id.grid_view);
            GridFashionAdapter adapter = new GridFashionAdapter(inflater, R.layout.grid_items, list, getActivity());
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(this);

            TextView txvNoFashion = mView.findViewById(R.id.txv_no_fashion);
            txvNoFashion.setVisibility(View.GONE);
        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        listener.onSelectFashion(position);
    }
}
