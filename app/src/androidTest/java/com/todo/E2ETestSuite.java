package com.todo;

import com.todo.ui.feature.login.LoginActivityTest;
import com.todo.ui.feature.tasks.TasksActivity;
import com.todo.ui.feature.tasks.TasksActivityTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({LoginActivityTest.class,TasksActivityTest.class,})
public class E2ETestSuite {
}
