package com.todo.ui.feature.tasks

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView

import com.todo.R
import com.todo.data.model.TaskModel
import com.todo.util.DateUtils
import com.todo.util.UiUtils

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.reactivex.subjects.Subject

internal class TaskItemViewHolder(itemView: View, private val dateUtils: DateUtils, private val clickSubject: Subject<TaskModel>) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.task_delete_layout)
    lateinit var taskDeleteLayout: FrameLayout

    @BindView(R.id.task_completed_layout)
    lateinit var taskCompletedLayout: FrameLayout

    @BindView(R.id.task_layout)
    lateinit var taskLayout: ConstraintLayout

    @BindView(R.id.task_view_priority)
    lateinit var viewPriority: View

    @BindView(R.id.task_textview_title)
    lateinit var textViewTitle: TextView

    @BindView(R.id.task_textview_deadline)
    lateinit var textViewDeadline: TextView

    private lateinit var taskModel: TaskModel

    init {
        ButterKnife.bind(this, itemView)
    }

    fun setItem(taskModel: TaskModel) {
        this.taskModel = taskModel
        textViewTitle.text = taskModel.title
        textViewDeadline.text = dateUtils.getDisplayDate(taskModel.deadline)
        viewPriority.setBackgroundResource(UiUtils.getPriorityColorRes(taskModel.priority))

    }


    @OnClick(R.id.task_layout)
    fun onTaskClick() {
        clickSubject.onNext(taskModel)
    }


}

