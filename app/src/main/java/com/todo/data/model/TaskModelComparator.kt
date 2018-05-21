package com.todo.data.model

import java.io.Serializable
import java.util.Comparator

class TaskModelComparator {

    class ByDateComparator : Comparator<TaskModel>, Serializable {

        override fun compare(taskModel1: TaskModel, taskModel2: TaskModel): Int {
            return java.lang.Long.compare(taskModel1.deadline, taskModel2.deadline)
        }
    }

    class ByPriorityComparator : Comparator<TaskModel>, Serializable {

        override fun compare(taskModel1: TaskModel, taskModel2: TaskModel): Int {
            return Integer.compare(taskModel1.priority, taskModel2.priority)
        }
    }

    class ByTitleComparator : Comparator<TaskModel>, Serializable {

        override fun compare(taskModel1: TaskModel, taskModel2: TaskModel): Int {
            return taskModel1.title.compareTo(taskModel2.title, ignoreCase = true)
        }
    }
}
