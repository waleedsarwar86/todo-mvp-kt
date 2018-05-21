package com.todo.ui.feature.tasks

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.*
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.todo.R
import com.todo.data.model.TaskModel
import com.todo.di.activity.ActivityComponent
import com.todo.ui.base.BaseActivity
import com.todo.ui.feature.addedittask.AddEditTaskActivity
import com.todo.ui.feature.login.LoginActivity
import com.todo.util.DateUtils
import java.util.*
import javax.inject.Inject

class TasksActivity : BaseActivity(), TasksContract.View, TaskItemTouchHelper.TaskItemTouchHelperCallback {

    /********* Dagger Injected Fields   */

    @Inject
    lateinit var presenter: TasksContract.Presenter

    @Inject
    lateinit var dateUtils: DateUtils

    /********* Butterknife View Binding Fields   */

    @BindView(R.id.tasks_recyclerview_tasks)
    lateinit var recyclerViewTasks: RecyclerView

    @BindView(R.id.tasks_linear_layout_empty)
    lateinit var linearLayoutEmpty: LinearLayout

    private lateinit var tasksAdapter: TasksAdapter

    /********* Lifecycle Methods Implementation  */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tasks_activity)
        setUnbinder(ButterKnife.bind(this))
        initializeToolbar()
        initializeRecyclerView()
        presenter.attachView(this)
        presenter.setTasksSortType(TasksSortType.BY_DATE)
        presenter.getTasks()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tasks_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.tasks_menu_item_by_date -> {
                presenter.setTasksSortType(TasksSortType.BY_DATE)
                presenter.getTasks()
                return true
            }
            R.id.tasks_menu_item_by_priority -> {
                presenter.setTasksSortType(TasksSortType.BY_PRIORITY)
                presenter.getTasks()
                return true
            }
            R.id.tasks_menu_item_by_name -> {
                presenter.setTasksSortType(TasksSortType.BY_TITLE)
                presenter.getTasks()
                return true
            }
            R.id.tasks_menu_item_logout -> {
                presenter.logout()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    /********* DaggerActivity Inherited Methods  */
    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    /********* TasksContract.View Inherited Methods  */

    override fun showAddEditTaskActivity() {
        AddEditTaskActivity.startActivity(this)
    }

    override fun showLoginUi() {
        LoginActivity.startActivity(this)
    }

    override fun showTasks(taskModels: List<TaskModel>) {
        tasksAdapter.clear()
        tasksAdapter.addAll(taskModels)
    }

    override fun showTasksEmptyView() {
        recyclerViewTasks.visibility = View.GONE
        linearLayoutEmpty.visibility = View.VISIBLE
    }

    override fun hideTasksEmptyView() {

        linearLayoutEmpty.visibility = View.GONE
        recyclerViewTasks.visibility = View.VISIBLE
    }


    /********* TaskItemTouchHelper.TaskItemTouchHelperCallback Implemented Methods  */

    override fun onTaskDeleted(position: Int) {
        val removedTaskModel = tasksAdapter.remove(position)
        addDisposable(showSnackBar(R.string.tasks_message_deleted, R.string.tasks_action_undo)
                .subscribe { undo ->
                    if (undo) {
                        tasksAdapter.add(position, removedTaskModel)
                    } else {
                        // remove from backend
                        presenter.deleteTask(removedTaskModel)
                    }
                })
    }

    override fun onTaskCompleted(position: Int) {
        presenter.updateTask(tasksAdapter.get(position))
        val completedTaskModel = tasksAdapter.remove(position)
        addDisposable(showSnackBar(R.string.tasks_message_completed, R.string.tasks_action_undo)
                .subscribe { undo ->
                    if (undo) {
                        tasksAdapter.add(position, completedTaskModel)
                    } else {
                        completedTaskModel?.completed = true
                        presenter.updateTask(completedTaskModel)
                    }
                })
    }

    /********* Butterknife Binded Methods   */

    @OnClick(R.id.tasks_button_add_task)
    fun buttonAddTaskClicked() {
        presenter.addTask()
    }

    /********* Member Methods   */

    private fun initializeToolbar() {
        val myToolbar = findViewById<Toolbar>(R.id.tasks_toolbar)
        setSupportActionBar(myToolbar)
    }

    private fun initializeRecyclerView() {

        tasksAdapter = TasksAdapter(dateUtils, ArrayList())
        recyclerViewTasks.adapter = tasksAdapter
        addDisposable(tasksAdapter.onItemClick().subscribe({ this.onTaskSelected(it) }))

        val linearLayoutManager = LinearLayoutManager(this)
        recyclerViewTasks.layoutManager = linearLayoutManager
        recyclerViewTasks.itemAnimator = DefaultItemAnimator()
        recyclerViewTasks.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val itemTouchHelperCallback = TaskItemTouchHelper(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, this)
        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerViewTasks)

    }

    private fun onTaskSelected(taskModel: TaskModel) {
        AddEditTaskActivity.startActivity(this, taskModel)
    }

    companion object {


        /********* Static Methods  */


        fun startActivity(context: Context) {
            val intent = Intent(context, TasksActivity::class.java)
            context.startActivity(intent)
        }

        fun getIntentForNotification(context: Context): Intent {
            val intent = Intent(context, TasksActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            return intent
        }
    }


}
