package com.todo.util.validation.validator

import com.todo.util.StringUtils
import com.todo.util.validation.rule.Rule
import io.reactivex.Observable

class RulesValidatorImpl(private val stringUtils: StringUtils) : RulesValidator {


    override fun validate(inputObservable: Observable<String>, rules: List<Rule>): Observable<String> {

        return inputObservable.switchMap { input ->
            Observable.fromIterable(rules)
                    .concatMap { rule -> rule.validate(input) }
                    .buffer(rules.size)
                    .map { this.getErrorMessageOrEmpty(it) }
        }
    }

    private fun getErrorMessageOrEmpty(errorMessages: List<String>): String {
        for (errorMessage in errorMessages) {
            if (stringUtils.isNotEmpty(errorMessage)) {
                return errorMessage
            }
        }
        return StringUtils.EMPTY
    }
}
