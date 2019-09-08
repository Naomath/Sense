package com.sense.naoto.sense.processings;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Base64;

import com.sense.naoto.sense.R;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ImageHelper {

    public static Bitmap fromBytesToBitmap(byte[] bytes) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    public static byte[] fromBase64ToBytes(String imageCode) {
        byte[] decodedByte = Base64.decode(imageCode, 0);
        return decodedByte;
    }

    public static byte[] fromBitmapToBytes(Bitmap bitmap) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(bitmap.getByteCount());
        bitmap.copyPixelsToBuffer(byteBuffer);
        byte[] bmparr = byteBuffer.array();
        return bmparr;
    }

    public static Bitmap fromResourceIdToBitmap(Activity activity, int resId) {
        Resources r = activity.getResources();
        Bitmap bmp = BitmapFactory.decodeResource(r, resId);
        return bmp;
    }


    public static Bitmap fromBase64ToBitmap(String imageCode) {
        byte[] bytes = fromBase64ToBytes(imageCode);

        return fromBytesToBitmap(bytes);
    }

    public static String fromBitmapToBase64(Bitmap image) {
        Bitmap immagex = image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b, Base64.NO_WRAP);
        return imageEncoded;
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {

        Matrix matrix = new Matrix();
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap resizeBitmap(int maxSize, Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Bitmap resizedBitmap = null;

        if (width > maxSize || height > maxSize) {
            int widthScale = width / maxSize;
            int heightScale = height / maxSize;

            int resizedWidth = 0;
            int resizedHeight = 0;

            if (widthScale >= heightScale) {
                resizedWidth = width / widthScale;
                resizedHeight = height / widthScale;
            } else {
                resizedWidth = width / heightScale;
                resizedHeight = height / heightScale;
            }

            resizedBitmap = Bitmap.createScaledBitmap(bitmap,
                    resizedWidth,
                    resizedHeight,
                    true);
        } else {
            resizedBitmap = bitmap;
        }
        return resizedBitmap;
    }

}

