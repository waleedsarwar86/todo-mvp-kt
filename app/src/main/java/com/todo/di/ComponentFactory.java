package com.todo.di;

import com.todo.di.application.DaggerApplication;
import com.todo.di.activity.ActivityComponent;
import com.todo.di.activity.DaggerActivity;
import com.todo.di.application.ApplicationComponent;

public final class ComponentFactory {

    private ComponentFactory() {
    }

    public static ApplicationComponent createApplicationComponent(final DaggerApplication daggerApplication) {
        return ApplicationComponent.Initializer.init(daggerApplication);
    }

    public static ActivityComponent createActivityComponent(final DaggerActivity daggerActivity, final DaggerApplication daggerApplication) {
        return ActivityComponent.Initializer.init(daggerActivity, daggerApplication.getApplicationComponent());
    }

}
