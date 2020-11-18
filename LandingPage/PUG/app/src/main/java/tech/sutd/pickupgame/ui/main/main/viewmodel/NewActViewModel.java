package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import javax.inject.Inject;

import tech.sutd.pickupgame.data.ui.new_activity.NewRepository;
import tech.sutd.pickupgame.models.ui.NewActivity;

public class NewActViewModel extends ViewModel {

    private NewRepository repository;

    private LiveData<PagedList<NewActivity>> allNewActivitiesByClock, allNewActivitiesBySport, newActivitiesByClock2;

    @Inject
    public NewActViewModel(@NonNull Application application) {
        this.repository = new NewRepository(application);
        allNewActivitiesByClock = repository.getAllNewActivitiesByClock();
        allNewActivitiesBySport = repository.getAllNewActivitiesBySport();
        newActivitiesByClock2 = repository.getNewActivitiesByClock2();
    }

    public void insert(NewActivity newActivity) {
        repository.insert(newActivity);
    }

    public void update(NewActivity newActivity) {
        repository.update(newActivity);
    }

    public void delete(NewActivity newActivity) {
        repository.delete(newActivity);
    }

    public void deleteAllUpcomingActivities() {
        repository.deleteAllNewActivities();
    }

    public LiveData<PagedList<NewActivity>> getAllNewActivitiesByClock() {
        return allNewActivitiesByClock;
    }

    public LiveData<PagedList<NewActivity>> getAllNewActivitiesBySport() {
        return allNewActivitiesBySport;
    }

    public LiveData<PagedList<NewActivity>> getNewActivitiesByClock2() {
        return newActivitiesByClock2;
    }
}
