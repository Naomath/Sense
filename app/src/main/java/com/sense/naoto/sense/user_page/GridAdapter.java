package com.sense.naoto.sense.user_page;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.sense.naoto.sense.R;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {

    class ViewHolder{
        ImageView imageView;
    }

    private List<Integer> imageList = new ArrayList<>();
    private LayoutInflater inflater;
    private int layoutId;

    public GridAdapter(){}

    public GridAdapter(LayoutInflater inflater, int layoutId, List<Integer> integerList){
        super();
        this.inflater = inflater;
        this.layoutId = layoutId;
        imageList = integerList;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null){
            view = inflater.inflate(layoutId, viewGroup, false);

            holder = new ViewHolder();
            holder.imageView = view.findViewById(R.id.image_view);
            view.setTag(holder);

        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.imageView.setImageResource(imageList.get(i));

        return view;
    }

    @Override
    public int getCount() {
        return imageList.size();
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
