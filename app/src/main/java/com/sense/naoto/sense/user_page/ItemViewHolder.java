package com.sense.naoto.sense.user_page;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sense.naoto.sense.R;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    public ImageView itemImage;
    public TextView txvItemName;
    public ProgressBar progressBar;

    public ItemViewHolder(View itemView) {
        super(itemView);
        itemImage = itemView.findViewById(R.id.item_image);
        txvItemName = itemView.findViewById(R.id.txv_item_name);
        progressBar = itemView.findViewById(R.id.progressBar);
    }
}
