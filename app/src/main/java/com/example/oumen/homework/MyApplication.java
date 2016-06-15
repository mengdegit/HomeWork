package com.example.oumen.homework;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * Created by oumen on 2016/6/14.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
