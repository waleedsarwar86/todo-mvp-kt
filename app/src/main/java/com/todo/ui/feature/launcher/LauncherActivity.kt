package com.todo.ui.feature.launcher

import android.os.Bundle

import com.todo.di.activity.ActivityComponent
import com.todo.ui.base.BaseActivity
import com.todo.ui.feature.login.LoginActivity
import com.todo.ui.feature.tasks.TasksActivity

import javax.inject.Inject

class LauncherActivity : BaseActivity(), LauncherContract.View {

    /********* Dagger Injected Fields   */

    @Inject
    lateinit var presenter: LauncherContract.Presenter

    /********* Lifecycle Methods Implementation  */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
        presenter.showNextActivity()
    }

    /********* DaggerActivity Inherited Methods  */

    override fun inject(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    /********* LauncherContract.View Inherited Methods  */

    override fun showLoginActivity() {
        LoginActivity.startActivity(this)
        finish()
    }

    override fun showTasksActivity() {
        TasksActivity.startActivity(this)
        finish()
    }

}
