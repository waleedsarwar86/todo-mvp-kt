package com.todo;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class TodoApplication extends BaseApplication {


    /********* Lifecycle Methods ********/

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());


    }

}
