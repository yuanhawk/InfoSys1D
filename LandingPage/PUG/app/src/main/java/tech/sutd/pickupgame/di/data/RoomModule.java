package tech.sutd.pickupgame.di.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import dagger.Module;
import dagger.Provides;
import tech.sutd.pickupgame.data.AppExecutors;
import tech.sutd.pickupgame.data.DataManager;
import tech.sutd.pickupgame.data.ui.helper.UserRoomHelper;
import tech.sutd.pickupgame.data.ui.new_activity.NewDatabase;
import tech.sutd.pickupgame.data.ui.new_activity.NewRepository;
import tech.sutd.pickupgame.data.ui.user.UserDao;
import tech.sutd.pickupgame.data.ui.user.UserDatabase;
import tech.sutd.pickupgame.data.ui.user.UserRoom;
import tech.sutd.pickupgame.data.ui.your_activity.YourRoom;
import tech.sutd.pickupgame.data.AppDataManager;
import tech.sutd.pickupgame.data.ui.helper.YourRoomHelper;
import tech.sutd.pickupgame.data.ui.your_activity.YourDatabase;
import tech.sutd.pickupgame.data.ui.helper.NewRoomHelper;
import tech.sutd.pickupgame.models.User;

@Module
public class RoomModule {

    private static UserDatabase userDatabase;

    @Provides
    static NewRoomHelper provideNewRoomHelper(NewRepository repository) {
        return repository;
    }

    @NonNull
    @Provides
    static NewDatabase provideNewDatabase(Application application) {
        return Room.databaseBuilder(application,
                NewDatabase.class, "new_activities_database")
                .build();
    }

    @NonNull
    @Provides
    static YourDatabase provideYourDatabase(Application application) {
        return Room.databaseBuilder(application,
                YourDatabase.class, "your_activities_database")
                .build();
    }

    @NonNull
    @Provides
    static UserDatabase provideUserDatabase(Application application) {
        userDatabase = Room.databaseBuilder(application,
                UserDatabase.class, "user_database")
                .fallbackToDestructiveMigration()
                .addCallback(roomCallback)
                .build();
        return userDatabase;
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

    @Provides
    static DataManager provideDataManager(YourRoomHelper yourRoomHelper, UserRoomHelper userRoomHelper) {
        return new AppDataManager(yourRoomHelper, userRoomHelper);
    }

    @Provides
    static YourRoomHelper provideYourRoomHelper(YourRoom helper) {
        return helper;
    }

    @Provides
    static UserRoomHelper provideUserRoomHelper(UserRoom helper) {
        return helper;
    }

}
