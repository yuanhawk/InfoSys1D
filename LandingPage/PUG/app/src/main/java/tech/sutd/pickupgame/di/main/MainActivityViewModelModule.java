package tech.sutd.pickupgame.di.main;

import androidx.lifecycle.ViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import tech.sutd.pickupgame.di.ViewModelKey;
import tech.sutd.pickupgame.ui.main.main.viewmodel.NewActViewModel;
import tech.sutd.pickupgame.ui.main.main.viewmodel.UpcomingActViewModel;

@Module
public abstract class MainActivityViewModelModule {

    @MainScope
    @Binds
    @IntoMap
    @ViewModelKey(UpcomingActViewModel.class)
    public abstract ViewModel bindUpcomingActViewModel(UpcomingActViewModel viewModel);

    @MainScope
    @Binds
    @IntoMap
    @ViewModelKey(NewActViewModel.class)
    public abstract ViewModel bindNewActViewModel(NewActViewModel viewModel);
}
