package tech.sutd.pickupgame.data.ui.new_activity;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import tech.sutd.pickupgame.data.AppExecutors;
import tech.sutd.pickupgame.data.ui.upcoming_activity.UpcomingDatabase;
import tech.sutd.pickupgame.data.ui.upcoming_activity.UpcomingRepository;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.NewActivity;

public class NewRepository {

    private NewDao newDao;
    private LiveData<List<NewActivity>> allNewActivities;

    public NewRepository(Application application) {
        NewDatabase database = NewDatabase.getInstance(application);
        newDao = database.newDao();
        allNewActivities = newDao.getNewActivities();
    }

    public void insert(NewActivity newActivity) {
        new NewRepository.InsertNewActivityExecutor(newDao).execute(newActivity);
    }

    public void update(NewActivity newActivity) {
        new NewRepository.UpdateNewActivityExecutor(newDao).execute(newActivity);
    }

    public void delete(NewActivity newActivity) {
        new NewRepository.DeleteNewActivityExecutor(newDao).execute(newActivity);
    }

    public void deleteAllNewActivities() {
        new NewRepository.DeleteAllNewActivitiesExecutor(newDao).execute();
    }

    public LiveData<List<NewActivity>> getAllNewActivities() {
        return allNewActivities;
    }

    private static class InsertNewActivityExecutor {
        private NewDao newDao;

        public InsertNewActivityExecutor(NewDao newDao) {
            this.newDao = newDao;
        }

        public void execute(NewActivity newActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    newDao.insert(newActivity));
        }
    }

    private static class UpdateNewActivityExecutor {
        private NewDao newDao;

        public UpdateNewActivityExecutor(NewDao newDao) {
            this.newDao = newDao;
        }

        public void execute(NewActivity newActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    newDao.update(newActivity));
        }
    }

    private static class DeleteNewActivityExecutor {
        private NewDao newDao;

        public DeleteNewActivityExecutor(NewDao newDao) {
            this.newDao = newDao;
        }

        public void execute(NewActivity newActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    newDao.delete(newActivity));
        }
    }

    private static class DeleteAllNewActivitiesExecutor {
        private NewDao newDao;

        public DeleteAllNewActivitiesExecutor(NewDao newDao) {
            this.newDao = newDao;
        }

        public void execute() {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    newDao.deleteAllNewActivities());
        }
    }
}
