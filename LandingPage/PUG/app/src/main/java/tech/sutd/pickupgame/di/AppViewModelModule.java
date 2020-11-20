package tech.sutd.pickupgame.di;

import androidx.lifecycle.ViewModel;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.di.auth.AuthScope;
import tech.sutd.pickupgame.ui.auth.UserViewModel;

@Module
public abstract class AppViewModelModule {

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    public abstract ViewModel bindUserViewModel(UserViewModel viewModel);

}
