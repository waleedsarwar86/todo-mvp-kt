package com.todo.ui.feature.addedittask

import android.content.res.Resources
import com.todo.R
import com.todo.data.model.TaskModel
import com.todo.data.repository.TodoRepository
import com.todo.device.TaskReminderScheduler
import com.todo.util.StringUtilsImpl
import com.todo.util.UiSchedulersTransformerTestImpl
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import com.todo.ui.feature.addedittask.AddEditTaskContract.View

class AddEditTaskPresenterTest {

    private lateinit var fakeTask: TaskModel

    @Spy
    private val uiSchedulersTransformer = UiSchedulersTransformerTestImpl()

    @Mock
    private lateinit var todoRepository: TodoRepository

    @Mock
    private lateinit var taskReminderScheduler: TaskReminderScheduler

    @Spy
    private var stringUtils = StringUtilsImpl()

    @Mock
    private lateinit var resources: Resources

    @InjectMocks
    private lateinit var addEditTaskPresenter: AddEditTaskPresenter


    private lateinit var view: AddEditTaskContract.View

    @Before
    fun setUp() {

        view = Mockito.mock(AddEditTaskContract.View::class.java)
        addEditTaskPresenter = Mockito.spy(AddEditTaskPresenter())
        addEditTaskPresenter.attachView(view)
        MockitoAnnotations.initMocks(this)

        fakeTask = TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false)

    }


    @Test
    fun createTask_taskWithEmptyTitle_shouldShowSnackBar() {

        // Setup conditions of the test
        fakeTask.title = ""
        `when`(resources!!.getString(R.string.add_edit_task_error_invalid_title)).thenReturn(FAKE_TITLE_REQUIRED_TEST)


        // Execute the code under test
        addEditTaskPresenter.createTask(fakeTask!!)

        // Make assertions on the results
        verify(view).showSnackBar(FAKE_TITLE_REQUIRED_TEST)

    }

    @Test
    fun createTask_validTask_shouldCreateSuccessfully() {

        // Setup conditions of the test
        fakeTask = TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false)
        `when`(todoRepository.createTask(fakeTask)).thenReturn(Single.just(fakeTask))
        `when`(resources!!.getString(R.string.add_edit_task_error_invalid_title)).thenReturn(FAKE_TITLE_REQUIRED_TEST)


        // Execute the code under test
        addEditTaskPresenter.createTask(fakeTask)

        // Make assertions on the results
        verify(view).finishActivity()

    }

    @Test
    fun updateTask_taskWithEmptyTitle_shouldShowSnackBar() {

        // Setup conditions of the test
        fakeTask.title = ""
        `when`(resources!!.getString(R.string.add_edit_task_error_invalid_title)).thenReturn(FAKE_TITLE_REQUIRED_TEST)


        // Execute the code under test
        addEditTaskPresenter.updateTask(fakeTask)

        // Make assertions on the results
        verify(view).showSnackBar(FAKE_TITLE_REQUIRED_TEST)

    }

    @Test
    fun updateTask_validTask_shouldUpdateSuccessfully() {

        // Setup conditions of the test
        fakeTask = TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false)
        `when`(todoRepository.updateTask(fakeTask)).thenReturn(Completable.complete())
        `when`(resources!!.getString(R.string.add_edit_task_error_invalid_title)).thenReturn(FAKE_TITLE_REQUIRED_TEST)


        // Execute the code under test
        addEditTaskPresenter.updateTask(fakeTask)

        // Make assertions on the results
        verify(view).finishActivity()

    }

    @Test
    fun scheduleReminder_reminderValueGreaterThanZero_shouldScheduleTask() {

        // Setup conditions of the test
        fakeTask = TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false)
        fakeTask.reminder = System.currentTimeMillis()

        // Execute the code under test
        addEditTaskPresenter.scheduleReminder(fakeTask)

        // Make assertions on the results
        verify(taskReminderScheduler).scheduleTaskReminder(fakeTask)

    }

    @Test
    fun scheduleReminder_reminderValueZero_shouldNotScheduleTask() {

        // Setup conditions of the test
        fakeTask = TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false)

        // Execute the code under test
        addEditTaskPresenter.scheduleReminder(fakeTask)

        // Make assertions on the results
        verify(taskReminderScheduler, times(0)).scheduleTaskReminder(fakeTask)

    }

    companion object {

        private const val FAKE_TITLE_REQUIRED_TEST = "title_required_test"
    }


}