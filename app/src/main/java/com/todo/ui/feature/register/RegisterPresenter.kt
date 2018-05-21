package com.todo.ui.feature.register

import android.content.res.Resources

import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.todo.R
import com.todo.data.repository.TodoRepository
import com.todo.ui.base.BasePresenter
import com.todo.util.StringUtils
import com.todo.util.validation.validator.RulesFactory
import com.todo.util.validation.validator.RulesValidator

import javax.inject.Inject

import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import timber.log.Timber

class RegisterPresenter : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {

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


    /********* RegisterContract.Presenter Inherited Methods  */

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

    override fun enableOrDisableRegisterButton(validateEmailObservable: Observable<Boolean>, validatePasswordObservable: Observable<Boolean>) {

        val isRegisterEnabled: Observable<Boolean> = Observable.combineLatest(
                validateEmailObservable,
                validatePasswordObservable,
                BiFunction { emailValid, passwordValid -> emailValid && passwordValid })

        addDisposable(isRegisterEnabled.subscribe({ if (it) view?.enableRegisterButton() else view?.disableRegisterButton() }))

    }

    override fun register(email: String, password: String) {
        view?.hideKeyboard()
        view?.showLoading()
        addDisposable(todoRepository.register(email, password)
                .compose(uiSchedulersTransformer.applySchedulersToCompletable())
                .subscribe({
                    view?.hideLoading()
                    view?.showTasksActivity()
                }) { throwable ->
                    Timber.e(throwable)
                    view?.hideLoading()
                    if (throwable is FirebaseAuthUserCollisionException) {
                        view?.showSnackBar(resources.getString(R.string.register_error_email_already_exist))
                    }
                })
    }

}
