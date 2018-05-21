package com.todo.util.validation.validator

import android.content.res.Resources

import com.todo.R
import com.todo.util.validation.rule.NotEmptyRule
import com.todo.util.validation.rule.Rule
import com.todo.util.validation.rule.ValidEmailRule

import java.util.ArrayList

class RulesFactoryImpl(private val resources: Resources) : RulesFactory {

    override fun createEmailFieldRules(): List<Rule> {
        val rules = ArrayList<Rule>()
        rules.add(NotEmptyRule(resources.getString(R.string.all_error_email_required)))
        rules.add(ValidEmailRule(resources.getString(R.string.all_error_email_invalid)))
        return rules
    }

    override fun createPasswordFieldRules(): List<Rule> {
        val rules = ArrayList<Rule>()
        rules.add(NotEmptyRule(resources.getString(R.string.all_error_password_required)))
        return rules
    }

}
