package tech.sutd.pickupgame.data.ui.helper;

import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import tech.sutd.pickupgame.models.ui.PastActivity;

public interface PastRoomHelper {

    Completable insertPastActivity(PastActivity pastActivity);
    Completable deleteAllPastActivities();
    Flowable<List<PastActivity>> getAllPastActivities();

}
