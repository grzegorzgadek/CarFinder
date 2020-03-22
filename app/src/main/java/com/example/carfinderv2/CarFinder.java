package com.example.carfinderv2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class CarFinder extends AppCompatActivity {

    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = getApplicationContext();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_finder);

        try {
            new MyTask(context).execute(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
