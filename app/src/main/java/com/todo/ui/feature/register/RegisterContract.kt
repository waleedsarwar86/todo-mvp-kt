package com.todo.ui.feature.register

import com.todo.ui.base.BaseContract

import io.reactivex.Observable

interface RegisterContract {

    interface View : BaseContract.View {

        fun showLoading()

        fun hideLoading()

        fun showEmailError(errorMessage: String)

        fun hideEmailError()

        fun showPasswordError(errorMessage: String)

        fun hidePasswordError()

        fun enableRegisterButton()

        fun disableRegisterButton()

        fun showTasksActivity()

    }

    interface Presenter : BaseContract.Presenter<View> {

        fun validateEmail(emailObservable: Observable<String>): Observable<Boolean>

        fun validatePassword(passwordObservable: Observable<String>): Observable<Boolean>

        fun enableOrDisableRegisterButton(validateEmailObservable: Observable<Boolean>, validatePasswordObservable: Observable<Boolean>)

        fun register(email: String, password: String)

    }
}
