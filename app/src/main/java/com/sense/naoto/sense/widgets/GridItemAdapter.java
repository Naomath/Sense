package com.sense.naoto.sense.widgets;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.GetImageFromDeviceTask;
import com.sense.naoto.sense.processings.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class GridItemAdapter extends BaseAdapter {


    class ViewHolder {
        ImageView imageView;
        ImageView imvSelected;
    }

    private List<FashionItem> itemList = new ArrayList<>();
    private LayoutInflater inflater;
    private int layoutId;
    private Activity mActivity;


    public GridItemAdapter() {
    }

    public GridItemAdapter(LayoutInflater inflater, int layoutId, List<FashionItem> itemList, Activity activity) {
        super();
        this.inflater = inflater;
        this.layoutId = layoutId;
        this.itemList = itemList;
        mActivity = activity;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GridItemAdapter.ViewHolder holder;

        if (view == null) {
            view = inflater.inflate(layoutId, viewGroup, false);

            holder = new GridItemAdapter.ViewHolder();
            holder.imageView = view.findViewById(R.id.image_view);
            holder.imvSelected = view.findViewById(R.id.imgSelected);
            view.setTag(holder);

        } else {
            holder = (GridItemAdapter.ViewHolder) view.getTag();
        }
        String imageCode = itemList.get(i).getImageCode();
        Bitmap image = ImageHelper.fromBase64ToBitmap(imageCode);

        holder.imageView.setImageBitmap(image);

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

}
