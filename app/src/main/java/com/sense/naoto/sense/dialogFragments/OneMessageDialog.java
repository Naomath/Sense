package com.sense.naoto.sense.dialogFragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.random_item.ShowRandomItemDialog;

public class OneMessageDialog extends DialogFragment {

    //View
    private Dialog mDialog;

    //定数
    private static final String KEY_TITLE = "TITLE";
    private static final String KEY_MESSAGE = "MESSAGE";

    public OneMessageDialog() {
    }

    public static OneMessageDialog newInstance(String title, String message) {
        OneMessageDialog instance = new OneMessageDialog();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TITLE, title);
        bundle.putString(KEY_MESSAGE, message);
        instance.setArguments(bundle);
        return instance;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Dialog dialog = getDialog();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int dialogWidth = (int) (metrics.widthPixels * 0.9);
        int dialogHeight = (int) (metrics.heightPixels * 0.3);

        lp.width = dialogWidth;
        lp.height = dialogHeight;
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDialog = new Dialog(getActivity());
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        mDialog.setContentView(R.layout.fragment_one_message_dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setViews();
        return mDialog;
    }

    private void setViews(){
        String title;
        String message;

        if (getArguments() != null) {
            title = getArguments().getString(KEY_TITLE);
            message = getArguments().getString(KEY_MESSAGE);
        } else {
            title ="TITLE";
            message ="NO MESSAGE";
        }

        TextView txvTitle = mDialog.findViewById(R.id.txv_title);
        txvTitle.setText(title);

        TextView txvMessage = mDialog.findViewById(R.id.txv_message);
        txvMessage.setText(message);

        ImageButton btnClose = mDialog.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
