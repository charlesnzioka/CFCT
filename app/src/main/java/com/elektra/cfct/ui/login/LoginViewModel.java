package com.elektra.cfct.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Log;
import android.util.Patterns;

import com.elektra.cfct.SessionManager;
import com.elektra.cfct.data.LoginRepository;
import com.elektra.cfct.data.Result;
import com.elektra.cfct.data.model.LoggedInUser;
import com.elektra.cfct.R;
import com.elektra.cfct.data.model.User;
import com.elektra.cfct.data.repository.auth.AuthRepository;
import com.elektra.cfct.util.Constants;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class LoginViewModel extends ViewModel {

    private static final String TAG = "LoginViewModel";

    // inject
    private final SessionManager sessionManager; // @Singleton scoped dependency

    @Inject
    public LoginViewModel(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        Log.d(TAG, "LoginViewModel: viewmodel is working...");
    }

    public LiveData<AuthResource<User>> observeAuthState(){
        return sessionManager.getAuthUser();
    }

    public void authenticateWithCredentials(String email, String password) {
        Log.d(TAG, "attemptLogin: attempting to login.");
        sessionManager.authenticateWithId(queryUserId(email, password));
    }

    private LiveData<AuthResource<User>> queryUserId(String email, String password) {

        Observable<User> userObservable = Observable.create(emitter -> {
            if(email.equals(Constants.USER_NAME) && password.equals(Constants.PASSWORD)){
                User user = new User(58,email,"John", "Doe");
                emitter.onNext(user);
                emitter.onComplete();

            }
            else{

                Exception e = new Exception("Invalid credentials");
                emitter.onError(e);

            }
        });

        return LiveDataReactiveStreams.fromPublisher(userObservable.toFlowable(BackpressureStrategy.LATEST)

                // instead of calling onError, do this
                .onErrorReturn(throwable -> {
                    User errorUser = new User();
                    errorUser.setId(-1);
                    return errorUser;
                })

                // wrap User object in AuthResource
                .map((Function<User, AuthResource<User>>) user -> {
                    if(user.getId() == -1){
                        return AuthResource.error("Could not authenticate", null);
                    }
                    return AuthResource.authenticated(user);
                })
                .subscribeOn(Schedulers.io()));
    }



    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
