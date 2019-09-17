package com.elektra.cfct.di.main;

import androidx.lifecycle.ViewModel;

import com.elektra.cfct.di.ViewModelKey;
import com.elektra.cfct.ui.main.ChildrenViewModel;
import com.elektra.cfct.ui.main.SponsorsViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SponsorsViewModel.class)
    public abstract ViewModel bindSponsorsViewModel(SponsorsViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(ChildrenViewModel.class)
    public abstract ViewModel bindChildrenViewModel(ChildrenViewModel viewModel);

}
