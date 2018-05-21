package com.todo.data.exception

import com.google.firebase.database.DatabaseError

/**
 * @author Waleed Sarwar
 * @since Dec 13, 2017
 */

class FirebaseDataException(val error: DatabaseError) : Exception() {

    override fun toString(): String {
        return "RxFirebaseDataException{" +
                "error=" + error +
                '}'.toString()
    }
}
