package tech.sutd.pickupgame.di.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.data.AppExecutors;
import tech.sutd.pickupgame.data.ui.new_activity.NewDatabase;
import tech.sutd.pickupgame.data.ui.past_activity.PastDatabase;
import tech.sutd.pickupgame.data.ui.upcoming_activity.UpcomingDatabase;
import tech.sutd.pickupgame.data.ui.user.UserDao;
import tech.sutd.pickupgame.data.ui.user.UserDatabase;
import tech.sutd.pickupgame.data.ui.your_activity.YourDatabase;
import tech.sutd.pickupgame.models.User;

@Module
public class RoomDatabaseModule {

    private static UserDatabase userDatabase;

    @Singleton
    @NonNull
    @Provides
    static NewDatabase provideNewDatabase(Application application) {
        return Room.databaseBuilder(application,
                NewDatabase.class, "new_activities_database")
                .build();
    }

    @Singleton
    @NonNull
    @Provides
    static YourDatabase provideYourDatabase(Application application) {
        return Room.databaseBuilder(application,
                YourDatabase.class, "your_activities_database")
                .build();
    }

    @Singleton
    @NonNull
    @Provides
    static UserDatabase provideUserDatabase(Application application) {
        return Room.databaseBuilder(application,
                UserDatabase.class, "user_database")
                .fallbackToDestructiveMigration()
                .addCallback(roomCallback)
                .build();
    }

    @Singleton
    @NonNull
    @Provides
    static UpcomingDatabase provideUpcomingDatabase(Application application) {
        return Room.databaseBuilder(application,
                UpcomingDatabase.class, "upcoming_activities_database")
                .build();
    }

    @Singleton
    @NonNull
    @Provides
    static PastDatabase providePastDatabase(Application application) {
        return Room.databaseBuilder(application,
                PastDatabase.class, "past_activities_database")
                .build();
    }

    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbExecutor(userDatabase).execute();
        }
    };

    private static class PopulateDbExecutor {
        private final UserDao userDao;

        public PopulateDbExecutor(UserDatabase db) {
            userDao = db.userDao();
        }

        private void execute() {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    userDao.insertUser(User.defaultUser()));
        }
    }
}
