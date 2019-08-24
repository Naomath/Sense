package com.sense.naoto.sense.sign_up;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.SignUpActivityHelper;
import com.sense.naoto.sense.classes.User;
import com.sense.naoto.sense.processings.ImageHelper;
import com.sense.naoto.sense.processings.UserPreferencesHelper;

public class SignUpActivity extends AppCompatActivity{

    private Activity mActivity;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mActivity = this;
        mContext = this;
        setViews();
    }

    private void setViews() {
        //todo:押してないときはボタンを押せなくする
        final EditText edtUserName = findViewById(R.id.edit_user_name);
        final EditText edtPassword = findViewById(R.id.edt_password);
        final EditText edtPasswordCheck = findViewById(R.id.edt_password_check);

        Button btnSignUp = findViewById(R.id.btn_sign_in);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String i1 = edtPassword.getText().toString();
                String i2 = edtPasswordCheck.getText().toString();
                String userName = edtUserName.getText().toString();

                if (userName.trim().equals("")) {
                    Toast.makeText(mContext, "ユーザーネームが入力されてません", Toast.LENGTH_SHORT).show();

                } else {
                    if (i2.equals(i1)) {
                        //todo:アカウント作成 iconimage firebase
                        String userId = User.newUserId();
                        saveUserLocal(userName, userId);
                        SignUpActivityHelper.launchMainActivity(mActivity);
                        //
                    } else {
                        //違かった場合
                        Toast.makeText(mContext, "同じパスワードが入力されていません", Toast.LENGTH_SHORT).show();
                        edtPassword.setText("");
                        edtPasswordCheck.setText("");
                    }

                }
            }
        });
    }

    private void saveUserLocal(String userName, String userId) {
        User user = new User();
        user.setUserName(userName);
        user.setPostNumber(0);
        user.setFollowerNumber(0);
        user.setFollowingNumber(0);
        user.setUserId(userId);

        Bitmap bitmap = ImageHelper.fromResourceIdToBitmap(mActivity, R.drawable.default_icon);
        String imageCode = ImageHelper.fromBitmapToBase64(bitmap);

        user.setIconImage(imageCode);
        UserPreferencesHelper.updataPreferences(user, mContext);

    }

}
