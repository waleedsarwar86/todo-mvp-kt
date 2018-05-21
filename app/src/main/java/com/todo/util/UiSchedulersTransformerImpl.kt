package com.todo.util

import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Action
import io.reactivex.schedulers.Schedulers

class UiSchedulersTransformerImpl : UiSchedulersTransformer {

    override fun applySchedulersToCompletable(): CompletableTransformer {
        return CompletableTransformer {
            it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }

    override fun <T> applySchedulersToSingle(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

    }

    override fun <T> applySchedulersToObservable(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }

    }

    override fun <T> applyObserveOnSchedulersToObservable(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.observeOn(AndroidSchedulers.mainThread())
        }
    }

}
