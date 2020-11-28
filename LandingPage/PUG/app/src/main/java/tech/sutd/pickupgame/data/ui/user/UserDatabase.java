package tech.sutd.pickupgame.data.ui.user;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import tech.sutd.pickupgame.data.AppExecutors;
import tech.sutd.pickupgame.models.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
