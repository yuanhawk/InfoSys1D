package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.Resource;
import tech.sutd.pickupgame.data.SchedulerProvider;
import tech.sutd.pickupgame.models.ui.PastActivity;
import tech.sutd.pickupgame.ui.BaseViewModel;

public class PastActViewModel extends BaseViewModel {

    private final MediatorLiveData<Resource<List<PastActivity>>> source = new MediatorLiveData<>();

    @Override
    public void setError(Throwable e) {

    }

    @Inject
    public PastActViewModel(SchedulerProvider provider, DataManager dataManager) {
        super(provider, dataManager);
    }

    public void insert(PastActivity pastActivity) {
        getCompositeDisposable().add(getDataManager().insertPastActivity(pastActivity)
                .doOnSubscribe(disposable -> doOnLoading())
                .subscribeOn(getProvider().io())
                .doOnError(this::setError)
                .subscribe());
    }


    public LiveData<Resource<List<PastActivity>>> getPastActivities() {
        source.setValue(Resource.loading(null));

        final LiveData<Resource<List<PastActivity>>> activitySource = LiveDataReactiveStreams.fromPublisher(
                getDataManager().getAllPastActivities()
                .onBackpressureBuffer()
                .map(Resource::success)
                .subscribeOn(getProvider().io())
        );

        source.addSource(activitySource, listResource -> {
            source.setValue(listResource);
            source.removeSource(activitySource);
        });
        return source;
    }

    private void doOnLoading() {
        source.postValue(Resource.loading(null));
    }
}
