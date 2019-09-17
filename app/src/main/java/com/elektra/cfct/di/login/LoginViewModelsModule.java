package com.elektra.cfct.di.login;

import androidx.lifecycle.ViewModel;

import com.elektra.cfct.di.ViewModelKey;
import com.elektra.cfct.ui.login.LoginViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class LoginViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);
}
