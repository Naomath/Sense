package com.sense.naoto.sense.random_item;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;


public class ShowRandomItemDialog extends DialogFragment {

    //変数
    private OnShowRandomItemDialogListener mListener;


    public ShowRandomItemDialog newInstanece(Fragment fragment) {
        ShowRandomItemDialog instance = new ShowRandomItemDialog();
        instance.setTargetFragment(fragment, 0);
        return instance;
    }

    public ShowRandomItemDialog() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Fragment targetFragment = this.getTargetFragment();
        try {
            mListener = (targetFragment != null) ? (OnShowRandomItemDialogListener) targetFragment
                    : (OnShowRandomItemDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Don't implement OnCustomDialogListener.");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        dialog.setContentView(R.layout.fragment_show_random_item_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }


    public interface OnShowRandomItemDialogListener {
        void onShowFashionsButtonClicked(FashionItem item);
    }


}
