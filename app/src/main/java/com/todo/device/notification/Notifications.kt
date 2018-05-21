package com.todo.device.notification

import android.app.Notification

interface Notifications {


    fun showNotification(notificationId: Int, notification: Notification)

    fun updateNotification(notificationId: Int, notification: Notification)

    fun hideNotification(notificationId: Int)

    fun hideNotifications()
}
