package com.sense.naoto.sense.random_item;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.MainActivityHelper;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.SavedDataHelper;

public class RandomItemFragment extends Fragment implements ShowRandomItemDialog.OnShowRandomItemDialogListener {


    //View
    private View mView;
    private final Fragment fragment = this;

    public RandomItemFragment() {
    }

    public static RandomItemFragment newInstance() {
        RandomItemFragment fragment = new RandomItemFragment();
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

        mView = inflater.inflate(R.layout.fragment_random_item, container, false);

        setViews();
        return mView;
    }

    private void setViews() {
        ImageButton btnRandom = mView.findViewById(R.id.btn_random);
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SavedDataHelper.isFashionItem(getContext())) {
                    //todo:itemがが一つでもある時
                    String prefKey = SavedDataHelper.getRandomFashionItemPrefKey(getContext());
                    ShowRandomItemDialog dialog = ShowRandomItemDialog.newInstance(fragment, prefKey);
                    dialog.show(getFragmentManager(), "show");

                }
            }
        });


    }

    @Override
    public void onShowFashionsButtonClicked(FashionItem item) {
        String prefKey = item.getPrefKey();
        MainActivityHelper.launchItemTagFashionsActivity(getActivity(), prefKey, 0);
    }
}
