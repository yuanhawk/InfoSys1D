package tech.sutd.pickupgame.data;

import androidx.lifecycle.LiveData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import tech.sutd.pickupgame.data.ui.helper.UserRoomHelper;
import tech.sutd.pickupgame.data.ui.helper.YourRoomHelper;
import tech.sutd.pickupgame.models.User;
import tech.sutd.pickupgame.models.ui.YourActivity;

public class AppDataManager implements DataManager {

    private final YourRoomHelper yourRoomHelper;
    private final UserRoomHelper userRoomHelper;

    @Inject
    public AppDataManager(YourRoomHelper yourRoomHelper, UserRoomHelper userRoomHelper) {
        this.yourRoomHelper = yourRoomHelper;
        this.userRoomHelper = userRoomHelper;
    }

    @Override
    public Completable insertYourActivity(YourActivity activity) {
        return yourRoomHelper.insertYourActivity(activity);
    }

    @Override
    public Completable deleteAllYourActivity() {
        return yourRoomHelper.deleteAllYourActivity();
    }

    @Override
    public Flowable<List<YourActivity>> getAllActivities() {
        return yourRoomHelper.getAllActivities();
    }


    @Override
    public Completable insertUser(User user) {
        return userRoomHelper.insertUser(user);
    }

    @Override
    public Completable deleteAll() {
        return userRoomHelper.deleteAll();
    }

    @Override
    public LiveData<List<User>> getUsers() {
        return userRoomHelper.getUsers();
    }
}
