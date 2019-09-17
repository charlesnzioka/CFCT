package com.elektra.cfct.data.repository.auth;

import androidx.lifecycle.LiveData;

import com.elektra.cfct.data.model.User;
import com.elektra.cfct.util.Constants;

public interface IAuthRepository {
    LiveData<User> authenticateUserWithId(String email, String password) throws Exception;
}
