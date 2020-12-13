package tech.sutd.pickupgame.data.ui.upcoming_activity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tech.sutd.pickupgame.models.ui.UpcomingActivity;

@Database(entities = {UpcomingActivity.class}, version = 1, exportSchema = false)
public abstract class UpcomingDatabase extends RoomDatabase {

    public abstract UpcomingDao upcomingDao();
}
