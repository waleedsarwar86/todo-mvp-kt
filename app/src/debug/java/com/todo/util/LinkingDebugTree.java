package com.todo.util;

import android.support.annotation.NonNull;

import java.util.Locale;

import timber.log.Timber;

public class LinkingDebugTree extends Timber.DebugTree {


    @Override
    protected String createStackElementTag(@NonNull StackTraceElement element) {
        return String.format(Locale.getDefault(), "(%s:%d)", element.getFileName(), +element.getLineNumber());
    }


}
