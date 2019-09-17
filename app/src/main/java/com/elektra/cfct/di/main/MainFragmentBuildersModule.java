package com.elektra.cfct.di.main;

import com.elektra.cfct.ui.main.ChildrenFragment;
import com.elektra.cfct.ui.main.SponsorsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract SponsorsFragment contributeMainFragment();

    @ContributesAndroidInjector
    abstract ChildrenFragment contributeChildrenFragment();

}
