package com.sense.naoto.sense.processings;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.classes.User;

import java.util.ArrayList;
import java.util.List;

public class FireBaseHelper {

    public static final int REQUEST_ALL = 0;


    private static String getFileExtension(Uri uri, Activity activity) {
        //画像の拡張子をゲットする

        ContentResolver cR = activity.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private static DatabaseReference getImageIdsDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference("imageIds");
    }

    private static DatabaseReference getUsersDatabaseReference() {
        return FirebaseDatabase.getInstance().getReference("users");
    }

    public static void uploadFashion(final String title, final String description, Uri uri
            , final Activity activity, final OnFirebaseCompleteListener listener) {

        final StorageReference storageReference = FirebaseStorage.getInstance().getReference("imageIds");
        final DatabaseReference databaseUploadReference = getImageIdsDatabaseReference();
        final DatabaseReference databaseUserReference = getUsersDatabaseReference();

        final StorageReference filereferrence = storageReference.child(System.currentTimeMillis() + "."
                + getFileExtension(uri, activity));

        StorageTask mUploadTask = filereferrence.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        filereferrence.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String url = uri.toString();
                                final User user = UserPreferencesHelper.getUserPreferences(activity);

                                Fashion fashion = new Fashion(url,
                                        title, description, user.getUserName(), user.getUserId());

                                final String uploadId = databaseUploadReference.push().getKey();
                                databaseUploadReference.child(uploadId).setValue(fashion);


                                databaseUserReference.child(user.getUserId()).setValue(user);

                                databaseUserReference.child(user.getUserId()).child("imageIds").push().setValue(uploadId);
                                listener.onFirebaseUploadCompleted();
//
                            }
                        });


                    }
                    //todo:userのImageIdsに追加されるのではなく、更新してしまう　


                })


                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "アップロードに失敗しました", Toast.LENGTH_SHORT).show();
                        listener.onFirebaseUploadFailed();
                    }
                });

    }


    public static void callThreeFashion(Activity activity, int request, OnGetItemFromFirebaseListener listener) {
        switch (request) {
            case REQUEST_ALL:
                //  list = getThreeFromALlPost();
                calltThreeFromALlPost(listener);
                break;

        }
    }


    private static void calltThreeFromALlPost(final OnGetItemFromFirebaseListener listener) {
        //全部の中から三つ取ってくる
        getImageIdsDatabaseReference().limitToFirst(3).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Fashion item = snapshot.getValue(Fashion.class);
                    listener.onAddFashion(item);
                }

                listener.onFirebaseDownloadCompleted();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                if (true) Log.d("oncancelled", "error");
            }
        });

    }


    public interface OnFirebaseCompleteListener {
        void onFirebaseUploadCompleted();

        void onFirebaseUploadFailed();
    }

    public interface OnGetItemFromFirebaseListener {
        void onAddFashion(Fashion fashion);

        void onFirebaseDownloadCompleted();

        void onFirebaseDownloadFailed();
    }

}
