package com.sense.naoto.sense.activity_helper;

import android.app.Activity;
import android.content.Intent;

import com.sense.naoto.sense.sign_up.SignUpActivity;

public class LoginActivityHelper {

    public static void launchSignUpActivity(Activity activity){
        Intent intent = new Intent(activity, SignUpActivity.class);
        activity.startActivity(intent);
    }
}
