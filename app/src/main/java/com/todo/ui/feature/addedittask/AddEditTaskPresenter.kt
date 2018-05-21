package com.todo.ui.feature.addedittask

import android.content.res.Resources
import android.support.annotation.VisibleForTesting

import com.todo.R
import com.todo.data.model.TaskModel
import com.todo.data.repository.TodoRepository
import com.todo.device.TaskReminderScheduler
import com.todo.ui.base.BasePresenter
import com.todo.util.StringUtils
import com.todo.util.StringUtilsImpl
import io.reactivex.functions.Consumer

import javax.inject.Inject

class AddEditTaskPresenter : BasePresenter<AddEditTaskContract.View>(), AddEditTaskContract.Presenter {

    /********* Dagger Injected Fields   */

    @Inject
    lateinit var todoRepository: TodoRepository

    @Inject
    lateinit var taskReminderScheduler: TaskReminderScheduler

    @Inject
    lateinit var stringUtils: StringUtils

    @Inject
    lateinit var resources: Resources

    override fun createTask(taskModel: TaskModel) {
        if (stringUtils.isNotEmpty(taskModel.title)) {
            addDisposable(todoRepository.createTask(taskModel)
                    .subscribe(Consumer<TaskModel> { this.scheduleReminder(it) }))
            view?.finishActivity()
        } else {
            view?.showSnackBar(resources.getString(R.string.add_edit_task_error_invalid_title))
        }
    }

    override fun updateTask(taskModel: TaskModel) {
        if (stringUtils.isNotEmpty(taskModel.title)) {
            todoRepository.updateTask(taskModel)
            view?.finishActivity()
        } else {
            view?.showSnackBar(resources.getString(R.string.add_edit_task_error_invalid_title))
        }
    }

    @VisibleForTesting
    protected fun scheduleReminder(taskModel: TaskModel) {
        if (taskModel.reminder > 0) {
            taskReminderScheduler.scheduleTaskReminder(taskModel)
        }
    }
}
