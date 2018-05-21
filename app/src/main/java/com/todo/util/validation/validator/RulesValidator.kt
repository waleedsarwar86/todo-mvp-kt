package com.todo.util.validation.validator

import com.todo.util.validation.rule.Rule

import io.reactivex.Observable

interface RulesValidator {

    fun validate(inputObservable: Observable<String>, rules: List<Rule>): Observable<String>


}
