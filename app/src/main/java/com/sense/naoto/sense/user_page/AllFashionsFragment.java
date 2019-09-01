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
import com.sense.naoto.sense.widgets.GridFashionAdapter;

import java.util.ArrayList;
import java.util.List;


public class AllFashionsFragment extends Fragment implements AdapterView.OnItemClickListener {

    //Views
    private View mView;
    private LayoutInflater mInflater;
    private GridView mGridView;

    //変数
    private int FASHION_LIST_SIZE = 0;
    private int mode = 0;
    //０がall
    //１がfav

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
        List<Fashion> allFashions = getAllFashions();
        FASHION_LIST_SIZE = allFashions.size();


        final GridFashionAdapter adapter = new GridFashionAdapter(mInflater, R.layout.fashion_grid, allFashions, getActivity());
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(this);

        TextView txvNoFashion = mView.findViewById(R.id.txv_no_fashion);
        txvNoFashion.setVisibility(View.GONE);


    }

    private List<Fashion> getAllFashions() {
        return SavedDataHelper.getMyFashionsOrderedByNew(getContext());
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int i = FASHION_LIST_SIZE - 1 - position;
        //ここのnumberは何番目に作られたかなので、positionと1をsizeから引いてうまくする

        MainActivityHelper.launchShowingMyFashionActivity(getActivity(), i);
    }
}
