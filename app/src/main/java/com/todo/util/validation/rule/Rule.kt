package com.todo.util.validation.rule


import io.reactivex.Observable

abstract class Rule internal constructor(protected val errorMessage: String) {

    abstract fun validate(input: String): Observable<String>

    companion object {
        internal const val EMPTY = ""
    }
}
