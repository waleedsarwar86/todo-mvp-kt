package com.todo.util.validation.validator

import com.todo.util.validation.rule.Rule

interface RulesFactory {

    fun createEmailFieldRules(): List<Rule>

    fun createPasswordFieldRules(): List<Rule>

}
