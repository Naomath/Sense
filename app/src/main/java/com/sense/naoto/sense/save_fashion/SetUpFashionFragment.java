package com.sense.naoto.sense.save_fashion;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.interfaces.SetUpFashionFmListener;
import com.sense.naoto.sense.processings.ButtonHelper;
import com.sense.naoto.sense.processings.CalendarHelper;
import com.sense.naoto.sense.processings.ImageHelper;
import com.sense.naoto.sense.processings.SavedDataHelper;
import com.sense.naoto.sense.user_page.ItemRecycleAdapter;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class SetUpFashionFragment extends Fragment implements TextWatcher {

    //定数
    public static int PICK_IMAGE_REQUEST = 1;
    public static final int NEW_FASHION = 0;
    public static final int NEW_ITEM = 1;


    //Views
    private View mView;
    private ImageView mImageView;
    private ProgressBar mCircleProgress;
    private Button mBtnPost;
    private ItemRecycleAdapter itemRecycleAdapter;


    //listener
    private SetUpFashionFmListener mListener;


    //変数
    private Bitmap mBitmap;
    private Uri mUri;
    private boolean isSelected = false;
    private int mode;
    private List<FashionItem> itemList;


    public SetUpFashionFragment() {
    }

    public static SetUpFashionFragment newInstance(String param1, String param2) {
        SetUpFashionFragment fragment = new SetUpFashionFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity() instanceof SetUpFashionFmListener)
            mListener = (SetUpFashionFmListener) getActivity();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {

            isSelected = true;

            try {
                Uri uriOfContent = data.getData();
                mUri = uriOfContent;
                // convertContentToFile(uriOfContent);

                InputStream in = getActivity().getContentResolver().openInputStream(uriOfContent);
                ExifInterface exifInterface = new ExifInterface(in);

                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
                Bitmap bmRotated = ImageHelper.rotateBitmap(bitmap, orientation);
                Bitmap resizedBitmap = ImageHelper.resizeBitmap(1000, bmRotated);

                mBitmap = resizedBitmap;
                mImageView.setImageBitmap(resizedBitmap);

            } catch (IOException e) {
                //todo:ここのエラー処理
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_set_up_fashion, container, false);

        mode = NEW_FASHION;

        setToolBarViews();

        setNewFashionViews();
        return mView;
    }


    private void setToolBarViews() {
        final ImageButton btnBack = mView.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) mListener.onLaunchMainActivity();
            }
        });

        mCircleProgress = mView.findViewById(R.id.circle_progress);

        mBtnPost = mView.findViewById(R.id.btn_post);
        ButtonHelper.unEnableButton(mBtnPost, getContext());


        Spinner spinner = mView.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        setNewFashionViews();

                        mode = NEW_FASHION;

                        break;
                    case 1:
                        setNewItemViews();

                        mode = NEW_ITEM;

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setNewFashionViews() {

        itemList = new ArrayList<>();

        FrameLayout frame = mView.findViewById(R.id.frame);
        frame.removeAllViews();

        getLayoutInflater().inflate(R.layout.save_new_fashion, frame);

        final Button btnSelect = frame.findViewById(R.id.btn_select_photo);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        mImageView = frame.findViewById(R.id.image_fashion);
        mImageView.setImageResource(R.drawable.no_fashion_selected);


        mBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBtnPost.setVisibility(View.GONE);
                mCircleProgress.setVisibility(View.VISIBLE);

                save("");
            }
        });

        ImageButton btnAddItem = frame.findViewById(R.id.btn_add_item);
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<FashionItem> items = SavedDataHelper.getMyItemsOrderedByNew(getContext());

                insertToRecyclerView(items.get(0));
            }
        });

        ButtonHelper.unEnableButton(mBtnPost, getContext());

        final RecyclerView recyclerView = mView.findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        itemRecycleAdapter = new ItemRecycleAdapter(null, getActivity());
        recyclerView.setAdapter(itemRecycleAdapter);

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                // 横にスワイプされたら要素を消す
                int swipedPosition = viewHolder.getAdapterPosition();
                ItemRecycleAdapter adapter = (ItemRecycleAdapter) recyclerView.getAdapter();
                adapter.removeItem(swipedPosition);
            }
        };

        (new ItemTouchHelper(callback)).attachToRecyclerView(recyclerView);

    }

    private void setNewItemViews() {

        FrameLayout frame = mView.findViewById(R.id.frame);
        frame.removeAllViews();

        getLayoutInflater().inflate(R.layout.save_new_item, frame);

        final Button btnSelect = frame.findViewById(R.id.btn_select_photo);
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        mImageView = frame.findViewById(R.id.image_fashion);
        mImageView.setImageResource(R.drawable.no_fashion_selected);

        final EditText editText = frame.findViewById(R.id.edit_title);
        editText.addTextChangedListener(this);


        mBtnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = editText.getText().toString();
                mBtnPost.setVisibility(View.GONE);
                mCircleProgress.setVisibility(View.VISIBLE);

                save(title);
            }
        });

        ButtonHelper.unEnableButton(mBtnPost, getContext());

    }


    private void save(String title) {

        String strDate = CalendarHelper.getNowDate();

        String prefKey = Fashion.newPreferenceKey();

        switch (mode) {
            case NEW_FASHION:

                Fashion fashion = new Fashion(strDate, mUri.toString(), prefKey);
                SavedDataHelper.saveNewFashion(getContext(), fashion);

                mListener.onLaunchMainActivity();
                break;

            case NEW_ITEM:

                FashionItem item = new FashionItem(mUri.toString(), title, strDate, prefKey);

                Bitmap resizedBmp = ImageHelper.resizeBitmap(200, mBitmap);

                SavedDataHelper.saveNewItem(getContext(), item, resizedBmp);

                mListener.onLaunchMainActivity();
                break;

        }

    }

    private void insertToRecyclerView(FashionItem item) {
        if (itemList != null) {
            int index = itemList.indexOf(item);
            if (-1 == index) {
                itemRecycleAdapter.addItem(item);
            }
        }
    }
    //textwatcherの継承したメソッド

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String iputedText = s.toString();

        if (iputedText.length() > 0 && isSelected) {
            ButtonHelper.enableButton(mBtnPost, getContext());
        } else {
            ButtonHelper.unEnableButton(mBtnPost, getContext());
        }
    }
}
