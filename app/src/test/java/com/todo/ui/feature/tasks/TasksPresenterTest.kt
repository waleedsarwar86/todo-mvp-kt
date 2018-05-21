package com.todo.ui.feature.tasks

import com.todo.data.model.TaskModel
import com.todo.data.model.TaskModelComparator
import com.todo.data.repository.TodoRepository
import com.todo.util.UiSchedulersTransformerTestImpl
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import java.util.*

class TasksPresenterTest {


    private lateinit var fakeTask: TaskModel
    private lateinit var fakeTasks: MutableList<TaskModel>

    @Spy
    private val uiSchedulersTransformer = UiSchedulersTransformerTestImpl()

    @Mock
    private lateinit var todoRepository: TodoRepository

    @InjectMocks
    private lateinit var tasksPresenter: TasksPresenter

    private lateinit var view: TasksContract.View

    @Before
    fun setUp() {

        view = Mockito.mock(TasksContract.View::class.java)
        tasksPresenter = Mockito.spy(TasksPresenter())
        tasksPresenter.attachView(view)
        MockitoAnnotations.initMocks(this)

        fakeTask = TaskModel("Id", "Title", System.currentTimeMillis(), TaskModel.PRIORITY_4, false)

        fakeTasks = ArrayList()
        var taskModel = TaskModel("Id1", "Title3", System.currentTimeMillis(), TaskModel.PRIORITY_2, false)
        fakeTasks.add(taskModel)
        taskModel = TaskModel("Id2", "Title2", System.currentTimeMillis(), TaskModel.PRIORITY_1, true)
        fakeTasks.add(taskModel)
        taskModel = TaskModel("Id2", "Title2", System.currentTimeMillis(), TaskModel.PRIORITY_3, false)
        fakeTasks.add(taskModel)


    }

    @Test
    fun addTask_shouldShowAddEditTaskActivity() {

        tasksPresenter.addTask()

        verify(view).showAddEditTaskActivity()
    }

    @Test
    fun getTask_shouldShowEmptyView() {


        // Setup conditions of the test
        `when`(todoRepository.tasks).thenReturn(Observable.just(emptyList()))

        // Execute the code under test
        tasksPresenter.getTasks()

        // Make assertions on the results

        verify(view).showTasksEmptyView()

    }

    @Test
    fun getTask_byDateSort_shouldShowTasks() {


        // Setup conditions of the test
        `when`(todoRepository.tasks).thenReturn(Observable.just(fakeTasks))


        // Execute the code under test
        tasksPresenter.getTasks()

        // Make assertions on the results
        verify(view).showTasks(fakeTasks)

    }

    @Test
    fun getTask_byPriority_shouldShowTasks() {


        // Setup conditions of the test
        `when`(todoRepository.tasks).thenReturn(Observable.just(fakeTasks))

        // Execute the code under test
        tasksPresenter.setTasksSortType(TasksSortType.BY_PRIORITY)
        tasksPresenter.getTasks()

        // Make assertions on the results

        Collections.sort(fakeTasks, TaskModelComparator.ByPriorityComparator())
        verify(view).showTasks(fakeTasks)

    }

    @Test
    fun getTask_byTitle_shouldShowTasks() {


        // Setup conditions of the test
        `when`(todoRepository.tasks).thenReturn(Observable.just(fakeTasks))

        // Execute the code under test
        tasksPresenter.setTasksSortType(TasksSortType.BY_TITLE)
        tasksPresenter.getTasks()

        // Make assertions on the results

        Collections.sort(fakeTasks, TaskModelComparator.ByTitleComparator())
        verify(view).showTasks(fakeTasks)

    }

    @Test
    fun deleteTask_shouldComplete() {


        // Setup conditions of the test
        `when`(todoRepository.deleteTask(fakeTask)).thenReturn(Completable.complete())
        `when`(todoRepository.tasks).thenReturn(Observable.just(fakeTasks))

        // Execute the code under test
        tasksPresenter.deleteTask(fakeTask)

        // Make assertions on the results

        verify(todoRepository).deleteTask(fakeTask)
        verify<TasksPresenter>(tasksPresenter).getTasks()

    }

    @Test
    fun updateTask_shouldComplete() {


        // Setup conditions of the test
        `when`(todoRepository.updateTask(fakeTask)).thenReturn(Completable.complete())
        `when`(todoRepository.tasks).thenReturn(Observable.just(fakeTasks))

        // Execute the code under test
        tasksPresenter.updateTask(fakeTask)

        // Make assertions on the results

        verify(todoRepository).updateTask(fakeTask)

    }

    @Test
    fun updateTask_shouldThrowError() {


        // Setup conditions of the test
        `when`(todoRepository.updateTask(fakeTask)).thenReturn(Completable.error(Exception()))
        `when`(todoRepository.tasks).thenReturn(Observable.just(fakeTasks))

        // Execute the code under test
        tasksPresenter.updateTask(fakeTask)

        // Make assertions on the results

        verify(tasksPresenter).getTasks()

    }

}
