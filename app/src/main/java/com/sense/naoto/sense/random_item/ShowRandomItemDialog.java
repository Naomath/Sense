package com.sense.naoto.sense.random_item;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.GetImageTask;
import com.sense.naoto.sense.processings.SavedDataHelper;


public class ShowRandomItemDialog extends DialogFragment {

    //定数
    public static final String ITEM_KEY = "ITEMKEY";

    //変数
    private OnShowRandomItemDialogListener mListener;
    private FashionItem mFashionItem;
    private GetImageTask imageTask;
    private boolean isDoGetImage = false;

    //View
    private Dialog mDialog;
    private ImageView mImageView;
    private ProgressBar mProgressBar;


    public static ShowRandomItemDialog newInstance(Fragment fragment, String prefKey) {
        ShowRandomItemDialog instance = new ShowRandomItemDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ITEM_KEY, prefKey);
        instance.setTargetFragment(fragment, 0);
        instance.setArguments(bundle);
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
    public void onDestroy() {
        imageTask.setListener(null);
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Dialog dialog = getDialog();

        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int dialogWidth = (int) (metrics.widthPixels * 0.9);
        int dialogHeight = (int) (metrics.heightPixels * 0.9);

        lp.width = dialogWidth;
        lp.height = dialogHeight;
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String prefKey = getArguments().getString(ITEM_KEY);
        mFashionItem = SavedDataHelper.getItemByPrefKey(getContext(), prefKey);

        mDialog = new Dialog(getActivity());
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        mDialog.setContentView(R.layout.fragment_show_random_item_dialog);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        setViews();
        return mDialog;
    }

    private void setViews(){
        ImageButton btnClose = mDialog.findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ImageButton btnExamples = mDialog.findViewById(R.id.btn_example);
        btnExamples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onShowFashionsButtonClicked(mFashionItem);
                dismiss();
            }
        });

        mProgressBar = mDialog.findViewById(R.id.progressBar);

        TextView txvItemName = mDialog.findViewById(R.id.txv_item_name);
        txvItemName.setText(mFashionItem.getName());

        mImageView = mDialog.findViewById(R.id.imv_item);
        ViewTreeObserver observer = mImageView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isDoGetImage) {
                    isDoGetImage = true;

                    imageTask = new GetImageTask();
                    imageTask.setListener(createListener());
                    imageTask.setActivity(getActivity());

                    GetImageTask.Param param = new GetImageTask.Param(mImageView.getWidth(),
                            mImageView.getHeight(), Uri.parse(mFashionItem.getLocalDeviceUri()));

                    imageTask.execute(param);

                }
            }
        });


    }

    private GetImageTask.GetImageFromDeviceListener createListener() {
        return new GetImageTask.GetImageFromDeviceListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                mProgressBar.setVisibility(View.GONE);
                mImageView.setImageBitmap(bitmap);
            }
        };
    }


    public interface OnShowRandomItemDialogListener {
        void onShowFashionsButtonClicked(FashionItem item);
    }


}
