package com.sense.naoto.sense.processings;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GetImageFromDeviceTask extends AsyncTask<Uri, Integer, Bitmap> {

    private GetImageFromDeviceListener listener;

    private Activity activity;

    @Override
    protected Bitmap doInBackground(Uri... uri) {

        Bitmap image = null;

        try {
            ParcelFileDescriptor parcelFileDescriptor = activity.getContentResolver().openFileDescriptor(uri[0], "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            parcelFileDescriptor.close();
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

}
