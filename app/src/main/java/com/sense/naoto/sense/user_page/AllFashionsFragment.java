package com.sense.naoto.sense.user_page;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.MainActivityHelper;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.widgets.FashionIsFavAdapter;
import com.sense.naoto.sense.widgets.GridFashionAdapter;
import com.sense.naoto.sense.widgets.ItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;


public class AllFashionsFragment extends Fragment implements AdapterView.OnItemClickListener {

    //Views
    private View mView;
    private LayoutInflater mInflater;
    private GridView mGridView;
    private GridFashionAdapter mGridAdapter;

    //変数
    private int FASHION_LIST_SIZE = 0;
    private int mode = 0;
    //０がall
    //１がfav
    private List<Fashion> mList = new ArrayList<>();

    public AllFashionsFragment() {
        // Required empty public constructor
    }

    public static AllFashionsFragment newInstance(String param1, String param2) {
        AllFashionsFragment fragment = new AllFashionsFragment();
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
        mView = inflater.inflate(R.layout.fragment_all_fashions, container, false);
        mInflater = inflater;

        setViews();
        return mView;
    }


    private void setViews() {
        mGridView = mView.findViewById(R.id.grid_view);
        mList = getAllFashions();
        FASHION_LIST_SIZE = mList.size();

        if(FASHION_LIST_SIZE > 0) {

            mGridAdapter = new GridFashionAdapter(mInflater, R.layout.fashion_grid, mList, getActivity());
            mGridView.setAdapter(mGridAdapter);
            mGridView.setOnItemClickListener(this);

            TextView txvNoFashion = mView.findViewById(R.id.txv_no_fashion);
            txvNoFashion.setVisibility(View.GONE);

        }

        Spinner spinner = mView.findViewById(R.id.spinner);
        FashionIsFavAdapter spinnerAdapter = new FashionIsFavAdapter(getContext(), R.layout.spinner_item_fashion_type);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                if (mode != position){
//                    //つまり今のと違うのが選択された場合
//                    mode = position;
//
//                    switch (mode){
//                        case 0:
//                            mList.clear();
//                            mList = getAllFashions();
//                            refreshGridView();
//                            break;
//                        case 1:
//                            mList.clear();
//                            mList = getFavFashions();
//                            refreshGridView();
//                            break;
//                    }
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void refreshGridView(){
        mGridAdapter.refresh(mList);
    }

    private List<Fashion> getAllFashions() {
        return SavedDataHelper.getMyFashionsOrderedByNew(getContext());
    }

    private List<Fashion> getFavFashions(){
        List<Fashion> favs = new ArrayList<>();

        for (Fashion item:getAllFashions()){
            if (item.isFav()){
                favs.add(item);
            }
        }
        return favs;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int i = FASHION_LIST_SIZE - 1 - position;
        //ここのnumberは何番目に作られたかなので、positionと1をsizeから引いてうまくする

        MainActivityHelper.launchShowingMyFashionActivity(getActivity(), i);
    }
}
