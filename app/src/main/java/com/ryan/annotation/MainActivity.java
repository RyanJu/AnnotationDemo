package com.ryan.annotation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ryan.Jugg;
import com.ryan.activity2.Main2Activity;
import com.ryan.annotations.findId;

public class MainActivity extends AppCompatActivity {

    @findId(R.id.id_tv)
    TextView mTv;

    @findId(R.id.id_iv)
    ImageView mIv;

    @findId(R.id.toolbar)
    Toolbar mToolBar;

    FloatingActionButton mFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Jugg.bind(this);

        mTv.setText("hello this is jugg bind");

        mIv.setBackgroundColor(getResources().getColor(R.color.colorAccent));

        setSupportActionBar(mToolBar);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });


    }

}
