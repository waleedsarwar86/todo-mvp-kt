package com.todo.data.model

import android.os.Parcel
import android.os.Parcelable
import android.support.v7.util.DiffUtil

import java.util.HashMap

/**
 * @author Waleed Sarwar
 * @since Dec 11, 2017
 */
class TaskModel(var deadline: Long = System.currentTimeMillis(), var priority: Int = PRIORITY_4) : Parcelable {


    /********* Member Methods   */


    lateinit var id: String
    lateinit var title: String
    var reminder: Long = 0
    var completed: Boolean = false

    val map: Map<String, Any>
        get() {

            val taskMap = HashMap<String, Any>()
            taskMap["id"] = id
            taskMap["title"] = title
            taskMap["deadline"] = deadline
            taskMap["reminder"] = reminder
            taskMap["priority"] = priority
            taskMap["completed"] = completed
            return taskMap
        }

    /********* Constructors  */

    constructor(id: String = "", title: String = "", deadline: Long = 0, reminder: Long = 0, priority: Int, completed: Boolean = false) : this(deadline, priority) {
        this.id = id
        this.title = title
        this.reminder = reminder
        this.priority = priority
        this.completed = completed

    }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readLong(),
            parcel.readLong(),
            parcel.readInt(),
            parcel.readByte().toInt() != 0)


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.id)
        dest.writeString(this.title)
        dest.writeLong(this.deadline)
        dest.writeLong(this.reminder)
        dest.writeInt(this.priority)
        dest.writeByte(if (this.completed) 1.toByte() else 0.toByte())
    }

    companion object {

        /********* Constants Fields   */

        const val PRIORITY_1 = 1
        const val PRIORITY_2 = 2
        const val PRIORITY_3 = 3
        const val PRIORITY_4 = 4

        val CREATOR: Parcelable.Creator<TaskModel> = object : Parcelable.Creator<TaskModel> {
            override fun createFromParcel(source: Parcel): TaskModel {
                return TaskModel(source)
            }

            override fun newArray(size: Int): Array<TaskModel?> {
                return arrayOfNulls(size)
            }
        }

        val DIFF_CALLBACK: DiffUtil.ItemCallback<TaskModel> = object : DiffUtil.ItemCallback<TaskModel>() {
            override fun areItemsTheSame(oldTask: TaskModel, newTask: TaskModel): Boolean {
                return oldTask.id == newTask.id
            }

            override fun areContentsTheSame(oldTask: TaskModel, newTask: TaskModel): Boolean {
                return oldTask.title == newTask.title
            }
        }
    }


}
