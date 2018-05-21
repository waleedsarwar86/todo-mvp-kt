package com.todo;

import android.os.StrictMode;

import com.todo.di.application.DaggerApplication;
import com.todo.util.LinkingDebugTree;

import timber.log.Timber;

public class TodoApplication extends BaseApplication {


    /********* Lifecycle Methods ********/

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new LinkingDebugTree());

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());

    }

}
