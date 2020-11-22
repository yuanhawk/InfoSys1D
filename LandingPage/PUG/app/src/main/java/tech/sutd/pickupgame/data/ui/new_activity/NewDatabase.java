package tech.sutd.pickupgame.data.ui.new_activity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tech.sutd.pickupgame.data.ui.upcoming_activity.UpcomingDao;
import tech.sutd.pickupgame.data.ui.upcoming_activity.UpcomingDatabase;
import tech.sutd.pickupgame.models.ui.NewActivity;

@Database(entities = {NewActivity.class}, version = 1, exportSchema = false)
public abstract class NewDatabase extends RoomDatabase {

    public abstract NewDao newDao();
}
