package com.todo.data.repository

import com.todo.data.model.TaskModel
import com.todo.data.source.UserRemoteDataSource

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


/**
 * @author Waleed Sarwar
 * @since Nov 25, 2017
 */

class TodoRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource) : TodoRepository {

    /********* TodoRepository Inherited Methods  */

    override val isUserLoggedIn: Single<Boolean>
        get() = Single.just(userRemoteDataSource.currentUser?.toString()?.isNotEmpty())

    override val tasks: Observable<List<TaskModel>>
        get() = userRemoteDataSource.tasks

    override fun login(email: String, password: String): Completable {
        return Completable.fromSingle(userRemoteDataSource.login(email, password))

    }

    override fun logout() {
        userRemoteDataSource.logout()
    }

    override fun register(email: String, password: String): Completable {
        return Completable.fromSingle(userRemoteDataSource.register(email, password))
    }


    override fun createTask(taskModel: TaskModel): Single<TaskModel> {
        return userRemoteDataSource.createTask(taskModel)
    }

    override fun updateTask(taskModel: TaskModel): Completable {
        return userRemoteDataSource.updateTask(taskModel)
    }

    override fun deleteTask(taskModel: TaskModel): Completable {
        return userRemoteDataSource.deleteTask(taskModel)
    }

    override fun deleteTasks(): Completable {
        return userRemoteDataSource.deleteTasks()
    }


}
