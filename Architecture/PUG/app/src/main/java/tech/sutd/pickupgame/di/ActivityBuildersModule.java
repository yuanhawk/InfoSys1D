package tech.sutd.pickupgame.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.sutd.pickupgame.di.auth.AuthFragmentBuildersModule;
import tech.sutd.pickupgame.di.auth.AuthScope;
import tech.sutd.pickupgame.di.auth.AuthViewModelModule;
import tech.sutd.pickupgame.ui.auth.AuthActivity;

@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = {
                    AuthFragmentBuildersModule.class, AuthViewModelModule.class
            }
    )
    abstract AuthActivity contributeAuthActivity();
}
