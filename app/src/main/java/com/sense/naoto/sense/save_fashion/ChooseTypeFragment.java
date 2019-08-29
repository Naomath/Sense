package com.sense.naoto.sense.save_fashion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sense.naoto.sense.R;

import lombok.Setter;

public class ChooseTypeFragment extends Fragment {

    //View
    private View mView;


    //Listener
    @Setter
    private ChooseTypeFragmentListener listener;

    public ChooseTypeFragment() {
        // Required empty public constructor
    }

    public static ChooseTypeFragment newInstance() {
        ChooseTypeFragment fragment = new ChooseTypeFragment();
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

        mView = inflater.inflate(R.layout.fragment_choose_type, container, false);

        setViews();

        return mView;
    }

    private void setViews(){

        Button btnSelectFashion = mView.findViewById(R.id.btn_fashion);
        btnSelectFashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDecidedType(SaveFashionAndItemActivity.TYPE_FASHION);
            }
        });

        Button btnSelectItem = mView.findViewById(R.id.btn_item);
        btnSelectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDecidedType(SaveFashionAndItemActivity.TYPE_ITEM);
            }
        });
    }

    public interface ChooseTypeFragmentListener{
        void onDecidedType(int i);
    }

}
