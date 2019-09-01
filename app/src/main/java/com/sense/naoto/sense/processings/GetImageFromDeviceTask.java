package com.sense.naoto.sense.processings;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import java.io.IOException;
import java.io.InputStream;

import lombok.Getter;
import lombok.Setter;

public class GetImageFromDeviceTask extends AsyncTask<GetImageFromDeviceTask.Param, Integer, Bitmap> {

    private GetImageFromDeviceListener listener;

    private Activity activity;

    @Override
    protected Bitmap doInBackground(Param... params) {

        Bitmap image = null;

        try {
            InputStream in = activity.getContentResolver().openInputStream(params[0].getUri());
            ExifInterface exifInterface = new ExifInterface(in);

            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), params[0].getUri());
            Bitmap  rotatedImage = ImageHelper.rotateBitmap(bitmap, orientation);

            image = ImageHelper.resizeBitmap(params[0].getMaxSize(), rotatedImage);

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
            listener.onSuccess(result);
        }
    }

    public void setListener(GetImageFromDeviceListener listener) {
        this.listener = listener;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }


    public interface GetImageFromDeviceListener {
        void onSuccess(Bitmap bitmap);
    }

    public static class Param {
        @Getter
        @Setter
        private int maxSize;
        @Getter
        @Setter
        private Uri uri;

        public Param() {
        }

        public Param(int maxSize, Uri uri) {
            this.maxSize = maxSize;
            this.uri = uri;
        }
    }

}
