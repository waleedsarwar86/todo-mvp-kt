package com.todo.di.application.module;

import android.content.res.Resources;
import android.support.v4.app.NotificationManagerCompat;

import com.todo.di.application.DaggerApplication;
import com.todo.device.notification.Notifications;
import com.todo.device.notification.NotificationsImpl;
import com.todo.util.CurrentTimeProvider;
import com.todo.util.CurrentTimeProviderImpl;
import com.todo.util.DateUtils;
import com.todo.util.DateUtilsImpl;
import com.todo.util.RxFirebaseUtils;
import com.todo.util.RxFirebaseUtilsImpl;
import com.todo.util.RxIdlingResourceImpl;
import com.todo.util.StringUtils;
import com.todo.util.StringUtilsImpl;
import com.todo.util.UiSchedulersTransformer;
import com.todo.util.UiSchedulersTransformerImpl;
import com.todo.util.validation.validator.RulesFactory;
import com.todo.util.validation.validator.RulesFactoryImpl;
import com.todo.util.validation.validator.RulesValidator;
import com.todo.util.validation.validator.RulesValidatorImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class UtilsModule {

    @Provides
    @Singleton
    CurrentTimeProvider provideCurrentTimeProvider() {
        return new CurrentTimeProviderImpl();
    }

    @Provides
    @Singleton
    DateUtils provideDateUtils(CurrentTimeProvider currentTimeProvider) {
        return new DateUtilsImpl(currentTimeProvider);
    }

    @Provides
    @Singleton
    RxFirebaseUtils provideRxFirebaseUtils() {
        return new RxFirebaseUtilsImpl(new RxIdlingResourceImpl());
    }

    @Provides
    @Singleton
    UiSchedulersTransformer provideSchedulersTransformer() {
        return new UiSchedulersTransformerImpl();
    }


    @Provides
    @Singleton
    StringUtils stringUtils() {
        return new StringUtilsImpl();
    }


    @Provides
    @Singleton
    Notifications provideNotifications(final NotificationManagerCompat notificationManagerCompat) {
        return new NotificationsImpl(notificationManagerCompat);
    }

    @Provides
    @Singleton
    NotificationManagerCompat provideNotificationManagerCompat(final DaggerApplication application) {
        return NotificationManagerCompat.from(application.getApplicationContext());
    }

    @Provides
    @Singleton
    RulesValidator provideRulesValidator(StringUtils stringUtils) {
        return new RulesValidatorImpl(stringUtils);
    }

    @Provides
    @Singleton
    RulesFactory provideRulesFactory(Resources resources) {
        return new RulesFactoryImpl(resources);
    }


    public interface Exposes {

        CurrentTimeProvider currentTimeProvider();

        DateUtils dateUtils();

        RxFirebaseUtils rxFirebaseUtils();

        UiSchedulersTransformer uiSchedulersTransformer();

        StringUtils stringUtils();

        Notifications notificationUtils();

        RulesValidator rulesValidator();

        RulesFactory rulesFactory();

    }
}
