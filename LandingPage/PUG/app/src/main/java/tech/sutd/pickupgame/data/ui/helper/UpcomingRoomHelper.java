package tech.sutd.pickupgame.data.ui.helper;

import androidx.paging.DataSource;
import androidx.paging.PagedList;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

public interface UpcomingRoomHelper {

    Completable insertUpcomingActivity(UpcomingActivity upcomingActivity);
    Completable deleteUpcomingActivities(String clock);
    Flowable<PagedList<UpcomingActivity>> getAllUpcomingActivitiesByClock();
    Flowable<PagedList<UpcomingActivity>> getUpcomingActivitiesByClockLimit2();

}
