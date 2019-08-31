package com.sense.naoto.sense.save_fashion;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.processings.ButtonHelper;

import lombok.Setter;


public class NameItemFragment extends Fragment implements TextWatcher {

    //View
    private View mView;
    private ImageButton mBtnComplete;

    //Listeners
    private OnNameItemFragmentListener nameListener;
    private OnBackFragmentListener backFragmentListener;


    //変数
    @Setter
    private Bitmap image;

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

    private void setViews() {
        ImageView imageView = mView.findViewById(R.id.imageView);
        imageView.setImageBitmap(image);

        final EditText editName = mView.findViewById(R.id.edit_name);
        editName.addTextChangedListener(this);

        mBtnComplete = mView.findViewById(R.id.btn_complete);
        mBtnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                completeNaming(editName.getText().toString());
            }
        });

        ButtonHelper.unEnableCheckButton(mBtnComplete, getContext());

        ImageButton btnBack = mView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFragmentListener.onBack();
            }
        });

    }

    public void setListeners(OnNameItemFragmentListener nameListener, OnBackFragmentListener backFragmentListener) {
        this.nameListener = nameListener;
        this.backFragmentListener = backFragmentListener;
    }

    private void completeNaming(String name){
        nameListener.onNameItem(name);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        if (s.toString().length()>0){
            ButtonHelper.enableCheckButton(mBtnComplete, getContext());
        }else {
            ButtonHelper.unEnableCheckButton(mBtnComplete, getContext());
        }
    }

    public interface OnNameItemFragmentListener {
        void onNameItem(String name);
    }

}
