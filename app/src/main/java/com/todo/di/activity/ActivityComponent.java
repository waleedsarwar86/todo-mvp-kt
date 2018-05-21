package com.todo.di.activity;

import com.todo.di.application.ApplicationComponent;
import com.todo.di.activity.module.ActivityModule;
import com.todo.di.activity.module.ActivityPresenterModule;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, ActivityPresenterModule.class})
public interface ActivityComponent extends ActivityComponentInjects, ActivityComponentExposes {

    final class Initializer {

        private Initializer() {
        }

        public static ActivityComponent init(final DaggerActivity daggerActivity, final ApplicationComponent applicationComponent) {
            return DaggerActivityComponent.builder()
                    .applicationComponent(applicationComponent)
                    .activityModule(new ActivityModule(daggerActivity))
                    .activityPresenterModule(new ActivityPresenterModule(daggerActivity))
                    .build();
        }
    }
}
