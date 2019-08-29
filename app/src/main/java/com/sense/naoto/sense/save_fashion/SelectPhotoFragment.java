package com.sense.naoto.sense.save_fashion;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.sense.naoto.sense.R;

public class SelectPhotoFragment extends Fragment {

    //View
    private View mView;

    //Listener
    private OnSelectPhotoFragmentListener selectListener;
    private OnBackFragmentListener backListener;

    public SelectPhotoFragment() {
        // Required empty public constructor
    }

    public static SelectPhotoFragment newInstance() {
        SelectPhotoFragment fragment = new SelectPhotoFragment();
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
        mView = inflater.inflate(R.layout.fragment_select_photo, container, false);
        setViews();

        //todo:イメージを洗濯していないと次に行けないようにする処理
        //todo:imageviewに画像をセット

        return mView;
    }

    public void setListeners(OnSelectPhotoFragmentListener selectListener,
                             OnBackFragmentListener backListener){
        this.selectListener = selectListener;
        this.backListener = backListener;
    }

    private void setViews(){
        ImageButton btnComplete = mView.findViewById(R.id.btn_complete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectListener.onSelectPhoto();
            }
        });

        ImageButton btnBack = mView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backListener.onBack();
            }
        });
    }

    public interface OnSelectPhotoFragmentListener{
        void onSelectPhoto();
    }


}
