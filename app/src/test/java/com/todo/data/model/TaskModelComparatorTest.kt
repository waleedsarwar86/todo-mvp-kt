package com.todo.data.model

import junit.framework.Assert

import org.junit.Before
import org.junit.Test

class TaskModelComparatorTest {


    private lateinit var byDateComparator: TaskModelComparator.ByDateComparator
    private lateinit var byTitleComparator: TaskModelComparator.ByTitleComparator
    private lateinit var byPriorityComparator: TaskModelComparator.ByPriorityComparator

    @Before
    fun setUp() {
        byDateComparator = TaskModelComparator.ByDateComparator()
        byTitleComparator = TaskModelComparator.ByTitleComparator()
        byPriorityComparator = TaskModelComparator.ByPriorityComparator()
    }

    @Test
    fun byDateComparator_shouldReturnEqual() {

        val fakeTask1 = TaskModel("id1", "1title", 999999, TaskModel.PRIORITY_1, true)
        val fakeTask2 = TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_2, false)

        val result = byDateComparator.compare(fakeTask1, fakeTask2)

        Assert.assertTrue("Expected to be equal", result == 0)


    }

    @Test
    fun byDateComparator_shouldReturnGreaterThan() {

        val fakeTask1 = TaskModel("id1", "1title", 100000000, TaskModel.PRIORITY_1, true)
        val fakeTask2 = TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_2, false)
        val result = byDateComparator.compare(fakeTask1, fakeTask2)
        Assert.assertTrue("Expected to be greater than", result >= 1)


    }

    @Test
    fun byDateComparator_shouldReturnLessThan() {

        val fakeTask1 = TaskModel("id1", "1title", 999998, TaskModel.PRIORITY_1, true)
        val fakeTask2 = TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_2, false)

        val result = byDateComparator.compare(fakeTask1, fakeTask2)
        Assert.assertTrue("Expected to be less than", result <= -1)

    }

    @Test
    fun byTitleComparator_shouldReturnEqual() {

        val fakeTask1 = TaskModel("id1", "title", 999999, TaskModel.PRIORITY_1, true)
        val fakeTask2 = TaskModel("id2", "title", 999999, TaskModel.PRIORITY_2, false)

        val result = byTitleComparator.compare(fakeTask1, fakeTask2)
        Assert.assertTrue("Expected to be equal", result == 0)


    }

    @Test
    fun byTitleComparator_shouldReturnGreaterThan() {

        val fakeTask1 = TaskModel("id1", "2title", 100000000, TaskModel.PRIORITY_1, true)
        val fakeTask2 = TaskModel("id2", "1title", 999999, TaskModel.PRIORITY_2, false)

        val result = byTitleComparator.compare(fakeTask1, fakeTask2)
        Assert.assertTrue("Expected to be greater than", result >= 1)


    }

    @Test
    fun byTitleComparator_shouldReturnLessThan() {

        val fakeTask1 = TaskModel("id1", "1title", 999998, TaskModel.PRIORITY_1, true)
        val fakeTask2 = TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_2, false)

        val result = byTitleComparator.compare(fakeTask1, fakeTask2)
        Assert.assertTrue("Expected to be less than", result <= -1)

    }

    @Test
    fun byPriorityComparator_shouldReturnEqual() {

        val fakeTask1 = TaskModel("id1", "1title", 999999, TaskModel.PRIORITY_1, true)
        val fakeTask2 = TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_1, false)

        val result = byPriorityComparator!!.compare(fakeTask1, fakeTask2)
        Assert.assertTrue("Expected to be equal", result == 0)


    }

    @Test
    fun byPriorityComparator_shouldReturnGreaterThan() {

        val fakeTask1 = TaskModel("id1", "1title", 100000000, TaskModel.PRIORITY_2, true)
        val fakeTask2 = TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_1, false)

        val result = byPriorityComparator.compare(fakeTask1, fakeTask2)
        Assert.assertTrue("Expected to be greater than", result >= 1)


    }

    @Test
    fun byPriorityComparator_shouldReturnLessThan() {

        val fakeTask1 = TaskModel("id1", "1title", 999998, TaskModel.PRIORITY_1, true)
        val fakeTask2 = TaskModel("id2", "2title", 999999, TaskModel.PRIORITY_2, false)

        val result = byPriorityComparator.compare(fakeTask1, fakeTask2)
        Assert.assertTrue("Expected to be less than", result <= -1)

    }
}