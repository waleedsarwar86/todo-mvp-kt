package com.todo.util

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.Query

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


interface RxFirebaseUtils {


    fun <T> getSingle(task: Task<T>?): Single<T>

    fun getCompletable(task: Task<Void>?): Completable


    fun getObservable(query: Query?): Observable<DataSnapshot>

    fun getSingle(query: Query?): Single<DataSnapshot>

}