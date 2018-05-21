package com.todo.di.application;

import android.app.Application;
import android.content.Context;

import com.todo.di.ComponentFactory;

public abstract class DaggerApplication extends Application {

    /********* Member Fields  ********/

    private ApplicationComponent applicationComponent;

    /********* Static Methods  ********/

    public static DaggerApplication from(final Context context) {
        return (DaggerApplication) context.getApplicationContext();
    }

    /********* Lifecycle Methods ********/


    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = ComponentFactory.createApplicationComponent(this);

    }

    /********* Member Methods  ********/

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
