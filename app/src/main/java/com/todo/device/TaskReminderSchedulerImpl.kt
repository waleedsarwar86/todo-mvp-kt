package com.todo.device

import android.os.Bundle
import com.firebase.jobdispatcher.*
import com.todo.data.model.TaskModel
import com.todo.device.job.service.TaskReminderJobService
import com.todo.device.job.service.TaskReminderJobService.Companion.BUNDLE_TASK_PRIORITY
import com.todo.device.job.service.TaskReminderJobService.Companion.BUNDLE_TASK_TITLE
import java.util.*

class TaskReminderSchedulerImpl(private val jobDispatcher: FirebaseJobDispatcher) : TaskReminderScheduler {

    override fun scheduleTaskReminder(taskModel: TaskModel): Int {

        val reminderMs = taskModel.reminder
        val nowMs = Calendar.getInstance().time.time
        val diffSecs = ((reminderMs - nowMs) / 1000).toInt()

        val extras = Bundle()
        extras.putString(BUNDLE_TASK_TITLE, taskModel.title)
        extras.putInt(BUNDLE_TASK_PRIORITY, taskModel.priority)

        val job = jobDispatcher.newJobBuilder()
                .setService(TaskReminderJobService::class.java)
                .setTag(taskModel.id)
                .setRecurring(false)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(diffSecs, diffSecs + 60))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setExtras(extras)
                .build()

        return jobDispatcher.schedule(job)
    }

    override fun cancelTaskReminder(taskId: String): Int {
        return jobDispatcher.cancel(taskId)
    }
}
