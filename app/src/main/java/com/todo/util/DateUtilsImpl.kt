package com.todo.util

import android.text.format.DateUtils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class DateUtilsImpl(private val currentTimeProvider: CurrentTimeProvider) : com.todo.util.DateUtils {

    override fun getDisplayDate(deadline: Long): String {

        return DateUtils.getRelativeTimeSpanString(deadline, currentTimeProvider.currentTimeMillis, DateUtils.DAY_IN_MILLIS).toString()
    }

    override fun getDisplayTime(reminder: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = reminder
        val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }

}
