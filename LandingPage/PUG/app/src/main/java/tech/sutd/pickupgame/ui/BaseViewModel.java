package tech.sutd.pickupgame.ui;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.SchedulerProvider;

public abstract class BaseViewModel extends ViewModel {

    private final CompositeDisposable compositeDisposable;

    @Inject SchedulerProvider provider;

    @Inject DataManager dataManager;

    public BaseViewModel(SchedulerProvider provider, DataManager dataManager) {
        this.compositeDisposable = new CompositeDisposable();
        this.provider = provider;
        this.dataManager = dataManager;
    }

    public abstract void setError(Throwable e);

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public SchedulerProvider getProvider() {
        return provider;
    }

    @Override
    protected void onCleared() {
        if (getCompositeDisposable() != null)
            getCompositeDisposable().dispose();
        super.onCleared();
    }
}
