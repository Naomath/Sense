package com.sense.naoto.sense.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.ImageHelper;

import java.util.List;

public class ItemListAdapter extends ArrayAdapter<FashionItem> {

    //変数
    private int mResource;
    private List<FashionItem> fashionItems;
    private LayoutInflater mInflater;

    public ItemListAdapter(Context context, int resource, List<FashionItem> items){
        super(context, resource, items);

        mResource = resource;
        fashionItems = items;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        }
        else {
            view = mInflater.inflate(mResource, null);
        }

        FashionItem item = fashionItems.get(position);

        ImageView itemImageView = view.findViewById(R.id.item_image);
        Bitmap image = ImageHelper.fromBase64ToBitmap(item.getImageCode());
        itemImageView.setImageBitmap(image);

        TextView  txvName = view.findViewById(R.id.txv_item_name);
        txvName.setText(item.getName());

        return view;
    }
}
