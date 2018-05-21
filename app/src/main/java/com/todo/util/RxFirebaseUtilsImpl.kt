package com.todo.util

import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.todo.data.exception.FirebaseDataException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


class RxFirebaseUtilsImpl(private val rxIdlingResource: RxIdlingResource) : RxFirebaseUtils {

    override fun <T> getSingle(task: Task<T>): Single<T> {

        rxIdlingResource.increment()

        return Single.create { emitter ->
            task.addOnSuccessListener { t ->
                if (!emitter.isDisposed) {
                    emitter.onSuccess(t)
                    rxIdlingResource.decrement()
                }

            }
            task.addOnFailureListener { e ->
                if (!emitter.isDisposed) {
                    emitter.onError(e)
                    rxIdlingResource.decrement()
                }
            }
        }


    }

    override fun getCompletable(task: Task<Void>): Completable {

        rxIdlingResource.increment()

        return Completable.create { emitter ->
            task.addOnSuccessListener {
                if (!emitter.isDisposed) {
                    emitter.onComplete()
                    rxIdlingResource.decrement()
                }

            }
            task.addOnFailureListener { e ->
                rxIdlingResource.increment()
                if (!emitter.isDisposed) {
                    emitter.onError(e)
                    rxIdlingResource.decrement()
                }
            }
        }


    }


    override fun getObservable(query: Query): Observable<DataSnapshot> {

        rxIdlingResource.increment()

        return Observable.create { emitter ->
            query.addValueEventListener(object : ValueEventListener {

                internal var firstTime = true

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!emitter.isDisposed) {
                        emitter.onNext(dataSnapshot)
                        if (firstTime) {
                            rxIdlingResource.decrement()
                            firstTime = false
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    if (!emitter.isDisposed) {
                        emitter.onError(FirebaseDataException(databaseError))
                    }
                }
            })
        }

    }

    override fun getSingle(query: Query): Single<DataSnapshot> {

        rxIdlingResource.increment()

        return Single.create { emitter ->
            query.addListenerForSingleValueEvent(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (!emitter.isDisposed) {
                        emitter.onSuccess(dataSnapshot)
                        rxIdlingResource.decrement()
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    if (!emitter.isDisposed) {
                        rxIdlingResource.decrement()
                        emitter.onError(FirebaseDataException(databaseError))
                    }
                }

            })
        }

    }

}