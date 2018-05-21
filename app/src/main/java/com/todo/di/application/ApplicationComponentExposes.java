package com.todo.di.application;

import com.todo.di.application.module.ApplicationModule;
import com.todo.di.application.module.DataModule;
import com.todo.di.application.module.ServiceModule;
import com.todo.di.application.module.UtilsModule;

public interface ApplicationComponentExposes extends ApplicationModule.Exposes,
        ServiceModule.Exposes,
        UtilsModule.Exposes,
        DataModule.Exposes {

}
