package com.todo.device.notification

import android.app.Notification
import android.support.v4.app.NotificationManagerCompat

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class NotificationsImplTest {

    private lateinit var notificationsImpl: NotificationsImpl
    private lateinit var notificationManagerCompat: NotificationManagerCompat
    private lateinit var notification: Notification

    @Before
    fun setUp() {
        notification = mock(Notification::class.java)
        notificationManagerCompat = mock(NotificationManagerCompat::class.java)
        notificationsImpl = NotificationsImpl(notificationManagerCompat)
    }


    @Test
    fun shouldShowNewNotification() {

        notificationsImpl.showNotification(TEST_NOTIFICATION_ID, notification)

        verify<NotificationManagerCompat>(notificationManagerCompat, times(1)).notify(TEST_NOTIFICATION_ID, notification)
        verifyNoMoreInteractions(notificationManagerCompat)

    }

    @Test
    fun shouldUpdateExistingNotification() {

        notificationsImpl.updateNotification(TEST_NOTIFICATION_ID, notification)

        verify<NotificationManagerCompat>(notificationManagerCompat, times(1)).notify(TEST_NOTIFICATION_ID, notification)
        verifyNoMoreInteractions(notificationManagerCompat)
    }

    @Test
    fun shouldHideExistingNotification() {
        notificationsImpl.hideNotification(TEST_NOTIFICATION_ID)

        verify<NotificationManagerCompat>(notificationManagerCompat, times(1)).cancel(TEST_NOTIFICATION_ID)
        verifyNoMoreInteractions(notificationManagerCompat)
    }

    @Test
    fun shouldHideAllNotifications() {
        notificationsImpl.hideNotifications()

        verify<NotificationManagerCompat>(notificationManagerCompat, times(1)).cancelAll()
        verifyNoMoreInteractions(notificationManagerCompat)
    }

    companion object {
        const val TEST_NOTIFICATION_ID = 1000
    }

}