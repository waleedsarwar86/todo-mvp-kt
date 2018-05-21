package com.todo.ui.base

import com.todo.util.UiSchedulersTransformer

import java.lang.ref.WeakReference

import javax.inject.Inject

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/********* Constructors  */

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    /********* Dagger Injected Fields   */

    @Inject
    lateinit var uiSchedulersTransformer: UiSchedulersTransformer

    /********* Member Fields   */

    private var viewReference: WeakReference<V>? = null
    private var compositeDisposable: CompositeDisposable? = null

    /********* Member Methods  */

    protected val view: V?
        get() {

            if (viewReference == null) {
                throw IllegalStateException("View is not attached")
            }
            return viewReference?.get()
        }

    /********* BaseContract.Presenter Inherited Methods  */

    override fun attachView(view: V) {
        this.viewReference = WeakReference(view)
    }

    override fun detachView() {
        viewReference = null
        compositeDisposable?.clear()
    }


    protected fun addDisposable(disposable: Disposable) {
        if (compositeDisposable == null || compositeDisposable?.isDisposed == true) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable!!.add(disposable)
    }


}
