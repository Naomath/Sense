package com.sense.naoto.sense.user_page;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.classes.FashionItem;
import com.sense.naoto.sense.processings.GetImageFromDeviceTask;
import com.sense.naoto.sense.processings.ImageHelper;

import java.util.ArrayList;
import java.util.List;

public class ItemRecycleAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<FashionItem> itemList;

    private Activity activity;

    public ItemRecycleAdapter(List<FashionItem> itemList, Activity activity) {
        if (itemList == null) {
            this.itemList = new ArrayList<>();
        } else {
            this.itemList = itemList;

        }

        this.activity = activity;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_row, viewGroup, false);
        ItemViewHolder holder = new ItemViewHolder(inflate);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int i) {
       //todo:itemの画像処理
        Bitmap image = ImageHelper.fromBase64ToBitmap(itemList.get(i).getImageCode());

        holder.itemImage.setImageBitmap(image);
        holder.txvItemName.setText(itemList.get(i).getName());

    }

    @Override
    public int getItemCount() {
        if (itemList == null) {
            return 0;
        } else {
            return itemList.size();

        }
    }

    public void addItem(FashionItem item) {
        itemList.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int position){
        itemList.remove(position);
        notifyDataSetChanged();
    }


}
