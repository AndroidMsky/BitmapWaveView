package com.example.liangmutian.bitmapwaveview;

import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {
    BitmapWave mBitmapWave,mBitmapWave2;
    int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBitmapWave=(BitmapWave)findViewById(R.id.bitmapwave1);

        mBitmapWave2=(BitmapWave)findViewById(R.id.bitmapwave2);

        mBitmapWave.setMode(1);
    }

    public void change(View v){

        type+=1;
        mBitmapWave2.setMode((type)%4);



    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

    }

}