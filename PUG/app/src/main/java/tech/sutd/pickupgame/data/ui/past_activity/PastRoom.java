package tech.sutd.pickupgame.data.ui.past_activity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import tech.sutd.pickupgame.data.ui.helper.PastRoomHelper;
import tech.sutd.pickupgame.models.ui.PastActivity;

public class PastRoom implements PastRoomHelper {

    private final PastDatabase database;

    @Inject
    public PastRoom(PastDatabase database) {
        this.database = database;
    }

    @Override
    public Completable insertPastActivity(PastActivity pastActivity) {
        return database.pastDao().insert(pastActivity);
    }

    @Override
    public Completable deleteAllPastActivities() {
        return database.pastDao().deleteAllPastActivities();
    }

    @Override
    public Flowable<List<PastActivity>> getAllPastActivities() {
        return database.pastDao().getAllPastActivities();
    }

}
