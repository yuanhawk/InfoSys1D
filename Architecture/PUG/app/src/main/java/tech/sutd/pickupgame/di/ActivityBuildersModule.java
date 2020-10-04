package tech.sutd.pickupgame.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.sutd.pickupgame.di.auth.AuthScope;
import tech.sutd.pickupgame.ui.auth.AuthActivity;

@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector
    abstract AuthActivity contributeAuthActivity();
}
