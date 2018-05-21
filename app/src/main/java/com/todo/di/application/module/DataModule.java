package com.todo.di.application.module;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.todo.data.repository.TodoRepository;
import com.todo.data.repository.TodoRepositoryImpl;
import com.todo.data.source.UserRemoteDataSource;
import com.todo.util.RxFirebaseUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public final class DataModule {


    @Provides
    @Singleton
    TodoRepository provideTodoRepository(final UserRemoteDataSource userRemoteDataSource) {
        return new TodoRepositoryImpl(userRemoteDataSource);
    }

    @Provides
    @Singleton
    public FirebaseDatabase provideFirebaseDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
        return firebaseDatabase;
    }

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }


    @Provides
    @Singleton
    UserRemoteDataSource provideUserRemoteDataSource(final FirebaseDatabase firebaseDatabase
            , final FirebaseAuth firebaseAuth, final RxFirebaseUtils rxFirebaseUtils) {
        return new UserRemoteDataSource(firebaseDatabase, firebaseAuth, rxFirebaseUtils);
    }


    public interface Exposes {

        TodoRepository todoRepository();
    }
}
