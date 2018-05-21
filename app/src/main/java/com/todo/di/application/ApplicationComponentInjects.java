package com.todo.di.application;

import com.todo.device.job.service.TaskReminderJobService;

public interface ApplicationComponentInjects {

    void inject(DaggerApplication daggerApplication);

    void inject(TaskReminderJobService taskReminderJobService);


}
