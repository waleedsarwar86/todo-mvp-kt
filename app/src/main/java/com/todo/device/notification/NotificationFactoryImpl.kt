package com.todo.device.notification

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat

import com.todo.R
import com.todo.data.model.TaskModel
import com.todo.ui.feature.tasks.TasksActivity
import com.todo.util.UiUtils

import android.content.Context.NOTIFICATION_SERVICE


class NotificationFactoryImpl(private val context: Context) : NotificationFactory {

    init {
        createNotificationChannels()
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun createNotificationChannels() {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

            val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager


            notificationManager.getNotificationChannel(NotificationFactory.CHANNEL_ID_REMINDERS)
                    ?: run {
                        val name = context.getString(R.string.notification_channel_name_reminders)
                        val description = context.getString(R.string.notification_channel_description_reminders)
                        val mChannel = NotificationChannel(NotificationFactory.CHANNEL_ID_REMINDERS, name, NotificationManager.IMPORTANCE_DEFAULT)
                        mChannel.description = description
                        notificationManager.createNotificationChannel(mChannel)
                    }
        }
    }

    override fun createTaskReminderNotification(taskModel: TaskModel): Notification {

        val intent = TasksActivity.getIntentForNotification(context)
        val pendingIntent = PendingIntent.getActivity(context,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT)

        val taskDetails = "Priority: " + UiUtils.getPriorityString(context, taskModel.priority) + "\n"

        val builder = NotificationCompat.Builder(context, NotificationFactory.CHANNEL_ID_REMINDERS)
                .setSmallIcon(R.drawable.notification_vector_alarm)
                .setContentTitle(taskModel.title)
                .setStyle(NotificationCompat.BigTextStyle().bigText(taskDetails))
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, R.color.all_primary))
                .setGroup(NotificationFactory.GROUP_KEY_TASK_REMINDERS)
                .setGroupSummary(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_MAX)

        return builder.build()
    }
}
