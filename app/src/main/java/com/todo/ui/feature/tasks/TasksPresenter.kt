package com.todo.ui.feature.tasks

import android.content.res.Resources

import com.todo.BuildConfig
import com.todo.data.model.TaskModel
import com.todo.data.model.TaskModelComparator
import com.todo.data.repository.TodoRepository
import com.todo.ui.base.BasePresenter

import java.util.Collections

import javax.inject.Inject

import io.reactivex.functions.Action
import timber.log.Timber

class TasksPresenter : BasePresenter<TasksContract.View>(), TasksContract.Presenter {

    /********* Dagger Injected Fields   */

    @Inject
    lateinit var todoRepository: TodoRepository

    @Inject
    lateinit var resources: Resources

    private var tasksSortType: TasksSortType

    /********* Constructors  */

    init {
        tasksSortType = TasksSortType.BY_DATE
    }

    override fun setTasksSortType(tasksSortType: TasksSortType) {
        this.tasksSortType = tasksSortType
    }

    /********* LauncherContract.Presenter Interface Methods  */

    override fun addTask() {
        view?.showAddEditTaskActivity()
    }

    override fun getTasks() {
        addDisposable(todoRepository.tasks
                .compose(uiSchedulersTransformer.applySchedulersToObservable())
                .map { tasks ->
                    when (tasksSortType) {
                        TasksSortType.BY_PRIORITY -> Collections.sort(tasks, TaskModelComparator.ByPriorityComparator())
                        TasksSortType.BY_TITLE -> Collections.sort(tasks, TaskModelComparator.ByTitleComparator())
                        TasksSortType.BY_DATE -> Collections.sort(tasks, TaskModelComparator.ByDateComparator())
                    }
                    tasks
                }
                .subscribe({ tasks ->
                    if (tasks.isEmpty()) {
                        view?.showTasksEmptyView()
                    } else {
                        view?.hideTasksEmptyView()
                        view?.showTasks(tasks)
                    }
                }, { Timber.e(it) }))
    }

    override fun logout() {
        if (BuildConfig.DEBUG) {
            addDisposable(todoRepository.deleteTasks().subscribe {
                todoRepository.logout()
                view?.showLoginUi()
            })
        } else {
            todoRepository.logout()
            view?.showLoginUi()
        }
    }

    override fun deleteTask(taskModel: TaskModel) {
        addDisposable(todoRepository.deleteTask(taskModel)
                .compose(uiSchedulersTransformer.applySchedulersToCompletable())
                .subscribe({ this.getTasks() }, { Timber.e(it) }))
    }

    override fun updateTask(taskModel: TaskModel) {
        addDisposable(todoRepository.updateTask(taskModel)
                .compose(uiSchedulersTransformer.applySchedulersToCompletable())
                .subscribe({ Timber.d("TaskModel updated successfully") }) { throwable ->
                    getTasks()
                    Timber.e(throwable)
                })
    }


}
