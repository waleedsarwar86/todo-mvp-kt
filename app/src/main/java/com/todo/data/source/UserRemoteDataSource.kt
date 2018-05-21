package com.todo.data.source

import android.support.annotation.VisibleForTesting

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.todo.BuildConfig
import com.todo.data.model.TaskModel
import com.todo.util.RxFirebaseUtils

import java.util.ArrayList

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

class UserRemoteDataSource(firebaseDatabase: FirebaseDatabase, firebaseAuth: FirebaseAuth, rxFirebaseUtils: RxFirebaseUtils) : RemoteDataSource(firebaseDatabase, firebaseAuth, rxFirebaseUtils) {

    private var childReference: DatabaseReference? = null

    /********* Member Methods   */

    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    val tasks: Observable<List<TaskModel>>
        get() = rxFirebaseUtils.getObservable(getChildReference())
                .map { dataSnapshot ->
                    val taskModels = ArrayList<TaskModel>()
                    for (child in dataSnapshot.children) {
                        child.getValue(TaskModel::class.java)?.let {
                            taskModels.add(it)
                        }
                    }
                    taskModels
                }

    fun login(email: String, password: String): Single<AuthResult> {
        return rxFirebaseUtils.getSingle(firebaseAuth.signInWithEmailAndPassword(email, password))
    }


    fun logout() {
        firebaseAuth.signOut()
    }

    fun register(email: String, password: String): Single<AuthResult> {
        return rxFirebaseUtils.getSingle(firebaseAuth.createUserWithEmailAndPassword(email, password))

    }

    fun createTask(taskModel: TaskModel): Single<TaskModel> {

        val key = getChildReference()?.push()?.key
        key?.let {
            taskModel.id = it
        }
        getChildReference()?.child(key)?.setValue(taskModel)
        return Single.just(taskModel)

    }


    fun updateTask(taskModel: TaskModel): Completable {
        val databaseReference = getChildReference()?.child(taskModel.id)
        val voidTask = databaseReference?.updateChildren(taskModel.map)
        return rxFirebaseUtils.getCompletable(voidTask)
    }

    fun deleteTask(taskModel: TaskModel): Completable {
        return rxFirebaseUtils.getCompletable(getChildReference()?.child(taskModel.id)?.removeValue())


    }

    fun deleteTasks(): Completable {
        return rxFirebaseUtils.getCompletable(getChildReference()?.removeValue())
    }


    @VisibleForTesting
    internal fun getChildReference(): DatabaseReference? {

        if (childReference == null) {
            childReference = firebaseDatabase.reference
                    .child(RemoteDataSource.FIREBASE_CHILD_KEY_TASKS)
                    .child(firebaseAuth.currentUser?.uid)
        }

        return childReference
    }
}
