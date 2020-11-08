package tech.sutd.pickupgame.data.ui.your_activity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tech.sutd.pickupgame.models.ui.YourActivity;

@Database(entities = {YourActivity.class}, version = 1, exportSchema = false)
public abstract class YourDatabase extends RoomDatabase {

    public static YourDatabase instance;

    private static final Object LOCK = new Object();

    public abstract YourDao yourDao();

    public static synchronized YourDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        YourDatabase.class, "your_activities_database")
                        .build();
            }
        }
        return instance;
    }
}
