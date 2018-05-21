package com.todo.ui.feature.login;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.todo.BuildConfig;
import com.todo.R;
import com.todo.ui.feature.register.RegisterActivity;
import com.todo.ui.feature.tasks.TasksActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.todo.util.TextInputLayoutMatchers.withTextInputLayoutError;
import static com.todo.util.ToolbarMatchers.withToolbarTitle;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    public static final String INVALID_EMAIL = "invalid_email";


    @Rule
    public ActivityTestRule<LoginActivity> loginActivityTestRule = new ActivityTestRule<>(LoginActivity.class);


    @Test
    public void loginForm_invalidEmailAddress_shouldShowEmailError() {

        onView(withId(R.id.login_input_edit_text_email)).perform(typeText(INVALID_EMAIL));

        onView(withId(R.id.login_button_login)).check(matches(not(isEnabled())));
        onView(withId(R.id.login_input_layout_email)).check(matches(withTextInputLayoutError(R.string.all_error_email_invalid)));

    }

    @Test
    public void loginForm_invalidEmptyPassword_shouldShowPasswordError() {

        onView(withId(R.id.login_input_edit_text_password)).perform(typeText("password"), clearText());

        onView(withId(R.id.login_button_login)).check(matches(not(isEnabled())));
        onView(withId(R.id.login_input_layout_password)).check(matches(withTextInputLayoutError(R.string.all_error_password_required)));

    }

    @Test
    public void loginForm_validEmailAndPassword_shouldShowTasksActivity() {


        onView(withId(R.id.login_input_edit_text_email)).perform(typeText(BuildConfig.FIREBASE_TEST_EMAIL));
        onView(withId(R.id.login_input_edit_text_password)).perform(typeText(BuildConfig.FIREBASE_TEST_PASSWORD));

        onView(withId(R.id.login_button_login)).check(matches(isEnabled()));
        onView(withId(R.id.login_button_login)).perform(scrollTo(), click());

        onView(isAssignableFrom(Toolbar.class)).check(matches(withToolbarTitle(R.string.all_name_app)));


    }


    @Test
    public void buttonRegisterClicked_shouldShowRegisterScreen() {


        onView(withId(R.id.login_button_register)).perform(click());

        onView(isAssignableFrom(Toolbar.class)).check(matches(withToolbarTitle(R.string.register_title)));
    }

    private String getString(int resId) {

        return loginActivityTestRule.getActivity().getString(resId);
    }
}