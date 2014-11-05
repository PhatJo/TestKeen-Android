package com.example.kiptoomagutt.testkeen;

import android.app.Application;
import android.content.Context;

/**
 * Created by kiptoo.magutt on 11/5/14.
 */
public class MyApp extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize.
        mContext = getApplicationContext();
    }
    public static Context getContext() {
        return mContext;
    }
}
