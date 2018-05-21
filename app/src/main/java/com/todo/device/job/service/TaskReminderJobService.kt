package com.todo.device.job.service

import android.os.Bundle

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.todo.di.application.DaggerApplication
import com.todo.data.model.TaskModel
import com.todo.device.notification.NotificationFactory
import com.todo.device.notification.Notifications

import javax.inject.Inject

class TaskReminderJobService : JobService() {


    @Inject
    lateinit var notifications: Notifications

    @Inject
    lateinit var notificationFactory: NotificationFactory


    override fun onCreate() {
        super.onCreate()
        DaggerApplication.from(application)
                .applicationComponent
                .inject(this)
    }

    override fun onStartJob(job: JobParameters): Boolean {
        val extras = job.extras
        extras?.let {
            val taskModel = getTask(it)
            showTaskReminderNotification(taskModel)
            return true
        }
        return false
    }

    override fun onStopJob(job: JobParameters): Boolean {
        return false
    }

    private fun getTask(extras: Bundle): TaskModel {
        val taskModel = TaskModel(title = extras.getString(BUNDLE_TASK_TITLE), priority = extras.getInt(BUNDLE_TASK_PRIORITY));
        return taskModel
    }

    private fun showTaskReminderNotification(taskModel: TaskModel) {
        notifications.showNotification(TASK_REMINDER_NOTIFICATION_ID, notificationFactory.createTaskReminderNotification(taskModel))
    }

    companion object {

        private const val TASK_REMINDER_NOTIFICATION_ID = 1832

        const val BUNDLE_TASK_TITLE = "bundle_task_title"
        const val BUNDLE_TASK_PRIORITY = "bundle_task_priority"
    }
}




