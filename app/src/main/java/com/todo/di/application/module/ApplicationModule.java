package com.todo.di.application.module;

import android.content.res.Resources;

import com.todo.di.application.DaggerApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class ApplicationModule {


    private final DaggerApplication daggerApplication;

    public ApplicationModule(final DaggerApplication daggerApplication) {
        this.daggerApplication = daggerApplication;
    }

    @Provides
    @Singleton
    DaggerApplication provideTodoApplication() {
        return daggerApplication;
    }

    @Provides
    @Singleton
    Resources provideResources() {
        return daggerApplication.getResources();
    }


    public interface Exposes {

        DaggerApplication todoApplication();

        Resources resources();
    }
}
