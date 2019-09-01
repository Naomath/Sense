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
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.widgets.GridFashionAdapter;

import java.util.List;


public class AllFashionsFragment extends Fragment implements AdapterView.OnItemClickListener{


    private View mView;

    private LayoutInflater mInflater;

    private int FASHION_LIST_SIZE = 0;

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

    private void setViews(){
        GridView gridView = mView.findViewById(R.id.grid_view);

        List<Fashion> fahsionList = SavedDataHelper.getMyFashionsOrderedByNew(getContext());
        FASHION_LIST_SIZE = fahsionList.size();

        if (FASHION_LIST_SIZE >0){
            GridFashionAdapter adapter = new GridFashionAdapter(mInflater, R.layout.fashion_grid, fahsionList, getActivity());
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(this);

            TextView txvNoFashion = mView.findViewById(R.id.txv_no_fashion);
            txvNoFashion.setVisibility(View.GONE);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        int i = FASHION_LIST_SIZE - 1 -position;
        //ここのnumberは何番目に作られたかなので、positionと1をsizeから引いてうまくする

        MainActivityHelper.launchShowingMyFashionActivity(getActivity(), i);
    }
}
