package com.todo.data.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import com.todo.data.model.TaskModel
import com.todo.data.source.UserRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class TodoRepositoryImplTest {

    @Mock
    private lateinit var userRemoteDataSource: UserRemoteDataSource
    @Mock
    private lateinit var mockAuthResult: AuthResult

    private lateinit var fakeTaskModel: TaskModel
    private lateinit var todoRepository: TodoRepositoryImpl

    @Before
    fun setUp() {

        fakeTaskModel = TaskModel("id", "title", 0, 1, false)
        todoRepository = TodoRepositoryImpl(userRemoteDataSource)
    }

    @Test
    fun isUserLoggedIn_shouldReturnTrue() {

        val firebaseUser = Mockito.mock(FirebaseUser::class.java)
        `when`(userRemoteDataSource.currentUser).thenReturn(firebaseUser)

        val testObserver = todoRepository.isUserLoggedIn.test()

        testObserver.apply {
            assertNoErrors()
            assertValue(true)
            assertComplete()
        }

    }

    @Test
    fun isUserLoggedIn_shouldReturnFalse() {
        `when`(userRemoteDataSource.currentUser).thenReturn(null)

        val testObserver = todoRepository.isUserLoggedIn.test()

        testObserver.apply {
            assertNoErrors()
            assertValue(false)
            assertComplete()
        }

    }

    @Test
    fun login_shouldCompleteWithNoErrors() {

        `when`(userRemoteDataSource.login(anyString(), anyString())).thenReturn(Single.just(mockAuthResult))

        val testObserver = todoRepository.login(anyString(), anyString()).test()

        testObserver.apply {
            assertNoErrors()
            assertComplete()
            dispose()
        }
    }

    @Test
    fun login_shouldFailWithError() {

        val exception = Exception()
        `when`(userRemoteDataSource.login(anyString(), anyString())).thenReturn(Single.error(exception))

        val testObserver = todoRepository.login(anyString(), anyString()).test()

        testObserver.apply {
            assertError(exception)
            dispose()
        }

    }

    @Test
    fun register_shouldCompleteWithNoError() {

        `when`(userRemoteDataSource.register(anyString(), anyString())).thenReturn(Single.just(mockAuthResult))

        val testObserver = todoRepository.register(anyString(), anyString()).test()

        testObserver.apply {
            assertNoErrors()
            assertComplete()
            dispose()
        }
    }

    @Test
    fun register_shouldFailWithError() {

        val exception = Exception()
        `when`(userRemoteDataSource.register(anyString(), anyString())).thenReturn(Single.error(exception))

        val testObserver = todoRepository.register(anyString(), anyString()).test()

        testObserver.apply {
            assertError(exception)
            dispose()
        }

    }

    @Test
    fun createTask_shouldCompleteWithCreatedTask() {

        `when`(userRemoteDataSource.createTask(fakeTaskModel)).thenReturn(Single.just(fakeTaskModel))

        val testObserver = todoRepository.createTask(fakeTaskModel).test()


        testObserver.apply {
            assertNoErrors()
            assertValue(fakeTaskModel)
            assertComplete()
            dispose()
        }
    }

    @Test
    fun createTask_shouldFailWithError() {

        val exception = Exception()
        `when`(userRemoteDataSource.createTask(fakeTaskModel)).thenReturn(Single.error(exception))

        val testObserver = todoRepository.createTask(fakeTaskModel).test()

        testObserver.apply {
            assertError(exception)
            dispose()
        }

    }

    @Test
    fun updateTask_shouldCompleteWithNoErrors() {

        `when`(userRemoteDataSource.updateTask(fakeTaskModel)).thenReturn(Completable.complete())

        val testObserver = todoRepository.updateTask(fakeTaskModel).test()

        testObserver.apply {
            assertNoErrors()
            assertComplete()
            dispose()
        }
    }

    @Test
    fun updateTask_shouldFailWithError() {

        val exception = Exception()
        `when`(userRemoteDataSource.updateTask(fakeTaskModel)).thenReturn(Completable.error(exception))

        val testObserver = todoRepository.updateTask(fakeTaskModel).test()

        testObserver.apply {
            assertError(exception)
            dispose()
        }


    }

    @Test
    fun deleteTask_shouldCompleteWithNoErrors() {

        `when`(userRemoteDataSource.deleteTask(fakeTaskModel)).thenReturn(Completable.complete())

        val testObserver = todoRepository.deleteTask(fakeTaskModel).test()

        testObserver.apply {
            assertNoErrors()
            assertComplete()
            dispose()
        }
    }

    @Test
    fun deleteTask_shouldFailWithError() {

        val exception = Exception()
        `when`(userRemoteDataSource.deleteTask(fakeTaskModel)).thenReturn(Completable.error(exception))

        val testObserver = todoRepository.deleteTask(fakeTaskModel).test()

        testObserver.apply {
            assertError(exception)
            dispose()
        }

    }

    @Test
    fun getTask_shouldReturnMockedTasks() {

        val mockedTaskModels = emptyList<TaskModel>()
        `when`(userRemoteDataSource.tasks).thenReturn(Observable.just(mockedTaskModels))

        val testObserver = todoRepository.tasks.test()

        testObserver.apply {
            assertNoErrors()
            assertValue(mockedTaskModels)
            dispose()
        }
    }

    @Test
    fun getTask_shouldFailWithError() {

        val exception = Exception()
        `when`(userRemoteDataSource.tasks).thenReturn(Observable.error(exception))

        val testObserver = todoRepository.tasks.test()

        testObserver.apply {
            assertError(exception)
            dispose()
        }
    }

}