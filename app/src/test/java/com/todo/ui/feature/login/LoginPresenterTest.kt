package com.todo.ui.feature.login

import android.content.res.Resources
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.todo.data.repository.TodoRepository
import com.todo.util.StringUtilsImpl
import com.todo.util.UiSchedulersTransformerTestImpl
import com.todo.util.validation.validator.RulesFactory
import com.todo.util.validation.validator.RulesValidator
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy

class LoginPresenterTest {


    private lateinit var fakeEmailObservable: Observable<String>
    private lateinit var fakePasswordObservable: Observable<String>

    @Spy
    private val uiSchedulersTransformer = UiSchedulersTransformerTestImpl()

    @Mock
    private lateinit var todoRepository: TodoRepository

    @Mock
    private lateinit var resources: Resources

    @Spy
    private val stringUtils = StringUtilsImpl()

    @Mock
    private lateinit var rulesValidator: RulesValidator

    @Mock
    private lateinit var rulesFactory: RulesFactory

    @InjectMocks
    private lateinit var loginPresenter: LoginPresenter

    private lateinit var view: LoginContract.View


    @Before
    fun setUp() {

        view = Mockito.mock(LoginContract.View::class.java)
        loginPresenter = LoginPresenter()
        loginPresenter.attachView(view)
        initMocks(this)

        fakeEmailObservable = Observable.just(FAKE_EMAIL)
        fakePasswordObservable = Observable.just(FAKE_PASSWORD)

        `when`(rulesFactory.createEmailFieldRules()).thenReturn(emptyList())
        `when`(rulesFactory.createPasswordFieldRules()).thenReturn(emptyList())

    }

    @After
    fun tearDown() {
        loginPresenter.detachView()
    }

    @Test
    fun validateEmail_validEmail_shouldHideEmailErrorAndReturnTrue() {

        // Setup conditions of the test
        `when`(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(""))

        // Execute the code under test
        val testObserver = loginPresenter.validateEmail(fakeEmailObservable).test()

        // Make assertions on the results
        verify(view).hideEmailError()

        testObserver.apply {
            assertNoErrors()
            assertValue(java.lang.Boolean.TRUE)
            assertComplete()
            dispose()
        }


    }

    @Test
    fun validateEmail_invalidEmail_shouldShowEmailErrorAndReturnFalse() {

        // Setup conditions of the test
        `when`(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(FAKE_FIELD_ERROR))

        // Execute the code under test
        val testObserver = loginPresenter.validateEmail(fakeEmailObservable).test()

        // Make assertions on the results

        verify(view).showEmailError(FAKE_FIELD_ERROR)
        testObserver.apply {
            assertNoErrors()
            assertValue(java.lang.Boolean.FALSE)
            assertComplete()
            dispose()
        }


    }

    @Test
    fun validatePassword_validEmail_shouldHidePasswordErrorAndReturnTrue() {

        // Setup conditions of the test
        `when`(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(""))

        // Execute the code under test
        val testObserver = loginPresenter.validatePassword(fakePasswordObservable).test()

        // Make assertions on the results
        verify<LoginContract.View>(view).hidePasswordError()
        testObserver.apply {
            assertNoErrors()
            assertValue(java.lang.Boolean.TRUE)
            assertComplete()
            dispose()
        }


    }

    @Test
    fun validatePassword_invalidPassword_shouldShowPasswordErrorAndReturnFalse() {

        // Setup conditions of the test
        `when`(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(FAKE_FIELD_ERROR))

        // Execute the code under test
        val testObserver = loginPresenter.validatePassword(fakePasswordObservable).test()

        // Make assertions on the results
        verify(view).showPasswordError(FAKE_FIELD_ERROR)
        testObserver.apply {
            assertNoErrors()
            assertValue(java.lang.Boolean.FALSE)
            assertComplete()
            dispose()
        }


    }

    @Test
    fun enableOrDisable_validEmailPassword_shouldEnableRegisterButton() {

        // Execute the code under test
        loginPresenter.enableOrDisableLoginButton(Observable.just(java.lang.Boolean.TRUE), Observable.just(java.lang.Boolean.TRUE))

        // Make assertions on the results
        verify(view).enableLoginButton()

    }

    @Test
    fun enableOrDisable_invalidEmailValidPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        loginPresenter.enableOrDisableLoginButton(Observable.just(java.lang.Boolean.FALSE), Observable.just(java.lang.Boolean.TRUE))

        // Make assertions on the results
        verify(view).disableLoginButton()

    }

    @Test
    fun enableOrDisable_validEmailInvalidPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        loginPresenter.enableOrDisableLoginButton(Observable.just(java.lang.Boolean.TRUE), Observable.just(java.lang.Boolean.FALSE))

        // Make assertions on the results
        verify(view).disableLoginButton()

    }

    @Test
    fun enableOrDisable_invalidEmailPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        loginPresenter.enableOrDisableLoginButton(Observable.just(java.lang.Boolean.FALSE), Observable.just(java.lang.Boolean.FALSE))

        // Make assertions on the results
        verify(view).disableLoginButton()

    }

    @Test
    fun login_validCredentials_showShowTaskActivity() {

        `when`(todoRepository.login(anyString(), anyString())).thenReturn(Completable.complete())

        loginPresenter.login(FAKE_EMAIL, FAKE_PASSWORD)

        verify(view, Mockito.times(1)).hideKeyboard()
        verify(view, Mockito.times(1)).showLoading()
        verify(view, Mockito.times(1)).showTasksActivity()


    }

    @Test
    fun login_invalidCredentials_showSnackBarWithError() {

        val invalidCredentials = Mockito.mock(FirebaseAuthInvalidCredentialsException::class.java)
        `when`(todoRepository.login(anyString(), anyString())).thenReturn(Completable.error(invalidCredentials))
        `when`(resources.getString(Mockito.anyInt())).thenReturn(FAKE_INVALID_CREDENTIALS_ERROR)

        loginPresenter.login(FAKE_EMAIL, FAKE_PASSWORD)

        verify(view, Mockito.times(1)).hideKeyboard()
        verify(view, Mockito.times(1)).showLoading()
        verify(view, Mockito.times(1)).showSnackBar(FAKE_INVALID_CREDENTIALS_ERROR)


    }

    @Test
    fun register_showShowRegisterActivity() {
        loginPresenter.register()
        verify(view, Mockito.times(1)).showRegisterActivity()

    }

    companion object {

        private const val FAKE_FIELD_ERROR = "fake_field_error"
        private const val FAKE_EMAIL = "fake_email@android.com"
        private const val FAKE_PASSWORD = "fake_password"
        private const val FAKE_INVALID_CREDENTIALS_ERROR = "fake_invalid_credentials_error"
    }


}