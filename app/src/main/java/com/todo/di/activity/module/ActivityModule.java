package com.todo.di.activity.module;

import android.app.Activity;

import com.todo.di.activity.DaggerActivity;
import com.todo.di.activity.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final DaggerActivity daggerActivity;

    public ActivityModule(final DaggerActivity daggerActivity) {
        this.daggerActivity = daggerActivity;
    }

    @Provides
    @ActivityScope
    Activity provideActivity() {
        return daggerActivity;
    }

}
