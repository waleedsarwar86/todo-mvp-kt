package com.todo.ui.feature.launcher

import com.todo.data.repository.TodoRepository
import com.todo.ui.base.BasePresenter
import io.reactivex.functions.Consumer

import javax.inject.Inject

import timber.log.Timber

/********* Constructors  */

class LauncherPresenter : BasePresenter<LauncherContract.View>(), LauncherContract.Presenter {

    /********* Dagger Injected Fields   */

    @Inject
    lateinit var todoRepository: TodoRepository

    /********* LauncherContract.Presenter Interface Methods Implementation  */

    override fun showNextActivity() {
        addDisposable(todoRepository.isUserLoggedIn
                .compose(uiSchedulersTransformer.applySchedulersToSingle())
                .subscribe({ isUserLoggedIn ->
                    if (isUserLoggedIn) view?.showLoginActivity() else view?.showLoginActivity()
                }, { Timber.e(it) }))
    }
}
