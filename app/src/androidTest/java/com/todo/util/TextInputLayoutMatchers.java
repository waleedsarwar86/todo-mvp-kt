package com.todo.util;

import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static com.todo.util.TextInputLayoutMatchers.TextInputLayoutMethod.GET_ERROR;
import static com.todo.util.TextInputLayoutMatchers.TextInputLayoutMethod.GET_HINT;
import static org.hamcrest.Matchers.is;

public class TextInputLayoutMatchers {

    public static Matcher<View> withTextInputLayoutHint(final int resourceId) {
        return new WithStringResourceMatcher(resourceId, GET_HINT);
    }

    public static Matcher<View> withTextInputLayoutHint(final CharSequence text) {
        return new WithCharSequenceMatcher(is(text), GET_HINT);
    }

    public static Matcher<View> withTextInputLayoutError(final CharSequence text) {
        return new WithCharSequenceMatcher(is(text), GET_ERROR);
    }


    public static Matcher<View> withTextInputLayoutError(final int resourceId) {
        return new WithStringResourceMatcher(resourceId, GET_ERROR);
    }

    private static final class WithCharSequenceMatcher extends BoundedMatcher<View, TextInputLayout> {

        private final Matcher<CharSequence> charSequenceMatcher;
        private final TextInputLayoutMethod method;

        private WithCharSequenceMatcher(Matcher<CharSequence> charSequenceMatcher, TextInputLayoutMethod method) {
            super(TextInputLayout.class);
            this.charSequenceMatcher = charSequenceMatcher;
            this.method = method;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("with text: ");
            charSequenceMatcher.describeTo(description);
        }

        @Override
        protected boolean matchesSafely(TextInputLayout textInputLayout) {
            CharSequence actualText;
            switch (method) {
                case GET_HINT:
                    actualText = textInputLayout.getHint();
                    break;
                case GET_ERROR:
                    actualText = textInputLayout.getError();
                    break;
                default:
                    throw new IllegalStateException("Unexpected TextInputLayout method: " + method.toString());
            }

            return charSequenceMatcher.matches(actualText);
        }
    }


    static final class WithStringResourceMatcher extends BoundedMatcher<View, TextInputLayout> {

        private final int resourceId;

        private final TextInputLayoutMethod method;

        @Nullable
        private String resourceName;
        @Nullable
        private String expectedText;


        private WithStringResourceMatcher(int resourceId, TextInputLayoutMethod method) {
            super(TextInputLayout.class);
            this.resourceId = resourceId;
            this.method = method;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("with string from resource id: ").appendValue(resourceId);
            if (null != resourceName) {
                description.appendText("[").appendText(resourceName).appendText("]");
            }
            if (null != expectedText) {
                description.appendText(" value: ").appendText(expectedText);
            }
        }

        @Override
        public boolean matchesSafely(TextInputLayout textInputLayout) {
            if (null == expectedText) {
                try {
                    expectedText = textInputLayout.getResources().getString(resourceId);
                    resourceName = textInputLayout.getResources().getResourceEntryName(resourceId);
                } catch (Resources.NotFoundException ignored) {
                    /* view could be from a context unaware of the resource id. */
                }
            }
            CharSequence actualText;
            switch (method) {
                case GET_HINT:
                    actualText = textInputLayout.getHint();
                    break;
                case GET_ERROR:
                    actualText = textInputLayout.getError();
                    break;
                default:
                    throw new IllegalStateException("Unexpected TextView method: " + method.toString());
            }
            // FYI: actualText may not be string ... its just a char sequence convert to string.
            return null != expectedText
                    && null != actualText
                    && expectedText.equals(actualText.toString());
        }
    }

    enum TextInputLayoutMethod {
        GET_ERROR,
        GET_HINT
    }

}
