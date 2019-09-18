package com.sense.naoto.sense.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.GetImageTask;

import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter extends ArrayAdapter<FashionItem> {

    //定数
    private int SIZE = 0;

    //変数
    private int mResource;
    private List<FashionItem> fashionItems = new ArrayList<>();
    private LayoutInflater mInflater;
    private Activity mActivity;

    //GetImageTask関連
    private List<Boolean> isDones = new ArrayList<>();
    private List<GetImageTask> tasks = new ArrayList<>();
    private int taskFlag = 0;

    private List<ProgressBar> progressBarList = new ArrayList<>();
    private List<ImageView> imageViewList = new ArrayList<>();

    public ItemListAdapter(Context context, int resource, List<FashionItem> items, Activity activity){
        super(context, resource, items);

        mActivity = activity;
        mResource = resource;
        fashionItems = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        SIZE = fashionItems.size();
        for (int i = 0; i < SIZE; i++) {
            isDones.add(false);
            tasks.add(new GetImageTask());
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        }
        else {
            view = mInflater.inflate(mResource, null);
        }

        final FashionItem item = fashionItems.get(position);

        final ImageView imageView = view.findViewById(R.id.imv_item);

        progressBarList.add((ProgressBar) view.findViewById(R.id.progressBar));
        imageViewList.add(imageView);

        TextView  txvName = view.findViewById(R.id.txv_item_name);
        txvName.setText(item.getName());

        ViewTreeObserver observer = imageView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isDones.get(position)) {
                    isDones.set(position, true);

                    final GetImageTask task = tasks.get(position);
                    task.setListener(createListener());

                    task.setActivity(mActivity);

                    GetImageTask.Param param = new GetImageTask.Param(imageView.getWidth(),
                            imageView.getHeight(), Uri.parse(item.getLocalDeviceUri()));

                    task.execute(param);

                }
            }
        });

        return view;
    }

    private GetImageTask.GetImageFromDeviceListener createListener() {
        return new GetImageTask.GetImageFromDeviceListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {

                progressBarList.get(taskFlag).setVisibility(View.GONE);
                imageViewList.get(taskFlag).setImageBitmap(bitmap);
                taskFlag ++;
            }
        };
    }
}
