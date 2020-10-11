package tech.sutd.pickupgame.di.auth;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.ViewModelKey;
import tech.sutd.pickupgame.ui.auth.login.LoginViewModel;
import tech.sutd.pickupgame.ui.auth.register.RegisterViewModel;

@Module
public abstract class AuthViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel.class)
    public abstract ViewModel bindRegisterViewModel(RegisterViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    public abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);
}
