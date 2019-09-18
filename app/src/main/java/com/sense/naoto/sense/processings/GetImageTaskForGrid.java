package com.sense.naoto.sense.processings;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;
import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

public class GetImageTaskForGrid extends AsyncTask<GetImageTaskForGrid.Param, Integer, Bitmap> {

    private GetImageFromDeviceForGridListener listener;

    private ImageView imageView;
    private ProgressBar progressBar;

    private String prefKey;

    private Activity activity;

    @Override
    protected Bitmap doInBackground(GetImageTaskForGrid.Param... params) {

        Bitmap image = null;
        imageView = params[0].getImageView();
        progressBar = params[0].getProgressBar();
        prefKey = params[0].getPrefKey();

        try {
            InputStream in = activity.getContentResolver().openInputStream(params[0].getUri());
            ExifInterface exifInterface = new ExifInterface(in);
            in.close();
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            in = activity.getContentResolver().openInputStream(params[0].getUri());

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            int originalWidth = options.outWidth;
            int originalHeight = options.outHeight;

            in.close();

            in = activity.getContentResolver().openInputStream(params[0].getUri());

            int scaleW = originalWidth / params[0].getMaxWidth();
            int scaleH = originalHeight / params[0].getMaxHeight();
            if (scaleW >= scaleH) {
                options.inSampleSize = scaleW;
            } else {
                options.inSampleSize = scaleH;
            }

            options.inJustDecodeBounds = false;
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);

            image = ImageHelper.rotateBitmap(bitmap, orientation);



        } catch (IOException e) {

        }
        return image;
    }


    // 途中経過をメインスレッドに返す
    @Override
    protected void onProgressUpdate(Integer... progress) {
    }


    // 非同期処理が終了後、結果をメインスレッドに返す
    @Override
    protected void onPostExecute(Bitmap result) {
        if (listener != null) {
            listener.onSuccess(result, prefKey, imageView , progressBar);
        }
    }

    public void setListener(GetImageFromDeviceForGridListener listener) {
        this.listener = listener;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public interface GetImageFromDeviceForGridListener {
        void onSuccess(Bitmap bitmap, String prefKey, ImageView imageView, ProgressBar progressBar);
    }

    public static class Param {
        @Getter
        @Setter
        private int maxWidth;
        @Getter
        @Setter
        private int maxHeight;
        @Getter
        @Setter
        private Uri uri;
        @Getter
        @Setter
        private ImageView imageView;
        @Getter
        @Setter
        private ProgressBar progressBar;
        @Getter
        @Setter
        private String prefKey;


        public Param() {}

        public Param(int maxWidth, int maxHeight, Uri uri, ImageView imageView, ProgressBar progressBar, String prefKey) {
            this.maxWidth = maxWidth;
            this.maxHeight = maxHeight;
            this.uri = uri;
            this.imageView = imageView;
            this.progressBar = progressBar;
            this.prefKey = prefKey;
        }
    }


//
//    public static class ReturnValue {
//        @Getter
//        @Setter
//        private Bitmap bitmap;
//
//        @Getter
//        @Setter
//        private String prefKey;
//
//        @Getter
//        @Setter
//        private ImageView imageView;
//
//        @Getter
//        @Setter
//        private ProgressBar progressBar;
//
//        public ReturnValue(){}
//
//        public ReturnValue(Bitmap bitmap, String prefKey,
//                           ImageView imageView, ProgressBar progressBar){
//            this.bitmap = bitmap;
//            this.prefKey = prefKey;
//            this.imageView = imageView;
//            this.progressBar = progressBar;
//        }
//    }

}

