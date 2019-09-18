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
import com.sense.naoto.sense.processings.GetImageTaskForGrid;

import java.util.ArrayList;
import java.util.HashMap;
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
    private List<FashionItem> allitemList = new ArrayList<>();
    private List<FashionItem> usedList = new ArrayList<>();
    private LayoutInflater inflater;
    private int layoutId;
    private Activity mActivity;


    //GetImageTask関連
    private List<Boolean> isDones = new ArrayList<>();
    private HashMap<String, Bitmap> bmpMap = new HashMap<>();

    public GridItemAdapter() {
    }

    public GridItemAdapter(LayoutInflater inflater, int layoutId, List<FashionItem> itemList, Activity activity) {
        super();
        this.inflater = inflater;
        this.layoutId = layoutId;
        this.usedList = itemList;
        mActivity = activity;
        SIZE = itemList.size();

        for (FashionItem item : itemList) {
            allitemList.add(item);
        }

        for (int i = 0; i < SIZE; i++) {
            isDones.add(false);
        }
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        final FashionItem item = usedList.get(i);

        if (view == null) {
            view = inflater.inflate(layoutId, viewGroup, false);

            holder = new GridItemAdapter.ViewHolder();
            holder.imageView = view.findViewById(R.id.image_view);
            holder.imvSelected = view.findViewById(R.id.imgSelected);
            holder.progressBar = view.findViewById(R.id.progressBar);

            view.setTag(holder);

        } else {
            holder = (GridItemAdapter.ViewHolder) view.getTag();
        }

        if (bmpMap.get(item.getPrefKey()) != null) {
            holder.imageView.setImageBitmap(bmpMap.get(item.getPrefKey()));
            holder.progressBar.setVisibility(View.GONE);
        } else {

            ViewTreeObserver observer = holder.imageView.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    if (!isDones.get(i)) {
                        isDones.set(i, true);

                        final GetImageTaskForGrid task = new GetImageTaskForGrid();
                        task.setListener(createListener());
                        task.setActivity(mActivity);

                        GetImageTaskForGrid.Param param = new GetImageTaskForGrid.Param(holder.imageView.getWidth(),
                                holder.imageView.getHeight(), Uri.parse(item.getLocalDeviceUri()),
                                holder.imageView, holder.progressBar, item.getPrefKey());

                        task.execute(param);

                    }
                }
            });

        }


        return view;
    }


    public void changeToAll() {
        //ALlに切り替える時
        usedList.clear();
        for (FashionItem item : allitemList) {
            usedList.add(item);
        }
        notifyDataSetChanged();

    }

    public int changeToSpecific(int type) {
        usedList.clear();
        for (FashionItem item : allitemList) {
            if (item.getType() == type) {
                usedList.add(item);
            }
        }

        notifyDataSetChanged();
        return usedList.size();
    }

    @Override
    public int getCount() {
        return usedList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private GetImageTaskForGrid.GetImageFromDeviceForGridListener createListener() {
        return new GetImageTaskForGrid.GetImageFromDeviceForGridListener() {
            @Override
            public void onSuccess(Bitmap bitmap, String prefKey
                    , ImageView imageView, ProgressBar progressBar) {
                imageView.setImageBitmap(bitmap);
                progressBar.setVisibility(View.GONE);

                bmpMap.put(prefKey, bitmap);
            }
        };
    }

}
