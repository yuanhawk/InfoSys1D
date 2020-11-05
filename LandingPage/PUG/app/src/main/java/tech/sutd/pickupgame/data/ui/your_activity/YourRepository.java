package tech.sutd.pickupgame.data.ui.your_activity;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import tech.sutd.pickupgame.data.AppExecutors;
import tech.sutd.pickupgame.models.ui.NewActivity;
import tech.sutd.pickupgame.models.ui.YourActivity;

public class YourRepository {

    private YourDao yourDao;
    private LiveData<List<YourActivity>> allYourActivities;

    public YourRepository(Application application) {
        YourDatabase database = YourDatabase.getInstance(application);
        yourDao = database.yourDao();
        allYourActivities = yourDao.getYourActivities();
    }

    public void insert(YourActivity yourActivity) {
        new InsertYourActivityExecutor(yourDao).execute(yourActivity);
    }

    public void update(YourActivity yourActivity) {
        new UpdateYourActivityExecutor(yourDao).execute(yourActivity);
    }

    public void delete(YourActivity yourActivity) {
        new DeleteYourActivityExecutor(yourDao).execute(yourActivity);
    }

    public void deleteAllYourActivitiesExecutor() {
        new DeleteAllYourActivitiesExecutor(yourDao).execute();
    }

    public LiveData<List<YourActivity>> getAllYourActivities() {
        return allYourActivities;
    }

    private static class InsertYourActivityExecutor {
        private YourDao yourDao;

        public InsertYourActivityExecutor(YourDao yourDao) {
            this.yourDao = yourDao;
        }

        public void execute(YourActivity yourActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    yourDao.insert(yourActivity));
        }
    }

    private static class UpdateYourActivityExecutor {
        private YourDao yourDao;

        public UpdateYourActivityExecutor(YourDao yourDao) {
            this.yourDao = yourDao;
        }

        public void execute(YourActivity yourActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    yourDao.update(yourActivity));
        }
    }

    private static class DeleteYourActivityExecutor {
        private YourDao yourDao;

        public DeleteYourActivityExecutor(YourDao yourDao) {
            this.yourDao = yourDao;
        }

        public void execute(YourActivity yourActivity) {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    yourDao.delete(yourActivity));
        }
    }

    private static class DeleteAllYourActivitiesExecutor {
        private YourDao yourDao;

        public DeleteAllYourActivitiesExecutor(YourDao yourDao) {
            this.yourDao = yourDao;
        }

        public void execute() {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    yourDao.deleteYourActivities());
        }
    }
}
