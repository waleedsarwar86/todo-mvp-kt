package com.todo.util

import java.util.concurrent.TimeUnit

class CurrentTimeProviderImpl : CurrentTimeProvider {

    override val currentTimeMillis: Long
        get() = System.currentTimeMillis()

    override val unixTimestamp: Long
        get() = TimeUnit.MILLISECONDS.toSeconds(currentTimeMillis)
}
