package com.todo.util

interface StringUtils {

    fun isEmpty(string: String?): Boolean

    fun isNotEmpty(string: String?): Boolean

    companion object {

        const val EMPTY = ""
    }

}
