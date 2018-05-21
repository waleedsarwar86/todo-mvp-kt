package com.todo.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import butterknife.Unbinder
import com.todo.R
import com.todo.di.activity.DaggerActivity
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseActivity : DaggerActivity(), BaseContract.View {

    /********* Member Fields   */

    private var unbinder: Unbinder? = null
    private var snackbar: Snackbar? = null
    private var compositeDisposable: CompositeDisposable? = null
    private lateinit var rootView: View

    /********* Lifecycle Methods  */

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        rootView = findViewById(R.id.root_view)
    }

    @CallSuper
    override fun onDestroy() {
        unbinder?.unbind()
        compositeDisposable?.clear()
        super.onDestroy()
    }

    /********* BaseContract.View Inherited Methods  */

    override fun hideKeyboard() {
        val view = this.currentFocus
        view?.let {
            val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun showSnackBar(message: String) {
        Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
    }

    override fun showSnackBar(message: String, action: String): Single<Boolean> {

        return Single.create { emitter ->

            snackbar?.let {
                it.setText(message)
                it.duration = Snackbar.LENGTH_LONG
            } ?: run {
                snackbar = Snackbar.make(rootView, message, Snackbar.LENGTH_LONG)
            }

            snackbar?.setAction(action) { emitter.onSuccess(true) }
            snackbar?.addCallback(object : Snackbar.Callback() {
                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(java.lang.Boolean.FALSE)
                    }
                }
            })
            snackbar?.show()
        }

    }

    override fun showSnackBar(@StringRes messageRes: Int, @StringRes actionRes: Int): Single<Boolean> {

        return showSnackBar(getString(messageRes), getString(actionRes))
    }


    override fun finishActivity() {
        finish()
    }

    /********* Member Methods   */

    fun setUnbinder(unbinder: Unbinder) {
        this.unbinder = unbinder
    }

    protected fun addDisposable(disposable: Disposable) {


        if (compositeDisposable == null || compositeDisposable?.isDisposed == true) {
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable?.add(disposable)
    }

}
