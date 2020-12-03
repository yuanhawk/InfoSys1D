package tech.sutd.pickupgame.data.ui.new_activity;

import androidx.paging.PagedList;
import androidx.paging.RxPagedListBuilder;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import tech.sutd.pickupgame.data.ui.helper.NewRoomHelper;
import tech.sutd.pickupgame.models.ui.NewActivity;

public class NewRoom implements NewRoomHelper {

    private final NewDatabase database;

    @Inject
    public NewRoom(NewDatabase database) {
        this.database = database;
    }

    @Override
    public Completable insertNewActivity(NewActivity newActivity) {
        return database.newDao().insertNewActivity(newActivity);
    }

    @Override
    public Completable deleteNewActivityById(String id) {
        return database.newDao().deleteNewActivityById(id);
    }

    @Override
    public Completable deleteNewActivityByChecked(int checked) {
        return database.newDao().deleteNewActivityByChecked(checked);
    }

    @Override
    public Completable deleteNewActivityByClock(String clock) {
        return database.newDao().deleteNewActivityByClock(clock);
    }

    @Override
    public Flowable<PagedList<NewActivity>> getAllNewActivitiesByClock() {
        return new RxPagedListBuilder<>(
                database.newDao().getAllNewActivitiesByClock(), 20
        ).buildFlowable(BackpressureStrategy.DROP);
    }

    @Override
    public Flowable<PagedList<NewActivity>> getAllNewActivitiesBySport() {
        return new RxPagedListBuilder<>(
                database.newDao().getAllNewActivitiesBySport(), 20
        ).buildFlowable(BackpressureStrategy.DROP);
    }

    @Override
    public Flowable<PagedList<NewActivity>> getNewActivitiesByClock2() {
        return new RxPagedListBuilder<>(
                database.newDao().getNewActivitiesByClockLimit2(), 2
        ).buildFlowable(BackpressureStrategy.BUFFER);
    }
}
