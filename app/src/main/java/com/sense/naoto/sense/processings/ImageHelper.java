package com.sense.naoto.sense.processings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.nio.ByteBuffer;

public class ImageHelper {

    public static Bitmap fromImageToBitmap(Image image) {
        Image.Plane plane = image.getPlanes()[0];
        ByteBuffer buf = plane.getBuffer();
        byte[] b = new byte[buf.remaining()];
        buf.get(b);
        Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        return bitmap;
    }
}
