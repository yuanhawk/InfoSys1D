package tech.sutd.pickupgame.data.ui.past_activity;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import tech.sutd.pickupgame.data.AppExecutors;
import tech.sutd.pickupgame.models.ui.PastActivity;

public class PastRepository {

    private final PastDao pastDao;
    private final LiveData<List<PastActivity>> allPastActivities;

    public PastRepository(Application application) {
        PastDatabase database = PastDatabase.getInstance(application);
        pastDao = database.pastDao();
        allPastActivities = pastDao.getPastActivities();
    }

    public void insert(PastActivity pastActivity) {
        new InsertPastActivityExecutor(pastDao).execute(pastActivity);
    }

    public void update(PastActivity pastActivity) {
        new UpdatePastActivityExecutor(pastDao).execute(pastActivity);
    }

    public void delete(PastActivity pastActivity) {
        new DeletePastActivityExecutor(pastDao).execute(pastActivity);
    }

    public void deleteAllPastActivities() {
        new DeleteAllPastActivitiesExecutor(pastDao).execute();
    }

    public LiveData<List<PastActivity>> getAllPastActivities() {
        return allPastActivities;
    }

    private static class InsertPastActivityExecutor {
        private PastDao pastDao;

        public InsertPastActivityExecutor(PastDao pastDao) {
            this.pastDao = pastDao;
        }

        public void execute(PastActivity pastActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    pastDao.insert(pastActivity));
        }
    }

    private static class UpdatePastActivityExecutor {
        private PastDao pastDao;

        public UpdatePastActivityExecutor(PastDao pastDao) {
            this.pastDao = pastDao;
        }

        public void execute(PastActivity pastActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    pastDao.update(pastActivity));
        }
    }

    private static class DeletePastActivityExecutor {
        private PastDao pastDao;

        public DeletePastActivityExecutor(PastDao pastDao) {
            this.pastDao = pastDao;
        }

        public void execute(PastActivity pastActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    pastDao.delete(pastActivity));
        }
    }

    private static class DeleteAllPastActivitiesExecutor {
        private PastDao pastDao;

        public DeleteAllPastActivitiesExecutor(PastDao pastDao) {
            this.pastDao = pastDao;
        }

        public void execute() {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    pastDao.deleteAllPastActivities());
        }
    }

}
