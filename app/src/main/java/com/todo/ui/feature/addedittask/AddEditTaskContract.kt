package com.todo.ui.feature.addedittask

import com.todo.data.model.TaskModel
import com.todo.ui.base.BaseContract

interface AddEditTaskContract {

    interface View : BaseContract.View

    interface Presenter : BaseContract.Presenter<View> {
        fun createTask(taskModel: TaskModel)
        fun updateTask(taskModel: TaskModel)
    }
}
