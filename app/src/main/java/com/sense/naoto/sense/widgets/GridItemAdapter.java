package com.sense.naoto.sense.widgets;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.GetImageTask;

import java.util.ArrayList;
import java.util.List;

public class GridItemAdapter extends BaseAdapter {


    private class ViewHolder {
        ImageView imageView;
        ImageView imvSelected;
        ProgressBar progressBar;
    }

    //定数
    private int SIZE = 0;

    //変数
    private List<FashionItem> itemList = new ArrayList<>();
    private LayoutInflater inflater;
    private int layoutId;
    private Activity mActivity;

    //GetImageTask関連
    private List<Boolean> isDones = new ArrayList<>();
    private List<GetImageTask> tasks = new ArrayList<>();
    private int taskFlag = 0;

    private List<ProgressBar> progressBarList = new ArrayList<>();
    private List<ImageView> imageViewList = new ArrayList<>();


    public GridItemAdapter() {}

    public GridItemAdapter(LayoutInflater inflater, int layoutId, List<FashionItem> itemList, Activity activity) {
        super();
        this.inflater = inflater;
        this.layoutId = layoutId;
        this.itemList = itemList;
        mActivity = activity;
        SIZE = itemList.size();

        for (int i = 0; i < SIZE; i++) {
            isDones.add(false);
            tasks.add(new GetImageTask());
        }
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        final FashionItem item = itemList.get(i);

        if (view == null) {
            view = inflater.inflate(layoutId, viewGroup, false);

            holder = new GridItemAdapter.ViewHolder();
            holder.imageView = view.findViewById(R.id.image_view);
            holder.imvSelected = view.findViewById(R.id.imgSelected);
            holder.progressBar = view.findViewById(R.id.progressBar);

            progressBarList.add(holder.progressBar);
            imageViewList.add(holder.imageView);

            view.setTag(holder);

        } else {
            holder = (GridItemAdapter.ViewHolder) view.getTag();
        }

        ViewTreeObserver observer = holder.imageView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isDones.get(i)) {
                    isDones.set(i, true);

                    final GetImageTask task = tasks.get(i);
                    task.setListener(createListener());
                    task.setActivity(mActivity);

                    GetImageTask.Param param = new GetImageTask.Param(holder.imageView.getWidth(),
                            holder.imageView.getHeight(), Uri.parse(item.getLocalDeviceUri()));

                    task.execute(param);

                }
            }
        });


        return view;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
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
