package tech.sutd.pickupgame.data.ui.new_activity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tech.sutd.pickupgame.data.ui.upcoming_activity.UpcomingDao;
import tech.sutd.pickupgame.data.ui.upcoming_activity.UpcomingDatabase;
import tech.sutd.pickupgame.models.ui.NewActivity;

@Database(entities = {NewActivity.class}, version = 1)
public abstract class NewDatabase extends RoomDatabase {

    public static NewDatabase instance;

    private static final Object LOCK = new Object();

    public abstract NewDao newDao();

    public static synchronized NewDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        NewDatabase.class, "new_activities_database")
                        .build();
            }
        }
        return instance;
    }
}
