package tech.sutd.pickupgame.ui.main.main.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import tech.sutd.pickupgame.data.ui.new_activity.NewRepository;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

public class NewActViewModel extends ViewModel {

    private NewRepository repository;

    private LiveData<List<NewActivity>> newActivities;

    @Inject
    public NewActViewModel(@NonNull Application application) {
        this.repository = new NewRepository(application);
        newActivities = repository.getAllNewActivities();
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

    public LiveData<List<NewActivity>> getNewActivities() {
        return newActivities;
    }
}
