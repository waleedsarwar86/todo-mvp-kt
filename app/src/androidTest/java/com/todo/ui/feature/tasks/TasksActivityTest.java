package com.todo.ui.feature.tasks;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.todo.R;
import com.todo.data.model.TaskModel;
import com.todo.ui.feature.addedittask.AddEditTaskActivity;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToHolder;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class TasksActivityTest {

    @Rule
    public IntentsTestRule<TasksActivity> tasksActivityIntentsTestRule = new IntentsTestRule<>(TasksActivity.class);

    @Test
    public void buttonAddTaskClicked_shouldShowAddEditTaskScreen() {

        onView(withId(R.id.tasks_button_add_task)).perform(click());
        intended(hasComponent(AddEditTaskActivity.class.getName()));
    }


    @Test
    public void buttonAddTaskClicked_givenTask_shouldShowTaskOnScreen() {

        TaskModel taskModel = new TaskModel();
        taskModel.setTitle("Testing");


        // click on add task button
        onView(withId(R.id.tasks_button_add_task)).perform(click());

        // Add title
        onView(withId(R.id.add_edit_task_input_edit_text_task_title)).perform(typeText(taskModel.getTitle()), closeSoftKeyboard());

        // save the task
        onView(withId(R.id.add_edit_task_button_done)).perform(click());

        // scroll task list to added Task, by finding it's title
        onView(withId(R.id.tasks_recyclerview_tasks)).perform(scrollToHolder(withTask(taskModel)));

        // Verify task is displayed on screen
        onView(withItemText(taskModel.getTitle())).check(matches(isDisplayed()));


        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getTargetContext());

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withText("Logout")).perform(click());

    }

    private static Matcher<RecyclerView.ViewHolder> withTask(final TaskModel taskModel) {


        return new BoundedMatcher<RecyclerView.ViewHolder, TasksAdapter.TaskItemViewHolder>(TasksAdapter.TaskItemViewHolder.class) {
            @Override
            protected boolean matchesSafely(TasksAdapter.TaskItemViewHolder item) {
                return item.textViewTitle.getText().toString().equalsIgnoreCase(taskModel.getTitle());
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("view holder with title: " + taskModel.getTitle());
            }
        };
    }

    private Matcher<View> withItemText(final String itemText) {
        return new TypeSafeMatcher<View>() {
            @Override
            public boolean matchesSafely(View item) {
                return allOf(isDescendantOfA(isAssignableFrom(RecyclerView.class)), withText(itemText)).matches(item);
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("is isDescendantOfA RV with text " + itemText);
            }
        };
    }


}