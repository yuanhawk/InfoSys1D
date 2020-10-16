package tech.sutd.pickupgame.di.auth;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.di.ViewModelKey;
import tech.sutd.pickupgame.ui.auth.UserViewModel;

@Module
public abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(UserViewModel.class)
    public abstract ViewModel bindUserViewModel(UserViewModel viewModel);
}
