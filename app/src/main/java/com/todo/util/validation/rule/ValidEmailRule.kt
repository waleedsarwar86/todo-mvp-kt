package com.todo.util.validation.rule

class ValidEmailRule(errorMessage: String) : MatchPatternRule(errorMessage, EMAIL_PATTERN) {

    companion object {

        private const val EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    }
}
