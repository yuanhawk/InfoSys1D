package tech.sutd.pickupgame.di.auth;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.sutd.pickupgame.ui.auth.GetStartedFragment;
import tech.sutd.pickupgame.ui.auth.LoginFragment;
import tech.sutd.pickupgame.ui.auth.RegisterFragment;

@Module
public abstract class AuthFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract GetStartedFragment contributeGetStartedFragment();

    @ContributesAndroidInjector
    abstract LoginFragment contributeLoginFragment();

    @ContributesAndroidInjector
    abstract RegisterFragment contributeRegisterFragment();
}
