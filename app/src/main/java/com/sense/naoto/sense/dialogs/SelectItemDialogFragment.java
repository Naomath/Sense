package com.sense.naoto.sense.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.user_page.GridItemAdapter;
import com.sense.naoto.sense.widgets.ExpandableHeightGridView;

import java.util.List;

import lombok.Setter;

public class SelectItemDialogFragment extends DialogFragment implements AdapterView.OnItemClickListener{


    @Setter
    private Activity activity;

    public SelectItemDialogFragment() {}

    public static SelectItemDialogFragment newInstance(String param1, String param2) {
        SelectItemDialogFragment fragment = new SelectItemDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(activity);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        // フルスクリーン
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.fragment_select_item_dialog);
        // 背景を透明にする
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ExpandableHeightGridView gridView = dialog.findViewById(R.id.grid_view);

        List<FashionItem> itemList = SavedDataHelper.getMyItemsOrderedByNew(getContext());

        LayoutInflater inflater =(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        GridItemAdapter adapter = new GridItemAdapter(inflater, R.layout.grid_items, itemList, getActivity());
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        return dialog;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //todo
    }
}
