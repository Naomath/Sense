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
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.processings.GetImageTaskForGrid;

import java.util.ArrayList;
import java.util.HashMap;
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
    private List<Fashion> allList = new ArrayList<>();
    private List<Fashion> usedList = new ArrayList<>();
    private LayoutInflater inflater;
    private int layoutId;
    private Activity mActivity;

    private HashMap<String, Bitmap> bmpMap = new HashMap<>();

    //GetImageTask関連
    private List<Boolean> isDones = new ArrayList<>();


    public GridFashionAdapter() {}

    public GridFashionAdapter(LayoutInflater inflater, int layoutId,
                              List<Fashion> fashionList, Activity activity) {
        super();
        this.inflater = inflater;
        this.layoutId = layoutId;
        this.usedList = fashionList;
        SIZE = fashionList.size();
        mActivity = activity;

        for (int i = 0; i < SIZE; i++) {
            isDones.add(false);
        }

        for (Fashion item : fashionList) {
            allList.add(item);
        }
    }


    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;

        final Fashion fashion = usedList.get(i);

        if (view == null) {
            view = inflater.inflate(layoutId, viewGroup, false);

            holder = new ViewHolder();
            holder.progressBar = view.findViewById(R.id.progressBar);
            holder.imageView = view.findViewById(R.id.image_view);
            holder.imvFav = view.findViewById(R.id.imv_fav);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }


        //お気に入りになっているかどうか
        if (fashion.isFav()) {
            holder.imvFav.setImageResource(R.drawable.active_heart);
        } else {
            holder.imvFav.setVisibility(View.GONE);

        }


        //使う画像がすでにあるかどうか
        if (bmpMap.get(fashion.getPrefKey()) != null) {
            holder.imageView.setImageBitmap(bmpMap.get(fashion.getPrefKey()));
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
                                holder.imageView.getHeight(), Uri.parse(fashion.getLocalDeviceUri()),
                                holder.imageView, holder.progressBar, fashion.getPrefKey());

                        task.execute(param);

                    }
                }
            });

        }


        return view;
    }


    public void changeToAll(){
        //Allに切り替える時
        usedList = allList;
        notifyDataSetChanged();
    }

    public void changeToFav(List<Fashion> favs){
        //favに切り替える時
        usedList = favs;
        notifyDataSetChanged();
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
            public void onSuccess(Bitmap bitmap, String prefKey, ImageView imageView, ProgressBar progressBar) {
                imageView.setImageBitmap(bitmap);
                progressBar.setVisibility(View.GONE);

                bmpMap.put(prefKey, bitmap);
            }
        };
    }

}
