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


public class CheckFashionFragment extends Fragment {

    //View
    private View mView;

    //Listeners
    private OnCheckFashionFragmentListener checkListener;
    private OnBackFragmentListener backFragmentListener;

    public CheckFashionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_check_fashion, container, false);
        setViews();
        return mView;
    }

    public void setListeners(OnCheckFashionFragmentListener checkListener, OnBackFragmentListener backFragmentListener) {
        this.checkListener = checkListener;
        this.backFragmentListener = backFragmentListener;
    }

    private void setViews() {
        Button btnSelectFashion = mView.findViewById(R.id.btn_complete);
        btnSelectFashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListener.onCheckFashion();
            }
        });

        Button btnSelectItem = mView.findViewById(R.id.btn_back);
        btnSelectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFragmentListener.onBack();
            }
        });
    }

    public interface OnCheckFashionFragmentListener {
        void onCheckFashion();
    }
}
