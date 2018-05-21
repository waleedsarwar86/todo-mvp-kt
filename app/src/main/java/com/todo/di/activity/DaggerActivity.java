package com.todo.di.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.todo.di.application.DaggerApplication;
import com.todo.di.ComponentFactory;

public abstract class DaggerActivity extends AppCompatActivity {

    /********* Member Fields  ********/

    private ActivityComponent activityComponent;

    /********* Lifecycle Methods ********/

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inject(getActivityComponent());
    }

    /********* Abstract Methods ********/

    protected abstract void inject(final ActivityComponent activityComponent);

    /********* Member Methods  ********/

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = ComponentFactory.createActivityComponent(this, getDaggerApplication());
        }
        return activityComponent;
    }

    private DaggerApplication getDaggerApplication() {
        return ((DaggerApplication) getApplication());
    }

}
