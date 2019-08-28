package com.sense.naoto.sense.user_page;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.processings.GetImageFromDeviceTask;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    class ViewHolder {
        ImageView imageView;
        ProgressBar progressBar;
    }

    private List<Fashion> fashionList = new ArrayList<>();
    private LayoutInflater inflater;
    private int layoutId;
    private Activity mActivity;

    private List<ImageView> imageViews = new ArrayList<>();
    private List<ProgressBar> progressBars = new ArrayList<>();

    private int flag;


    public GridAdapter() { }

    public GridAdapter(LayoutInflater inflater, int layoutId, List<Fashion> fashionList, Activity activity) {
        super();
        this.inflater = inflater;
        this.layoutId = layoutId;
        this.fashionList = fashionList;
        mActivity = activity;
        flag = 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(layoutId, viewGroup, false);

            holder = new ViewHolder();
            holder.imageView = view.findViewById(R.id.image_view);
            holder.progressBar = view.findViewById(R.id.circle_progress);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        imageViews.add(holder.imageView);
        progressBars.add(holder.progressBar);

        GetImageFromDeviceTask imagetask = new GetImageFromDeviceTask();
        imagetask.setListener(createListener());
        imagetask.setActivity(mActivity);

        GetImageFromDeviceTask.Param param = new GetImageFromDeviceTask.Param(500, Uri.parse(fashionList.get(i).getLocalDeviceUri()));

        imagetask.execute(param);

        return view;
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

                progressBars.get(flag).setVisibility(View.GONE);
                imageViews.get(flag).setImageBitmap(bitmap);
                flag++;
            }
        };
    }
}
