package com.todo.util

// TODO: 29/04/2018 remove static methods and use dagger to inject the dependency
interface RxIdlingResource {

    val isIdleNow: Boolean

    fun increment()

    fun decrement()


}
