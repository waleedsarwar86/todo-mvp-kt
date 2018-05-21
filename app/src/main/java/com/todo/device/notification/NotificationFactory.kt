package com.todo.device.notification

import android.app.Notification

import com.todo.data.model.TaskModel

interface NotificationFactory {

    fun createTaskReminderNotification(taskModel: TaskModel): Notification

    companion object {

        const val CHANNEL_ID_REMINDERS = "channel_reminders"

        const val GROUP_KEY_TASK_REMINDERS = "group_key_task_reminders"
    }
}
