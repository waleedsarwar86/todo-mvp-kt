package com.todo.util

interface DateUtils {

    fun getDisplayDate(deadline: Long): String

    fun getDisplayTime(reminder: Long): String
}
