package com.todo.util

class StringUtilsImpl : StringUtils {

    override fun isEmpty(string: String?): Boolean {
        return string.isNullOrBlank()

    }

    override fun isNotEmpty(string: String?): Boolean {
        return string?.isNotBlank() ?: false
    }


}
