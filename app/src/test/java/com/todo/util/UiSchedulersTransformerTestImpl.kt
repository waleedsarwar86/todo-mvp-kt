package com.todo.util

import io.reactivex.CompletableTransformer
import io.reactivex.ObservableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author Waleed Sarwar
 * @since Dec 10, 2017
 */

class UiSchedulersTransformerTestImpl : UiSchedulersTransformer {


    override fun applySchedulersToCompletable(): CompletableTransformer {
        return CompletableTransformer {
            it.subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline())
        }
    }

    override fun <T> applySchedulersToSingle(): SingleTransformer<T, T> {
        return SingleTransformer {
            it.subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline())
        }
    }

    override fun <T> applySchedulersToObservable(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.subscribeOn(Schedulers.trampoline())
                    .observeOn(Schedulers.trampoline())
        }
    }

    override fun <T> applyObserveOnSchedulersToObservable(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it.observeOn(Schedulers.trampoline())
        }
    }
}
