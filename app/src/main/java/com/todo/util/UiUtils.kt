package com.todo.util

import android.content.Context
import android.view.View

import com.todo.R
import com.todo.data.model.TaskModel

object UiUtils {

    fun getPriorityColorRes(priority: Int): Int {

        return when (priority) {
            TaskModel.PRIORITY_1 -> R.color.task_priority_crucial
            TaskModel.PRIORITY_2 -> R.color.task_priority_high
            TaskModel.PRIORITY_3 -> R.color.task_priority_normal
            TaskModel.PRIORITY_4 -> R.color.task_priority_low
            else -> R.color.all_white
        }
    }


    fun getPriorityString(context: Context, priority: Int): String {

        return when (priority) {

            TaskModel.PRIORITY_1 -> context.getString(R.string.all_label_priority_crucial)
            TaskModel.PRIORITY_2 -> context.getString(R.string.all_label_priority_high)
            TaskModel.PRIORITY_3 -> context.getString(R.string.all_label_priority_normal)
            TaskModel.PRIORITY_4 -> context.getString(R.string.all_label_priority_low)
            else -> context.getString(R.string.all_label_priority_low)
        }
    }

    fun disableWithAlpha(vararg views: View) {
        for (v in views) {
            v.isEnabled = false
            v.alpha = 0.2f
        }
    }

    fun enableWithAlpha(vararg views: View) {
        for (v in views) {
            v.isEnabled = true
            v.alpha = 1f
        }
    }

}
