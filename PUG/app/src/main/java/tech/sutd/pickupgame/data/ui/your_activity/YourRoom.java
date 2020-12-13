package tech.sutd.pickupgame.data.ui.your_activity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import tech.sutd.pickupgame.data.ui.helper.YourRoomHelper;
import tech.sutd.pickupgame.models.ui.YourActivity;

public class YourRoom implements YourRoomHelper {

    private final YourDatabase database;

    @Inject
    public YourRoom(YourDatabase database) {
        this.database = database;
    }

    @Override
    public Completable insertYourActivity(YourActivity activity) {
        return database.yourDao().insertYourActivity(activity);
    }

    @Override
    public Completable deleteYourActivityById(String id) {
        return database.yourDao().deleteYourActivityById(id);
    }

    @Override
    public Completable deleteAllYourActivities() {
        return database.yourDao().deleteAllYourActivities();
    }

    @Override
    public Flowable<List<YourActivity>> getAllYourActivitiesLimit10() {
        return database.yourDao().getAllYourActivitiesLimit10();
    }

    @Override
    public Flowable<List<YourActivity>> getAllYourActivities() {
        return database.yourDao().getAllYourActivities();
    }
}
