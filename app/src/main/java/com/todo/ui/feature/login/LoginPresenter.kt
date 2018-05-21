package com.todo.ui.feature.login

import android.content.res.Resources
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.todo.R
import com.todo.data.repository.TodoRepository
import com.todo.ui.base.BasePresenter
import com.todo.util.StringUtils
import com.todo.util.validation.validator.RulesFactory
import com.todo.util.validation.validator.RulesValidator
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import timber.log.Timber
import javax.inject.Inject

/********* Constructors  */

class LoginPresenter : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    /********* Dagger Injected Fields   */

    @Inject
    lateinit var todoRepository: TodoRepository

    @Inject
    lateinit var resources: Resources

    @Inject
    lateinit var stringUtils: StringUtils

    @Inject
    lateinit var rulesValidator: RulesValidator

    @Inject
    lateinit var rulesFactory: RulesFactory

    /********* LoginContract.Presenter Inherited Methods  */


    override fun validateEmail(emailObservable: Observable<String>): Observable<Boolean> {
        return rulesValidator.validate(emailObservable, rulesFactory.createEmailFieldRules())
                .compose(uiSchedulersTransformer.applyObserveOnSchedulersToObservable())
                .doOnNext { errorMessage ->
                    if (stringUtils.isEmpty(errorMessage)) {
                        view?.hideEmailError()
                    } else {
                        view?.showEmailError(errorMessage)
                    }
                }
                .map({ it.isEmpty() })
    }

    override fun validatePassword(passwordObservable: Observable<String>): Observable<Boolean> {
        return rulesValidator
                .validate(passwordObservable, rulesFactory.createPasswordFieldRules())
                .compose(uiSchedulersTransformer.applyObserveOnSchedulersToObservable())
                .doOnNext { errorMessage ->
                    if (stringUtils.isEmpty(errorMessage)) {
                        view?.hidePasswordError()
                    } else {
                        view?.showPasswordError(errorMessage)
                    }
                }.map({ it.isEmpty() })
    }

    override fun enableOrDisableLoginButton(validateEmailObservable: Observable<Boolean>, validatePasswordObservable: Observable<Boolean>) {

        val isSignInEnabled: Observable<Boolean> = Observable.combineLatest(
                validateEmailObservable,
                validatePasswordObservable,
                BiFunction { emailValid, passwordValid -> emailValid && passwordValid })

        addDisposable(isSignInEnabled.subscribe({ if (it) view?.enableLoginButton() else view?.disableLoginButton() }))

    }


    override fun login(email: String, password: String) {

        // ideally your testing related code should not be here
        view?.hideKeyboard()
        view?.showLoading()

        addDisposable(todoRepository.login(email, password)
                .compose(uiSchedulersTransformer.applySchedulersToCompletable())
                .subscribe({ view?.showTasksActivity() }) { throwable ->
                    view?.hideLoading()
                    Timber.e(throwable)
                    if (throwable is FirebaseAuthInvalidUserException || throwable is FirebaseAuthInvalidCredentialsException) {
                        view?.showSnackBar(resources.getString(R.string.login_error_invalid_credentials))
                    }
                })

    }

    override fun register() {
        view?.showRegisterActivity()
    }


}
