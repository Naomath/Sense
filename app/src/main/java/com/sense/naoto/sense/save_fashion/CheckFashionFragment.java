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
import android.widget.ListView;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.widgets.ItemListAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;


public class CheckFashionFragment extends Fragment {

    //View
    private View mView;

    //Listeners
    private OnCheckFashionFragmentListener checkListener;
    private OnBackFragmentListener backFragmentListener;

    //変数
    @Setter
    private Bitmap bitmap;
    @Setter
    private List<FashionItem> fashionItems = new ArrayList<>();

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
        ImageButton btnSelectFashion = mView.findViewById(R.id.btn_complete);
        btnSelectFashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkListener.onCheckFashion();
            }
        });

        ImageButton btnSelectItem = mView.findViewById(R.id.btn_back);
        btnSelectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backFragmentListener.onBack();
            }
        });

        ImageView fashionImageView = mView.findViewById(R.id.imageView);
        fashionImageView.setImageBitmap(bitmap);

        TextView txvNoItems = mView.findViewById(R.id.txv_no_items);

        if (fashionItems.size()>0){
            ListView listView = mView.findViewById(R.id.listView);
            ItemListAdapter adapter = new ItemListAdapter(getContext(), R.layout.item_list_row, fashionItems, getActivity());
            listView.setAdapter(adapter);

            txvNoItems.setVisibility(View.GONE);
        }
    }

    public interface OnCheckFashionFragmentListener {
        void onCheckFashion();
    }
}
