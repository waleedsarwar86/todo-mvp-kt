package com.todo.util

import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer

interface UiSchedulersTransformer {


    fun applySchedulersToCompletable(): CompletableTransformer

    fun <T> applySchedulersToSingle(): SingleTransformer<T, T>

    fun <T> applySchedulersToObservable(): ObservableTransformer<T, T>

    fun <T> applyObserveOnSchedulersToObservable(): ObservableTransformer<T, T>
}
