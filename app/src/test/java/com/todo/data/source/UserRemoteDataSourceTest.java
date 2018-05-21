package com.todo.data.source;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.todo.data.model.TaskModel;
import com.todo.util.RxFirebaseUtils;
import com.todo.util.RxFirebaseUtilsImpl;
import com.todo.util.RxIdlingResource;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.observers.TestObserver;

import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRemoteDataSourceTest {

    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    @Mock
    private FirebaseAuth mockFirebaseAuth;
    @Mock
    private DatabaseReference mockChildReference;
    @Mock
    private AuthResult mockAuthResult;

    @Mock
    private Task<Void> mockVoidTask;
    @Mock
    private Task<AuthResult> mockAuthResultTask;

    @Captor
    private ArgumentCaptor<OnSuccessListener> testOnSuccessListener;
    @Captor
    private ArgumentCaptor<OnFailureListener> testOnFailureListener;

    private TaskModel fakeTaskModel;
    private RxFirebaseUtils rxFirebaseUtils;
    private UserRemoteDataSource userRemoteDataSource;


    @Before
    public void setUp() {
        setupTask(mockVoidTask);
        setupTask(mockAuthResultTask);
        fakeTaskModel = new TaskModel("id", "title", 0, 1, false);
        RxIdlingResource rxIdlingResource = mock(RxIdlingResource.class);
        rxFirebaseUtils = new RxFirebaseUtilsImpl(rxIdlingResource);
        userRemoteDataSource = Mockito.spy(new UserRemoteDataSource(mockFirebaseDatabase, mockFirebaseAuth, rxFirebaseUtils));
        doReturn(mockChildReference).when(userRemoteDataSource).getChildReference();

    }

    @SuppressWarnings("unchecked")
    private <T> void setupTask(Task<T> task) {
        when(task.addOnSuccessListener(testOnSuccessListener.capture())).thenReturn(task);
        when(task.addOnFailureListener(testOnFailureListener.capture())).thenReturn(task);
    }


    @Test
    public void getCurrentUser_shouldReturnNotNull() {

        // Setup conditions of the test
        FirebaseUser fakeLoggedInUser = mock(FirebaseUser.class);
        when(mockFirebaseAuth.getCurrentUser()).thenReturn(fakeLoggedInUser);

        // Execute the code under test
        FirebaseUser firebaseUser = userRemoteDataSource.getCurrentUser();

        // Make assertions on the results
        Assert.assertNotNull(firebaseUser);

    }

    @Test
    public void getCurrentUser_shouldReturnNull() {

        // Setup conditions of the test
        when(mockFirebaseAuth.getCurrentUser()).thenReturn(null);

        // Execute the code under test
        FirebaseUser firebaseUser = userRemoteDataSource.getCurrentUser();

        // Make assertions on the results
        Assert.assertNull(firebaseUser);

    }

    @Test
    @SuppressWarnings("unchecked")
    public void login_shouldCaptureOnSuccessListener() {

        // Setup conditions of the test
        when(mockFirebaseAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthResultTask);

        // Execute the code under test
        TestObserver<AuthResult> testObserver = userRemoteDataSource.login(anyString(), anyString()).test();
        verify(mockAuthResultTask).addOnSuccessListener(testOnSuccessListener.capture());
        testOnSuccessListener.getValue().onSuccess(mockAuthResult);

        // Make assertions on the results
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(mockAuthResult);
        testObserver.dispose();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void login_shouldCaptureOnFailureListener() {

        // Setup conditions of the test
        Exception exception = new Exception("Invalid Credentials");
        when(mockFirebaseAuth.signInWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthResultTask);

        // Execute the code under test
        TestObserver<AuthResult> testObserver = userRemoteDataSource.login(anyString(), anyString()).test();
        verify(mockAuthResultTask).addOnFailureListener(testOnFailureListener.capture());
        testOnFailureListener.getValue().onFailure(exception);

        // Make assertions on the results
        testObserver.assertError(exception);
        testObserver.dispose();


    }

    @Test
    @SuppressWarnings("unchecked")
    public void registerUser_shouldCaptureOnSuccessListener() {

        // Setup conditions of the test
        when(mockFirebaseAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthResultTask);

        // Execute the code under test
        TestObserver<AuthResult> testObserver = userRemoteDataSource.register(anyString(), anyString()).test();
        verify(mockAuthResultTask).addOnSuccessListener(testOnSuccessListener.capture());
        testOnSuccessListener.getValue().onSuccess(mockAuthResult);


        // Make assertions on the results
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValue(mockAuthResult);
        testObserver.dispose();


    }

    @Test
    @SuppressWarnings("unchecked")
    public void registerUser_shouldCaptureOnFailureListener() {

        // Setup conditions of the test
        Exception exception = new Exception("Invalid Credentials");
        when(mockFirebaseAuth.createUserWithEmailAndPassword(anyString(), anyString())).thenReturn(mockAuthResultTask);

        // Execute the code under test
        TestObserver<AuthResult> testObserver = userRemoteDataSource.register(anyString(), anyString()).test();
        verify(mockAuthResultTask).addOnFailureListener(testOnFailureListener.capture());
        testOnFailureListener.getValue().onFailure(exception);

        // Make assertions on the results
        testObserver.assertError(exception);
        testObserver.dispose();


    }

    @Test
    public void createTask_shouldReturnCreatedTask() {

        // Setup conditions of the test
        when(mockChildReference.push()).thenReturn(mockChildReference);
        when(mockChildReference.getKey()).thenReturn(fakeTaskModel.getId());
        when(mockChildReference.child(fakeTaskModel.getId())).thenReturn(mockChildReference);
        when(mockChildReference.setValue(fakeTaskModel)).thenReturn(mockVoidTask);

        // Execute the code under test
        TestObserver<TaskModel> testObserver = userRemoteDataSource.createTask(fakeTaskModel).test();


        // Make assertions on the results
        testObserver.assertNoErrors();
        testObserver.assertValue(fakeTaskModel);
        testObserver.dispose();

    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateTask_shouldCaptureOnSuccessListener() {


        // Setup conditions of the test
        when(mockChildReference.child(fakeTaskModel.getId())).thenReturn(mockChildReference);
        when(mockChildReference.updateChildren(anyMap())).thenReturn(mockVoidTask);

        // Execute the code under test
        TestObserver testObserver = userRemoteDataSource.updateTask(fakeTaskModel).test();
        verify(mockVoidTask).addOnSuccessListener(testOnSuccessListener.capture());
        testOnSuccessListener.getValue().onSuccess(mockVoidTask.getResult());

        // Make assertions on the results
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.dispose();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void updateTask_shouldCaptureOnFailureListener() {

        // Setup conditions of the test
        Exception exception = new Exception();
        when(mockChildReference.child(fakeTaskModel.getId())).thenReturn(mockChildReference);
        when(mockChildReference.updateChildren(anyMap())).thenReturn(mockVoidTask);

        // Execute the code under test
        TestObserver testObserver = userRemoteDataSource.updateTask(fakeTaskModel).test();
        verify(mockVoidTask).addOnFailureListener(testOnFailureListener.capture());
        testOnFailureListener.getValue().onFailure(exception);

        // Execute the code under test
        testObserver.assertError(exception);
        testObserver.dispose();

    }

    @Test
    @SuppressWarnings("unchecked")
    public void delete_shouldCaptureOnSuccessListener() {

        // Setup conditions of the test
        when(mockChildReference.child(fakeTaskModel.getId())).thenReturn(mockChildReference);
        when(mockChildReference.removeValue()).thenReturn(mockVoidTask);

        // Execute the code under test
        TestObserver testObserver = userRemoteDataSource.deleteTask(fakeTaskModel).test();
        verify(mockVoidTask).addOnSuccessListener(testOnSuccessListener.capture());
        testOnSuccessListener.getValue().onSuccess(mockVoidTask.getResult());

        // Execute the code under test
        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.dispose();
    }


    @Test
    @SuppressWarnings("unchecked")
    public void deleteTask_shouldCaptureOnFailureListener() {

        // Setup conditions of the test
        Exception exception = new Exception();
        when(mockChildReference.child(fakeTaskModel.getId())).thenReturn(mockChildReference);
        when(mockChildReference.removeValue()).thenReturn(mockVoidTask);

        // Execute the code under test
        TestObserver testObserver = userRemoteDataSource.deleteTask(fakeTaskModel).test();
        verify(mockVoidTask).addOnFailureListener(testOnFailureListener.capture());
        testOnFailureListener.getValue().onFailure(exception);

        // Execute the code under test
        testObserver.assertError(exception);
        testObserver.dispose();

    }


}