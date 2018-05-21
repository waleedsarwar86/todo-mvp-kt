package com.todo.ui.feature.launcher

import com.todo.ui.base.BaseContract

interface LauncherContract {

    interface View : BaseContract.View {

        fun showLoginActivity()

        fun showTasksActivity()
    }

    interface Presenter : BaseContract.Presenter<View> {

        fun showNextActivity()

    }
}
