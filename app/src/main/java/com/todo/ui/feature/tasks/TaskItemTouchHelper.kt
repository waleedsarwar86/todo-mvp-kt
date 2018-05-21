package com.todo.ui.feature.tasks

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View

class TaskItemTouchHelper internal constructor(dragDirs: Int, swipeDirs: Int, private val callback: TaskItemTouchHelperCallback) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.LEFT) {
            callback.onTaskDeleted(position)
        } else if (direction == ItemTouchHelper.RIGHT) {
            callback.onTaskCompleted(position)
        }
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && viewHolder is TaskItemViewHolder) {

            if (dX > 0) {
                viewHolder.taskDeleteLayout.visibility = View.GONE
                viewHolder.taskCompletedLayout.visibility = View.VISIBLE
            } else {
                viewHolder.taskCompletedLayout.visibility = View.GONE
                viewHolder.taskDeleteLayout.visibility = View.VISIBLE
            }
            ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView, viewHolder.taskLayout, dX, dY,
                    actionState, isCurrentlyActive)
        }
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder) {

        if (viewHolder is TaskItemViewHolder) {
            ItemTouchHelper.Callback.getDefaultUIUtil().clearView(viewHolder.taskLayout)
        }
    }

    interface TaskItemTouchHelperCallback {

        fun onTaskDeleted(position: Int)

        fun onTaskCompleted(position: Int)
    }
}
