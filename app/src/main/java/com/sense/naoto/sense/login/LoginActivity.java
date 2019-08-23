package com.sense.naoto.sense.login;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.LoginActivityHelper;

public class LoginActivity extends AppCompatActivity {


    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mActivity = this;

        setViews();
    }

    @Override
    public void finish(){
        super.finish();

    }

    private void setViews(){
        Button btnLogin = findViewById(R.id.btn_sign_in);
        EditText edtId = findViewById(R.id.edit_user_name);
        EditText edtPasseord = findViewById(R.id.edt_password);
        //todo:ボタンを押された時の処理　

        Button btnSignUP = findViewById(R.id.btn_sign_up);
        btnSignUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginActivityHelper.launchSignUpActivity(mActivity);
            }
        });
    }
}
