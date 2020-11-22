package tech.sutd.pickupgame.data.ui.upcoming_activity;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import tech.sutd.pickupgame.data.AppExecutors;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

public class UpcomingRepository {

    private final UpcomingDao upcomingDao;
    private final LiveData<PagedList<UpcomingActivity>> allUpcomingActivitiesByClock, upcomingActivitiesByClockTwo;

    public UpcomingRepository(Application application) {
        UpcomingDatabase database = UpcomingDatabase.getInstance(application);
        upcomingDao = database.upcomingDao();
        allUpcomingActivitiesByClock = new LivePagedListBuilder<>(
                upcomingDao.getAllUpcomingActivitiesByClock(), 20
        ).build();
        upcomingActivitiesByClockTwo = new LivePagedListBuilder<>(
                upcomingDao.getUpcomingActivitiesByClockLimit2(), 2
        ).build();
    }

    public void insert(UpcomingActivity upcomingActivity) {
        new InsertUpcomingActivityExecutor(upcomingDao).execute(upcomingActivity);
    }

    public void update(UpcomingActivity upcomingActivity) {
        new UpdateUpcomingActivityExecutor(upcomingDao).execute(upcomingActivity);
    }

    public void delete(UpcomingActivity upcomingActivity) {
        new DeleteUpcomingActivityExecutor(upcomingDao).execute(upcomingActivity);
    }

    public void deleteAllUpcomingActivities() {
        new DeleteAllUpcomingActivitiesExecutor(upcomingDao).execute();
    }

    public LiveData<PagedList<UpcomingActivity>> getAllUpcomingActivitiesByClock() {
        return allUpcomingActivitiesByClock;
    }

    public LiveData<PagedList<UpcomingActivity>> getUpcomingActivitiesByClockTwo() {
        return upcomingActivitiesByClockTwo;
    }

    private static class InsertUpcomingActivityExecutor {
        private UpcomingDao upcomingDao;

        public InsertUpcomingActivityExecutor(UpcomingDao upcomingDao) {
            this.upcomingDao = upcomingDao;
        }

        public void execute(UpcomingActivity upcomingActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    upcomingDao.insert(upcomingActivity));
        }
    }

    private static class UpdateUpcomingActivityExecutor {
        private UpcomingDao upcomingDao;

        public UpdateUpcomingActivityExecutor(UpcomingDao upcomingDao) {
            this.upcomingDao = upcomingDao;
        }

        public void execute(UpcomingActivity upcomingActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    upcomingDao.update(upcomingActivity));
        }
    }

    private static class DeleteUpcomingActivityExecutor {
        private UpcomingDao upcomingDao;

        public DeleteUpcomingActivityExecutor(UpcomingDao upcomingDao) {
            this.upcomingDao = upcomingDao;
        }

        public void execute(UpcomingActivity upcomingActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    upcomingDao.delete(upcomingActivity));
        }
    }

    private static class DeleteAllUpcomingActivitiesExecutor {
        private UpcomingDao upcomingDao;

        public DeleteAllUpcomingActivitiesExecutor(UpcomingDao upcomingDao) {
            this.upcomingDao = upcomingDao;
        }

        public void execute() {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    upcomingDao.deleteAllUpcomingActivities());
        }
    }

}
