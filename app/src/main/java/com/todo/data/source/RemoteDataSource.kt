package com.todo.data.source

import android.support.annotation.VisibleForTesting

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.todo.util.RxFirebaseUtils

/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

open class RemoteDataSource internal constructor(val firebaseDatabase: FirebaseDatabase, val firebaseAuth: FirebaseAuth, val rxFirebaseUtils: RxFirebaseUtils) {
    companion object {

        @VisibleForTesting
        val FIREBASE_CHILD_KEY_TASKS = "tasks"
    }
}
