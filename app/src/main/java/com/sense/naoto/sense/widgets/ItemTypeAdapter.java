package com.sense.naoto.sense.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemTypeAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layoutID;
    private List<String> typeNames = new ArrayList<>();
    private List<Integer> typeImageIDs = new ArrayList<>();

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
    }

    public ItemTypeAdapter(Context context, int resID) {
        this.inflater = LayoutInflater.from(context);
        layoutID = resID;

        for (int i = 0; i < 8; i++) {
            typeNames.add(FashionItem.getTypeName(i));
            typeImageIDs.add(FashionItem.getTypeResID(i));
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(layoutID, null);
            holder = new ViewHolder();

            holder.imageView = convertView.findViewById(R.id.imageView);
            holder.textView = convertView.findViewById(R.id.txv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.imageView.setImageResource(typeImageIDs.get(position));
        holder.textView.setText(typeNames.get(position));

        return convertView;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

}
