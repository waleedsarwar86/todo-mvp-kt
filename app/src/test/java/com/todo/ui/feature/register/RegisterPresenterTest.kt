package com.todo.ui.feature.register

import android.content.res.Resources
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.todo.R
import com.todo.data.repository.TodoRepository
import com.todo.util.StringUtilsImpl
import com.todo.util.UiSchedulersTransformerTestImpl
import com.todo.util.validation.validator.RulesFactory
import com.todo.util.validation.validator.RulesValidator
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.anyList
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.Spy

class RegisterPresenterTest {

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
    private lateinit var registerPresenter: RegisterPresenter

    private lateinit var view: RegisterContract.View

    @Before
    fun setUp() {

        view = mock(RegisterContract.View::class.java)
        registerPresenter = RegisterPresenter()
        registerPresenter.attachView(view)
        initMocks(this)

        fakeEmailObservable = Observable.just(FAKE_EMAIL)
        fakePasswordObservable = Observable.just(FAKE_PASSWORD)

        `when`(rulesFactory.createEmailFieldRules()).thenReturn(emptyList())
        `when`(rulesFactory.createPasswordFieldRules()).thenReturn(emptyList())
    }

    @Test
    fun validateEmail_validEmail_shouldHideEmailErrorAndReturnTrue() {

        // Setup conditions of the test
        `when`(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(""))

        // Execute the code under test
        val testObserver = registerPresenter.validateEmail(fakeEmailObservable).test()

        // Make assertions on the results
        verify(view).hideEmailError()
        testObserver.assertNoErrors()
        testObserver.assertValue(java.lang.Boolean.TRUE)
        testObserver.assertComplete()
        testObserver.dispose()


    }

    @Test
    fun validateEmail_invalidEmail_shouldShowEmailErrorAndReturnFalse() {

        // Setup conditions of the test
        `when`(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(FAKE_FIELD_ERROR))

        // Execute the code under test
        val testObserver = registerPresenter.validateEmail(fakeEmailObservable).test()

        // Make assertions on the results
        verify(view).showEmailError(FAKE_FIELD_ERROR)
        testObserver.assertNoErrors()
        testObserver.assertValue(java.lang.Boolean.FALSE)
        testObserver.assertComplete()
        testObserver.dispose()


    }

    @Test
    fun validatePassword_validEmail_shouldHidePasswordErrorAndReturnTrue() {

        // Setup conditions of the test
        `when`(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(""))

        // Execute the code under test
        val testObserver = registerPresenter.validatePassword(fakePasswordObservable).test()

        // Make assertions on the results
        verify(view).hidePasswordError()
        testObserver.assertNoErrors()
        testObserver.assertValue(java.lang.Boolean.TRUE)
        testObserver.assertComplete()
        testObserver.dispose()


    }

    @Test
    fun validatePassword_invalidPassword_shouldShowPasswordErrorAndReturnFalse() {

        // Setup conditions of the test
        `when`(rulesValidator.validate(any(), anyList())).thenReturn(Observable.just(FAKE_FIELD_ERROR))

        // Execute the code under test
        val testObserver = registerPresenter.validatePassword(fakePasswordObservable).test()

        // Make assertions on the results
        verify(view).showPasswordError(FAKE_FIELD_ERROR)
        testObserver.assertNoErrors()
        testObserver.assertValue(java.lang.Boolean.FALSE)
        testObserver.assertComplete()
        testObserver.dispose()


    }

    @Test
    fun enableOrDisable_validEmailPassword_shouldEnableRegisterButton() {

        // Execute the code under test
        registerPresenter.enableOrDisableRegisterButton(Observable.just(java.lang.Boolean.TRUE), Observable.just(java.lang.Boolean.TRUE))

        // Make assertions on the results
        verify(view).enableRegisterButton()

    }

    @Test
    fun enableOrDisable_invalidEmailValidPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        registerPresenter.enableOrDisableRegisterButton(Observable.just(java.lang.Boolean.FALSE), Observable.just(java.lang.Boolean.TRUE))

        // Make assertions on the results
        verify(view).disableRegisterButton()

    }

    @Test
    fun enableOrDisable_validEmailInvalidPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        registerPresenter.enableOrDisableRegisterButton(Observable.just(java.lang.Boolean.TRUE), Observable.just(java.lang.Boolean.FALSE))

        // Make assertions on the results
        verify(view).disableRegisterButton()

    }

    @Test
    fun enableOrDisable_invalidEmailPassword_shouldDisableRegisterButton() {

        // Execute the code under test
        registerPresenter.enableOrDisableRegisterButton(Observable.just(java.lang.Boolean.FALSE), Observable.just(java.lang.Boolean.FALSE))

        // Make assertions on the results
        verify(view).disableRegisterButton()

    }

    @Test
    fun register_validEmailPassword_shouldShowTaskActivity() {

        // Setup conditions of the test
        `when`(todoRepository.register(FAKE_EMAIL, FAKE_PASSWORD)).thenReturn(Completable.complete())

        // Execute the code under test
        registerPresenter.register(FAKE_EMAIL, FAKE_PASSWORD)

        // Make assertions on the results
        verify(view).showTasksActivity()
    }

    @Test
    fun register_emailExist_shouldShowSnackBarWithError() {

        // Setup conditions of the test
        val emailExistException = Mockito.mock(FirebaseAuthUserCollisionException::class.java)
        `when`(todoRepository.register(FAKE_EMAIL, FAKE_PASSWORD)).thenReturn(Completable.error(emailExistException))
        Mockito.`when`(resources.getString(R.string.register_error_email_already_exist)).thenReturn(FAKE_EMAIL_EXIST_ERROR)

        // Execute the code under test
        registerPresenter.register(FAKE_EMAIL, FAKE_PASSWORD)

        // Make assertions on the results
        verify(view).hideLoading()
        verify(view).showSnackBar(FAKE_EMAIL_EXIST_ERROR)
    }

    @Test
    fun register_validEmailAndPassword_shouldHideLoadingUnknownError() {

        // Setup conditions of the test

        val unknownException = Mockito.mock(Exception::class.java)

        `when`(todoRepository.register(FAKE_EMAIL, FAKE_PASSWORD)).thenReturn(Completable.error(unknownException))

        // Execute the code under test
        registerPresenter.register(FAKE_EMAIL, FAKE_PASSWORD)

        // Make assertions on the results
        verify(view).hideLoading()
    }

    companion object {

        private const val FAKE_FIELD_ERROR = "fake_field_error"
        private const val FAKE_EMAIL = "fake_email@android.com"
        private const val FAKE_PASSWORD = "fake_password"
        private const val FAKE_EMAIL_EXIST_ERROR = "email_exist_error"
    }
}