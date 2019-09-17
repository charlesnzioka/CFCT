package com.elektra.cfct.data.repository.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.elektra.cfct.data.model.User;
import com.elektra.cfct.util.Constants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthRepository implements IAuthRepository {
    private static MutableLiveData<User> user;

    @Inject
    public AuthRepository() {

    }
    @Override
    public LiveData<User> authenticateUserWithId(String email, String password){
        if(email.equals(Constants.USER_NAME) && password.equals(Constants.PASSWORD)){
            user.setValue(new User(58,email,"John", "Doe"));

        }
        else{

            user.setValue(new User(-1, email,"John", "Doe"));
        }

        return user;
    }
}
