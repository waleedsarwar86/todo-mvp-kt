package com.todo.device

import com.todo.data.model.TaskModel

interface TaskReminderScheduler {


    fun scheduleTaskReminder(taskModel: TaskModel): Int

    fun cancelTaskReminder(taskId: String): Int

    companion object {

        val WINDOW = 60 // in seconds
    }
}
