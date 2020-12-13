package tech.sutd.pickupgame.data.ui.your_activity;


import androidx.room.Database;
import androidx.room.RoomDatabase;

import tech.sutd.pickupgame.models.ui.YourActivity;

@Database(entities = {YourActivity.class}, version = 1, exportSchema = false)
public abstract class YourDatabase extends RoomDatabase {

    public abstract YourDao yourDao();
}
