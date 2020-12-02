package tech.sutd.pickupgame.data.ui.upcoming_activity;

import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import tech.sutd.pickupgame.data.ui.helper.UpcomingRoomHelper;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

public class UpcomingRoom implements UpcomingRoomHelper {

    private final UpcomingDatabase database;

    @Inject
    public UpcomingRoom(UpcomingDatabase database) {
        this.database = database;
    }

    @Override
    public Completable insertUpcomingActivity(UpcomingActivity upcomingActivity) {
        return database.upcomingDao().insertUpcomingActivity(upcomingActivity);
    }

    @Override
    public Completable deleteUpcomingActivities(String clock) {
        return database.upcomingDao().deleteUpcomingActivity(clock);
    }

    @Override
    public Completable deleteUpcomingActivitiesById(String id) {
        return database.upcomingDao().deleteUpcomingActivityById(id);
    }

    @Override
    public Flowable<PagedList<UpcomingActivity>> getAllUpcomingActivitiesByClock() {
        return new RxPagedListBuilder<>(
                database.upcomingDao().getAllUpcomingActivitiesByClock(), 20
        ).buildFlowable(BackpressureStrategy.DROP);
    }

    @Override
    public Flowable<PagedList<UpcomingActivity>> getUpcomingActivitiesByClockLimit2() {
        return new RxPagedListBuilder<>(
                database.upcomingDao().getUpcomingActivitiesByClockLimit2(), 2
        ).buildFlowable(BackpressureStrategy.BUFFER);
    }


}
