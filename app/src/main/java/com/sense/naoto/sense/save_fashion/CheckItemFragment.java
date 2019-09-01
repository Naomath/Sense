package com.sense.naoto.sense.save_fashion;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;

import lombok.Setter;


public class CheckItemFragment extends Fragment {

    //View
    private View mView;

    //Listeners
    private OnCheckItemFragmentListener itemListener;
    private OnBackFragmentListener backListener;

    //変数
    @Setter
    private Bitmap image;
    @Setter
    private String name;
    @Setter
    private int itemType;

    public CheckItemFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_check_item, container, false);
        setViews();
        return mView;
    }

    public void setListeners(OnCheckItemFragmentListener itemListener, OnBackFragmentListener backListener) {
        this.itemListener = itemListener;
        this.backListener = backListener;
    }

    private void setViews() {

        ImageButton btnSelectFashion = mView.findViewById(R.id.btn_complete);
        btnSelectFashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.onCheckItem();
            }
        });

        ImageButton btnSelectItem = mView.findViewById(R.id.btn_back);
        btnSelectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backListener.onBack();
            }
        });

        ImageView imageView = mView.findViewById(R.id.imageView);
        imageView.setImageBitmap(image);

        TextView txvName = mView.findViewById(R.id.txv_item_name);
        txvName.setText(name);

        ImageView imvItemType = mView.findViewById(R.id.imv_item_type);
        imvItemType.setImageResource(FashionItem.getTypeResID(itemType));

        TextView txvTypeName = mView.findViewById(R.id.txv_item_type);
        txvTypeName.setText(FashionItem.getTypeName(itemType));
    }


    public interface OnCheckItemFragmentListener {
        void onCheckItem();
    }
}
