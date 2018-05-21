package com.todo.data.repository

import com.todo.data.model.TaskModel

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single


interface TodoRepository {

    val isUserLoggedIn: Single<Boolean>

    val tasks: Observable<List<TaskModel>>

    fun login(email: String, password: String): Completable

    fun logout()

    fun register(email: String, password: String): Completable


    fun createTask(taskModel: TaskModel): Single<TaskModel>

    fun updateTask(taskModel: TaskModel): Completable

    fun deleteTask(taskModel: TaskModel): Completable

    fun deleteTasks(): Completable


}
