package com.todo.ui.feature.launcher

import com.todo.data.repository.TodoRepository
import com.todo.util.UiSchedulersTransformerTestImpl
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy


class LauncherPresenterTest {

    @Spy
    private val uiSchedulersTransformer = UiSchedulersTransformerTestImpl()

    @Mock
    private lateinit var todoRepository: TodoRepository

    @InjectMocks
    private lateinit var launcherPresenter: LauncherPresenter

    private lateinit var view: LauncherContract.View

    @Before
    fun setUp() {
        view = mock(LauncherContract.View::class.java)
        launcherPresenter = LauncherPresenter()
        launcherPresenter.attachView(view)
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun showNextActivity_ShouldShowLoginActivity() {

        // Setup conditions of the test
        `when`(todoRepository.isUserLoggedIn).thenReturn(Single.just(java.lang.Boolean.FALSE))

        // Execute the code under test
        launcherPresenter.showNextActivity()

        // Make assertions on the results..
        verify(view, times(1)).showLoginActivity()
    }

    @Test
    fun showNextActivity_ShouldShowTaskActivity() {

        // Setup conditions of the test
        `when`(todoRepository.isUserLoggedIn).thenReturn(Single.just(java.lang.Boolean.TRUE))

        // Execute the code under test
        launcherPresenter.showNextActivity()

        // Make assertions on the results..
        verify(view, times(1)).showTasksActivity()
    }
}