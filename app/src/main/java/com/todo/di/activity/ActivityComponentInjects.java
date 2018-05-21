package com.todo.di.activity;

import com.todo.ui.feature.addedittask.AddEditTaskActivity;
import com.todo.ui.feature.addedittask.AddEditTaskPresenter;
import com.todo.ui.feature.launcher.LauncherActivity;
import com.todo.ui.feature.launcher.LauncherPresenter;
import com.todo.ui.feature.login.LoginActivity;
import com.todo.ui.feature.login.LoginPresenter;
import com.todo.ui.feature.register.RegisterActivity;
import com.todo.ui.feature.register.RegisterPresenter;
import com.todo.ui.feature.tasks.TasksActivity;
import com.todo.ui.feature.tasks.TasksPresenter;

public interface ActivityComponentInjects {

    void inject(LauncherActivity launcherActivity);
    void inject(LauncherPresenter launcherPresenter);

    void inject(LoginActivity loginActivity);
    void inject(LoginPresenter loginPresenter);

    void inject(RegisterActivity registerActivity);
    void inject(RegisterPresenter registerPresenter);

    void inject(TasksActivity tasksActivity);
    void inject(TasksPresenter tasksPresenter);

    void inject(AddEditTaskActivity addEditTaskActivity);
    void inject(AddEditTaskPresenter addEditTaskPresenter);

}
