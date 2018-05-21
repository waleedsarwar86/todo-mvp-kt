package com.todo.di.activity.module;

import com.todo.di.activity.DaggerActivity;
import com.todo.di.activity.ActivityComponent;
import com.todo.di.activity.ActivityScope;
import com.todo.ui.feature.addedittask.AddEditTaskContract;
import com.todo.ui.feature.addedittask.AddEditTaskPresenter;
import com.todo.ui.feature.launcher.LauncherContract;
import com.todo.ui.feature.launcher.LauncherPresenter;
import com.todo.ui.feature.login.LoginContract;
import com.todo.ui.feature.login.LoginPresenter;
import com.todo.ui.feature.register.RegisterContract;
import com.todo.ui.feature.register.RegisterPresenter;
import com.todo.ui.feature.tasks.TasksContract;
import com.todo.ui.feature.tasks.TasksPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public final class ActivityPresenterModule {

    private final DaggerActivity daggerActivity;

    public ActivityPresenterModule(final DaggerActivity daggerActivity) {
        this.daggerActivity = daggerActivity;
    }

    private ActivityComponent getActivityComponent() {
        return daggerActivity.getActivityComponent();
    }

    @Provides
    @ActivityScope
    public LauncherContract.Presenter provideLauncherPresenter() {
        final LauncherPresenter launcherPresenter = new LauncherPresenter();
        getActivityComponent().inject(launcherPresenter);
        return launcherPresenter;
    }

    @Provides
    @ActivityScope
    public LoginContract.Presenter provideLoginPresenter() {
        final LoginPresenter loginPresenter = new LoginPresenter();
        getActivityComponent().inject(loginPresenter);
        return loginPresenter;
    }

    @Provides
    @ActivityScope
    public RegisterContract.Presenter provideRegisterPresenter() {
        final RegisterPresenter registerPresenter = new RegisterPresenter();
        getActivityComponent().inject(registerPresenter);
        return registerPresenter;
    }

    @Provides
    @ActivityScope
    public TasksContract.Presenter provideTasksPresenter() {
        final TasksPresenter tasksPresenter = new TasksPresenter();
        getActivityComponent().inject(tasksPresenter);
        return tasksPresenter;
    }

    @Provides
    @ActivityScope
    public AddEditTaskContract.Presenter provideAddEditTaskPresenter() {
        final AddEditTaskPresenter addEditTaskPresenter = new AddEditTaskPresenter();
        getActivityComponent().inject(addEditTaskPresenter);
        return addEditTaskPresenter;
    }

}
