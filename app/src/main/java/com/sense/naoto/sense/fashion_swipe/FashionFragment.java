package com.sense.naoto.sense.fashion_swipe;

import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.MainActivityHelper;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.GetImageFromDeviceTask;
import com.sense.naoto.sense.processings.ImageHelper;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.widgets.ItemListAdapter;
import com.squareup.picasso.Picasso;
import com.varunest.sparkbutton.SparkButton;
import com.varunest.sparkbutton.SparkEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FashionFragment extends Fragment {
    /*
    このFragmentはファッションの画像とその他の情報を表示するFragment
     */

    //定数
    private static final String FASHION_ARGS_KEY = "fashion";
    private static final String REQUEST_ARGS_KEY =  "REQUEST";
    private static final int REQUEST_MINE = 0;
    private static final int REQUEST_FASHIONS_TAGGED_ITEM = 1;

    //変数
    private Fashion mFashion;
    private GetImageFromDeviceTask imagetask;
    private boolean isInitialized = false;
    private int mRequest;
    private boolean isDoGetImage = false;


    //Views
    private View mView;
    private ImageView mFashionImage;
    private ProgressBar mProgressBar;


    public FashionFragment() {
        // Required empty public constructor
    }

    public static FashionFragment newInstance(Fashion fashion, int request) {
        FashionFragment fragment = new FashionFragment();
        Bundle args = new Bundle();
        args.putParcelable(FASHION_ARGS_KEY, fashion);
        args.putInt(REQUEST_ARGS_KEY, request);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mFashion = getArguments().getParcelable(FASHION_ARGS_KEY);
            mRequest = getArguments().getInt(REQUEST_ARGS_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_fashion, container, false);

        setViews();
        setBottomSheetViews();
        return mView;
    }


    @Override
    public void onDestroy() {
        imagetask.setListener(null);
        super.onDestroy();
    }

    public void setViews() {
        mFashionImage = mView.findViewById(R.id.fashionImageView);

        ViewTreeObserver observer = mFashionImage.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isDoGetImage){
                    isDoGetImage = true;

                    imagetask = new GetImageFromDeviceTask();
                    imagetask.setListener(createListener());
                    imagetask.setActivity(getActivity());

                    GetImageFromDeviceTask.Param param = new GetImageFromDeviceTask.Param(mFashionImage.getWidth(),
                            mFashionImage.getHeight(),Uri.parse(mFashion.getLocalDeviceUri()));

                    imagetask.execute(param);

                }
            }
        });

        SparkButton favButton = mView.findViewById(R.id.fav_button);
        favButton.setEventListener(new SparkEventListener() {
            @Override
            public void onEvent(ImageView button, boolean buttonState) {
                if (!isInitialized) {
                    SavedDataHelper.changeFavState(getContext(), mFashion);
                }
            }

            @Override
            public void onEventAnimationEnd(ImageView button, boolean buttonState) {
            }

            @Override
            public void onEventAnimationStart(ImageView button, boolean buttonState) {
            }
        });

        if (mFashion.isFav()){
            isInitialized = true;
            favButton.callOnClick();
            isInitialized = false;
        }



        mProgressBar = mView.findViewById(R.id.progressBar);
    }

    public void setBottomSheetViews(){
        View bottomSheet = mView.findViewById(R.id.description_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {

            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

        TextView txvDate = bottomSheet.findViewById(R.id.txv_date);
        txvDate.setText(mFashion.getStrDate());

        List<FashionItem> items = SavedDataHelper.getItemsByPrefKeyList(getContext(), mFashion.getItemPrefKey());

        if (items.size()>0){
            final ListView itemListView = bottomSheet.findViewById(R.id.listView);
            ItemListAdapter adapter = new ItemListAdapter(getContext(), R.layout.item_list_row,items);
            itemListView.setAdapter(adapter);

            TextView txvNoItems = bottomSheet.findViewById(R.id.txv_no_items);
            txvNoItems.setVisibility(View.GONE);

            if (mRequest == REQUEST_MINE){
                //この時だけ、アイテムをクリックして飛べるようにする
                itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        FashionItem item = (FashionItem) itemListView.getItemAtPosition(position);
                        MainActivityHelper.launchItemTagFashionsActivity(getActivity(), item.getPrefKey(), 0);
                    }
                });
            }
        }


    }


    private GetImageFromDeviceTask.GetImageFromDeviceListener createListener() {
        return new GetImageFromDeviceTask.GetImageFromDeviceListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {
                mProgressBar.setVisibility(View.GONE);
                mFashionImage.setImageBitmap(bitmap);
            }
        };
    }


}
