package com.todo.util.validation.rule

import java.util.regex.Pattern

import io.reactivex.Observable

open class MatchPatternRule(errorMessage: String, pattern: String) : Rule(errorMessage) {

    private val pattern: Pattern = Pattern.compile(pattern)

    override fun validate(input: String): Observable<String> {
        return if (matchPattern(input)) {
            Observable.just(Rule.EMPTY)
        } else {
            Observable.just(errorMessage)
        }
    }

    private fun matchPattern(input: String): Boolean {
        return pattern.matcher(input).matches()
    }
}
