package com.sense.naoto.sense.user_page;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;


public class AllFashionsFragment extends Fragment implements AdapterView.OnItemClickListener {

    //Views
    private View mView;
    private LayoutInflater mInflater;
    private GridView mGridView;
    private GridFashionAdapter mGridAdapter;
    private TextView txvNoFashion;

    private ImageButton mBtnAll;
    private ImageView mImvAllSelected;

    private ImageButton mBtnFav;
    private ImageView mImvFavSelected;


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

        mode = 0;

        setViews();
        return mView;
    }


    private void setViews() {
        mGridView = mView.findViewById(R.id.grid_view);
        mList = getAllFashions();
        FASHION_LIST_SIZE = mList.size();

        txvNoFashion = mView.findViewById(R.id.txv_no_fashion);

        mBtnAll = mView.findViewById(R.id.btn_all);
        mBtnAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode != 0) {
                    mode = 0;

                    selectAll();
                }
            }
        });
        mImvAllSelected = mView.findViewById(R.id.imv_all_selected);

        mBtnFav = mView.findViewById(R.id.btn_fav);
        mBtnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mode != 1) {
                    mode = 1;
                    selectFav();
                }
            }
        });
        mImvFavSelected = mView.findViewById(R.id.imv_fav_selected);


        if (FASHION_LIST_SIZE > 0) {

            mGridAdapter = new GridFashionAdapter(mInflater, R.layout.fashion_grid, mList, getActivity());
            mGridView.setAdapter(mGridAdapter);
            mGridView.setOnItemClickListener(this);

            txvNoFashion.setVisibility(View.GONE);

            selectAllFirst();

        } else {
            txvNoFashion.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.GONE);
            mBtnFav.setVisibility(View.GONE);
            mBtnAll.setVisibility(View.GONE);

            //ここらへんはボタンの構成する要素のGONE
            mView.findViewById(R.id.txv_all).setVisibility(View.GONE);
            mView.findViewById(R.id.imv_fav).setVisibility(View.GONE);
            mView.findViewById(R.id.txv_fav).setVisibility(View.GONE);
        }


    }

    private void selectAllFirst() {
        //最初にallを選択する時の、
        mImvAllSelected.setVisibility(View.VISIBLE);
        mImvFavSelected.setVisibility(View.GONE);
    }

    private void selectAll() {
        //Allが選択された時の処理
        mImvAllSelected.setVisibility(View.VISIBLE);
        mImvFavSelected.setVisibility(View.GONE);

        mGridAdapter.changeToAll();
        txvNoFashion.setVisibility(View.GONE);
    }

    private void selectFav() {
        //Favが選択された時の処理
        mImvAllSelected.setVisibility(View.GONE);
        mImvFavSelected.setVisibility(View.VISIBLE);

        int number = mGridAdapter.changeToFav(getFavFashions());
        if (number ==0){
            //何も入っていなかった時
            txvNoFashion.setText(R.string.no_fav_item);
            txvNoFashion.setVisibility(View.VISIBLE);
        }else {
            txvNoFashion.setVisibility(View.GONE);
        }
    }


    private List<Fashion> getAllFashions() {
        return SavedDataHelper.getMyFashionsOrderedByNew(getContext());
    }

    private List<Fashion> getFavFashions() {
        List<Fashion> favs = new ArrayList<>();

        for (Fashion item : getAllFashions()) {
            if (item.isFav()) {
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
