package com.todo.ui.feature.tasks

import android.arch.paging.PagedListAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.todo.R
import com.todo.data.model.TaskModel
import com.todo.util.DateUtils
import java.util.concurrent.TimeUnit

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * @author Waleed Sarwar
 * @since Dec 11, 2017
 */

internal class TasksAdapter internal constructor(private val dateUtils: DateUtils, private val taskModels: MutableList<TaskModel>)
    : PagedListAdapter<TaskModel, TaskItemViewHolder>(TaskModel.DIFF_CALLBACK) {

    private val onItemClickSubject = PublishSubject.create<TaskModel>()

    /********* RecyclerView.Adapter Inherited Methods  */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskItemViewHolder(itemView, dateUtils, onItemClickSubject)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        holder.setItem(taskModels[position])
    }

    override fun getItemCount(): Int {
        return taskModels.size
    }

    /********* Member Methods   */

    fun add(index: Int, taskModel: TaskModel) {

        if (index > taskModels.size) {
            throw IllegalStateException("Index is no in range")
        } else {
            taskModels.add(index, taskModel)
            notifyItemInserted(index)
        }
    }

    fun addAll(collection: Collection<TaskModel>) {
        val position = taskModels.size
        taskModels.addAll(collection)
        notifyItemRangeInserted(position, collection.size)

    }


    fun remove(taskModel: TaskModel): TaskModel {

        val index = taskModels.indexOf(taskModel)
        if (taskModels.remove(taskModel)) {
            notifyItemRemoved(index)

        }
        return taskModel
    }

    fun remove(index: Int): TaskModel? {

        var taskModel: TaskModel? = null
        if (index >= taskModels.size) {
            throw IllegalStateException("Index is not in range")
        } else {
            taskModel = taskModels.removeAt(index)
            notifyItemRemoved(index)
        }
        return taskModel

    }

    internal operator fun get(index: Int): TaskModel {
        if (index < 0 || index >= taskModels.size) {
            throw IllegalStateException("Index is not in range")
        }
        return taskModels[index]
    }


    fun clear() {
        taskModels.clear()
        notifyDataSetChanged()
    }


    fun onItemClick(): Observable<TaskModel> {
        return onItemClickSubject.throttleFirst(CLICK_THROTTLE_WINDOW_MILLIS, TimeUnit.MILLISECONDS)
    }

    companion object {
        private const val CLICK_THROTTLE_WINDOW_MILLIS = 300L
    }

}
