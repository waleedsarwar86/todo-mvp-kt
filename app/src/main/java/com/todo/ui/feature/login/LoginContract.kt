package com.todo.ui.feature.login

import com.todo.ui.base.BaseContract

import io.reactivex.Observable


interface LoginContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showEmailError(errorMessage: String)

        fun hideEmailError()

        fun showPasswordError(errorMessage: String)

        fun hidePasswordError()

        fun enableLoginButton()

        fun disableLoginButton()

        fun showRegisterActivity()

        fun showTasksActivity()

    }

    interface Presenter : BaseContract.Presenter<LoginContract.View> {

        fun validateEmail(emailObservable: Observable<String>): Observable<Boolean>

        fun validatePassword(passwordObservable: Observable<String>): Observable<Boolean>

        fun enableOrDisableLoginButton(validateEmailObservable: Observable<Boolean>, validatePasswordObservable: Observable<Boolean>)


        fun login(email: String, password: String)

        fun register()

    }
}
