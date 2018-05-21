package com.todo.ui.feature.tasks

import com.todo.data.model.TaskModel
import com.todo.ui.base.BaseContract

interface TasksContract {

    interface View : BaseContract.View {

        fun showAddEditTaskActivity()

        fun showLoginUi()

        fun showTasks(taskModels: List<TaskModel>)

        fun showTasksEmptyView()

        fun hideTasksEmptyView()

    }

    interface Presenter : BaseContract.Presenter<TasksContract.View> {


        fun setTasksSortType(tasksSortType: TasksSortType)

        fun addTask()

        fun getTasks()

        fun logout()

        fun deleteTask(taskModel: TaskModel)

        fun updateTask(taskModel: TaskModel)
    }
}
