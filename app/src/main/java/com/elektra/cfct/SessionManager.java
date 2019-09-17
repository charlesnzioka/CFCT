package com.elektra.cfct;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.elektra.cfct.data.model.LoggedInUser;
import com.elektra.cfct.data.model.User;
import com.elektra.cfct.ui.login.AuthResource;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SessionManager {

    private static final String TAG = "SessionManager";

    // data
    private MediatorLiveData<AuthResource<User>> cachedUser = new MediatorLiveData<>();

    @Inject
    public SessionManager() {

    }

    public void authenticateWithId(final LiveData<AuthResource<User>> source) {
        if(cachedUser != null){
            cachedUser.setValue(AuthResource.loading((User)null));
            cachedUser.addSource(source, new Observer<AuthResource<User>>() {
                @Override
                public void onChanged(AuthResource<User> userAuthResource) {
                    cachedUser.setValue(userAuthResource);
                    cachedUser.removeSource(source);

                    if(userAuthResource.status.equals(AuthResource.AuthStatus.ERROR)){
                        cachedUser.setValue(AuthResource.<User>logout());
                    }
                }
            });
        }
    }

    public void logOut() {
        Log.d(TAG, "logOut: logging out...");
        cachedUser.setValue(AuthResource.<User>logout());
    }


    public LiveData<AuthResource<User>> getAuthUser(){
        return cachedUser;
    }
}