package com.todo.ui.feature.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.constraint.Group
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar

import com.jakewharton.rxbinding2.widget.RxTextView
import com.todo.R
import com.todo.di.activity.ActivityComponent
import com.todo.ui.base.BaseActivity
import com.todo.ui.feature.register.RegisterActivity
import com.todo.ui.feature.tasks.TasksActivity

import javax.inject.Inject

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import io.reactivex.Observable

class LoginActivity : BaseActivity(), LoginContract.View {

    /********* Dagger Injected Fields   */

    @Inject
    lateinit var presenter: LoginContract.Presenter

    /********* Butterknife View Binding Fields   */

    @BindView(R.id.login_group_form)
    lateinit var groupForm: Group

    @BindView(R.id.login_input_layout_email)
    lateinit var inputLayoutEmail: TextInputLayout

    @BindView(R.id.login_input_edit_text_email)
    lateinit var inputEditTextEmail: TextInputEditText

    @BindView(R.id.login_input_layout_password)
    lateinit var inputLayoutPassword: TextInputLayout

    @BindView(R.id.login_input_edit_text_password)
    lateinit var inputEditTextPassword: EditText

    @BindView(R.id.login_button_login)
    lateinit var buttonLogin: Button

    @BindView(R.id.login_progress_bar)
    lateinit var progressBar: ProgressBar


    private val emailObservable: Observable<String>
        get() = RxTextView.textChanges(inputEditTextEmail).skip(1).map({ it.toString() })

    private val passwordObservable: Observable<String>
        get() = RxTextView.textChanges(inputEditTextPassword).skip(1).map({ it.toString() })

    /********* Lifecycle Methods  */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        setUnbinder(ButterKnife.bind(this))
        initializeToolbar()
        presenter.attachView(this)
        setupFormValidations()
    }

    /********* BaseActivity Inherited Methods  */

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    /********* LoginContract.View Inherited Methods  */

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
        inputLayoutEmail.setHintTextAppearance(R.style.TextAppearance_ErrorCaption)
    }

    override fun hideEmailError() {
        inputLayoutEmail.error = null
        inputLayoutEmail.setHintTextAppearance(R.style.TextAppearance_AccentCaption)
    }

    override fun showPasswordError(errorMessage: String) {
        inputLayoutPassword.error = errorMessage
        inputLayoutPassword.setHintTextAppearance(R.style.TextAppearance_ErrorCaption)
    }

    override fun hidePasswordError() {
        inputLayoutPassword.error = null
        inputLayoutEmail.setHintTextAppearance(R.style.TextAppearance_AccentCaption)
    }

    override fun enableLoginButton() {
        buttonLogin.isEnabled = true
    }

    override fun disableLoginButton() {
        buttonLogin.isEnabled = false
    }

    override fun showRegisterActivity() {
        RegisterActivity.startActivity(this)
    }

    override fun showTasksActivity() {
        TasksActivity.startActivity(this)
        finish()
    }

    /********* Butterknife Binded Methods   */

    @OnClick(R.id.login_button_login)
    fun buttonLoginClicked() {
        presenter.login(inputEditTextEmail.text.toString(), inputEditTextPassword.text.toString())
    }

    @OnClick(R.id.login_button_register)
    fun buttonRegisterClicked() {
        presenter.register()
    }

    /********* Member Methods   */

    private fun initializeToolbar() {
        val myToolbar = findViewById<Toolbar>(R.id.login_toolbar)
        setSupportActionBar(myToolbar)
    }

    private fun setupFormValidations() {
        val validateEmailObservable = presenter.validateEmail(emailObservable)
        val validatePasswordObservable = presenter.validatePassword(passwordObservable)
        presenter.enableOrDisableLoginButton(validateEmailObservable, validatePasswordObservable)

    }

    companion object {

        /********* Static Methods  */


        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

}
