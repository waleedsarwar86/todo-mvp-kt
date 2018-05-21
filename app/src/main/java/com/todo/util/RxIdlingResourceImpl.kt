package com.todo.util

import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.idling.CountingIdlingResource

class RxIdlingResourceImpl : RxIdlingResource {

    private val countingIdlingResource: CountingIdlingResource

    override val isIdleNow: Boolean
        get() = countingIdlingResource.isIdleNow

    init {
        countingIdlingResource = CountingIdlingResource(TAG, true)
        IdlingRegistry.getInstance().register(countingIdlingResource)
    }


    override fun increment() {
        countingIdlingResource.increment()
    }

    override fun decrement() {
        countingIdlingResource.decrement()
    }

    companion object {

        private const val TAG = "RxIdlingResource"
    }
}
