package tech.sutd.sportbuddy.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import tech.sutd.sportbuddy.di.auth.AuthScope;
import tech.sutd.sportbuddy.ui.auth.AuthActivity;

@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector
    abstract AuthActivity contributeAuthActivity();
}
