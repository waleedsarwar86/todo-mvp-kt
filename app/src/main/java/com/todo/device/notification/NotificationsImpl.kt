package com.todo.device.notification

import android.app.Notification
import android.support.v4.app.NotificationManagerCompat

class NotificationsImpl(private val notificationManagerCompat: NotificationManagerCompat) : Notifications {

    override fun showNotification(notificationId: Int, notification: Notification) {
        notificationManagerCompat.notify(notificationId, notification)
    }

    override fun updateNotification(notificationId: Int, notification: Notification) {
        notificationManagerCompat.notify(notificationId, notification)
    }

    override fun hideNotification(notificationId: Int) {
        notificationManagerCompat.cancel(notificationId)
    }

    override fun hideNotifications() {
        notificationManagerCompat.cancelAll()
    }
}
