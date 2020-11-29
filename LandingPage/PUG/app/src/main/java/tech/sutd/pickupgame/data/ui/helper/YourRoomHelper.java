package tech.sutd.pickupgame.data.ui.helper;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import tech.sutd.pickupgame.models.ui.YourActivity;

public interface YourRoomHelper {

    Completable insertYourActivity(YourActivity yourActivities);

    Completable deleteAllYourActivities();

    Flowable<List<YourActivity>> getAllYourActivities();

}