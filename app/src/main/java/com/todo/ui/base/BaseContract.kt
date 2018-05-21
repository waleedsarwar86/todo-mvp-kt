package com.todo.ui.base


import io.reactivex.Single

/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

interface BaseContract {

    interface View {

        fun hideKeyboard()

        fun showSnackBar(message: String)

        fun showSnackBar(message: String, action: String): Single<Boolean>

        fun showSnackBar(messageRes: Int, actionRes: Int): Single<Boolean>


        fun finishActivity()

    }

    interface Presenter<V : BaseContract.View> {

        fun attachView(View: V)

        fun detachView()


    }

}
