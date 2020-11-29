package tech.sutd.pickupgame.data.ui.helper;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import tech.sutd.pickupgame.models.ui.NewActivity;

public interface NewRoomHelper {

    Completable insertNewActivity(NewActivity newActivity);
    Completable deleteNewActivityByClock(String clock);
    Flowable<PagedList<NewActivity>> getAllNewActivitiesByClock();
    Flowable<PagedList<NewActivity>> getAllNewActivitiesBySport();
    Flowable<PagedList<NewActivity>> getNewActivitiesByClock2();

}
