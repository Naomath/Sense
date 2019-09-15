package com.sense.naoto.sense.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.processings.GetImageFromDeviceTask;
import com.sense.naoto.sense.processings.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class GridFashionAdapter extends BaseAdapter {

    private class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
        ImageView imvFav;
    }

    //定数
    private int SIZE = 0;

    //変数
    private List<Fashion> fashionList = new ArrayList<>();
    private LayoutInflater inflater;
    private int layoutId;
    private Activity mActivity;

    //GetImageTask関連
    private List<Boolean> isDones = new ArrayList<>();
    private List<GetImageFromDeviceTask> tasks = new ArrayList<>();
    private int taskFlag = 0;

    private List<ProgressBar> progressBarList = new ArrayList<>();
    private List<ImageView> imageViewList = new ArrayList<>();


    public GridFashionAdapter() {}

    public GridFashionAdapter(LayoutInflater inflater, int layoutId, List<Fashion> fashionList, Activity activity) {
        super();
        this.inflater = inflater;
        this.layoutId = layoutId;
        this.fashionList = fashionList;
        SIZE = fashionList.size();
        mActivity = activity;

        for (int i = 0; i < SIZE; i++) {
            isDones.add(false);
            tasks.add(new GetImageFromDeviceTask());
        }
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        final Fashion fashion = fashionList.get(i);

        if (view == null) {
            view = inflater.inflate(layoutId, viewGroup, false);

            holder = new ViewHolder();
            holder.progressBar = view.findViewById(R.id.progressBar);
            holder.imageView = view.findViewById(R.id.image_view);
            holder.imvFav = view.findViewById(R.id.imv_fav);

            progressBarList.add(holder.progressBar);
            imageViewList.add(holder.imageView);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }


        if (fashion.isFav()) {
            holder.imvFav.setImageResource(R.drawable.active_heart);
        } else {
            holder.imvFav.setVisibility(View.GONE);

        }

        ViewTreeObserver observer = holder.imageView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (!isDones.get(i)) {
                    isDones.set(i, true);

                    final GetImageFromDeviceTask task = tasks.get(i);
                    task.setListener(createListener());
                    task.setActivity(mActivity);

                    GetImageFromDeviceTask.Param param = new GetImageFromDeviceTask.Param(holder.imageView.getWidth(),
                            holder.imageView.getHeight(), Uri.parse(fashion.getLocalDeviceUri()));

                    task.execute(param);

                }
            }
        });


        return view;
    }

    public void refresh(List<Fashion> items) {
        this.fashionList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return fashionList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    private GetImageFromDeviceTask.GetImageFromDeviceListener createListener() {
        return new GetImageFromDeviceTask.GetImageFromDeviceListener() {
            @Override
            public void onSuccess(Bitmap bitmap) {

                progressBarList.get(taskFlag).setVisibility(View.GONE);
                imageViewList.get(taskFlag).setImageBitmap(bitmap);
                taskFlag ++;
            }
        };
    }

}
