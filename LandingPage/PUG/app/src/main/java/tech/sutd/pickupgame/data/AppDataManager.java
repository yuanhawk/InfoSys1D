package tech.sutd.pickupgame.data;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.ui.helper.YourRoomHelper;
import tech.sutd.pickupgame.models.ui.YourActivity;

public class AppDataManager implements DataManager {

    private final YourRoomHelper yourRoomHelper;

    @Inject
    public AppDataManager(YourRoomHelper yourRoomHelper) {
        this.yourRoomHelper = yourRoomHelper;
    }

    @Override
    public Completable insertYourActivity(YourActivity activity) {
        return yourRoomHelper.insertYourActivity(activity);
    }

    @Override
    public Completable deleteAll() {
        return yourRoomHelper.deleteAll();
    }

    @Override
    public Flowable<List<YourActivity>> getAllActivities() {
        return yourRoomHelper.getAllActivities();
    }
}
