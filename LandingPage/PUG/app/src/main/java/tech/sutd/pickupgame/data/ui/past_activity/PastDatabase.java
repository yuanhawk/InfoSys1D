package tech.sutd.pickupgame.data.ui.past_activity;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import tech.sutd.pickupgame.models.ui.PastActivity;
import tech.sutd.pickupgame.models.ui.UpcomingActivity;

@Database(entities = {PastActivity.class}, version = 1, exportSchema = false)
public abstract class PastDatabase extends RoomDatabase {

    public abstract PastDao pastDao();
}
