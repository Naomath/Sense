package com.sense.naoto.sense.processings;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.classes.User;

public class FireBaseHelper {


    private static String getFileExtension(Uri uri, Activity activity) {
        //画像の拡張子をゲットする

        ContentResolver cR = activity.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public static void uploadFashion(final String title, final String description, Uri uri
            , final Activity activity, final OnFirebaseCompleteListener listener) {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference("uploads");
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("uploads");

        StorageReference filereferrence = storageReference.child(System.currentTimeMillis() + "."
                + getFileExtension(uri, activity));

        StorageTask mUploadTask = filereferrence.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final String[] url = {null};

                        Task<Uri> firebaseUri = taskSnapshot.getStorage().getDownloadUrl();
                        firebaseUri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                url[0] = uri.toString();
                                //todo:こいつがうまく取得されていない

                            }
                        });
                        User user = UserPreferencesHelper.getUserPreferences(activity);

                        Fashion fashion = new Fashion(url[0],
                        title, description, user.getUserName(), user.getUserId());

                        String uploadId = databaseReference.push().getKey();
                        databaseReference.child(uploadId).setValue(fashion);

                        listener.onFirebaseComplete();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "アップロードに失敗しました", Toast.LENGTH_SHORT).show();
                        listener.onFirebaseFailed();
                    }
                });

    }

    public interface OnFirebaseCompleteListener {
        void onFirebaseComplete();

        void onFirebaseFailed();
    }

}
