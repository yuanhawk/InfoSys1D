package tech.sutd.pickupgame.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import tech.sutd.pickupgame.models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public static UserDatabase instance;

    private static final Object LOCK = new Object();

    public abstract UserDao userDao();

    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        UserDatabase.class, "user_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbExecutor(instance).execute();
        }
    };

    private static class PopulateDbExecutor {
        private final UserDao userDao;

        public PopulateDbExecutor(UserDatabase db) {
            userDao = db.userDao();
        }

        private void execute() {
            AppExecutors.getInstance().getDiskIO().execute(() ->
                    userDao.insert(User.defaultUser()));
        }
    }
}
