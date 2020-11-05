package tech.sutd.pickupgame.di.main.upcoming_fragment;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.di.ViewModelKey;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.YourActViewModel;

@Module
public abstract class UpcomingActFragmentViewModelModule {

    @UpcomingActFragmentScope
    @Binds
    @IntoMap
    @ViewModelKey(UpcomingActViewModel.class)
    public abstract ViewModel bindUpcomingActViewModel(UpcomingActViewModel viewModel);

    @UpcomingActFragmentScope
    @Binds
    @IntoMap
    @ViewModelKey(YourActViewModel.class)
    public abstract ViewModel bindYourActViewModel(YourActViewModel viewModel);
}
