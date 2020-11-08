package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import javax.inject.Inject;

import tech.sutd.pickupgame.data.ui.upcoming_activity.UpcomingRepository;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

public class UpcomingActViewModel extends ViewModel {

    private UpcomingRepository repository;

    private LiveData<PagedList<UpcomingActivity>> allUpcomingActivitiesByClock, upcomingActivitiesByClock2;

    @Inject
    public UpcomingActViewModel(@NonNull Application application) {
        this.repository = new UpcomingRepository(application);
        allUpcomingActivitiesByClock = repository.getAllUpcomingActivitiesByClock();
        upcomingActivitiesByClock2 = repository.getUpcomingActivitiesByClockTwo();
    }

    public void insert(UpcomingActivity upcomingActivity) {
        repository.insert(upcomingActivity);
    }

    public void update(UpcomingActivity upcomingActivity) {
        repository.update(upcomingActivity);
    }

    public void delete(UpcomingActivity upcomingActivity) {
        repository.delete(upcomingActivity);
    }

    public void deleteAllUpcomingActivities() {
        repository.deleteAllUpcomingActivities();
    }

    public LiveData<PagedList<UpcomingActivity>> getAllUpcomingActivitiesByClock() {
        return allUpcomingActivitiesByClock;
    }

    public LiveData<PagedList<UpcomingActivity>> getUpcomingActivitiesByClock2() {
        return upcomingActivitiesByClock2;
    }
}
