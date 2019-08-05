package com.sense.naoto.sense.processings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageHelper {

    public static Bitmap fromImageToBitmap(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }
}

