package tech.sutd.pickupgame.data.ui.new_activity;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import tech.sutd.pickupgame.data.AppExecutors;
import tech.sutd.pickupgame.models.ui.NewActivity;

public class NewRepository {

    private NewDao newDao;
    private LiveData<PagedList<NewActivity>> allNewActivitiesByClock, allNewActivitiesBySport, newActivitiesByClock2;

    public NewRepository(Application application) {
        NewDatabase database = NewDatabase.getInstance(application);
        newDao = database.newDao();
        allNewActivitiesByClock = new LivePagedListBuilder<>(
                newDao.getAllNewActivitiesByClock(), 20
        ).build();
        allNewActivitiesBySport = new LivePagedListBuilder<>(
                newDao.getAllNewActivitiesBySport(), 20
        ).build();
        newActivitiesByClock2 = new LivePagedListBuilder<>(
                newDao.getNewActivitiesByClockLimit2(), 2
        ).build();
    }

    public void insert(NewActivity newActivity) {
        new NewRepository.InsertNewActivityExecutor(newDao).execute(newActivity);
    }

    public void update(NewActivity newActivity) {
        new NewRepository.UpdateNewActivityExecutor(newDao).execute(newActivity);
    }

    public void delete(String clock) {
        new NewRepository.DeleteNewActivityExecutor(newDao).execute(clock);
    }

    public void deleteAllNewActivities() {
        new NewRepository.DeleteAllNewActivitiesExecutor(newDao).execute();
    }

    public LiveData<PagedList<NewActivity>> getAllNewActivitiesByClock() {
        return allNewActivitiesByClock;
    }

    public LiveData<PagedList<NewActivity>> getAllNewActivitiesBySport() {
        return allNewActivitiesBySport;
    }

    public LiveData<PagedList<NewActivity>> getNewActivitiesByClock2() {
        return newActivitiesByClock2;
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

        public void execute(String clock) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    newDao.delete(clock));
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
