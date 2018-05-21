package com.todo.util.validation.rule


import io.reactivex.Observable

class NotEmptyRule(errorMessage: String) : Rule(errorMessage) {

    override fun validate(input: String): Observable<String> {
        return if (isEmpty(input)) {
            Observable.just(errorMessage)
        } else Observable.just(Rule.EMPTY)
    }

    private fun isEmpty(value: String): Boolean {
        return value.trim().isEmpty()
    }
}
