package com.todo.ui.feature.addedittask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.FragmentManager
import android.support.v7.widget.SwitchCompat
import android.widget.ImageView
import android.widget.TextView

import com.todo.R
import com.todo.data.model.TaskModel
import com.todo.di.activity.ActivityComponent
import com.todo.ui.base.BaseActivity
import com.todo.util.CurrentTimeProvider
import com.todo.util.DateUtils
import com.todo.util.DateUtilsImpl
import com.todo.util.StringUtils
import com.todo.util.StringUtilsImpl
import com.todo.util.UiUtils

import java.util.Calendar

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import butterknife.OnClick

class AddEditTaskActivity : BaseActivity(), AddEditTaskContract.View {

    /********* Dagger Injected Fields   */

    @Inject
    lateinit var presenter: AddEditTaskContract.Presenter

    @Inject
    lateinit var stringUtils: StringUtils

    @Inject
    lateinit var dateUtils: DateUtils

    @Inject
    lateinit var currentTimeProvider: CurrentTimeProvider


    /********* Butterknife Binded Fields   */

    @BindView(R.id.add_edit_task_input_edit_text_task_title)
    private lateinit var textInputEditTextTitle: TextInputEditText

    @BindView(R.id.add_edit_task_text_view_deadline)
    private lateinit var textViewDeadline: TextView

    @BindView(R.id.add_edit_task_text_view_priority)
    private lateinit var textViewPriority: TextView


    @BindView(R.id.add_edit_task_image_view_add_alarm)
    private lateinit var imageViewAddAlarm: ImageView

    @BindView(R.id.add_edit_task_text_view_reminder_label)
    private lateinit var textViewReminderLabel: TextView

    @BindView(R.id.add_edit_task_text_view_reminder_time)
    private lateinit var textViewReminderTime: TextView

    @BindView(R.id.add_edit_task_switch_reminder)
    private lateinit var switchReminder: SwitchCompat

    private lateinit var calendar: Calendar
    private var taskModel: TaskModel? = null


    /********* Lifecycle Methods  */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_edit_task_activity)
        setUnbinder(ButterKnife.bind(this))
        init()
        populateViews()
        presenter.attachView(this)
    }

    /********* BaseActivity Inherited Methods  */

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    /********* Butterknife Binded Methods   */

    @OnClick(R.id.add_edit_task_image_view_back_arrow)
    fun imageViewBackArrowClicked() {
        super.onBackPressed()
    }


    @OnClick(R.id.add_edit_task_linear_layout_deadline)
    fun linearLayoutDeadlineClicked() {

        val dialog = DatePickerDialog(this, { view, year, monthOfYear, dayOfMonth ->

            calendar.set(year, monthOfYear, dayOfMonth)
            taskModel?.deadline = calendar.timeInMillis
            updateDeadlineTextView(calendar.timeInMillis)

        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
        dialog.show()
    }

    @OnClick(R.id.add_edit_task_linear_layout_priority)
    fun linearLayoutPriorityClicked() {
        showSelectPriorityDialog()
    }

    @OnCheckedChanged(R.id.add_edit_task_switch_reminder)
    fun switchReminderChecked(isChecked: Boolean) {
        if (isChecked) {
            setReminderEnabled()
        } else {
            setReminderDisabled()
        }
    }

    @OnClick(R.id.add_edit_task_linear_layout_reminder)
    fun linearLayoutReminderClicked() {
        if (switchReminder.isChecked) {

            val dialog = TimePickerDialog(this, { view, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                taskModel?.reminder = calendar.timeInMillis
                updateReminderTime(calendar.timeInMillis)

            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true)
            dialog.show()
        }
    }

    @OnClick(R.id.add_edit_task_button_done)
    fun buttonDoneClicked() {
        taskModel?.title = textInputEditTextTitle.text.toString()
        if (stringUtils.isEmpty(taskModel?.id ?: "")) {
            taskModel?.let {
                presenter.createTask(it)
            }
        } else {
            taskModel?.let {
                presenter.updateTask(it)
            }
        }
    }

    /********* Member Methods   */

    private fun init() {

        taskModel = intent.getParcelableExtra(EXTRA_TASK)
        calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeProvider.currentTimeMillis


        if (taskModel == null) {
            taskModel = TaskModel(currentTimeProvider.currentTimeMillis, TaskModel.PRIORITY_4)
            taskModel?.deadline = calendar.timeInMillis
        } else {
            calendar.timeInMillis = taskModel?.deadline ?: calendar.timeInMillis
        }

        setReminderDisabled()

    }

    private fun populateViews() {
        textInputEditTextTitle.setText(taskModel?.title)
        updateDeadlineTextView(taskModel?.deadline ?: 0)
        updatePriorityTextView(taskModel?.priority ?: TaskModel.PRIORITY_4)
        updateReminderTime(taskModel?.deadline ?: 0)

    }


    private fun updateDeadlineTextView(deadline: Long) {
        textViewDeadline.text = dateUtils.getDisplayDate(deadline)
    }

    private fun updatePriorityTextView(priority: Int) {
        taskModel?.priority = priority
        textViewPriority.text = UiUtils.getPriorityString(this, priority)
    }

    private fun updateReminderTime(deadline: Long) {
        textViewReminderTime.text = dateUtils.getDisplayTime(deadline)
    }


    private fun setReminderEnabled() {
        UiUtils.enableWithAlpha(imageViewAddAlarm, textViewReminderLabel, textViewReminderTime)
    }

    private fun setReminderDisabled() {
        UiUtils.disableWithAlpha(imageViewAddAlarm, textViewReminderLabel, textViewReminderTime)
    }


    private fun showSelectPriorityDialog() {
        val fragmentManager = supportFragmentManager
        val selectPriorityDialog = SelectPriorityDialog.newInstance(object : SelectPriorityDialog.SelectPriorityDialogCallback {
            override fun onPrioritySelected(priority: Int) {
                updatePriorityTextView(priority)
            }
        })
        selectPriorityDialog.show(fragmentManager, SelectPriorityDialog.TAG)
    }

    companion object {


        /********* Constants Fields   */

        private const val EXTRA_TASK = "extra_task"

        /********* Static Methods  */


        fun startActivity(context: Context) {
            val starter = Intent(context, AddEditTaskActivity::class.java)
            context.startActivity(starter)
        }

        fun startActivity(context: Context, taskModel: TaskModel) {
            val starter = Intent(context, AddEditTaskActivity::class.java)
            starter.putExtra(EXTRA_TASK, taskModel)
            context.startActivity(starter)
        }
    }

}
