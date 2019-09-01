package com.sense.naoto.sense.widgets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sense.naoto.sense.R;

import java.util.Arrays;
import java.util.List;

public class ItemTypeAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private int layoutID;
    private List<String> typeNames;
    private List<Integer> typeImageIDs;

    static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }

    public ItemTypeAdapter(Context context, int resID){
        this.inflater = LayoutInflater.from(context);
        layoutID = resID;
        typeNames = Arrays.asList("TOP","BOTTOM","ACCESSORY","OTHER");
        typeImageIDs = Arrays.asList(R.drawable.tops_icon,R.drawable.bottoms_icon,
                R.drawable.accessories_icon, R.drawable.other_icon);
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
        return 4;
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
