package com.elektra.cfct.di.main;

import com.elektra.cfct.ui.main.ChildrenRecyclerAdapter;
import com.elektra.cfct.ui.main.SponsorsRecyclerAdapter;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    @MainScope
    @Provides
    static SponsorsRecyclerAdapter provideAdapter(){
        return new SponsorsRecyclerAdapter();
    }

    @MainScope
    @Provides
    static ChildrenRecyclerAdapter provideChildrenAdapter(){
        return new ChildrenRecyclerAdapter();
    }
}
