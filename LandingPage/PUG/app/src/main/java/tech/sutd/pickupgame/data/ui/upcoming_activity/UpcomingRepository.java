package tech.sutd.pickupgame.data.ui.upcoming_activity;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import tech.sutd.pickupgame.data.AppExecutors;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

public class UpcomingRepository {

    private UpcomingDao upcomingDao;
    private LiveData<List<UpcomingActivity>> allUpcomingActivities;

    public UpcomingRepository(Application application) {
        UpcomingDatabase database = UpcomingDatabase.getInstance(application);
        upcomingDao = database.upcomingDao();
        allUpcomingActivities = upcomingDao.getUpcomingActivities();
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

    public LiveData<List<UpcomingActivity>> getAllUpcomingActivities() {
        return allUpcomingActivities;
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
