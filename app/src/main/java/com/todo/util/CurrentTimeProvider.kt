package com.todo.util

interface CurrentTimeProvider {

    val currentTimeMillis: Long

    val unixTimestamp: Long
}
