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


public class NameItemFragment extends Fragment {

    //View
    private View mView;

    //Listeners
    private OnNameItemFragmentListener nameListener;
    private OnBackFragmentListener backFragmentListener;

    public NameItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_name_item, container, false);
        setViews();
        return mView;
    }

    private void setViews(){
        Button btnComplete = mView.findViewById(R.id.btn_complete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameListener.onNameItem();
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

    public void setListeners(OnNameItemFragmentListener nameListener, OnBackFragmentListener backFragmentListener){
        this.nameListener = nameListener;
        this.backFragmentListener = backFragmentListener;
    }

    public interface OnNameItemFragmentListener{
        void onNameItem();
    }

}
