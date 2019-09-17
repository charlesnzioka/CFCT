package com.elektra.cfct.di;

import com.elektra.cfct.di.login.LoginModule;
import com.elektra.cfct.di.login.LoginViewModelsModule;
import com.elektra.cfct.di.main.MainFragmentBuildersModule;
import com.elektra.cfct.di.main.MainModule;
import com.elektra.cfct.di.main.MainScope;
import com.elektra.cfct.di.main.MainViewModelsModule;
import com.elektra.cfct.ui.login.LoginActivity;
import com.elektra.cfct.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {
    @ContributesAndroidInjector(
            modules = {
                    LoginViewModelsModule.class,
                    LoginModule.class
            }
    )
    abstract LoginActivity contributeLoginActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {MainFragmentBuildersModule.class, MainViewModelsModule.class, MainModule.class}
    )
    abstract MainActivity contributeMainActivity();
}
