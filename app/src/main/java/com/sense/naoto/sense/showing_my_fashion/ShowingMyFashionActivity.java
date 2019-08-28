package com.sense.naoto.sense.showing_my_fashion;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.sense.naoto.sense.R;
import com.sense.naoto.sense.activity_helper.ShowingMyFashionActivityHelper;
import com.sense.naoto.sense.classes.Fashion;
import com.sense.naoto.sense.fashion_swipe.FashionFragment;
import com.sense.naoto.sense.processings.SavedDataHelper;

public class ShowingMyFashionActivity extends AppCompatActivity {

    public int startingNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showing_my_fashion);

        startingNumber = ShowingMyFashionActivityHelper.getFashionNumberArg(this);

        setViews();

    }

    public void setViews(){

        Fashion fashion = SavedDataHelper.getFashionByNumber(this, startingNumber);

        Fragment fragment = FashionFragment.newInstance(fashion);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, fragment).commit();

        ImageButton btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        ShowingMyFashionActivityHelper.launchMainActivity(this);
    }
}
