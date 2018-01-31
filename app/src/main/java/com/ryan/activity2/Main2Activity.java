package com.ryan.activity2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.ryan.Jugg;
import com.ryan.annotation.R;
import com.ryan.annotations.click;
import com.ryan.annotations.findId;

public class Main2Activity extends AppCompatActivity {

    @findId(R.id.id_date_picker)
    DatePicker mDatePicker;

    @click(R.id.fab)
    void clickFab() {
        Snackbar.make(mDatePicker, "Click button method by jugg", Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Jugg.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mDatePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Snackbar.make(view, "" + year + " " + monthOfYear + " " + dayOfMonth, Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
