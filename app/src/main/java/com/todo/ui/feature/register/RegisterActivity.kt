package com.todo.ui.feature.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.Group
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar

import com.jakewharton.rxbinding2.widget.RxTextView
import com.todo.R
import com.todo.di.activity.ActivityComponent
import com.todo.ui.base.BaseActivity
import com.todo.ui.feature.tasks.TasksActivity

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.reactivex.Observable

class RegisterActivity : BaseActivity(), RegisterContract.View {

    /********* Dagger Injected Fields   */

    @Inject
    lateinit var presenter: RegisterContract.Presenter

    /********* Butterknife Binded Fields   */

    @BindView(R.id.register_group_form)
    lateinit var groupForm: Group

    @BindView(R.id.register_input_layout_email)
    lateinit var inputLayoutEmail: TextInputLayout

    @BindView(R.id.register_input_edit_text_email)
    lateinit var inputEditTextEmail: TextInputEditText

    @BindView(R.id.register_input_layout_password)
    lateinit var inputLayoutPassword: TextInputLayout

    @BindView(R.id.register_input_edit_text_password)
    lateinit var inputEditTextPassword: TextInputEditText

    @BindView(R.id.register_button_register)
    lateinit var buttonRegister: Button

    @BindView(R.id.register_progress_bar)
    lateinit var progressBar: ProgressBar

    private val emailObservable: Observable<String>
        get() = RxTextView.textChanges(inputEditTextEmail).skip(1).map({ it.toString() })

    private val passwordObservable: Observable<String>
        get() = RxTextView.textChanges(inputEditTextPassword).skip(1).map({ it.toString() })

    /********* Lifecycle Methods  */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        setUnbinder(ButterKnife.bind(this))
        initializeToolbar()
        presenter.attachView(this)
        setupFormValidations()
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()

    }

    /********* BaseActivity Inherited Methods  */

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }


    /********* Activity Inherited Methods  */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /********* RegisterContract.View Inherited Methods  */

    override fun showLoading() {
        groupForm.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.INVISIBLE
        groupForm.visibility = View.VISIBLE
    }

    override fun showEmailError(errorMessage: String) {
        inputLayoutEmail.error = errorMessage

    }

    override fun hideEmailError() {
        inputLayoutEmail.error = null
    }

    override fun showPasswordError(errorMessage: String) {
        inputLayoutPassword.error = errorMessage
    }

    override fun hidePasswordError() {
        inputLayoutPassword.error = null
    }

    override fun enableRegisterButton() {
        buttonRegister.isEnabled = true
    }

    override fun disableRegisterButton() {
        buttonRegister.isEnabled = false
    }

    override fun showTasksActivity() {
        val intent = Intent(this, TasksActivity::class.java)
        startActivity(intent)
    }

    /********* Butterknife Binded Methods   */

    @OnClick(R.id.register_button_register)
    fun buttonRegisterClicked() {
        presenter.register(inputEditTextEmail.text.toString(), inputEditTextPassword.text.toString())
    }

    /********* Member Methods   */

    private fun initializeToolbar() {
        val myToolbar = findViewById<Toolbar>(R.id.register_toolbar)
        setSupportActionBar(myToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun setupFormValidations() {
        val validateEmailObservable = presenter.validateEmail(emailObservable)
        val validatePasswordObservable = presenter.validatePassword(passwordObservable)
        presenter.enableOrDisableRegisterButton(validateEmailObservable, validatePasswordObservable)

    }

    companion object {

        /********* Static Methods  */

        fun startActivity(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }


}
